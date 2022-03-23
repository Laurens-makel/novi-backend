package student.laurens.novibackend.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import student.laurens.novibackend.entities.AbstractEntity;
import student.laurens.novibackend.entities.AbstractOwnedEntity;
import student.laurens.novibackend.entities.User;
import student.laurens.novibackend.exceptions.ResourceNotFoundException;
import student.laurens.novibackend.exceptions.ResourceNotOwnedException;
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

    abstract protected ResourceRepository getRepository();

    abstract public R getResource(final String string);

    /**
     * Retrieve the specific implemented class of <R> {@link AbstractEntity}.
     *
     * @return Class of R.
     */
    abstract public Class<R> getResourceClass();

    /**
     * Retrieves a resource from repository, specified by id.
     *
     * @param resourceId - Identifier of the resource to retrieve.
     */
    public R getResourceById(final Integer resourceId) throws ResourceNotFoundException {
        Optional<R> found = getRepository().findById(resourceId);

        if(!found.isPresent()){
            throw new ResourceNotFoundException(getResourceClass(), resourceId);
        }

        return found.get();
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
    public void createResource(final R resource){
        getRepository().save(resource);
    };

    /**
     * Updates a resource in repository, specified by resource id.
     *
     * @param resourceId - Identifier of the resource to update.
     * @param resource - The state of the resource to save.
     *
     * @throws ResourceNotFoundException - Thrown when resource could not be found.
     */
    public void updateResourceById(final Integer resourceId, R resource) throws ResourceNotFoundException {
        Optional<R> found = getRepository().findById(resourceId);

        if(!found.isPresent()){
            throw new ResourceNotFoundException(getResourceClass(), resourceId);
        }

        getRepository().save(resource);
    }

    /**
     * Deletes a resource from repository, specified by resource id.
     *
     * @param resourceId - Identifier of the resource to delete.
     *
     * @throws ResourceNotFoundException - Thrown when resource could not be found.
     */
    public void deleteResourceById(final Integer resourceId) throws ResourceNotFoundException {
        Optional<R> resource = getRepository().findById(resourceId);

        if(!resource.isPresent()){
            throw new ResourceNotFoundException(getResourceClass(), resourceId);
        }

        getRepository().delete(resource.get());
    };

    /**
     * Validates if current consumer is the owner of R when if R is an instance of {@link AbstractOwnedEntity}, if corresponding HttpMethod is protected by ownerships.
     *
     * @param resourceId - Identifier of the resource validate ownership of.
     * @param method - HttpMethod of the current action.
     * @param consumer - User which has the intention of interacting with the resource.
     *
     * @throws ResourceNotFoundException - Thrown when resource could not be found.
     * @throws ResourceNotOwnedException - Thrown when resource could is not owned by current consumer of API.
     */
    public void validateOwnershipOfResource(final Integer resourceId, final HttpMethod method, final User consumer) throws ResourceNotFoundException, ResourceNotOwnedException {
        Class<R> resourceClass = getResourceClass();

        if(AbstractOwnedEntity.class.isAssignableFrom(resourceClass) && isMethodOwnershipProtected(method)){
            log.info("Checking if allowed to ["+method+"] AbstractOwnedEntity ["+resourceClass+"] with identifier ["+resourceId+"]");
            AbstractOwnedEntity ownedResource = (AbstractOwnedEntity) getResourceById(resourceId);

            if(ownedResource.getOwnerUid() != consumer.getUid() && !consumer.hasRole("ADMIN") ){
                log.warn("User ["+consumer.getUid()+"] tried to ["+method+"] a forbidden ["+resourceClass+"] with identifier ["+resourceId+"]");
                throw new ResourceNotOwnedException(resourceClass, resourceId);
            }
        }
    }

    /**
     * @return indication if HttpMethod is protected by ownership.
     */
    protected boolean isMethodOwnershipProtected(HttpMethod method){
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
