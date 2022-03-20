package student.laurens.novibackend.services;

import org.springframework.core.GenericTypeResolver;
import org.springframework.http.HttpMethod;
import student.laurens.novibackend.entities.AbstractEntity;
import student.laurens.novibackend.exceptions.ResourceNotFoundException;
import student.laurens.novibackend.repositories.ResourceRepository;

import java.util.Optional;

public abstract class BaseService<R extends AbstractEntity> {
    private final Class<R> resourceType;

    protected BaseService() {
        this.resourceType = (Class<R>) GenericTypeResolver.resolveTypeArgument(getClass(), AbstractEntity.class);
    }

    abstract protected ResourceRepository getRepository();

    abstract public R getResource(String string);

    public R getResourceById(Integer resourceId) {
        Optional<R> found = getRepository().findById(resourceId);

        if(!found.isPresent()){
            throw new ResourceNotFoundException(resourceType, resourceId);
        }

        return found.get();
    }

    public Iterable<R> getResource(){
        return getRepository().findAll();
    }

    public void createResource(R resource){
        getRepository().save(resource);
    };

    public void updateResourceById(Integer resourceId, R resource){
        Optional<R> found = getRepository().findById(resourceId);

        if(!found.isPresent()){
            throw new ResourceNotFoundException(resourceType, resourceId);
        }

        getRepository().delete(found.get());
    }

    public void deleteResourceById(Integer resourceId){
        Optional<R> resource = getRepository().findById(resourceId);

        if(!resource.isPresent()){
            throw new ResourceNotFoundException(resourceType, resourceId);
        }

        getRepository().delete(resource.get());
    };


    /**
     * Override this method by a resource specific implementation to determine which Http methods are protected
     *
     * @return boolean indicating if method is allowed by users which are not the owner of the resource.
     */
    public boolean isMethodProtected(HttpMethod method){
        return false;
    };

    abstract public Class<R> getResourceClass();

}
