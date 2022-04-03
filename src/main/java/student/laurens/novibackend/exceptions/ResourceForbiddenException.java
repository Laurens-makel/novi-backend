package student.laurens.novibackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ResourceForbiddenException extends ResourceException {

    public ResourceForbiddenException(final Class resourceClass, final Integer identifier) {
        super("Forbidden to perform action on resource with provided ID ["+identifier+"]", resourceClass, identifier);
    }

    @Override
    protected HttpStatus getHttpStatus() {
        return this.getClass().getAnnotation(ResponseStatus.class).value();
    }
}
