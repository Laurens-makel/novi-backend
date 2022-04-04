package student.laurens.novibackend.controllers;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import student.laurens.novibackend.entities.AbstractEntity;
import student.laurens.novibackend.exceptions.ResourceForbiddenException;
import student.laurens.novibackend.exceptions.ResourceNotFoundException;
import student.laurens.novibackend.services.AppUserDetailsService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Base class for RestControllers which expose CRUD methods for {@link AbstractEntity}.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
public abstract class ResourceBaseRestController<R extends AbstractEntity> extends BaseRestController<R> {

    public ResourceBaseRestController(AppUserDetailsService appUserDetailsService){
        super(appUserDetailsService);
        this.appUserDetailsService = appUserDetailsService;
    }

    /**
     * Provides a default way to handle GET requests on {@link AbstractEntity} resources.
     *
     * @return List of resources.
     */
    protected ResponseEntity<Resources<R>> getResources() {
        logProcessingStarted(HttpMethod.GET);

        List<R> r = getService().getResources();
        Resources resources = resourcesWithLinks(r, getLinksForGetResources(r));

        logProcessingFinished(HttpMethod.GET);
        return createSuccessResponseGET(resources);
    }

    /**
     * Should be implemented by child-class, annotated with @GetMapping and call get() to handle GET requests.
     * Referred to in HATEOAS resource link building.
     *
     * @return List of resources.
     */
    abstract public ResponseEntity<Resources<R>> GET();

    protected Map<String, ControllerLinkBuilder> getLinksForGetResources(final List<R> resources) {
        Map<String, ControllerLinkBuilder> links = new HashMap<>();

        for(R resource : resources){
            links.put("GET " , linkTo(methodOn(this.getClass()).GET(resource.getId())));
        }

        return links;
    }
    /**
     * Provides a default way to handle GET requests on {@link AbstractEntity} resources.
     *
     * @param name - Name of the resource to retrieve.
     *
     * @throws ResourceNotFoundException - Thrown when resource could not be found.
     *
     * @return Persisted resource, specified by name.
     */
    protected ResponseEntity<Resource<R>> get(final String name) throws ResourceNotFoundException {
        logProcessingStarted(HttpMethod.GET, name);

        R r = getService().getResource(name);
        Resource<R> resource = resourceWithLinks(r, getLinksForGetResourceByName(name, r));

        logProcessingFinished(HttpMethod.GET, name);
        return createSuccessResponseGET(resource);
    }

    protected Map<String, ControllerLinkBuilder> getLinksForGetResourceByName(final String name, final R resource) {
        Map<String, ControllerLinkBuilder> links = new HashMap<>();

        links.put("DELETE", linkTo(methodOn(this.getClass()).DELETE(resource.getId())));
        links.put("PUT", linkTo(methodOn(this.getClass()).PUT(resource.getId(), resource)));

        return links;
    }

    /**
     * Provides a default way to handle GET requests on {@link AbstractEntity} resources.
     * Should be implemented by resource specific controller classes.
     *
     * @param resourceId - Identifier of the resource to retrieve.
     *
     * @throws ResourceNotFoundException - Thrown when resource could not be found.
     *
     * @return Persisted resource, specified by identifier.
     */
    protected ResponseEntity<Resource<R>> get(final Integer resourceId) throws ResourceNotFoundException {
        logProcessingStarted(HttpMethod.GET, resourceId);

        R r = getService().getResourceById(resourceId, getConsumer());
        Resource<R> resource = resourceWithLinks(r, getLinksForGetResource(resourceId, r));

        logProcessingFinished(HttpMethod.GET, resourceId);
        return createSuccessResponseGET(resource);
    }

    abstract public ResponseEntity<Resource<R>> GET(final Integer resourceId);

    protected Map<String, ControllerLinkBuilder> getLinksForGetResource(final Integer resourceId, final R resource) {
        Map<String, ControllerLinkBuilder> links = new HashMap<>();

        links.put("DELETE", linkTo(methodOn(this.getClass()).DELETE(resource.getId())));
        links.put("PUT", linkTo(methodOn(this.getClass()).PUT(resource.getId(), resource)));

        return links;
    }

