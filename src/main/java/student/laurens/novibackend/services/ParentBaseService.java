package student.laurens.novibackend.services;

import student.laurens.novibackend.entities.AbstractEntity;
import student.laurens.novibackend.entities.PermissionPolicy;
import student.laurens.novibackend.entities.User;

public abstract class ParentBaseService<R extends AbstractEntity, C extends AbstractEntity> extends BaseService<R> {
    abstract public PermissionPolicy isReadOnChildPermitted(User user, C childResource);
    abstract public PermissionPolicy isCreateChildPermitted(User user, C childResource);
    abstract public PermissionPolicy isUpdateOnChildPermitted(User user, C childResource);
    abstract public PermissionPolicy isDeleteOnChildPermitted(User user, C childResource);

}
