package student.laurens.novibackend.services;

import org.springframework.http.HttpMethod;
import student.laurens.novibackend.entities.AbstractEntity;
import student.laurens.novibackend.entities.AbstractOwnedEntity;
import student.laurens.novibackend.entities.User;
import student.laurens.novibackend.exceptions.ResourceDuplicateException;
import student.laurens.novibackend.exceptions.ResourceNotFoundException;
import student.laurens.novibackend.exceptions.ResourceForbiddenException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public abstract class ChildResourceBaseService<R extends AbstractEntity, P extends AbstractEntity> extends BaseService<R> {

    abstract protected ParentResourceBaseService<P> getParentService();

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
        return validateOwnershipOfResources(parentResourceId, resourceId, HttpMethod.GET, consumer)
                .orElse(
                        getRepository()
                                .findById(resourceId)
                                .orElseThrow( () -> new ResourceNotFoundException(getResourceClass(), resourceId) )
                );
    }

    /**
     * Retrieves a list of resources from repository.
     *
     * @param parentResourceId - Identifier of the parent resource validate ownership of.
     * @param consumer - User which has the intention of interacting with the resource.
     *
     * @throws ResourceNotFoundException - Thrown when resource could not be found.
     */
    public List<R> getResources(final Integer parentResourceId, final User consumer) {
        getParentService().exists(parentResourceId);
        validateOwnershipOfResources(parentResourceId, null, HttpMethod.GET, consumer);

        Iterable<R> resourcesIterable = getRepository().findAll();

        return StreamSupport.stream(resourcesIterable.spliterator(), false)
                .collect(Collectors.toList());
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
    public R createResource(final Integer parentResourceId, final R resource, final User consumer) throws ResourceNotFoundException, ResourceDuplicateException {
        log.info("Processing started for create request for resource with parent ID ["+parentResourceId+"], request by ["+consumer.getUsername()+"]");
        getParentService().exists(parentResourceId);
        validateOwnershipOfResources(parentResourceId, null, HttpMethod.POST, consumer);
        R created = create(resource);
        log.info("Processing finished with success for create request for resource with parent ID ["+parentResourceId+"], request by ["+consumer.getUsername()+"]");
        return created;
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
        log.info("Processing started for update request for resourceId ["+resourceId+"] with parentResourceId ["+parentResourceId+"], request by ["+consumer.getUsername()+"]");
        getParentService().exists(parentResourceId);
        exists(resourceId);
        validateOwnershipOfResources(parentResourceId, resourceId, HttpMethod.PUT, consumer);
        log.info("Processing finished with success for update request for resourceId ["+resourceId+"] with parentResourceId ["+parentResourceId+"], request by ["+consumer.getUsername()+"]");
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
        log.info("Processing started for delete request for resourceId ["+resourceId+"] with parentResourceId ["+parentResourceId+"], request by ["+consumer.getUsername()+"]");
        getParentService().exists(parentResourceId);
        exists(resourceId);
        validateOwnershipOfResources(parentResourceId, resourceId, HttpMethod.DELETE, consumer);
        getRepository().deleteById(resourceId);
        log.info("Processing finished with success for delete request for resourceId ["+resourceId+"] with parentResourceId ["+parentResourceId+"], request by ["+consumer.getUsername()+"]");
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
    public Optional<R> validateOwnershipOfResources(final Integer parentResourceId, final Integer resourceId, final HttpMethod method, final User consumer) throws ResourceNotFoundException, ResourceForbiddenException {
        log.info("Checking which PermissionPolicy applies for ["+method+"] on resourceId ["+resourceId+"] and parentResourceId ["+parentResourceId+"], request by ["+consumer.getUsername()+"]");
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
        log.info("Finished checking, PermissionPolicy ["+childPolicy+"] applies for ["+method+"] on resourceId ["+resourceId+"] and parentResourceId ["+parentResourceId+"], request by ["+consumer.getUsername()+"]");
        return validatePermissionPolicy(parentResourceId, resourceId, consumer, childPolicy, method);
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
    protected Optional<R> validatePermissionPolicy(final Integer parentResourceId, final Integer resourceId, final User consumer,
                                                   final PermissionPolicy policy, HttpMethod method) throws ResourceNotFoundException, ResourceForbiddenException {
        log.info("Checking policy ["+policy+"]");
        Optional<R> optional = Optional.empty();

        switch(policy){
            case ALLOW:
                log.info("Policy ["+policy+"], validation complete.");
                optional = Optional.empty();
                break;
            case ALLOW_CHILD_OWNED:
                log.info("Policy ["+policy+"], validation of child ownership started.");
                optional = validateOwnershipOfResource(resourceId, method, consumer);
                log.info("Policy ["+policy+"], validation of child ownership completed.");
                break;
            case ALLOW_PARENT_OR_CHILD_OWNED:
                try {
                    log.info("Policy ["+policy+"], validation of parent ownership started.");
                    getParentService().validateOwnershipOfResource(parentResourceId, method, consumer);
                    log.info("Policy ["+policy+"], validation of parent ownership completed.");
                } catch (ResourceForbiddenException e){
                    log.info("Parent resource ["+parentResourceId+"] not owned, validation of child ownership started.");
                    optional = validateOwnershipOfResource(resourceId, method, consumer);
                    log.info("Parent resource ["+parentResourceId+"] not owned, validation of child ownership completed.");
                }
                break;
            case ALLOW_PARENT_OWNED:
                log.info("Policy ["+policy+"], validation of parent ownership started.");
                getParentService().validateOwnershipOfResource(parentResourceId, method, consumer);
                log.info("Policy ["+policy+"], validation of parent ownership completed.");
                break;
            case DENY:
                log.warn("Policy ["+policy+"], access is denied.");
                throw new ResourceForbiddenException(getResourceClass(), resourceId);
            default:
                return optional;
        }
        return optional;
    }

}
