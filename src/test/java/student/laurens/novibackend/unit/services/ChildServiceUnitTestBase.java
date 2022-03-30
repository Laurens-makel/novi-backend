package student.laurens.novibackend.unit.services;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import student.laurens.novibackend.entities.AbstractEntity;
import student.laurens.novibackend.entities.AbstractOwnedEntity;
import student.laurens.novibackend.entities.User;
import student.laurens.novibackend.exceptions.ResourceForbiddenException;
import student.laurens.novibackend.services.ChildBaseService;
import student.laurens.novibackend.services.ParentBaseService;
import student.laurens.novibackend.services.PermissionPolicy;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Base class to provide default methods for testing Service for child resources.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
public abstract class ChildServiceUnitTestBase<R extends AbstractEntity,P extends AbstractOwnedEntity> extends UnitTestBase<R> {

    abstract protected ChildBaseService<R, P> getService();
    abstract protected ParentBaseService<P> getParentService();

    protected void mockParentResourceExists(P parentResource){
        Mockito.when(getParentService().exists(parentResource.getId())).thenReturn(true);
    }

    protected void verifyParentExistsIsCalledOnce(Integer parentResourceId) {
        Mockito.verify(getParentService(), VerificationModeFactory.times(1)).exists(parentResourceId);
    }
    protected void verifyIsReadOnChildPermittedIsCalledOnce(User consumer) {
        Mockito.verify(getParentService(), VerificationModeFactory.times(1)).isReadOnChildPermitted(consumer);
    }
    protected void verifyIsCreateOnChildPermittedIsCalledOnce(User consumer) {
        Mockito.verify(getParentService(), VerificationModeFactory.times(1)).isCreateChildPermitted(consumer);
    }
    protected void verifyIsUpdateOnChildPermittedIsCalledOnce(User consumer) {
        Mockito.verify(getParentService(), VerificationModeFactory.times(1)).isUpdateOnChildPermitted(consumer);
    }
    protected void verifyIsDeleteOnChildPermittedIsCalledOnce(User consumer) {
        Mockito.verify(getParentService(), VerificationModeFactory.times(1)).isDeleteOnChildPermitted(consumer);
    }
    /**
     * Implement this method by a resource specific implementation to create sample instances of the parent resource.
     *
     * @return Sample instance of parent resource.
     */
    abstract protected P createParent();

    /**
     * Implement this method by a resource specific implementation to create sample owned instances of the parent resource.
     *-
     * @return Sample instance of parent resource.
     */
    abstract protected P createOwnedParent(User owner);
    protected P createNotOwnedParent(){
        return createOwnedParent(createUniqueContentCreator());
    }

    /**
     * Implement this method by a resource specific implementation to create sample owned instances of the resource.
     *-
     * @return Sample instance of resource.
     */
    abstract protected R createOwned(P parentResource, User owner);
    protected R createNotOwned(P parentResource){
        return createOwned(parentResource, createUniqueContentCreator() );
    }

    @Override
    protected R create(){
        return createNotOwned(createNotOwnedParent());
    }

    @Test
    public void get_resource_owned_parent_owned_admin() {
        // given
        User consumer = consumer(createRole("ADMIN"));
        P parentResource = createOwnedParent(consumer);
        R resource = createOwned(parentResource, consumer);

        mockResourceGetById(resource);
        mockResourceExistsById(resource);
        mockParentResourceExists(parentResource);
        Mockito.when(getParentService().isReadOnChildPermitted(consumer)).thenReturn(PermissionPolicy.ALLOW);

        // when
        R found = getService().getResourceById(parentResource.getId(), resource.getId(), consumer);

        // then
        assertThat(found.getId()).isEqualTo(resource.getId());
        verifyIsReadOnChildPermittedIsCalledOnce(consumer);
        verifyGetOneIsCalledOnce(resource.getId());
        verifyExistsByIdIsCalledOnce(resource.getId());
        verifyParentExistsIsCalledOnce(parentResource.getId());
    }

