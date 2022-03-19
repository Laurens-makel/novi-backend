package student.laurens.novibackend.repositories;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import student.laurens.novibackend.entities.Role;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class to test actions on {@link RoleRepository}.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
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
    public void whenFindByName_ContentCreator_thenReturnRole() {
        // when
        Role found = repository.getRoleByName("CONTENT_CREATOR");

        // then
        assertThat(found.getName()).isEqualTo("CONTENT_CREATOR");
    }

    @Test
    public void whenFindByName_Moderator_thenReturnRole() {
        // when
        Role found = repository.getRoleByName("MODERATOR");

        // then
        assertThat(found.getName()).isEqualTo("MODERATOR");
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

    @Test
    public void whenUpdateRole_thenReturnRole() {
        // given
        Role role = createRole("TEST_ROLE");

        role.setName("UPDATED_ROLE");

        // when
        repository.save(role);
        Role found = entityManager.find(Role.class, role.getId());

        // then
        assertThat(found.getName()).isEqualTo(role.getName());
    }

    @Test
    public void whenDeleteRole_thenReturnNull() {
        // given
        Role role = createRole("TEST_ROLE");

        // when
        repository.delete(role);
        Role found = entityManager.find(Role.class, role.getId());

        // then
        assertThat(found).isEqualTo(null);
    }

    private Role createRole(String rolename){
        Role role = new Role();
        role.setName(rolename);

        entityManager.persistAndFlush(role);

        return role;
    }
}
