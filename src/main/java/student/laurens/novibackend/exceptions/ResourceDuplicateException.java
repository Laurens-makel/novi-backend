package student.laurens.novibackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class ResourceDuplicateException extends ResourceException{

    public ResourceDuplicateException(final Class resourceClass) {
        super("Resource already exists!", resourceClass, null);
    }
}
