package student.laurens.novibackend.repositories;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import student.laurens.novibackend.entities.Role;

import static org.assertj.core.api.Assertions.assertThat;

public class RoleRepositoryIntegrationTest extends RepositoryIntegrationTestBase {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RoleRepository repository;

    @Test
    public void whenFindByName_Admin_thenReturnRole() {
        // when
        Role found = repository.getRoleByName("ADMIN");

        // then
        assertThat(found.getName()).isEqualTo("ADMIN");
    }

    @Test
    public void whenFindByName_User_thenReturnRole() {
        // when
        Role found = repository.getRoleByName("USER");

        // then
        assertThat(found.getName()).isEqualTo("USER");
    }

    @Test
    public void whenFindByName_Unknown_thenReturnRole() {
        // when
        Role found = repository.getRoleByName("UNKNOWN");

        // then
        assertThat(found).isEqualTo(null);
    }

    @Test
    public void whenAddRole_thenReturnRole() {
        // given
        Role role = new Role();
        role.setName("TEST_ROLE");

        // when
        repository.save(role);
        Role found = repository.getRoleByName(role.getName());

        // then
        assertThat(found.getName()).isEqualTo(role.getName());
    }
}
