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

public abstract class BaseService<R extends AbstractEntity> {

    protected Logger log = LoggerFactory.getLogger(BaseService.class);

    abstract protected ResourceRepository getRepository();

    abstract public R getResource(final String string);

    public R getResourceById(final Integer resourceId) {
        Optional<R> found = getRepository().findById(resourceId);

        if(!found.isPresent()){
            throw new ResourceNotFoundException(getResourceClass(), resourceId);
        }

        return found.get();
    }

    public Iterable<R> getResource(){
        return getRepository().findAll();
    }

    public void createResource(final R resource){
        getRepository().save(resource);
    };

    public void updateResourceById(final Integer resourceId, R resource){
        Optional<R> found = getRepository().findById(resourceId);

        if(!found.isPresent()){
            throw new ResourceNotFoundException(getResourceClass(), resourceId);
        }

        getRepository().save(resource);
    }

    public void deleteResourceById(final Integer resourceId){
        Optional<R> resource = getRepository().findById(resourceId);

        if(!resource.isPresent()){
            throw new ResourceNotFoundException(getResourceClass(), resourceId);
        }

        getRepository().delete(resource.get());
    };

    abstract public Class<R> getResourceClass();

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
