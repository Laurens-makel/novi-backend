package student.laurens.novibackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    protected Class resourceClass;

    public ResourceNotFoundException(Class resourceClass, String message) {
        super(message);
        this.resourceClass = resourceClass;
    }

    public String getResourceClassName(){
        return this.resourceClass.getName();
    }

}
