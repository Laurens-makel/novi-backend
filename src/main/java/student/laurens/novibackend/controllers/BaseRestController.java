package student.laurens.novibackend.controllers;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import student.laurens.novibackend.entities.AbstractEntity;
import student.laurens.novibackend.entities.AbstractOwnedEntity;
import student.laurens.novibackend.entities.User;
import student.laurens.novibackend.exceptions.ResourceNotFoundException;
import student.laurens.novibackend.exceptions.ResourceNotOwnedException;
import student.laurens.novibackend.exceptions.UserNotFoundException;
import student.laurens.novibackend.services.AppUserDetailsService;
import student.laurens.novibackend.services.BaseService;

import java.util.HashMap;
import java.util.Map;

/**
 * Base class for RestControllers which expose CRUD methods for {@link AbstractEntity}.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
public abstract class BaseRestController<R extends AbstractEntity> {

    protected Logger log = LoggerFactory.getLogger(BaseRestController.class);

    @Autowired
    protected @Getter AppUserDetailsService appUserDetailsService;

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

    public ResponseEntity<R> get(final String name) throws ResourceNotFoundException {
        return new ResponseEntity<>(getService().getResource(name), HttpStatus.OK);
    }

    public ResponseEntity<R> get(final Integer resourceId) throws ResourceNotFoundException {
        getService().validateOwnershipOfResource(resourceId, HttpMethod.GET, getConsumer());

        return new ResponseEntity<>(getService().getResourceById(resourceId), HttpStatus.OK);
    }

    public ResponseEntity<R> create(final R resource) {
        getService().createResource(resource);

        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    public ResponseEntity<R> update(final Integer resourceId, final R resource) throws ResourceNotFoundException, ResourceNotOwnedException {
        getService().validateOwnershipOfResource(resourceId, HttpMethod.PUT, getConsumer());

        getService().updateResourceById(resourceId, resource);

        return new ResponseEntity<>(resource, HttpStatus.ACCEPTED);
    }

    public ResponseEntity<R> delete(final Integer resourceId) throws ResourceNotFoundException, ResourceNotOwnedException {
        getService().validateOwnershipOfResource(resourceId, HttpMethod.DELETE, getConsumer());

        getService().deleteResourceById(resourceId);

        return new ResponseEntity(createDeletedMessage(), HttpStatus.ACCEPTED);
    }

    /**
     * Uses SecurityContextHolder.getContext().getAuthentication().getName() to retrieve current consumer of the API.
     *
     * @return the current consumer of the API.
     * @throws UserNotFoundException - When the current consumer of the API is not a valid user.
     */
    protected User getConsumer() throws UserNotFoundException {
        String username = null;
        try {
            username = SecurityContextHolder.getContext().getAuthentication().getName();
            log.info("Getting currently logged in user [" + username + "]");
            User user = appUserDetailsService.getResource(username);
            log.info("Getting currently logged in user [" + user + "]");
            return user;
        } catch (Exception e) {
            log.warn("An exception occurred whilst attempting to retrieve the currently logged in user !", e);
            throw new UserNotFoundException(username);
        }
    }


}
