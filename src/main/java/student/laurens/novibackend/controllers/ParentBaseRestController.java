package student.laurens.novibackend.controllers;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import student.laurens.novibackend.entities.*;
import student.laurens.novibackend.exceptions.ResourceNotFoundException;
import student.laurens.novibackend.exceptions.ResourceNotOwnedException;
import student.laurens.novibackend.services.BaseService;

/**
 * Base class for RestControllers which expose CRUD methods for {@link AbstractOwnedWithParentEntity}.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
public abstract class ParentBaseRestController<R extends AbstractOwnedWithParentEntity, P extends AbstractOwnedParentEntity>
        extends BaseRestController<R>{

    abstract protected BaseService<P> getParentService();

    public ResponseEntity<R> update(final Integer parentResourceId, final Integer resourceId, final R resource) throws ResourceNotFoundException, ResourceNotOwnedException {
        log.info("Processing [PUT] request with parentResourceId ["+parentResourceId+"] and resourceId ["+resourceId+"] started");
        P parentResource = getParentService().getResourceById(parentResourceId); // EXISTS check

        PermissionPolicy childPolicy = parentResource.isUpdateOnChildPermitted(getConsumer(), resource);
        validatePermissionPolicy(parentResourceId, resource, resourceId, childPolicy, HttpMethod.PUT);

        getService().updateResourceById(resourceId, resource);

        log.info("Processing [PUT] request with parentResourceId ["+parentResourceId+"] and resourceId ["+resourceId+"] successfully finished");
        return new ResponseEntity<>(resource, HttpStatus.ACCEPTED);
    }

    public ResponseEntity<R> delete(final Integer parentResourceId, final Integer resourceId) throws ResourceNotFoundException, ResourceNotOwnedException {
        log.info("Processing [DELETE] request with parentResourceId ["+parentResourceId+"] and resourceId ["+resourceId+"]");
        P parentResource = getParentService().getResourceById(parentResourceId); // EXISTS check
        R resource = getService().getResourceById(resourceId);

        PermissionPolicy childPolicy = parentResource.isDeleteOnChildPermitted(getConsumer(), resource);
        validatePermissionPolicy(parentResourceId, resource, resourceId, childPolicy, HttpMethod.DELETE);

        log.info("Processing [DELETE] request with parentResourceId ["+parentResourceId+"] and resourceId ["+resourceId+"] successfully finished");
        return new ResponseEntity(createDeletedMessage(), HttpStatus.ACCEPTED);
    }

    protected void validatePermissionPolicy(final Integer parentResourceId, final R resource,
          final Integer resourceId, final PermissionPolicy policy, HttpMethod method) throws ResourceNotFoundException, ResourceNotOwnedException {
        log.info("Checking policy ["+policy+"]");

        if(policy.equals(PermissionPolicy.ALLOW)){
            log.info("Policy ["+policy+"], validation complete.");
            return;
        }

        if(policy.equals(PermissionPolicy.ALLOW_CHILD_OWNED)){
            log.info("Policy ["+policy+"], validation of child ownership started.");
            validateOwnershipOfResource(resourceId, method);
            log.info("Policy ["+policy+"], validation of child ownership completed.");
        }
        else if(policy.equals(PermissionPolicy.ALLOW_PARENT_OR_CHILD_OWNED)) {
            try {
                log.info("Policy ["+policy+"], validation of parent ownership started.");
                validateOwnershipOfParentResource(parentResourceId, method);
                log.info("Policy ["+policy+"], validation of parent ownership completed.");
            } catch (ResourceNotOwnedException e){
                log.info("Parent resource ["+parentResourceId+"] not owned, validation of child ownership started.");
                validateOwnershipOfResource(resourceId, method);
                log.info("Parent resource ["+parentResourceId+"] not owned, validation of child ownership completed.");
            }
        } else if(policy.equals(PermissionPolicy.ALLOW_PARENT_OWNED)){
            log.info("Policy ["+policy+"], validation of parent ownership started.");
            validateOwnershipOfParentResource(parentResourceId, method);
            log.info("Policy ["+policy+"], validation of parent ownership completed.");
        } else if(policy.equals(PermissionPolicy.DENY)){
            log.info("Policy ["+policy+"], access is denied.");
            // TODO: Change this to resource denied exception
            throw new ResourceNotOwnedException(resource.getClass(), resourceId);
        }
    }

    protected void validateOwnershipOfParentResource(final Integer parentResourceId, final HttpMethod method) throws ResourceNotFoundException, ResourceNotOwnedException  {
        Class<P> parentResourceClass = getParentService().getResourceClass();

        if(AbstractOwnedEntity.class.isAssignableFrom(parentResourceClass) && isMethodOwnershipProtected(method)){
            log.info("Checking if allowed to ["+method+"] AbstractOwnedEntity ["+parentResourceClass+"] with identifier ["+parentResourceId+"]");
            User consumer = getConsumer();
            AbstractOwnedEntity ownedResource = getParentService().getResourceById(parentResourceId);

            if(ownedResource.getOwnerUid() != consumer.getUid() && !consumer.hasRole("ADMIN") ){
                log.warn("User ["+consumer.getUid()+"] tried to ["+method+"] a forbidden ["+parentResourceClass+"] with identifier ["+parentResourceId+"]");
                throw new ResourceNotOwnedException(parentResourceClass, parentResourceId);
            }
        }
    }

}
