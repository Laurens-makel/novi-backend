package student.laurens.novibackend.services;

import student.laurens.novibackend.entities.AbstractEntity;
import student.laurens.novibackend.exceptions.ResourceNotFoundException;
import student.laurens.novibackend.repositories.ResourceRepository;

import java.util.Optional;

public abstract class BaseService<R extends AbstractEntity> {

    abstract protected ResourceRepository getRepository();

    abstract public R getResource(String string);

    public Iterable<R> getResource(){
        return getRepository().findAll();
    }

    public void createResource(R resource){
        getRepository().save(resource);
    };

    public void updateResourceById(Integer resourceId, R resource){
        Optional<R> found = getRepository().findById(resourceId);

        if(!found.isPresent()){
            throw new ResourceNotFoundException(resource.getClass(), resourceId);
        }

        getRepository().delete(found.get());
    }

    public void deleteResourceById(Integer resourceId){
        Optional<R> resource = getRepository().findById(resourceId);

        if(!resource.isPresent()){
            throw new ResourceNotFoundException(null, resourceId);
        }

        getRepository().delete(resource.get());
    };

}
