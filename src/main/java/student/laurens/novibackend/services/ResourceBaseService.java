package student.laurens.novibackend.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpMethod;
import student.laurens.novibackend.entities.AbstractEntity;
import student.laurens.novibackend.entities.AbstractOwnedEntity;
import student.laurens.novibackend.entities.User;
import student.laurens.novibackend.exceptions.ResourceDuplicateException;
import student.laurens.novibackend.exceptions.ResourceNotFoundException;
import student.laurens.novibackend.exceptions.ResourceForbiddenException;
import student.laurens.novibackend.repositories.ResourceRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Base class for Services which expose CRUD methods for {@link AbstractEntity}.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
public abstract class ResourceBaseService<R extends AbstractEntity> extends BaseService<R> {

    protected Logger log = LoggerFactory.getLogger(ResourceBaseService.class);

    /**
     * Retrieves a resource from repository, specified by id.
     *
     * @param resourceId - Identifier of the resource to retrieve.
     * @param consumer - User which has the intention of interacting with the resource.
     *
     * @throws ResourceNotFoundException - Thrown when resource could not be found.
     */
    public R getResourceById(final Integer resourceId, final User consumer) throws ResourceNotFoundException {
        log.info("Processing started for get request for resourceId ["+resourceId+"], request by ["+consumer.getUsername()+"]");

        R resource = validateOwnershipOfResource(resourceId, HttpMethod.GET, consumer)
                .orElseGet(() -> {
                    log.info("Resource with ID [" + resourceId + "] has not yet been found during ownership validation for user [" + consumer.getUsername() + "].");
                    return getResourceByIdWithoutValidations(resourceId);
                });

        log.info("Processing finished with success for get request for resourceId ["+resourceId+"], request by ["+consumer.getUsername()+"]");
        return resource;
    }

    /**
     * Retrieves a list of resources from repository.
     */
    public List<R> getResources(){
        Iterable<R> resourcesIterable = getRepository().findAll();

        return StreamSupport.stream(resourcesIterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    /**
     * Creates a resource in repository.
     *
     * @param resource - The state of the resource to save.
     */
    public R createResource(final R resource){
       return create(resource);
    };

    /**
     * Updates a resource in repository, specified by resource id.
     *
     * @param resourceId - Identifier of the resource to update.
     * @param resource - The state of the resource to save.
     * @param consumer - User which has the intention of interacting with the resource.
     *
     * @throws ResourceNotFoundException - Thrown when resource could not be found.
     * @throws ResourceForbiddenException - Thrown when resource could is not owned by current consumer of service.
     */
    public R updateResourceById(final Integer resourceId, final R resource, final User consumer) throws ResourceNotFoundException, ResourceForbiddenException {
        log.info("Processing started for update request for resourceId ["+resourceId+"], request by ["+consumer.getUsername()+"]");
        exists(resourceId);
        validateOwnershipOfResource(resourceId, HttpMethod.PUT, consumer);
        log.info("Processing finished with success for update request for resourceId ["+resourceId+"], request by ["+consumer.getUsername()+"]");
        return getRepository().save(resource);
    }

    /**
     * Deletes a resource from repository, specified by resource id.
     *
     * @param resourceId - Identifier of the resource to delete.
     * @param consumer - User which has the intention of interacting with the resource.
     *
     * @throws ResourceNotFoundException - Thrown when resource could not be found.
     * @throws ResourceForbiddenException - Thrown when resource could is not owned by current consumer of service.
     */
    public void deleteResourceById(final Integer resourceId, final User consumer) throws ResourceNotFoundException, ResourceForbiddenException {
        log.info("Processing started for delete request for resourceId ["+resourceId+"], request by ["+consumer.getUsername()+"]");
        exists(resourceId);
        validateOwnershipOfResource(resourceId, HttpMethod.DELETE, consumer);
        getRepository().deleteById(resourceId);
        log.info("Processing finished with success for delete request for resourceId ["+resourceId+"], request by ["+consumer.getUsername()+"]");
    };


}
