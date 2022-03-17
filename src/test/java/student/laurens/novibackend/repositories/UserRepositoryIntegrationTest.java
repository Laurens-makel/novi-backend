package student.laurens.novibackend.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import student.laurens.novibackend.users.User;
import student.laurens.novibackend.users.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:application-integration-test.properties")
public class UserRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repository;

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
