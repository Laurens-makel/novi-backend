package student.laurens.novibackend.services;

import org.springframework.http.HttpMethod;
import student.laurens.novibackend.entities.AbstractEntity;
import student.laurens.novibackend.entities.AbstractOwnedEntity;
import student.laurens.novibackend.entities.User;
import student.laurens.novibackend.exceptions.ResourceNotFoundException;
import student.laurens.novibackend.exceptions.ResourceForbiddenException;

public abstract class ChildBaseService <R extends AbstractEntity, P extends AbstractEntity> extends BaseService<R> {

    abstract protected ParentBaseService<P> getParentService();

    /**
     * Retrieves a resource from repository, specified by resource id.
     *
     * @param parentResourceId - Identifier of the parent resource validate ownership of.
     * @param resourceId - Identifier of the resource to RETRIEVE.
     * @param consumer - User which has the intention of interacting with the resource.
     *
     * @throws ResourceNotFoundException - Thrown when resource could not be found.
     */
    public R getResourceById(final Integer parentResourceId, final Integer resourceId, final User consumer) throws ResourceNotFoundException {
        getParentService().exists(parentResourceId);
        exists(resourceId);
        validateOwnershipOfResources(parentResourceId, resourceId, HttpMethod.GET, consumer);
        return getRepository().getOne(resourceId);
    }

    /**
     * Retrieves a list of resources from repository.
     *
     * @param parentResourceId - Identifier of the parent resource validate ownership of.
     * @param consumer - User which has the intention of interacting with the resource.
     *
     * @throws ResourceNotFoundException - Thrown when resource could not be found.
     */
    public Iterable<R> getResources(final Integer parentResourceId, final User consumer) {
        getParentService().exists(parentResourceId);
        validateOwnershipOfResources(parentResourceId, null, HttpMethod.GET, consumer);
        return getRepository().findAll();
    }
    /**
     * Retrieves a resource from repository, specified by resource id.
     *
     * @param parentResourceId - Identifier of the parent resource validate ownership of.
     * @param resource - The state of the resource to save.
     * @param consumer - User which has the intention of interacting with the resource.
     *
     * @throws ResourceNotFoundException - Thrown when resource could not be found.
     */
    public R createResource(final Integer parentResourceId, final R resource, final User consumer) throws ResourceNotFoundException {
        getParentService().exists(parentResourceId);
        validateOwnershipOfResources(parentResourceId, null, HttpMethod.POST, consumer);
        return getRepository().save(resource);
    }

    /**
     * Updates a resource in repository, specified by resource id.
     *
     * @param parentResourceId - Identifier of the parent resource validate ownership of.
     * @param resourceId - Identifier of the resource to update.
     * @param resource - The state of the resource to save.
     * @param consumer - User which has the intention of interacting with the resource.
     *
     * @throws ResourceNotFoundException - Thrown when resource could not be found.
     */
    public R updateResourceById(final Integer parentResourceId, final Integer resourceId, final R resource, final User consumer) throws ResourceNotFoundException {
        getParentService().exists(parentResourceId);
        exists(resourceId);
        validateOwnershipOfResources(parentResourceId, resourceId, HttpMethod.PUT, consumer);
        return getRepository().save(resource);
    }

    /**
     * Deletes a resource from repository, specified by resource id.
     *
     * @param parentResourceId - Identifier of the parent resource validate ownership of.
     * @param resourceId - Identifier of the resource to delete.
     * @param consumer - User which has the intention of interacting with the resource.
     *
     * @throws ResourceNotFoundException - Thrown when parent or resource could not be found.
     * @throws ResourceForbiddenException - Thrown when parent or resource could is not owned by current consumer of API.
     */
    public void deleteResourceById(final Integer parentResourceId, final Integer resourceId, final User consumer) throws ResourceNotFoundException, ResourceForbiddenException {
        getParentService().exists(parentResourceId);
        exists(resourceId);
        validateOwnershipOfResources(parentResourceId, resourceId, HttpMethod.DELETE, consumer);
        getRepository().deleteById(resourceId);

    };

