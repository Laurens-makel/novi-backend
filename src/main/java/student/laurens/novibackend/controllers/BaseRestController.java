package student.laurens.novibackend.controllers;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import student.laurens.novibackend.entities.AbstractEntity;
import student.laurens.novibackend.entities.User;
import student.laurens.novibackend.exceptions.ResourceNotFoundException;
import student.laurens.novibackend.exceptions.ResourceForbiddenException;
import student.laurens.novibackend.exceptions.UserNotFoundException;
import student.laurens.novibackend.services.AppUserDetailsService;
import student.laurens.novibackend.services.BaseService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Base class for RestControllers which expose CRUD methods for {@link AbstractEntity}.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
public abstract class BaseRestController<R extends AbstractEntity> {

    protected Logger log = LoggerFactory.getLogger(BaseRestController.class);

    protected @Getter AppUserDetailsService appUserDetailsService;

    public BaseRestController(AppUserDetailsService appUserDetailsService){
        this.appUserDetailsService = appUserDetailsService;
    }

    private final String DELETED_TEXT = "deleted";

    protected Map<String, String> createDeletedMessage(){
        return createMessage(DELETED_TEXT);
    }

    protected Map<String, String> createMessage(final String text){
        final Map<String, String> message = new HashMap<>();
        message.put("message", text);

        return message;
    }

    /**
     * Retrieve the specific {@link BaseService} implementation class of R.
     *
     * @return Class of R.
     */
    abstract protected BaseService<R> getService();

    /**
     * Provides a default way to handle GET requests on {@link student.laurens.novibackend.entities.AbstractEntity} resources.
     * Should be implemented by resource specific controller classes.
     */
    public ResponseEntity<List<R>> get() {
        logProcessingStarted(HttpMethod.GET);

        List<R> resources = getService().getResources();

        logProcessingFinished(HttpMethod.GET);
        return createSuccessResponseGET(resources);
    }

    abstract protected Map<String, ControllerLinkBuilder> getLinksForGetResourceByName(final String name, final R resource);

    /**
     * Provides a default way to handle GET requests on {@link student.laurens.novibackend.entities.AbstractEntity} resources.
     * Should be implemented by resource specific controller classes.
     *
     * @param name - Name of the resource to retrieve.
     *
     * @throws ResourceNotFoundException - Thrown when resource could not be found.
     * @return
     */
    public ResponseEntity<Resource<R>> get(final String name) throws ResourceNotFoundException {
        logProcessingStarted(HttpMethod.GET, name);

        R r = getService().getResource(name);
        Resource<R> resource = resourceWithLinks(r, getLinksForGetResourceByName(name, r));

        logProcessingFinished(HttpMethod.GET, name);
        return createSuccessResponseGET(resource);
    }

    abstract protected Map<String, ControllerLinkBuilder> getLinksForGetResource(final Integer resourceId, final R resource);
    /**
     * Provides a default way to handle GET requests on {@link student.laurens.novibackend.entities.AbstractEntity} resources.
     * Should be implemented by resource specific controller classes.
     *
     * @param resourceId - Identifier of the resource to retrieve.
     *
     * @throws ResourceNotFoundException - Thrown when resource could not be found.
     */
    public ResponseEntity<Resource<R>> get(final Integer resourceId) throws ResourceNotFoundException {
        logProcessingStarted(HttpMethod.GET, resourceId);

        R r = getService().getResourceById(resourceId, getConsumer());
        Resource<R> resource = resourceWithLinks(r, getLinksForGetResource(resourceId, r));

        logProcessingFinished(HttpMethod.GET, resourceId);
        return createSuccessResponseGET(resource);
    }

    abstract protected Map<String, ControllerLinkBuilder> getLinksForPostResource(final R resource);
    /**
     * Provides a default way to handle POST requests on {@link student.laurens.novibackend.entities.AbstractEntity} resources.
     * Should be implemented by resource specific controller classes.
     *
     * @param resource - The new resource to be created.
     */
    public ResponseEntity<Resource<R>> create(final R resource) {
        logProcessingStarted(HttpMethod.POST);

        R r = getService().createResource(resource);
        Resource<R> created = resourceWithLinks(r, getLinksForPostResource(resource));

        logProcessingFinished(HttpMethod.POST);
        return createSuccessResponsePOST(created);
    }

