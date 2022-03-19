package student.laurens.novibackend.exceptions;

import student.laurens.novibackend.entities.Tag;
import student.laurens.novibackend.services.TagService;

/**
 * Exception that is thrown by {@link TagService} when the provided UID does not correspond with an actual {@link Tag}.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
public class TagNotFoundException extends ResourceNotFoundException {

    public TagNotFoundException(final Integer identifier) {
        super(Tag.class, identifier);
    }

}
