package student.laurens.novibackend.unit.services;

import org.junit.Test;
import student.laurens.novibackend.entities.AbstractEntity;
import student.laurens.novibackend.entities.User;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Base class to provide default methods for testing Service.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
public abstract class OwnedServiceUnitTestBase<R extends AbstractEntity> extends UnitTestBase<R> {

    abstract protected R create(User owner);

    @Test
    public void get_resource_owned_admin() {
        // given
        R resource = create();
        mockResourceGetById(resource);
        mockResourceExistsById(resource);

        // when
        R found = getService().getResourceById(resource.getId(), consumer());

        // then
        assertThat(found.getId()).isEqualTo(resource.getId());
        verifyGetOneIsCalledOnce(resource.getId());
    }


    @Test
    public void create_resource_owned_admin() {
        // given
        R resource = create(consumer(createRole("ADMIN")));

        // when
        getService().createResource(resource);

        // then
        verifySaveIsCalledOnce(resource);
    }

    @Test
    public void update_resource_owned_admin() {
        // given
        User consumer = consumer(createRole("ADMIN"));
        R resource = create(consumer);
        mockResourceGetById(resource);
        mockResourceExistsById(resource);

        // when
        getService().updateResourceById(resource.getId(), resource, consumer);

        // then
        verifyExistsByIdIsCalledOnce(resource.getId());
        verifyGetOneIsCalledOnce(resource.getId());
        verifySaveIsCalledOnce(resource);
    }

    @Test
    public void delete_resource_owned_admin() {
        // given
        User consumer = consumer(createRole("ADMIN"));
        R resource = create(consumer);
        mockResourceGetById(resource);
        mockResourceExistsById(resource);

        // when
        getService().deleteResourceById(resource.getId(), consumer);

        // then
        verifyExistsByIdIsCalledOnce(resource.getId());
        verifyGetOneIsCalledOnce(resource.getId());
        verifyDeleteByIdIsCalledOnce(resource.getId());
    }
}
