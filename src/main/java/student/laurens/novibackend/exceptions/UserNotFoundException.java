package student.laurens.novibackend.exceptions;

import student.laurens.novibackend.entities.User;

/**
 * Exception that is thrown by AppUserDetailService when the provided UID does not correspond with an actual user.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
public class UserNotFoundException extends ResourceNotFoundException {

    public UserNotFoundException(final Integer identifier) {
        super(User.class, identifier);
    }

}