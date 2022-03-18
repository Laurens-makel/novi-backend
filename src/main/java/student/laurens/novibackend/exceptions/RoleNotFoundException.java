package student.laurens.novibackend.exceptions;

import student.laurens.novibackend.entities.Role;

public class RoleNotFoundException extends ResourceNotFoundException {

    public RoleNotFoundException(String message) {
        super(Role.class, message);
    }

}