    @Test
    public void get_resource_owned_parent_not_owned_admin() {
        // given
        User consumer = consumer(createRole("ADMIN"));
        User parentOwner = createDefaultContentCreator();
        P parentResource = createOwnedParent(parentOwner);
        R resource = createOwned(parentResource, consumer);

        mockResourceGetById(resource);
        mockResourceExistsById(resource);
        mockParentResourceExists(parentResource);
        Mockito.when(getParentService().isReadOnChildPermitted(consumer)).thenReturn(PermissionPolicy.ALLOW);

        // when
        R found = getService().getResourceById(parentResource.getId(), resource.getId(), consumer);

        // then
        assertThat(found.getId()).isEqualTo(resource.getId());
        verifyIsReadOnChildPermittedIsCalledOnce(consumer);
        verifyGetOneIsCalledOnce(resource.getId());
        verifyExistsByIdIsCalledOnce(resource.getId());
        verifyParentExistsIsCalledOnce(parentResource.getId());
    }

    @Test
    public void get_resource_not_owned_parent_not_owned_admin() {
        // given
        User consumer = consumer(createRole("ADMIN"));
        User owner = createDefaultContentCreator();
        User parentOwner = createDefaultContentCreator();
        P parentResource = createOwnedParent(parentOwner);
        R resource = createOwned(parentResource, owner);

        mockResourceGetById(resource);
        mockResourceExistsById(resource);
        mockParentResourceExists(parentResource);
        Mockito.when(getParentService().isReadOnChildPermitted(consumer)).thenReturn(PermissionPolicy.ALLOW);

        // when
        R found = getService().getResourceById(parentResource.getId(), resource.getId(), consumer);

        // then
        assertThat(found.getId()).isEqualTo(resource.getId());
        verifyIsReadOnChildPermittedIsCalledOnce(consumer);
        verifyGetOneIsCalledOnce(resource.getId());
        verifyExistsByIdIsCalledOnce(resource.getId());
        verifyParentExistsIsCalledOnce(parentResource.getId());
    }


    @Test
    public void create_resource_owned_parent_owned_admin() {
        // given
        User consumer = consumer(createRole("ADMIN"));
        P parentResource = createOwnedParent(consumer);
        R resource = createOwned(parentResource, consumer);

        mockResourceGetById(resource);
        mockResourceExistsById(resource);
        mockParentResourceExists(parentResource);
        Mockito.when(getParentService().isCreateChildPermitted(consumer)).thenReturn(PermissionPolicy.ALLOW);

        // when
        R created = getService().createResource(parentResource.getId(), resource, consumer);

        // then
        verifyIsCreateOnChildPermittedIsCalledOnce(consumer);
        verifyParentExistsIsCalledOnce(parentResource.getId());
        verifyIsCreateOnChildPermittedIsCalledOnce(consumer);
        verifySaveIsCalledOnce(resource);
    }

    @Test
    public void create_resource_owned_parent_not_owned_admin() {
        // given
        User parentOwner = createDefaultContentCreator();
        User consumer = consumer(createRole("ADMIN"));
        P parentResource = createOwnedParent(parentOwner);
        R resource = createOwned(parentResource, consumer);

        mockResourceGetById(resource);
        mockResourceExistsById(resource);
        mockParentResourceExists(parentResource);
        Mockito.when(getParentService().isCreateChildPermitted(consumer)).thenReturn(PermissionPolicy.ALLOW);

        // when
        R created = getService().createResource(parentResource.getId(), resource, consumer);

        // then
        verifyIsCreateOnChildPermittedIsCalledOnce(consumer);
        verifyParentExistsIsCalledOnce(parentResource.getId());
        verifyIsCreateOnChildPermittedIsCalledOnce(consumer);
        verifySaveIsCalledOnce(resource);
    }

