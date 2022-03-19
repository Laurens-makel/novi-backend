package student.laurens.novibackend.services;

import student.laurens.novibackend.entities.AbstractEntity;

public abstract class BaseService<R extends AbstractEntity> {

    abstract public R getResource(String string);

    abstract public Iterable<R> getResource();

    abstract public void createResource(R resource);

    abstract public void updateResourceById(Integer resourceId, R resource);

    abstract public void deleteResourceById(Integer resourceId);

}
