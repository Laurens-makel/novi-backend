package student.laurens.novibackend.entities;

public abstract class AbstractOwnedParentEntity<C extends AbstractOwnedWithParentEntity> extends AbstractOwnedEntity {

    abstract public PermissionPolicy isReadOnChildPermitted(User user, C childResource);
    abstract public PermissionPolicy isCreateChildPermitted(User user, C childResource);
    abstract public PermissionPolicy isUpdateOnChildPermitted(User user, C childResource);
    abstract public PermissionPolicy isDeleteOnChildPermitted(User user, C childResource);
}
