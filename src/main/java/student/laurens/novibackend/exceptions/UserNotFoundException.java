package student.laurens.novibackend.exceptions;

import student.laurens.novibackend.entities.User;
import student.laurens.novibackend.services.AppUserDetailsService;

/**
 * Exception that is thrown by {@link AppUserDetailsService} when the provided UID does not correspond with an actual {@link User}.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
public class UserNotFoundException extends ResourceNotFoundException {

    public UserNotFoundException(final Integer identifier) {
        super(User.class, identifier);
    }
    public UserNotFoundException(final String username) {
        super(User.class, username);
    }
}