    /**
     * Provides a default way to handle PUT requests on {@link student.laurens.novibackend.entities.AbstractEntity} resources.
     * Should be implemented by resource specific controller classes.
     *
     * @param resourceId - Identifier of the resource to update.
     * @param resource - New state of the resource.
     *
     * @throws ResourceNotFoundException - Thrown when resource could not be found.
     * @throws ResourceForbiddenException - Thrown when resource could is not owned by current consumer of the API.
     */
    public ResponseEntity<R> update(final Integer resourceId, final R resource) throws ResourceNotFoundException, ResourceForbiddenException {
        logProcessingStarted(HttpMethod.PUT, resourceId);

        R updated = getService().updateResourceById(resourceId, resource, getConsumer());

        logProcessingFinished(HttpMethod.PUT, resourceId);
        return createSuccessResponsePUT(updated);
    }

    /**
     * Provides a default way to handle DELETE requests on {@link student.laurens.novibackend.entities.AbstractEntity} resources.
     * Should be implemented by resource specific controller classes.
     *
     * @param resourceId - Identifier of the resource to delete.
     *
     * @throws ResourceNotFoundException - Thrown when resource could not be found.
     * @throws ResourceForbiddenException - Thrown when resource is not owned by current consumer of the API.
     */
    public ResponseEntity<R> delete(final Integer resourceId) throws ResourceNotFoundException, ResourceForbiddenException {
        logProcessingStarted(HttpMethod.DELETE, resourceId);

        getService().deleteResourceById(resourceId, getConsumer());

        logProcessingFinished(HttpMethod.DELETE, resourceId);
        return createSuccessResponseDELETE();
    }

    /**
     * Uses SecurityContextHolder.getContext().getAuthentication().getName() to retrieve current consumer of the API.
     *
     * @throws UserNotFoundException - When the current consumer of the API is not a valid user.
     *
     * @return the current consumer of the API.
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

    protected ResponseEntity<List<R>> createSuccessResponseGET(final List<R> resources){
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }
    protected ResponseEntity<Resource<R>> createSuccessResponseGET(final Resource<R> resource){
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }
    protected ResponseEntity<Resource<R>> createSuccessResponsePOST(final Resource<R> resource){
        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }
    protected ResponseEntity<R> createSuccessResponsePUT(final R resource){
        return new ResponseEntity<>(resource, HttpStatus.ACCEPTED);
    }
    protected ResponseEntity<R> createSuccessResponseDELETE(){
        return new ResponseEntity(createDeletedMessage(), HttpStatus.ACCEPTED);
    }

    protected Resource<R> resourceWithLinks(final R resource, Map<String, ControllerLinkBuilder> links){
        return resourceWithLinks(new Resource<R>(resource), links);
    }

    protected Resource<R> resourceWithLinks(final Resource<R> resource, Map<String, ControllerLinkBuilder> links){
        for (Map.Entry<String, ControllerLinkBuilder> entry : links.entrySet()) {
            String rel = entry.getKey();
            ControllerLinkBuilder link = entry.getValue();

            log.info("Adding link ["+link.toString()+"] with rel ["+rel+"] to response. ");
            resource.add(link.withRel(rel));
        }
        return resource;
    }

    protected void logProcessingStarted(final HttpMethod method) {
        log.info("Processing ["+method+"] request on resource ["+getService().getResourceClass()+"] started.");
    }
    protected void logProcessingStarted(final HttpMethod method, final Integer resourceId) {
        log.info("Processing ["+method+"] request on resource ["+getService().getResourceClass()+"] with resourceId ["+resourceId+"] started");
    }
    protected void logProcessingStarted(final HttpMethod method, final String resourceName) {
        log.info("Processing ["+method+"] request on resource ["+getService().getResourceClass()+"] with resourceName ["+resourceName+"] started");
    }

    protected void logProcessingFinished(final HttpMethod method, final Integer resourceId) {
        log.info("Processing ["+method+"] request on resource ["+getService().getResourceClass()+"] with resourceId ["+resourceId+"] successfully finished.");
    }
    protected void logProcessingFinished(final HttpMethod method, final String resourceName) {
        log.info("Processing ["+method+"] request on resource ["+getService().getResourceClass()+"] with resourceName ["+resourceName+"] successfully finished.");
    }
    protected void logProcessingFinished(final HttpMethod method) {
        log.info("Processing ["+method+"] request on resource ["+getService().getResourceClass()+"] successfully finished.");
    }

}
