package student.laurens.novibackend.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import student.laurens.novibackend.users.Role;
import student.laurens.novibackend.users.RoleRepository;
import student.laurens.novibackend.users.User;
import student.laurens.novibackend.users.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:application-integration-test.properties")
public class RoleRepositoryIntegrationTest {

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
}
