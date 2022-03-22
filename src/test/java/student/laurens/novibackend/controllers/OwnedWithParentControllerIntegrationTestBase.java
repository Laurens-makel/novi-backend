package student.laurens.novibackend.controllers;

import org.junit.After;
import org.junit.Before;
import student.laurens.novibackend.entities.AbstractOwnedEntity;
import student.laurens.novibackend.entities.User;
import student.laurens.novibackend.repositories.ResourceRepository;

public abstract class OwnedWithParentControllerIntegrationTestBase<R extends AbstractOwnedEntity, P extends AbstractOwnedEntity>
        extends TestBase<R> {


    abstract protected ResourceRepository<R> getRepository();
    abstract protected ResourceRepository<P> getParentRepository();

    @After
    public void base_after(){
        log.debug("Deleting all users from repository.");
        getRepository().deleteAll();
        getParentRepository().deleteAll();

        userRepository.deleteAll();
    }

    @Before
    public void base_before(){
        log.debug("Deleting all users from repository.");
        getRepository().deleteAll();
        getParentRepository().deleteAll();

        userRepository.deleteAll();
    }

    /**
     * Implement this method by a resource specific implementation to create sample instances of the parent resource.
     *
     * @return Sample instance of parent resource.
     */
    abstract protected P createParent();

    protected P saveParent(final P parentResource){
        getParentRepository().save(parentResource);
        return parentResource;
    }

    /**
     * Implement this method by a resource specific implementation to modify a sample instances of the parent resource.
     *
     * @return Sample instance of parent resource.
     */
    abstract protected P modifyParent(final P parentResource);

    /**
     * Implement this method by a resource specific implementation to create sample owned instances of the parent resource.
     *-
     * @return Sample instance of parent resource.
     */
    abstract protected P createOwnedParent(User owner);

    /**
     * Implement this method by a resource specific implementation to create sample not-owned instances of the resource.
     *
     * @return Sample instance of parent resource.
     */
    abstract protected P createNotOwnedParent();
}
