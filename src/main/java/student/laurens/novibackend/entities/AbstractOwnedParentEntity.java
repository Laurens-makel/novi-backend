package student.laurens.novibackend.entities;

/**
 * Base class for entities to implement, which are owned by a user and have parent {@link AbstractOwnedParentEntity}, required for typing purposes.
 *
 * Used by {@link student.laurens.novibackend.controllers.BaseRestController} to provide a generic way of protecting
 * resources that can only be modified by their owner.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
public abstract class AbstractOwnedParentEntity<C extends AbstractOwnedWithParentEntity> extends AbstractOwnedEntity {

    abstract public PermissionPolicy isReadOnChildPermitted(User user, C childResource);
    abstract public PermissionPolicy isCreateChildPermitted(User user, C childResource);
    abstract public PermissionPolicy isUpdateOnChildPermitted(User user, C childResource);
    abstract public PermissionPolicy isDeleteOnChildPermitted(User user, C childResource);
}
