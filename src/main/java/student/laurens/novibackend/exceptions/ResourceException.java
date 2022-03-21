package student.laurens.novibackend.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public abstract class ResourceException extends RuntimeException {
    protected Class resourceClass;
    protected Object identifier;

    abstract protected HttpStatus getHttpStatus();

    public ResourceException(final String message, final Class resourceClass, final Object identifier) {
        super(message);
        this.resourceClass = resourceClass;
        this.identifier = identifier;
    }

    public String getResourceClassName(){
        return this.resourceClass == null ? this.getClass().getName() : this.resourceClass.getName();
    }
}
