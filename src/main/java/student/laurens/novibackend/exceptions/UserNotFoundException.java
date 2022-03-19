package student.laurens.novibackend.exceptions;

import student.laurens.novibackend.entities.User;

public class UserNotFoundException extends ResourceNotFoundException {

    public UserNotFoundException(final Object identifier) {
        super(User.class, identifier);
    }

}