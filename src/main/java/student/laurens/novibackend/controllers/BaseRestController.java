package student.laurens.novibackend.controllers;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import student.laurens.novibackend.entities.AbstractEntity;
import student.laurens.novibackend.entities.User;
import student.laurens.novibackend.exceptions.UserNotFoundException;
import student.laurens.novibackend.services.AppUserDetailsService;
import student.laurens.novibackend.services.BaseService;
import student.laurens.novibackend.services.ResourceBaseService;

import java.util.Collections;
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
     * Retrieve the specific {@link ResourceBaseService} implementation class of R.
     *
     * @return Class of R.
     */
    abstract protected BaseService<R> getService();

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
    protected ResponseEntity<Resources<R>> createSuccessResponseGET(final Resources<R> resources){
        log.info("Creating ResponseEntity for GET resources on resource class ["+getService().getResourceClass()+"] with size of ["+resources.getContent().size()+"]");
        return new ResponseEntity(resources, HttpStatus.OK);
    }
    protected ResponseEntity<List<R>> createSuccessResponseGET(final List<R> resources){
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }
    protected ResponseEntity<Resource<R>> createSuccessResponseGET(final Resource<R> resource){
        log.info("Creating ResponseEntity for GET resource on resource class ["+getService().getResourceClass()+"] with id ["+resource.getContent().getId()+"]");
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }
    protected ResponseEntity<Resource<R>> createSuccessResponsePOST(final Resource<R> resource){
        log.info("Creating ResponseEntity for POST resource on resource class ["+getService().getResourceClass()+"].");
        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }
    protected ResponseEntity<Resource<R>> createSuccessResponsePUT(final Resource<R> resource){
        log.info("Creating ResponseEntity for PUT resource on resource class ["+getService().getResourceClass()+"].");
        return new ResponseEntity<>(resource, HttpStatus.ACCEPTED);
    }
    protected ResponseEntity<R> createSuccessResponseDELETE(){
        log.info("Creating ResponseEntity for DELETE resource on resource class ["+getService().getResourceClass()+"].");
        return new ResponseEntity(createDeletedMessage(), HttpStatus.ACCEPTED);
    }
    protected Resources<R> resourcesWithLinks(final List<R> resources, final Map<String, ControllerLinkBuilder> links){
        return resourceWithLinks(new Resources<>(Collections.singleton(resources)), links);
    }

    private Resources<R> resourceWithLinks(final Resources resources, final Map<String, ControllerLinkBuilder> links) {
        for (Map.Entry<String, ControllerLinkBuilder> entry : links.entrySet()) {
            String rel = entry.getKey();
            ControllerLinkBuilder link = entry.getValue();

            log.info("Adding link ["+link.toString()+"] with rel ["+rel+"] to response. ");
            resources.add(link.withRel(rel));
        }
        return resources;
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