    @Test
    public void update_resource_owned_parent_owned_admin() {
        // given
        User consumer = consumer(createRole("ADMIN"));
        P parentResource = createOwnedParent(consumer);
        R resource = createOwned(parentResource, consumer);

        mockResourceGetById(resource);
        mockResourceExistsById(resource);
        mockParentResourceExists(parentResource);
        Mockito.when(getParentService().isUpdateOnChildPermitted(consumer)).thenReturn(PermissionPolicy.ALLOW);

        // when
        getService().updateResourceById(parentResource.getId(), resource.getId(), resource, consumer);

        // then
        verifyIsUpdateOnChildPermittedIsCalledOnce(consumer);
        verifyParentExistsIsCalledOnce(parentResource.getId());
        verifyExistsByIdIsCalledOnce(resource.getId());
        verifySaveIsCalledOnce(resource);
    }

    @Test
    public void update_resource_owned_parent_not_owned_admin() {
        // given
        User parentOwner = createDefaultContentCreator();
        User consumer = consumer(createRole("ADMIN"));
        P parentResource = createOwnedParent(parentOwner);
        R resource = createOwned(parentResource, consumer);

        mockResourceGetById(resource);
        mockResourceExistsById(resource);
        mockParentResourceExists(parentResource);
        Mockito.when(getParentService().isUpdateOnChildPermitted(consumer)).thenReturn(PermissionPolicy.ALLOW);

        // when
        getService().updateResourceById(parentResource.getId(), resource.getId(), resource, consumer);

        // then
        verifyIsUpdateOnChildPermittedIsCalledOnce(consumer);
        verifyParentExistsIsCalledOnce(parentResource.getId());
        verifyExistsByIdIsCalledOnce(resource.getId());
        verifySaveIsCalledOnce(resource);
    }

    @Test
    public void update_resource_not_owned_parent_not_owned_admin() {
        // given
        User parentOwner = createDefaultContentCreator();
        User owner = createDefaultContentCreator();
        User consumer = consumer(createRole("ADMIN"));
        P parentResource = createOwnedParent(parentOwner);
        R resource = createOwned(parentResource, owner);

        mockResourceGetById(resource);
        mockResourceExistsById(resource);
        mockParentResourceExists(parentResource);
        Mockito.when(getParentService().isUpdateOnChildPermitted(consumer)).thenReturn(PermissionPolicy.ALLOW);

        // when
        getService().updateResourceById(parentResource.getId(), resource.getId(), resource, consumer);

        // then
        verifyIsUpdateOnChildPermittedIsCalledOnce(consumer);
        verifyParentExistsIsCalledOnce(parentResource.getId());
        verifyExistsByIdIsCalledOnce(resource.getId());
        verifySaveIsCalledOnce(resource);
    }

    @Test
    public void delete_resource_owned_parent_owned_user() {
        // given
        User consumer = consumer(createRole("USER"));
        P parentResource = createOwnedParent(consumer);
        R resource = createOwned(parentResource, consumer);

        mockResourceGetById(resource);
        mockResourceExistsById(resource);
        mockParentResourceExists(parentResource);
        Mockito.when(getParentService().isDeleteOnChildPermitted(consumer)).thenReturn(PermissionPolicy.ALLOW_CHILD_OWNED);

        // when
        getService().deleteResourceById(parentResource.getId(), resource.getId(), consumer);

        // then
        verifyIsDeleteOnChildPermittedIsCalledOnce(consumer);
        verifyParentExistsIsCalledOnce(parentResource.getId());
        verifyExistsByIdIsCalledOnce(resource.getId());
        verifyDeleteByIdIsCalledOnce(resource.getId());
    }

    @Test(expected = ResourceForbiddenException.class)
    public void delete_resource_not_owned_parent_owned_user() {
        // given
        User consumer = consumer(createRole("USER"));
        User owner = createDefaultContentCreator();
        P parentResource = createOwnedParent(consumer);
        R resource = createOwned(parentResource, owner);

        mockResourceGetById(resource);
        mockResourceExistsById(resource);
        mockParentResourceExists(parentResource);
        Mockito.when(getParentService().isDeleteOnChildPermitted(consumer)).thenReturn(PermissionPolicy.ALLOW_CHILD_OWNED);

        // when
        getService().deleteResourceById(parentResource.getId(), resource.getId(), consumer);

        // then expect ResourceForbiddenException
    }

