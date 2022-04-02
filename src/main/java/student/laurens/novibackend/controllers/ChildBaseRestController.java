package student.laurens.novibackend.controllers;

import org.springframework.hateoas.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import student.laurens.novibackend.entities.*;
import student.laurens.novibackend.exceptions.ResourceNotFoundException;
import student.laurens.novibackend.exceptions.ResourceForbiddenException;
import student.laurens.novibackend.services.AppUserDetailsService;
import student.laurens.novibackend.services.ChildBaseService;

import java.util.List;

/**
 * Base class for RestControllers which expose CRUD methods for {@link AbstractOwnedWithParentEntity}.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
public abstract class ChildBaseRestController<R extends AbstractEntity, P extends AbstractEntity>
        extends BaseRestController<R>{

    public ChildBaseRestController(AppUserDetailsService appUserDetailsService) {
        super(appUserDetailsService);
    }

    @Override
    abstract protected ChildBaseService<R, P> getService();

    /**
     * Provides a default way to handle GET requests on {@link student.laurens.novibackend.entities.AbstractOwnedWithParentEntity} resources.
     * Should be implemented by resource specific controller classes.
     *
     * @param parentResourceId - Identifier of parent resource of the resource to retrieve.
     * @param resourceId - Identifier of the resource to retrieve.
     *
     * @throws ResourceNotFoundException - Thrown when parent resource or resource could not be found.
     * @throws ResourceForbiddenException - Thrown when parent resource or resource is not owned by current consumer of the API.
     * @return
     */
    public ResponseEntity<Resource<R>> get(final Integer parentResourceId, final Integer resourceId) throws ResourceNotFoundException, ResourceForbiddenException {
        logProcessingStarted(HttpMethod.GET, parentResourceId, resourceId);

        R r = getService().getResourceById(parentResourceId, resourceId, getConsumer());
        Resource<R> resource = resourceWithLinks(r, getLinksForGetResource(resourceId, r));

        logProcessingFinished(HttpMethod.GET, parentResourceId, resourceId);
        return createSuccessResponseGET(resource);
    }

    /**
     * Provides a default way to handle GET requests on {@link student.laurens.novibackend.entities.AbstractOwnedWithParentEntity} resources.
     * Should be implemented by resource specific controller classes.
     *
     * @param parentResourceId - Identifier of parent resource of the resource to retrieve.
     *
     * @throws ResourceNotFoundException - Thrown when parent resource or resource could not be found.
     * @throws ResourceForbiddenException - Thrown when parent resource or resource is not owned by current consumer of the API.
     */
    public ResponseEntity<List<R>> getResources(final Integer parentResourceId) throws ResourceNotFoundException, ResourceForbiddenException {
        logProcessingStarted(HttpMethod.GET, parentResourceId);

        List<R> resources = getService().getResources(parentResourceId, getConsumer());

        logProcessingFinished(HttpMethod.GET, parentResourceId);
        return createSuccessResponseGET(resources);
    }

    /**
     * Provides a default way to handle PUT requests on {@link student.laurens.novibackend.entities.AbstractOwnedWithParentEntity} resources.
     * Should be implemented by resource specific controller classes.
     *
     * @param parentResourceId - Identifier of parent resource of the resource to update.
     * @param resource - New state of the resource.
     *
     * @throws ResourceNotFoundException - Thrown when parent resource or resource could not be found.
     * @throws ResourceForbiddenException - Thrown when parent resource or resource is not owned by current consumer of the API.
     * @return
     */
    public ResponseEntity<Resource<R>> create(final Integer parentResourceId, final R resource) throws ResourceNotFoundException, ResourceForbiddenException {
        logProcessingStarted(HttpMethod.POST, parentResourceId);

        R r = getService().createResource(parentResourceId, resource, getConsumer());
        Resource<R> created = resourceWithLinks(r, getLinksForPostResource(resource));

        logProcessingFinished(HttpMethod.POST, parentResourceId);
        return createSuccessResponsePOST(created);
    }

    /**
     * Provides a default way to handle PUT requests on {@link student.laurens.novibackend.entities.AbstractOwnedWithParentEntity} resources.
     * Should be implemented by resource specific controller classes.
     *
     * @param parentResourceId - Identifier of parent resource of the resource to update.
     * @param resourceId - Identifier of the resource to update.
     * @param resource - New state of the resource.
     *
     * @throws ResourceNotFoundException - Thrown when parent resource or resource could not be found.
     * @throws ResourceForbiddenException - Thrown when parent resource or resource is not owned by current consumer of the API.
     */
    public ResponseEntity<R> update(final Integer parentResourceId, final Integer resourceId, final R resource) throws ResourceNotFoundException, ResourceForbiddenException {
        logProcessingStarted(HttpMethod.PUT, parentResourceId, resourceId);

        R updated = getService().updateResourceById(parentResourceId, resourceId, resource, getConsumer());

        logProcessingFinished(HttpMethod.PUT, parentResourceId, resourceId);
        return createSuccessResponsePUT(updated);
    }

    /**
     * Provides a default way to handle DELETE requests on {@link student.laurens.novibackend.entities.AbstractOwnedWithParentEntity} resources.
     * Should be implemented by resource specific controller classes.
     *
     * @param parentResourceId - Identifier of parent resource of the resource to delete.
     * @param resourceId - Identifier of the resource to delete.
     *
     * @throws ResourceNotFoundException - Thrown when parent resource or resource could not be found.
     * @throws ResourceForbiddenException - Thrown when parent resource or resource is not owned by current consumer of the API.
     */
    public ResponseEntity<R> delete(final Integer parentResourceId, final Integer resourceId) throws ResourceNotFoundException, ResourceForbiddenException {
        logProcessingStarted(HttpMethod.DELETE, parentResourceId, resourceId);

        getService().deleteResourceById(parentResourceId, resourceId, getConsumer());

        logProcessingFinished(HttpMethod.DELETE, parentResourceId, resourceId);
        return createSuccessResponseDELETE();
    }

    protected void logProcessingStarted(final HttpMethod method, final Integer parentResourceId, final Integer resourceId) {
        log.info("Processing ["+method+"] request on resource ["+getService().getResourceClass()+"] with parentResourceId ["+parentResourceId+"] and resourceId ["+resourceId+"] started.");
    }

    protected void logProcessingFinished(final HttpMethod method, final Integer parentResourceId, final Integer resourceId) {
        log.info("Processing ["+method+"] request on resource ["+getService().getResourceClass()+"] with parentResourceId ["+parentResourceId+"] and resourceId ["+resourceId+"] successfully finished.");
    }
}
