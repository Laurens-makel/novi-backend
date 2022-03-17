package student.laurens.novibackend.repositories;

import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import student.laurens.novibackend.users.User;
import student.laurens.novibackend.users.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRepositoryIntegrationTest extends RepositoryIntegrationTestBase {

    @Autowired
    private TestEntityManager entityManager;

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

    private User createTestUser(String firstname, String lastname, String username, String password){
        User testUser = new User();

        testUser.setFirstName(firstname);
        testUser.setLastName(lastname);
        testUser.setUsername(username);
        testUser.setPassword(password);

        entityManager.persistAndFlush(testUser);

        return testUser;
    }

}
