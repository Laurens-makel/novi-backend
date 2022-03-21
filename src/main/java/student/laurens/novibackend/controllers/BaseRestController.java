package student.laurens.novibackend.controllers;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import student.laurens.novibackend.entities.AbstractEntity;
import student.laurens.novibackend.entities.AbstractOwnedEntity;
import student.laurens.novibackend.entities.User;
import student.laurens.novibackend.exceptions.ResourceNotFoundException;
import student.laurens.novibackend.exceptions.ResourceNotOwnedException;
import student.laurens.novibackend.services.AppUserDetailsService;
import student.laurens.novibackend.services.BaseService;

import java.util.HashMap;
import java.util.Map;

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
        validateOwnershipOfResource(resourceId, HttpMethod.GET);

        return new ResponseEntity<>(getService().getResourceById(resourceId), HttpStatus.OK);
    }

    public ResponseEntity<R> create(final R resource) {
        getService().createResource(resource);

        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    public ResponseEntity<R> update(final Integer resourceId, final R resource) throws ResourceNotFoundException, ResourceNotOwnedException {
        validateOwnershipOfResource(resourceId, HttpMethod.PUT);

        getService().updateResourceById(resourceId, resource);

        return new ResponseEntity<>(resource, HttpStatus.ACCEPTED);
    }

    public ResponseEntity<R> delete(final Integer resourceId) throws ResourceNotFoundException, ResourceNotOwnedException {
        validateOwnershipOfResource(resourceId, HttpMethod.DELETE);

        getService().deleteResourceById(resourceId);

        return new ResponseEntity(createDeletedMessage(), HttpStatus.ACCEPTED);
    }

    private void validateOwnershipOfResource(final Integer resourceId, final HttpMethod method) throws ResourceNotOwnedException {
        Class<R> resourceClass = getService().getResourceClass();

        if(AbstractOwnedEntity.class.isAssignableFrom(resourceClass) && isMethodProtected(method)){
            log.info("Checking if allowed to ["+method+"] AbstractOwnedEntity ["+resourceClass+"] with identifier ["+resourceId+"]");
            User consumer = getConsumer();
            AbstractOwnedEntity ownedResource = (AbstractOwnedEntity) getService().getResourceById(resourceId);

            if(ownedResource.getOwnerUid() != consumer.getUid() && !consumer.hasRole("ADMIN") ){
                log.warn("User ["+consumer.getUid()+"] tried to ["+method+"] a forbidden ["+resourceClass+"] with identifier ["+resourceId+"]");
                throw new ResourceNotOwnedException(resourceClass, resourceId);
            }
        }
    }

    protected ResponseEntity forbidden(){
        return new ResponseEntity(createMessage("Resource forbidden"), HttpStatus.FORBIDDEN);
    }

    protected User getConsumer(){
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            log.info("Getting currently logged in user ["+username+"]");
            User user = appUserDetailsService.getResource(username);
            log.info("Getting currently logged in user ["+user+"]");
            return user;
        } catch (Exception e){
            log.warn("An exception occured whilst attempting to retrieve the currently logged in user !", e);
        }

        return null;
    }

    protected boolean isMethodProtected(HttpMethod method){
        if(method.equals(HttpMethod.GET)){
            return isGetProtected();
        }
        if(method.equals(HttpMethod.PUT)){
            return isPutProtected();
        }
        if(method.equals(HttpMethod.DELETE)){
            return isDeleteProtected();
        }
        return false;
    }

    protected boolean isGetProtected(){
        return false;
    }

    protected boolean isPutProtected(){
        return true;
    }

    protected boolean isDeleteProtected(){
        return true;
    }

}
