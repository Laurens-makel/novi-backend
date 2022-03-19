package student.laurens.novibackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    protected Class resourceClass;
    protected Object identifier;

    public ResourceNotFoundException(final Class resourceClass, final Object identifier) {
        super("Unable to find resource with provided ID ["+identifier+"]");
        this.resourceClass = resourceClass;
        this.identifier = identifier;
    }

    public String getResourceClassName(){
        return this.resourceClass.getName();
    }

}
