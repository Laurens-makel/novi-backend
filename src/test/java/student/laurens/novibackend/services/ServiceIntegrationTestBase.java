package student.laurens.novibackend.services;

import org.junit.runner.RunWith;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import student.laurens.novibackend.entities.User;

@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-integration-test.properties")
public abstract class ServiceIntegrationTestBase {
    protected User createTestUser(String firstname, String lastname, String username, String password){
        User testUser = new User();

        testUser.setFirstName(firstname);
        testUser.setLastName(lastname);
        testUser.setUsername(username);
        testUser.setPassword(password);

        return testUser;
    }
}
