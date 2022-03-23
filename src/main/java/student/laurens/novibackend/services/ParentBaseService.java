package student.laurens.novibackend.services;

import student.laurens.novibackend.entities.AbstractEntity;
import student.laurens.novibackend.entities.AbstractOwnedWithParentEntity;
import student.laurens.novibackend.entities.PermissionPolicy;
import student.laurens.novibackend.entities.User;

/**
 * Base class for Services which expose CRUD methods for {@link AbstractOwnedWithParentEntity} which has child entities.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
public abstract class ParentBaseService<R extends AbstractEntity, C extends AbstractEntity> extends BaseService<R> {

    /**
     * Validates if user is allowed to read a specific instance of the corresponding child resource C.
     *
     * @param user - User which has the intention to read the child resource.
     * @param childResource - Instance of the child resource the user wants to read.
     *
     * @return Security Policy which applies to reading the child resource.
     */
    abstract public PermissionPolicy isReadOnChildPermitted(User user, C childResource);

    /**
     * Validates if user is allowed to create a specific instance of the corresponding child resource C.
     *
     * @param user - User which has the intention to create the child resource.
     * @param childResource - Instance of the child resource the user wants to create.
     *
     * @return Security Policy which applies to creating the child resource.
     */
    abstract public PermissionPolicy isCreateChildPermitted(User user, C childResource);

    /**
     * Validates if user is allowed to update a specific instance of the corresponding child resource C.
     *
     * @param user - User which has the intention to update the child resource.
     * @param childResource - Instance of the child resource the user wants to update.
     *
     * @return Security Policy which applies to updating the child resource.
     */
    abstract public PermissionPolicy isUpdateOnChildPermitted(User user, C childResource);

    /**
     * Validates if user is allowed to delete a specific instance of the corresponding child resource C.
     *
     * @param user - User which has the intention to delete the child resource.
     * @param childResource - Instance of the child resource the user wants to delete.
     *
     * @return Security Policy which applies to deleting the child resource.
     */
    abstract public PermissionPolicy isDeleteOnChildPermitted(User user, C childResource);

}
