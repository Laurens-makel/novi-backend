package student.laurens.novibackend.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import student.laurens.novibackend.entities.AbstractEntity;
import student.laurens.novibackend.entities.AbstractOwnedEntity;
import student.laurens.novibackend.entities.User;
import student.laurens.novibackend.exceptions.ResourceNotFoundException;
import student.laurens.novibackend.exceptions.ResourceForbiddenException;
import student.laurens.novibackend.repositories.ResourceRepository;

import java.util.Optional;

/**
 * Base class for Services which expose CRUD methods for {@link AbstractEntity}.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
public abstract class BaseService<R extends AbstractEntity> {

    protected Logger log = LoggerFactory.getLogger(BaseService.class);

    /**
     * Retrieve the specific {@link ResourceRepository} implementation of R.
     *
     * @return Class of R.
     */
    abstract protected ResourceRepository<R> getRepository();

    abstract public R getResource(final String string);

    /**
     * Retrieve the specific implemented class of <R> {@link AbstractEntity}.
     *
     * @return Class of R.
     */
    abstract public Class<R> getResourceClass();

    /**
     * Validate a resource exists.
     *
     * @param resourceId - Identifier of the resource to validate.
     *
     * @throws ResourceNotFoundException - Thrown when resource could not be found.
     */
    public boolean exists(final Integer resourceId) throws ResourceNotFoundException {
        if(!getRepository().existsById(resourceId)){
            throw new ResourceNotFoundException(getResourceClass(), resourceId);
        }
        return true;
    }

    /**
     * Retrieves a resource from repository, specified by id.
     *
     * @param resourceId - Identifier of the resource to retrieve.
     * @param consumer - User which has the intention of interacting with the resource.
     *
     * @throws ResourceNotFoundException - Thrown when resource could not be found.
     */
    public R getResourceById(final Integer resourceId, final User consumer) throws ResourceNotFoundException {
        Optional<R> resource = validateOwnershipOfResource(resourceId, HttpMethod.GET, consumer);
        if(resource.isEmpty()){
            return getResourceByIdWithoutValidations(resourceId);
        }
        return resource.get();
    }
    protected R getResourceByIdWithoutValidations(final Integer resourceId) throws ResourceNotFoundException {
        R found = getRepository().getOne(resourceId);

        if(found == null){
            throw new ResourceNotFoundException(getResourceClass(), resourceId);
        }

        return found;
    }

    /**
     * Retrieves a list of resources from repository.
     */
    public Iterable<R> getResources(){
        return getRepository().findAll();
    }

    /**
     * Creates a resource in repository.
     *
     * @param resource - The state of the resource to save.
     */
    public R createResource(final R resource){
        return getRepository().save(resource);
    };

    /**
     * Updates a resource in repository, specified by resource id.
     *
     * @param resourceId - Identifier of the resource to update.
     * @param resource - The state of the resource to save.
     * @param consumer - User which has the intention of interacting with the resource.
     *
     * @throws ResourceNotFoundException - Thrown when resource could not be found.
     * @throws ResourceForbiddenException - Thrown when resource could is not owned by current consumer of service.
     */
    public R updateResourceById(final Integer resourceId, final R resource, final User consumer) throws ResourceNotFoundException, ResourceForbiddenException {
        exists(resourceId);
        validateOwnershipOfResource(resourceId, HttpMethod.PUT, consumer);
        return getRepository().save(resource);
    }

    /**
     * Deletes a resource from repository, specified by resource id.
     *
     * @param resourceId - Identifier of the resource to delete.
     * @param consumer - User which has the intention of interacting with the resource.
     *
     * @throws ResourceNotFoundException - Thrown when resource could not be found.
     * @throws ResourceForbiddenException - Thrown when resource could is not owned by current consumer of service.
     */
    public void deleteResourceById(final Integer resourceId, final User consumer) throws ResourceNotFoundException, ResourceForbiddenException {
        exists(resourceId);
        validateOwnershipOfResource(resourceId, HttpMethod.DELETE, consumer);
        getRepository().deleteById(resourceId);
    };

    /**
     * Validates if current consumer is the owner of R when if R is an instance of {@link AbstractOwnedEntity}, if corresponding HttpMethod is protected by ownerships.
     *
     * @param resourceId - Identifier of the resource validate ownership of.
     * @param method - HttpMethod of the current action.
     * @param consumer - User which has the intention of interacting with the resource.
     *
     * @throws ResourceNotFoundException - Thrown when resource could not be found.
     * @throws ResourceForbiddenException - Thrown when resource could is not owned by current consumer of service.
     */
    public  Optional<R> validateOwnershipOfResource(final Integer resourceId, final HttpMethod method, final User consumer) throws ResourceNotFoundException, ResourceForbiddenException {
        Class<R> resourceClass = getResourceClass();

        if(AbstractOwnedEntity.class.isAssignableFrom(resourceClass) && isMethodOwnershipProtected(method)){
            log.info("Checking if allowed to ["+method+"] AbstractOwnedEntity ["+resourceClass+"] with identifier ["+resourceId+"]");
            R resource = getResourceByIdWithoutValidations(resourceId);
            AbstractOwnedEntity ownedResource = (AbstractOwnedEntity) resource;

            if(ownedResource.getOwnerUid() != consumer.getUid() && !consumer.hasRole("ADMIN") ){
                log.warn("User ["+consumer.getUid()+"] tried to ["+method+"] a forbidden ["+resourceClass+"] with identifier ["+resourceId+"]");
                throw new ResourceForbiddenException(resourceClass, resourceId);
            }

            return Optional.of(resource);
        }
        return Optional.empty();
    }

    /**
     * @return indication if HttpMethod is protected by ownership.
     */
    protected boolean isMethodOwnershipProtected(final HttpMethod method){
        if(method.equals(HttpMethod.GET)){
            return isGetOwnershipProtected();
        }
        if(method.equals(HttpMethod.PUT)){
            return isPutOwnershipProtected();
        }
        if(method.equals(HttpMethod.DELETE)){
            return isDeleteOwnershipProtected();
        }
        return false;
    }

    /**
     * @return indication if HttpMethod.GET is protected by ownership.
     */
    protected boolean isGetOwnershipProtected(){
        return false;
    }

    /**
     * @return indication if HttpMethod.PUT is protected by ownership.
     */
    protected boolean isPutOwnershipProtected(){
        return true;
    }

    /**
     * @return indication if HttpMethod.DELETE is protected by ownership.
     */
    protected boolean isDeleteOwnershipProtected(){
        return true;
    }

}
