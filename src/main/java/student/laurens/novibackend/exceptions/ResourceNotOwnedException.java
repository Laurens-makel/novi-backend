package student.laurens.novibackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ResourceNotOwnedException extends ResourceException {

    public ResourceNotOwnedException(final Class resourceClass, final Integer identifier) {
        super("Forbidden to perform action on resource with provided ID ["+identifier+"]", resourceClass, identifier, HttpStatus.FORBIDDEN);
    }

}
