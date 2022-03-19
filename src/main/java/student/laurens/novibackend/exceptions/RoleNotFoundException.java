package student.laurens.novibackend.exceptions;

import student.laurens.novibackend.entities.Role;

/**
 * Exception that is thrown by RoleService when the provided UID does not correspond with an actual role.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
public class RoleNotFoundException extends ResourceNotFoundException {

    public RoleNotFoundException(final Integer identifier) {
        super(Role.class, identifier);
    }

}
