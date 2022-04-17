package student.laurens.novibackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Abstract exception for resources.
 *
 * Used by {@link RestExceptionHandler} to format these to a format (JSON or XML is supported) the consumer specified by the <code>Accept</code> header.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
@ResponseStatus
public abstract class ResourceException extends RuntimeException {
    protected Class resourceClass;
    protected Object identifier;

    public HttpStatus getHttpStatus() {
        return this.getClass().getAnnotation(ResponseStatus.class).value();
    }

    public ResourceException(final String message, final Class resourceClass, final Object identifier) {
        super(message);
        this.resourceClass = resourceClass;
        this.identifier = identifier;
    }

    public String getResourceClassName(){
        return this.resourceClass == null ? this.getClass().getName() : this.resourceClass.getName();
    }
}