    @Test(expected = ResourceForbiddenException.class)
    public void delete_resource_not_owned_parent_not_owned_user() {
        // given
        User consumer = consumer(createRole("USER"));
        User owner = createDefaultContentCreator();
        User parentOwner = createDefaultContentCreator();
        P parentResource = createOwnedParent(parentOwner);
        R resource = createOwned(parentResource, owner);

        mockResourceGetById(resource);
        mockResourceExistsById(resource);
        mockParentResourceExists(parentResource);
        Mockito.when(getParentService().isDeleteOnChildPermitted(consumer)).thenReturn(PermissionPolicy.ALLOW_CHILD_OWNED);

        // when
        getService().deleteResourceById(parentResource.getId(), resource.getId(), consumer);

        // then expect ResourceForbiddenException
    }

    @Test
    public void delete_resource_owned_parent_owned_admin() {
        // given
        User consumer = consumer(createRole("ADMIN"));
        P parentResource = createOwnedParent(consumer);
        R resource = createOwned(parentResource, consumer);

        mockResourceGetById(resource);
        mockResourceExistsById(resource);
        mockParentResourceExists(parentResource);
        Mockito.when(getParentService().isDeleteOnChildPermitted(consumer)).thenReturn(PermissionPolicy.ALLOW);

        // when
        getService().deleteResourceById(parentResource.getId(), resource.getId(), consumer);

        // then
        verifyIsDeleteOnChildPermittedIsCalledOnce(consumer);
        verifyParentExistsIsCalledOnce(parentResource.getId());
        verifyExistsByIdIsCalledOnce(resource.getId());
        verifyDeleteByIdIsCalledOnce(resource.getId());
    }

    @Test
    public void delete_resource_owned_parent_not_owned_admin() {
        // given
        User parentOwner = createDefaultContentCreator();
        User consumer = consumer(createRole("ADMIN"));
        P parentResource = createOwnedParent(parentOwner);
        R resource = createOwned(parentResource, consumer);

        mockResourceGetById(resource);
        mockResourceExistsById(resource);
        mockParentResourceExists(parentResource);
        Mockito.when(getParentService().isDeleteOnChildPermitted(consumer)).thenReturn(PermissionPolicy.ALLOW);

        // when
        getService().deleteResourceById(parentResource.getId(), resource.getId(), consumer);

        // then
        verifyIsDeleteOnChildPermittedIsCalledOnce(consumer);
        verifyParentExistsIsCalledOnce(parentResource.getId());
        verifyExistsByIdIsCalledOnce(resource.getId());
        verifyDeleteByIdIsCalledOnce(resource.getId());
    }

    @Test
    public void delete_resource_not_owned_parent_not_owned_admin() {
        // given
        User parentOwner = createDefaultContentCreator();
        User owner = createDefaultContentCreator();
        User consumer = consumer(createRole("ADMIN"));
        P parentResource = createOwnedParent(parentOwner);
        R resource = createOwned(parentResource, owner);

        mockResourceGetById(resource);
        mockResourceExistsById(resource);
        mockParentResourceExists(parentResource);
        Mockito.when(getParentService().isDeleteOnChildPermitted(consumer)).thenReturn(PermissionPolicy.ALLOW);

        // when
        getService().deleteResourceById(parentResource.getId(), resource.getId(), consumer);

        // then
        verifyIsDeleteOnChildPermittedIsCalledOnce(consumer);
        verifyParentExistsIsCalledOnce(parentResource.getId());
        verifyExistsByIdIsCalledOnce(resource.getId());
        verifyDeleteByIdIsCalledOnce(resource.getId());
    }
}