    /**
     * Provides a default way to handle POST requests on {@link AbstractEntity} resources.
     *
     * @param resource - The new resource to be created.
     *
     * @return Confirmation message.
     */
    protected ResponseEntity<Resource<R>> create(final R resource) {
        logProcessingStarted(HttpMethod.POST);

        R r = getService().createResource(resource);
        Resource<R> created = resourceWithLinks(r, getLinksForPostResource(resource));

        logProcessingFinished(HttpMethod.POST);
        return createSuccessResponsePOST(created);
    }

    /**
     * Should be implemented by child-class, annotated with @PostMapping and call create(resource) to handle POST requests.
     * Referred to in HATEOAS resource link building.
     *
     * @param resource - The new resource to be created.
     *
     * @return Confirmation message.
     */
    abstract public ResponseEntity<Resource<R>> POST(final R resource);

    protected Map<String, ControllerLinkBuilder> getLinksForPostResource(final R resource) {
        Map<String, ControllerLinkBuilder> links = new HashMap<>();

        links.put("DELETE", linkTo(methodOn(this.getClass()).DELETE(resource.getId())));
        links.put("PUT", linkTo(methodOn(this.getClass()).PUT(resource.getId(), resource)));

        return links;
    }

    /**
     * Provides a default way to handle PUT requests on {@link AbstractEntity} resources.
     *
     * @param resourceId - Identifier of the resource to update.
     * @param resource - New state of the resource.
     *
     * @throws ResourceNotFoundException - Thrown when resource could not be found.
     * @throws ResourceForbiddenException - Thrown when resource could is not owned by current consumer of the API.
     *
     * @return Confirmation message.
     */
    protected ResponseEntity<Resource<R>> update(final Integer resourceId, final R resource) throws ResourceNotFoundException, ResourceForbiddenException {
        logProcessingStarted(HttpMethod.PUT, resourceId);

        R r = getService().updateResourceById(resourceId, resource, getConsumer());
        Resource<R> updated = resourceWithLinks(r, getLinksForPutResource(r));

        logProcessingFinished(HttpMethod.PUT, resourceId);
        return createSuccessResponsePUT(updated);
    }

    /**
     * Should be implemented by child-class, annotated with @PutMapping and call update(resourceId, resource) to handle PUT requests.
     * Referred to in HATEOAS resource link building.
     *
     * @param resourceId - Identifier of the resource to update.
     * @param resource - New state of the resource.
     *
     * @throws ResourceNotFoundException - Thrown when resource could not be found.
     * @throws ResourceForbiddenException - Thrown when resource could is not owned by current consumer of the API.
     *
     * @return Confirmation message.
     */
    abstract public ResponseEntity<Resource<R>> PUT(final Integer resourceId, final R resource) throws ResourceNotFoundException, ResourceForbiddenException;

    protected Map<String, ControllerLinkBuilder> getLinksForPutResource(R resource) {
        Map<String, ControllerLinkBuilder> links = new HashMap<>();

        links.put("DELETE", linkTo(methodOn(this.getClass()).DELETE(resource.getId())));

        return links;
    }

    /**
     * Provides a default way to handle DELETE requests on {@link AbstractEntity} resources.
     *
     * @param resourceId - Identifier of the resource to delete.
     *
     * @throws ResourceNotFoundException - Thrown when resource could not be found.
     * @throws ResourceForbiddenException - Thrown when resource is not owned by current consumer of the API.
     *
     * @return Confirmation message.
     */
    protected ResponseEntity<R> delete(final Integer resourceId) throws ResourceNotFoundException, ResourceForbiddenException {
        logProcessingStarted(HttpMethod.DELETE, resourceId);

        getService().deleteResourceById(resourceId, getConsumer());

        logProcessingFinished(HttpMethod.DELETE, resourceId);
        return createSuccessResponseDELETE();
    }

    /**
     * Should be implemented by child-class, annotated with @DeleteMapping and call delete(resourceId) to handle DELETE requests.
     * Referred to in HATEOAS resource link building.
     *
     * @param resourceId - Identifier of the resource to delete.
     *
     * @throws ResourceNotFoundException - Thrown when resource could not be found.
     * @throws ResourceForbiddenException - Thrown when resource could is not owned by current consumer of the API.
     *
     * @return Confirmation message.
     */
    abstract public ResponseEntity<R> DELETE(final Integer resourceId) throws ResourceNotFoundException, ResourceForbiddenException;

}
