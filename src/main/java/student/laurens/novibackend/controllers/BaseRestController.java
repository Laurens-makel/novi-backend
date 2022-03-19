package student.laurens.novibackend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import student.laurens.novibackend.entities.AbstractEntity;
import student.laurens.novibackend.exceptions.ResourceNotFoundException;
import student.laurens.novibackend.services.BaseService;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseRestController<R extends AbstractEntity> {
    private final String DELETED_TEXT = "deleted";

    protected Map<String, String> createDeletedMessage(){
        return createMessage(DELETED_TEXT);
    }

    protected Map<String, String> createMessage(String text){
        final Map<String, String> message = new HashMap<>();
        message.put("message", text);

        return message;
    }

    abstract protected BaseService<R> getService();

    public ResponseEntity<Iterable<R>> get() {
        return new ResponseEntity<>(getService().getResource(), HttpStatus.OK);
    }

    public ResponseEntity<R> get(String name) throws ResourceNotFoundException {
        return new ResponseEntity<>(getService().getResource(name), HttpStatus.OK);
    }

    public ResponseEntity<R> create(R resource) {
        getService().createResource(resource);

        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    public ResponseEntity<R> update(Integer resourceId, R resource) throws ResourceNotFoundException {
        getService().updateResourceById(resourceId, resource);

        return new ResponseEntity<>(resource, HttpStatus.ACCEPTED);
    }

    public ResponseEntity<R> delete(Integer resourceId) throws ResourceNotFoundException {
        getService().deleteResourceById(resourceId);

        return new ResponseEntity(createDeletedMessage(), HttpStatus.ACCEPTED);
    }

}
