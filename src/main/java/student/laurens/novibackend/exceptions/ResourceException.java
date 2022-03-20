package student.laurens.novibackend.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public abstract class ResourceException extends RuntimeException {
    protected Class resourceClass;
    protected Object identifier;

    protected @Getter HttpStatus httpStatus;

    public ResourceException(final String message, final Class resourceClass, final Integer identifier, final HttpStatus httpStatus) {
        super(message);
        this.resourceClass = resourceClass;
        this.identifier = identifier;
        this.httpStatus = httpStatus;
    }

    public String getResourceClassName(){
        return this.resourceClass == null ? this.getClass().getName() : this.resourceClass.getName();
    }
}
