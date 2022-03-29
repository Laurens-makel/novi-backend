package student.laurens.novibackend.unit.repositories;

import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import student.laurens.novibackend.entities.User;
import student.laurens.novibackend.repositories.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class to test actions on {@link UserRepository}.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
public class UserRepositoryUnitTest extends RepositoryUnitTestBase {

    @Autowired
    private UserRepository repository;

    @After
    public void after(){
        repository.deleteAll();
    }

    @Test
    public void whenFindByName_thenReturnUser() {
        // given
        User user = createTestUser("Bob", "Doe", "USER", "myPass123");

        // when
        User found = repository.getUserByUsername(user.getUsername());

        // then
        assertThat(found.getUid()).isEqualTo(user.getUid());
        assertThat(found.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(found.getLastName()).isEqualTo(user.getLastName());
        assertThat(found.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    public void whenFindByName_Unknown_thenReturnNull() {
        // given
        User user = createTestUser("Bob", "Doe", "USER", "myPass123");

        // when
        User found = repository.getUserByUsername("Unknown");

        // then
        assertThat(found).isEqualTo(null);
    }

}
