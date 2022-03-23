package student.laurens.novibackend.controllers;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import student.laurens.novibackend.entities.*;
import student.laurens.novibackend.exceptions.ResourceNotFoundException;
import student.laurens.novibackend.exceptions.ResourceNotOwnedException;
import student.laurens.novibackend.services.ParentBaseService;

/**
 * Base class for RestControllers which expose CRUD methods for {@link AbstractOwnedWithParentEntity}.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
public abstract class ParentBaseRestController<R extends AbstractEntity, P extends AbstractEntity>
        extends BaseRestController<R>{

    abstract protected ParentBaseService<P, R> getParentService();

    protected void logProcessingStarted(final HttpMethod method, final Integer parentResourceId, final Integer resourceId) {
        log.info("Processing ["+method+"] request on resource ["+getService().getResourceClass()+"] with parentResourceId ["+parentResourceId+"] and resourceId ["+resourceId+"] started.");
    }

    protected void logProcessingFinished(final HttpMethod method, final Integer parentResourceId, final Integer resourceId) {
        log.info("Processing ["+method+"] request on resource ["+getService().getResourceClass()+"] with parentResourceId ["+parentResourceId+"] and resourceId ["+resourceId+"] successfully finished.");
    }

    public ResponseEntity<R> update(final Integer parentResourceId, final Integer resourceId, final R resource) throws ResourceNotFoundException, ResourceNotOwnedException {
        logProcessingStarted(HttpMethod.PUT, parentResourceId, resourceId);
        getParentService().exists(parentResourceId);

        PermissionPolicy childPolicy = getParentService().isUpdateOnChildPermitted(getConsumer(), resource);
        validatePermissionPolicy(parentResourceId, resource, getConsumer(), resourceId, childPolicy, HttpMethod.PUT);

        getService().updateResourceById(resourceId, resource);

        logProcessingFinished(HttpMethod.PUT, parentResourceId, resourceId);
        return new ResponseEntity<>(resource, HttpStatus.ACCEPTED);
    }

    public ResponseEntity<R> delete(final Integer parentResourceId, final Integer resourceId) throws ResourceNotFoundException, ResourceNotOwnedException {
        logProcessingStarted(HttpMethod.DELETE, parentResourceId, resourceId);
        getParentService().exists(parentResourceId);
        R resource = getService().getResourceById(resourceId);

        PermissionPolicy childPolicy = getParentService().isDeleteOnChildPermitted(getConsumer(), resource);
        validatePermissionPolicy(parentResourceId, resource, getConsumer(), resourceId, childPolicy, HttpMethod.DELETE);

        logProcessingFinished(HttpMethod.DELETE, parentResourceId, resourceId);
        return new ResponseEntity(createDeletedMessage(), HttpStatus.ACCEPTED);
    }

    protected void validatePermissionPolicy(final Integer parentResourceId, final R resource, final User consumer,
          final Integer resourceId, final PermissionPolicy policy, HttpMethod method) throws ResourceNotFoundException, ResourceNotOwnedException {
        log.info("Checking policy ["+policy+"]");

        if(policy.equals(PermissionPolicy.ALLOW)){
            log.info("Policy ["+policy+"], validation complete.");
            return;
        }

        if(policy.equals(PermissionPolicy.ALLOW_CHILD_OWNED)){
            log.info("Policy ["+policy+"], validation of child ownership started.");
            getService().validateOwnershipOfResource(resourceId, method, consumer);
            log.info("Policy ["+policy+"], validation of child ownership completed.");
        }
        else if(policy.equals(PermissionPolicy.ALLOW_PARENT_OR_CHILD_OWNED)) {
            try {
                log.info("Policy ["+policy+"], validation of parent ownership started.");
                getParentService().validateOwnershipOfResource(parentResourceId, method, consumer);
                log.info("Policy ["+policy+"], validation of parent ownership completed.");
            } catch (ResourceNotOwnedException e){
                log.info("Parent resource ["+parentResourceId+"] not owned, validation of child ownership started.");
                getService().validateOwnershipOfResource(resourceId, method, consumer);
                log.info("Parent resource ["+parentResourceId+"] not owned, validation of child ownership completed.");
            }
        } else if(policy.equals(PermissionPolicy.ALLOW_PARENT_OWNED)){
            log.info("Policy ["+policy+"], validation of parent ownership started.");
            getParentService().validateOwnershipOfResource(parentResourceId, method, consumer);
            log.info("Policy ["+policy+"], validation of parent ownership completed.");
        } else if(policy.equals(PermissionPolicy.DENY)){
            log.info("Policy ["+policy+"], access is denied.");
            // TODO: Change this to resource denied exception
            throw new ResourceNotOwnedException(resource.getClass(), resourceId);
        }
    }

}
