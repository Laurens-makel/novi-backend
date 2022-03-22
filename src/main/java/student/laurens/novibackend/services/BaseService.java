package student.laurens.novibackend.services;

import student.laurens.novibackend.entities.AbstractEntity;
import student.laurens.novibackend.exceptions.ResourceNotFoundException;
import student.laurens.novibackend.repositories.ResourceRepository;

import java.util.Optional;

public abstract class BaseService<R extends AbstractEntity> {

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

}
