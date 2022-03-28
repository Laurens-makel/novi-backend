package student.laurens.novibackend.entities;

/**
 * Base class for entities to implement, which are owned by a user, required for typing purposes.
 *
 * Used by {@link student.laurens.novibackend.controllers.BaseRestController} to provide a generic way of protecting
 * resources that can only be modified by their owner.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
public abstract class AbstractOwnedWithParentEntity<P extends AbstractEntity> extends AbstractOwnedEntity {

    abstract protected P getParent();

    public Integer getParentId(){
        return getParent().getId();
    }
}
