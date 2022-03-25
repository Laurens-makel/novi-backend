package student.laurens.novibackend.controllers;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import student.laurens.novibackend.entities.*;
import student.laurens.novibackend.exceptions.ResourceNotFoundException;
import student.laurens.novibackend.exceptions.ResourceNotOwnedException;
import student.laurens.novibackend.services.ChildBaseService;

/**
 * Base class for RestControllers which expose CRUD methods for {@link AbstractOwnedWithParentEntity}.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
public abstract class ChildBaseRestController<R extends AbstractEntity, P extends AbstractEntity>
        extends BaseRestController<R>{

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
     * @throws ResourceNotOwnedException - Thrown when parent resource or resource is not owned by current consumer of the API.
     */
    public ResponseEntity<R> get(final Integer parentResourceId, final Integer resourceId) throws ResourceNotFoundException, ResourceNotOwnedException {
        logProcessingStarted(HttpMethod.GET, parentResourceId, resourceId);

        R resource = getService().getResourceById(parentResourceId, resourceId, getConsumer());

        logProcessingFinished(HttpMethod.GET, parentResourceId, resourceId);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }


    /**
     * Provides a default way to handle GET requests on {@link student.laurens.novibackend.entities.AbstractOwnedWithParentEntity} resources.
     * Should be implemented by resource specific controller classes.
     *
     * @param parentResourceId - Identifier of parent resource of the resource to retrieve.
     *
     * @throws ResourceNotFoundException - Thrown when parent resource or resource could not be found.
     * @throws ResourceNotOwnedException - Thrown when parent resource or resource is not owned by current consumer of the API.
     */
    public ResponseEntity<Iterable<R>> getResources(final Integer parentResourceId) throws ResourceNotFoundException, ResourceNotOwnedException {
        logProcessingStarted(HttpMethod.GET, parentResourceId);

        Iterable<R> resources = getService().getResources(parentResourceId, getConsumer());

        logProcessingFinished(HttpMethod.GET, parentResourceId);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }



    /**
     * Provides a default way to handle PUT requests on {@link student.laurens.novibackend.entities.AbstractOwnedWithParentEntity} resources.
     * Should be implemented by resource specific controller classes.
     *
     * @param parentResourceId - Identifier of parent resource of the resource to update.
     * @param parentResourceId - Identifier of the parent resource of the resource to create.
     * @param resource - New state of the resource.
     *
     * @throws ResourceNotFoundException - Thrown when parent resource or resource could not be found.
     * @throws ResourceNotOwnedException - Thrown when parent resource or resource is not owned by current consumer of the API.
     */
    public ResponseEntity<R> create(final Integer parentResourceId, final R resource) throws ResourceNotFoundException, ResourceNotOwnedException {
        logProcessingStarted(HttpMethod.POST, parentResourceId);

        R created = getService().createResource(parentResourceId, resource, getConsumer());

        logProcessingFinished(HttpMethod.POST, parentResourceId);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
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
     * @throws ResourceNotOwnedException - Thrown when parent resource or resource is not owned by current consumer of the API.
     */
    public ResponseEntity<R> update(final Integer parentResourceId, final Integer resourceId, final R resource) throws ResourceNotFoundException, ResourceNotOwnedException {
        logProcessingStarted(HttpMethod.PUT, parentResourceId, resourceId);

        R updated = getService().updateResourceById(parentResourceId, resourceId, resource, getConsumer());

        logProcessingFinished(HttpMethod.PUT, parentResourceId, resourceId);
        return new ResponseEntity<>(updated, HttpStatus.ACCEPTED);
    }

    /**
     * Provides a default way to handle DELETE requests on {@link student.laurens.novibackend.entities.AbstractOwnedWithParentEntity} resources.
     * Should be implemented by resource specific controller classes.
     *
     * @param parentResourceId - Identifier of parent resource of the resource to delete.
     * @param resourceId - Identifier of the resource to delete.
     *
     * @throws ResourceNotFoundException - Thrown when parent resource or resource could not be found.
     * @throws ResourceNotOwnedException - Thrown when parent resource or resource is not owned by current consumer of the API.
     */
    public ResponseEntity<R> delete(final Integer parentResourceId, final Integer resourceId) throws ResourceNotFoundException, ResourceNotOwnedException {
        logProcessingStarted(HttpMethod.DELETE, parentResourceId, resourceId);

        getService().deleteResourceById(parentResourceId, resourceId, getConsumer());

        logProcessingFinished(HttpMethod.DELETE, parentResourceId, resourceId);
        return new ResponseEntity(createDeletedMessage(), HttpStatus.ACCEPTED);
    }

    protected void logProcessingStarted(final HttpMethod method, final Integer parentResourceId, final Integer resourceId) {
        log.info("Processing ["+method+"] request on resource ["+getService().getResourceClass()+"] with parentResourceId ["+parentResourceId+"] and resourceId ["+resourceId+"] started.");
    }

    protected void logProcessingFinished(final HttpMethod method, final Integer parentResourceId, final Integer resourceId) {
        log.info("Processing ["+method+"] request on resource ["+getService().getResourceClass()+"] with parentResourceId ["+parentResourceId+"] and resourceId ["+resourceId+"] successfully finished.");
    }
}