    /**
     * Validates if current consumer is the owner of R when if R is an instance of {@link AbstractOwnedEntity}, if corresponding HttpMethod is protected by ownerships.
     *
     * @param parentResourceId - Identifier of the parent resource validate ownership of.
     * @param resourceId - Identifier of the resource to validate ownership of
     * @param method - HttpMethod of the current action.
     * @param consumer - User which has the intention of interacting with the resource.
     *
     * @throws ResourceNotFoundException - Thrown when parent or resource could not be found.
     * @throws ResourceForbiddenException - Thrown when parent or resource could is not owned by current consumer of API.
     */
    public void validateOwnershipOfResources(final Integer parentResourceId, final Integer resourceId, final HttpMethod method, final User consumer) throws ResourceNotFoundException, ResourceForbiddenException {
        PermissionPolicy childPolicy;
        switch (method){
            case GET:
                childPolicy = getParentService().isReadOnChildPermitted(consumer);
                break;
            case POST:
                childPolicy = getParentService().isCreateChildPermitted(consumer);
                break;
            case PUT:
                childPolicy = getParentService().isUpdateOnChildPermitted(consumer);
                break;
            case DELETE:
                childPolicy = getParentService().isDeleteOnChildPermitted(consumer);
                break;
            default:
                childPolicy = PermissionPolicy.DENY;
        }
        validatePermissionPolicy(parentResourceId, resourceId, consumer, childPolicy, method);
    }

    /**
     * Executes {@link PermissionPolicy} policy validation.
     *
     * @param parentResourceId - Identifier of parent of resource.
     * @param resourceId - Identifier of resource.
     * @param consumer - User which has the intention to delete the child resource.
     * @param policy - {@link PermissionPolicy} to validate.
     * @param method - HttpMethod as intention of consumer.
     *
     * @throws ResourceNotFoundException - Thrown when parent or resource could not be found.
     * @throws ResourceForbiddenException - Thrown when parent or resource is not owned by current consumer of API.
     */
    protected void validatePermissionPolicy(final Integer parentResourceId, final Integer resourceId, final User consumer,
                                            final PermissionPolicy policy, HttpMethod method) throws ResourceNotFoundException, ResourceForbiddenException {
        log.info("Checking policy ["+policy+"]");

        if(policy.equals(PermissionPolicy.ALLOW)){
            log.info("Policy ["+policy+"], validation complete.");
            return;
        }

        if(policy.equals(PermissionPolicy.ALLOW_CHILD_OWNED)){
            log.info("Policy ["+policy+"], validation of child ownership started.");
            validateOwnershipOfResource(resourceId, method, consumer);
            log.info("Policy ["+policy+"], validation of child ownership completed.");
        }
        else if(policy.equals(PermissionPolicy.ALLOW_PARENT_OR_CHILD_OWNED)) {
            try {
                log.info("Policy ["+policy+"], validation of parent ownership started.");
                getParentService().validateOwnershipOfResource(parentResourceId, method, consumer);
                log.info("Policy ["+policy+"], validation of parent ownership completed.");
            } catch (ResourceForbiddenException e){
                log.info("Parent resource ["+parentResourceId+"] not owned, validation of child ownership started.");
                validateOwnershipOfResource(resourceId, method, consumer);
                log.info("Parent resource ["+parentResourceId+"] not owned, validation of child ownership completed.");
            }
        } else if(policy.equals(PermissionPolicy.ALLOW_PARENT_OWNED)){
            log.info("Policy ["+policy+"], validation of parent ownership started.");
            getParentService().validateOwnershipOfResource(parentResourceId, method, consumer);
            log.info("Policy ["+policy+"], validation of parent ownership completed.");
        } else if(policy.equals(PermissionPolicy.DENY)){
            log.info("Policy ["+policy+"], access is denied.");
            // TODO: Change this to resource denied exception
            throw new ResourceForbiddenException(getResourceClass(), resourceId);
        }
    }

}
