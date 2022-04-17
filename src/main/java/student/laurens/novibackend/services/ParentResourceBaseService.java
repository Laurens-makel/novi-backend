package student.laurens.novibackend.services;

import student.laurens.novibackend.entities.*;

/**
 * Base class for Services which expose CRUD methods for {@link AbstractOwnedWithParentEntity} which has child entities.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
public abstract class ParentResourceBaseService<R extends AbstractEntity> extends ResourceBaseService<R> {

    /**
     * Validates if user is allowed to read a child.
     *
     * @param user - User which has the intention to read the child resource.
     *
     * @return Security Policy which applies to reading the child resource.
     */
    abstract public PermissionPolicy isReadOnChildPermitted(final User user);

    /**
     * Validates if user is allowed to create a child.
     *
     * @param user - User which has the intention to create the child resource.
     *
     * @return Security Policy which applies to creating the child resource.
     */
    abstract public PermissionPolicy isCreateChildPermitted(final User user);

    /**
     * Validates if user is allowed to update a child.
     *
     * @param user - User which has the intention to update the child resource.
     *
     * @return Security Policy which applies to updating the child resource.
     */
    abstract public PermissionPolicy isUpdateOnChildPermitted(final User user);

    /**
     * Validates if user is allowed to delete a child.
     *
     * @param user - User which has the intention to delete the child resource.
     *
     * @return Security Policy which applies to deleting the child resource.
     */
    abstract public PermissionPolicy isDeleteOnChildPermitted(final User user);

}
