package student.laurens.novibackend.exceptions;

import student.laurens.novibackend.entities.Blogpost;
import student.laurens.novibackend.services.BlogpostService;

/**
 * Exception that is thrown by {@link BlogpostService} when the provided UID does not correspond with an actual {@link Blogpost}.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
public class BlogpostNotFoundException extends ResourceNotFoundException {

    public BlogpostNotFoundException(final Integer identifier) {
        super(Blogpost.class, identifier);
    }

}
