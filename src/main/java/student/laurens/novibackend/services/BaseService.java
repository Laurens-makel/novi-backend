package student.laurens.novibackend.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpMethod;
import student.laurens.novibackend.entities.AbstractEntity;
import student.laurens.novibackend.entities.AbstractOwnedEntity;
import student.laurens.novibackend.entities.User;
import student.laurens.novibackend.exceptions.ResourceDuplicateException;
import student.laurens.novibackend.exceptions.ResourceForbiddenException;
import student.laurens.novibackend.exceptions.ResourceNotFoundException;
import student.laurens.novibackend.repositories.ResourceRepository;

import java.util.Optional;

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
     * Creates a resource in repository.
     *
     * @param resource - The state of the resource to save.
     */
    protected R create(final R resource){
        log.info("Processing started for create request.");

        try {
            return getRepository().save(resource);
        } catch (Exception e){
            log.warn("An exception with class ["+e.getClass()+"] occurred whilst creating a resource of class ["+getResourceClass()+"]");
            if(e.getClass().equals(DataIntegrityViolationException.class)){
                throw new ResourceDuplicateException(getResourceClass());
            }
            throw e;
        }
//        log.info("Processing finished for create request, created resource ID ["+created.getId()+"].");
    };

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

    protected R getResourceByIdWithoutValidations(final Integer resourceId) throws ResourceNotFoundException {
        log.info("Processing started for get resource without validations request for resourceId ["+resourceId+"]");
        return getRepository().findById(resourceId)
                .orElseThrow(() -> new ResourceNotFoundException(getResourceClass(), resourceId));
    }

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
    public Optional<R> validateOwnershipOfResource(final Integer resourceId, final HttpMethod method, final User consumer) throws ResourceNotFoundException, ResourceForbiddenException {
        Class<R> resourceClass = getResourceClass();
        log.info("Validate ownership of resource called, method ["+method+"] on AbstractOwnedEntity ["+resourceClass+"] with identifier ["+resourceId+"]");

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

        log.info("Validate ownership of resource skipped, method ["+method+"] on AbstractOwnedEntity ["+resourceClass+"] is not protected.");
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
