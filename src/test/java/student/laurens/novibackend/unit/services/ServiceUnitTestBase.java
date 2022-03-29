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
public abstract class ServiceUnitTestBase<R extends AbstractEntity> extends UnitTestBase<R> {

    @Test
    public void get_resource() {
        // given
        R resource = create();
        mockResourceGetById(resource);

        // when
        R found = getService().getResourceById(resource.getId(), consumer());

        // then
        assertThat(found.getId()).isEqualTo(resource.getId());
        verifyGetOneIsCalledOnce(resource.getId());
    }

    @Test
    public void create_resource() {
        // given
        R resource = create();

        // when
        getService().createResource(resource);

        // then
        verifySaveIsCalledOnce(resource);
    }

    @Test
    public void update_resource() {
        // given
        User consumer = consumer();
        R resource = create();
        mockResourceExistsById(resource);

        // when
        getService().updateResourceById(resource.getId(), resource, consumer);

        // then
        verifyExistsByIdIsCalledOnce(resource.getId());
        verifySaveIsCalledOnce(resource);
    }

    @Test
    public void delete_resource() {
        // Given
        R resource = create();
        User consumer = consumer();
        mockResourceExistsById(resource);

        // when
        getService().deleteResourceById(resource.getId(), consumer);

        // then
        verifyExistsByIdIsCalledOnce(resource.getId());
        verifyDeleteByIdIsCalledOnce(resource.getId());
    }
}
