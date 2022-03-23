package student.laurens.novibackend.services;

import org.springframework.http.HttpMethod;
import student.laurens.novibackend.entities.*;
import student.laurens.novibackend.exceptions.ResourceNotFoundException;
import student.laurens.novibackend.exceptions.ResourceNotOwnedException;

/**
 * Base class for Services which expose CRUD methods for {@link AbstractOwnedWithParentEntity} which has child entities.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
public abstract class ParentBaseService<R extends AbstractEntity, C extends AbstractEntity> extends BaseService<R> {

    /**
     * Validates if user is allowed to read a child.
     *
     * @param user - User which has the intention to read the child resource.
     *
     * @return Security Policy which applies to reading the child resource.
     */
    abstract public PermissionPolicy isReadOnChildPermitted(User user);

    /**
     * Validates if user is allowed to create a child.
     *
     * @param user - User which has the intention to create the child resource.
     *
     * @return Security Policy which applies to creating the child resource.
     */
    abstract public PermissionPolicy isCreateChildPermitted(User user);

    /**
     * Validates if user is allowed to update a child.
     *
     * @param user - User which has the intention to update the child resource.
     *
     * @return Security Policy which applies to updating the child resource.
     */
    abstract public PermissionPolicy isUpdateOnChildPermitted(User user);

    /**
     * Validates if user is allowed to delete a child.
     *
     * @param user - User which has the intention to delete the child resource.
     *
     * @return Security Policy which applies to deleting the child resource.
     */
    abstract public PermissionPolicy isDeleteOnChildPermitted(User user);

}
