package student.laurens.novibackend.unit.services;

import org.junit.Test;
import student.laurens.novibackend.entities.AbstractEntity;
import student.laurens.novibackend.entities.User;
import student.laurens.novibackend.services.ParentResourceBaseService;
import student.laurens.novibackend.services.PermissionPolicy;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class ParentServiceUnitTestBase<R extends AbstractEntity> extends OwnedServiceUnitTestBase {

    abstract protected ParentResourceBaseService<R> getService();

    protected abstract PermissionPolicy get_expected_is_read_on_child_permitted_admin();
    protected abstract PermissionPolicy get_expected_is_create_on_child_permitted_admin();
    protected abstract PermissionPolicy get_expected_is_update_on_child_permitted_admin();
    protected abstract PermissionPolicy get_expected_is_delete_on_child_permitted_admin();


    @Test
    public void is_read_on_child_permitted_admin(){
        // given
        User consumer = consumer(createRole(ADMIN_ROLE));

        // when
        PermissionPolicy policy = getService().isReadOnChildPermitted(consumer);

        // then
        assertThat(policy).isEqualTo(get_expected_is_read_on_child_permitted_admin());
    }

    @Test
    public void is_create_on_child_permitted_admin(){
        // given
        User consumer = consumer(createRole(ADMIN_ROLE));

        // when
        PermissionPolicy policy = getService().isCreateChildPermitted(consumer);

        // then
        assertThat(policy).isEqualTo(get_expected_is_create_on_child_permitted_admin());
    }

    @Test
    public void is_update_on_child_permitted_admin(){
        // given
        User consumer = consumer(createRole(ADMIN_ROLE));

        // when
        PermissionPolicy policy = getService().isUpdateOnChildPermitted(consumer);

        // then
        assertThat(policy).isEqualTo(get_expected_is_update_on_child_permitted_admin());
    }

    @Test
    public void is_delete_on_child_permitted_admin(){
        // given
        User consumer = consumer(createRole(ADMIN_ROLE));

        // when
        PermissionPolicy policy = getService().isDeleteOnChildPermitted(consumer);

        // then
        assertThat(policy).isEqualTo(get_expected_is_delete_on_child_permitted_admin());
    }
}
