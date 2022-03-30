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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
        log.info("Checking existence of resource with ID ["+resourceId+"]");
        if(!getRepository().existsById(resourceId)){
            log.warn("Resource with ID not found! ["+resourceId+"]");
            throw new ResourceNotFoundException(getResourceClass(), resourceId);
        }
        log.info("Resource with ID ["+resourceId+"] exists.");
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
        log.info("Processing started for get request for resourceId ["+resourceId+"], request by ["+consumer.getUsername()+"]");
        Optional<R> resource = validateOwnershipOfResource(resourceId, HttpMethod.GET, consumer);
        if(resource.isEmpty()){
            log.info("Resource with ID ["+resourceId+"] has not yet been found during ownership validation for user ["+consumer.getUsername()+"].");
            return getResourceByIdWithoutValidations(resourceId);
        }
        log.info("Processing finished with success for get request for resourceId ["+resourceId+"], request by ["+consumer.getUsername()+"]");
        return resource.get();
    }
    protected R getResourceByIdWithoutValidations(final Integer resourceId) throws ResourceNotFoundException {
        log.info("Processing started for get resource without validations request for resourceId ["+resourceId+"]");
        R found = getRepository().getOne(resourceId);

        if(found == null){
            log.warn("Resource with ID not found! ["+resourceId+"]");
            throw new ResourceNotFoundException(getResourceClass(), resourceId);
        }

        return found;
    }

    /**
     * Retrieves a list of resources from repository.
     */
    public List<R> getResources(){
        Iterable<R> resourcesIterable = getRepository().findAll();

        return StreamSupport.stream(resourcesIterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    /**
     * Creates a resource in repository.
     *
     * @param resource - The state of the resource to save.
     */
    public R createResource(final R resource){
        log.info("Processing started for create request.");

        R created = getRepository().save(resource);

//        log.info("Processing finished for create request, created resource ID ["+created.getId()+"].");
        return created;
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
        log.info("Processing started for update request for resourceId ["+resourceId+"], request by ["+consumer.getUsername()+"]");
        exists(resourceId);
        validateOwnershipOfResource(resourceId, HttpMethod.PUT, consumer);
        log.info("Processing finished with success for update request for resourceId ["+resourceId+"], request by ["+consumer.getUsername()+"]");
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
        log.info("Processing started for delete request for resourceId ["+resourceId+"], request by ["+consumer.getUsername()+"]");
        exists(resourceId);
        validateOwnershipOfResource(resourceId, HttpMethod.DELETE, consumer);
        getRepository().deleteById(resourceId);
        log.info("Processing finished with success for delete request for resourceId ["+resourceId+"], request by ["+consumer.getUsername()+"]");
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

            log.info("User ["+consumer.getUid()+"] is the owner of resource ["+ownedResource.getOwnerUid()+"]");
            return Optional.of(resource);
        }
        return Optional.empty();
    }

    /**
     * @return indication if HttpMethod is protected by ownership.
     */
    protected boolean isMethodOwnershipProtected(final HttpMethod method){
        boolean isProtected = false;
        log.info("Checking if method ["+method+"] is protected by resource ownership.");

        if(method.equals(HttpMethod.GET)){
            isProtected = isGetOwnershipProtected();
        }
        else if(method.equals(HttpMethod.PUT)){
            isProtected = isPutOwnershipProtected();
        }
        else if(method.equals(HttpMethod.DELETE)){
            isProtected = isDeleteOwnershipProtected();
        }

        log.info("Finished checking if ["+method+"] is protected by resource ownership, result ["+isProtected+"].");
        return isProtected;
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
