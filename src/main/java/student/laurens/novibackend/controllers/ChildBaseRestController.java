package student.laurens.novibackend.controllers;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import student.laurens.novibackend.entities.*;
import student.laurens.novibackend.exceptions.ResourceDuplicateException;
import student.laurens.novibackend.exceptions.ResourceNotFoundException;
import student.laurens.novibackend.exceptions.ResourceForbiddenException;
import student.laurens.novibackend.services.AppUserDetailsService;
import student.laurens.novibackend.services.ChildResourceBaseService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Base class for RestControllers which expose CRUD methods for {@link AbstractOwnedWithParentEntity}.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
public abstract class ChildBaseRestController<R extends AbstractOwnedWithParentEntity, P extends AbstractEntity>
        extends BaseRestController<R>{

    public ChildBaseRestController(AppUserDetailsService appUserDetailsService) {
        super(appUserDetailsService);
    }

    @Override
    abstract protected ChildResourceBaseService<R, P> getService();

    /**
     * Provides a default way to handle GET requests on {@link student.laurens.novibackend.entities.AbstractOwnedWithParentEntity} resources.
     *
     * @param parentResourceId - Identifier of parent resource of the resource to retrieve.
     * @param resourceId - Identifier of the resource to retrieve.
     *
     * @throws ResourceNotFoundException - Thrown when parent resource or resource could not be found.
     * @throws ResourceForbiddenException - Thrown when parent resource or resource is not owned by current consumer of the API.
     *
     * @return Persisted resource, specified by identifier.
     */
    public ResponseEntity<Resource<R>> get(final Integer parentResourceId, final Integer resourceId) throws ResourceNotFoundException, ResourceForbiddenException {
        logProcessingStarted(HttpMethod.GET, parentResourceId, resourceId);

        R r = getService().getResourceById(parentResourceId, resourceId, getConsumer());
        Resource<R> resource = resourceWithLinks(r, getLinksForGetResource(resourceId, r));

        logProcessingFinished(HttpMethod.GET, parentResourceId, resourceId);
        return createSuccessResponseGET(resource);
    }

    abstract public ResponseEntity<Resource<R>> GET(final Integer parentResourceId, final Integer resourceId);

    protected Map<String, ControllerLinkBuilder> getLinksForGetResource(final Integer resourceId, final R resource) {
        Map<String, ControllerLinkBuilder> links = new HashMap<>();

        links.put(HttpMethod.POST.name(), linkTo(methodOn(this.getClass()).POST(resource.getParentId(), resource)));
        links.put(HttpMethod.PUT.name(), linkTo(methodOn(this.getClass()).PUT(resource.getParentId(), resource.getId(), resource)));
        links.put(HttpMethod.DELETE.name(), linkTo(methodOn(this.getClass()).DELETE(resource.getParentId(), resource.getId())));

        return links;
    }

    /**
     * Provides a default way to handle GET requests on {@link student.laurens.novibackend.entities.AbstractOwnedWithParentEntity} resources.
     *
     * @param parentResourceId - Identifier of parent resource of the resource to retrieve.
     *
     * @throws ResourceNotFoundException - Thrown when parent resource or resource could not be found.
     * @throws ResourceForbiddenException - Thrown when parent resource or resource is not owned by current consumer of the API.
     *
     * @return List of resources.
     */
    public ResponseEntity<Resources<R>> getResources(final Integer parentResourceId) throws ResourceNotFoundException, ResourceForbiddenException {
        logProcessingStarted(HttpMethod.GET, parentResourceId);

        List<R> r = getService().getResources(parentResourceId, getConsumer());
        Resources resources = resourcesWithLinks(r, getLinksForGetResources(r));
        logProcessingFinished(HttpMethod.GET, parentResourceId);

        return createSuccessResponseGET(resources);
    }

    /**
     * Should be implemented by child-class, annotated with @GetMapping and call get() to handle GET requests.
     * Referred to in HATEOAS resource link building.
     *
     * @param parentResourceId - Identifier of parent resource of the resource to retrieve.
     *
     * @throws ResourceNotFoundException - Thrown when parent resource or resource could not be found.
     * @throws ResourceForbiddenException - Thrown when parent resource or resource is not owned by current consumer of the API.
     *
     * @return List of resources.
     */
    abstract public ResponseEntity<Resources<R>>  GET(final Integer parentResourceId) throws ResourceNotFoundException, ResourceForbiddenException;

    protected Map<String, ControllerLinkBuilder> getLinksForGetResources(final List<R> resources) {
        Map<String, ControllerLinkBuilder> links = new HashMap<>();

        for(R resource : resources){
            links.put(HttpMethod.GET.name(), linkTo(methodOn(this.getClass()).GET(resource.getParentId(), resource.getId())));
        }

        return links;
    }

    /**
     * Provides a default way to handle POST requests on {@link student.laurens.novibackend.entities.AbstractOwnedWithParentEntity} resources.
     *
     * @param parentResourceId - The identifier of the parent resource to create a child for.
     * @param resource - The new resource to be created.
     *
     * @throws ResourceNotFoundException - Thrown when parent resource or resource could not be found.
     * @throws ResourceForbiddenException - Thrown when parent resource or resource is not owned by current consumer of the API.
     * @throws ResourceNotFoundException - Thrown when to be created resource is a duplicate.
     *
     * @return Confirmation message.
     */
    public ResponseEntity<Resource<R>> create(final Integer parentResourceId, final R resource) throws ResourceNotFoundException, ResourceForbiddenException, ResourceDuplicateException {
        logProcessingStarted(HttpMethod.POST, parentResourceId);

        R r = getService().createResource(parentResourceId, resource, getConsumer());
        Resource<R> created = resourceWithLinks(r, getLinksForPostResource(resource));

        logProcessingFinished(HttpMethod.POST, parentResourceId);
        return createSuccessResponsePOST(created);
    }

    /**
     * Should be implemented by child-class, annotated with @PostMapping and call create(parentResourceId, resource) to handle POST requests.
     * Referred to in HATEOAS resource link building.
     *
     * @param parentResourceId - The identifier of the parent resource to create a child for.
     * @param resource - The new resource to be created.
     *
     * @throws ResourceNotFoundException - Thrown when to be created resource is a duplicate.
     *
     * @return Confirmation message.
     */
    abstract public ResponseEntity<Resource<R>> POST(final Integer parentResourceId, final R resource) throws ResourceDuplicateException;

    protected Map<String, ControllerLinkBuilder> getLinksForPostResource(R resource) {
        Map<String, ControllerLinkBuilder> links = new HashMap<>();

        links.put(HttpMethod.GET.name(), linkTo(methodOn(this.getClass()).GET(resource.getParentId(), resource.getId())));
        links.put(HttpMethod.PUT.name(), linkTo(methodOn(this.getClass()).PUT(resource.getParentId(), resource.getId(), resource)));
        links.put(HttpMethod.DELETE.name(), linkTo(methodOn(this.getClass()).DELETE(resource.getParentId(), resource.getId())));

        return links;
    }

    /**
     * Provides a default way to handle PUT requests on {@link student.laurens.novibackend.entities.AbstractOwnedWithParentEntity} resources.
     *
     * @param parentResourceId - Identifier of parent resource of the resource to update.
     * @param resourceId - Identifier of the resource to update.
     * @param resource - New state of the resource.
     *
     * @throws ResourceNotFoundException - Thrown when parent resource or resource could not be found.
     * @throws ResourceForbiddenException - Thrown when parent resource or resource is not owned by current consumer of the API.
     *
     * @return Confirmation message.
     */
    public ResponseEntity<Resource<R>> update(final Integer parentResourceId, final Integer resourceId, final R resource) throws ResourceNotFoundException, ResourceForbiddenException {
        logProcessingStarted(HttpMethod.PUT, parentResourceId, resourceId);

        R r = getService().updateResourceById(parentResourceId, resourceId, resource, getConsumer());
        Resource<R> updated = resourceWithLinks(r, getLinksForPutResource(r));

        logProcessingFinished(HttpMethod.PUT, parentResourceId, resourceId);
        return createSuccessResponsePUT(updated);
    }

    /**
     * Should be implemented by child-class, annotated with @PutMapping and call update(parentResourceId, resourceId, resource) to handle PUT requests.
     * Referred to in HATEOAS resource link building.
     *
     * @param parentResourceId - Identifier of parent resource of the resource to update.
     * @param resourceId - Identifier of the resource to update.
     * @param resource - New state of the resource.
     *
     * @throws ResourceNotFoundException - Thrown when resource could not be found.
     * @throws ResourceForbiddenException - Thrown when resource could is not owned by current consumer of the API.
     *
     * @return Confirmation message.
     */
    abstract public ResponseEntity<Resource<R>> PUT(final Integer parentResourceId, final Integer resourceId, final R resource) throws ResourceNotFoundException, ResourceForbiddenException;

    protected Map<String, ControllerLinkBuilder> getLinksForPutResource(R resource) {
        Map<String, ControllerLinkBuilder> links = new HashMap<>();

        links.put(HttpMethod.GET.name(), linkTo(methodOn(this.getClass()).GET(resource.getParentId(), resource.getId())));
        links.put(HttpMethod.POST.name(), linkTo(methodOn(this.getClass()).POST(resource.getParentId(), resource)));
        links.put(HttpMethod.DELETE.name(), linkTo(methodOn(this.getClass()).DELETE(resource.getParentId(), resource.getId())));

        return links;
    }

    /**
     * Provides a default way to handle DELETE requests on {@link student.laurens.novibackend.entities.AbstractOwnedWithParentEntity} resources.
     *
     * @param parentResourceId - Identifier of parent resource of the resource to delete.
     * @param resourceId - Identifier of the resource to delete.
     *
     * @throws ResourceNotFoundException - Thrown when parent resource or resource could not be found.
     * @throws ResourceForbiddenException - Thrown when parent resource or resource is not owned by current consumer of the API.
     *
     * @return Confirmation message.
     */
    public ResponseEntity<R> delete(final Integer parentResourceId, final Integer resourceId) throws ResourceNotFoundException, ResourceForbiddenException {
        logProcessingStarted(HttpMethod.DELETE, parentResourceId, resourceId);

        getService().deleteResourceById(parentResourceId, resourceId, getConsumer());
        Resource resource = resourceWithLinks(new Resource("Successfully deleted resource."), getLinksForDeleteResource(parentResourceId));

        logProcessingFinished(HttpMethod.DELETE, parentResourceId, resourceId);
        return createSuccessResponseDELETE(resource);
    }

    /**
     * Should be implemented by child-class, annotated with @DeleteMapping and call delete(parentResourceId, resourceId) to handle DELETE requests.
     * Referred to in HATEOAS resource link building.
     *
     * @param parentResourceId - Identifier of parent resource of the resource to delete.
     * @param resourceId - Identifier of the resource to delete.
     *
     * @throws ResourceNotFoundException - Thrown when resource could not be found.
     * @throws ResourceForbiddenException - Thrown when resource could is not owned by current consumer of the API.
     *
     * @return Confirmation message.
     */
    abstract public ResponseEntity<R> DELETE(final Integer parentResourceId, final Integer resourceId) throws ResourceNotFoundException, ResourceForbiddenException;

    protected Map<String, ControllerLinkBuilder> getLinksForDeleteResource(final Integer parentResourceId) {
        Map<String, ControllerLinkBuilder> links = new HashMap<>();

        links.put("GET All", linkTo(methodOn(this.getClass()).GET(parentResourceId)));

        return links;
    }

    protected void logProcessingStarted(final HttpMethod method, final Integer parentResourceId, final Integer resourceId) {
        log.info("Processing ["+method+"] request on resource ["+getService().getResourceClass()+"] with parentResourceId ["+parentResourceId+"] and resourceId ["+resourceId+"] started.");
    }
    protected void logProcessingFinished(final HttpMethod method, final Integer parentResourceId, final Integer resourceId) {
        log.info("Processing ["+method+"] request on resource ["+getService().getResourceClass()+"] with parentResourceId ["+parentResourceId+"] and resourceId ["+resourceId+"] successfully finished.");
    }
}
