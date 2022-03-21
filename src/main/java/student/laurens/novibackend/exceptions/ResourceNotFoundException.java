package student.laurens.novibackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import student.laurens.novibackend.entities.User;

/**
 * Default exception when a resource could not by find by the system, used by {@link RestExceptionHandler} to format these
 * to a format (JSON or XML is supported) the consumer specified by the <code>Accept</code> header
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends ResourceException {

    protected HttpStatus httpStatus;

    public ResourceNotFoundException(final Class resourceClass, final Integer identifier) {
        super("Unable to find resource with provided ID ["+identifier+"]", resourceClass, identifier);
    }

    public ResourceNotFoundException(final Class resourceClass, String username) {
        super("Unable to find resource with provided name ["+username+"]", resourceClass, username );
    }

    @Override
    protected HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
