package student.laurens.novibackend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import student.laurens.novibackend.NoviBackendApplication;
import student.laurens.novibackend.entities.User;
import student.laurens.novibackend.repositories.RoleRepository;
import student.laurens.novibackend.repositories.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = NoviBackendApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integration-test.properties")
public abstract class ControllerIntegrationTestBase {

    protected final String USER = "DefaultUser";
    protected final String USER_ROLE = "USER";

    protected final String ADMIN = "DefaultAdmin";
    protected final String ADMIN_ROLE = "ADMIN";

    protected final String CONTENT_CREATOR = "DefaultContentCreator";
    protected final String CONTENT_CREATOR_ROLE = "CONTENT_CREATOR";

    protected final String MODERATOR = "DefaultModerator";
    protected final String MODERATOR_ROLE = "MODERATOR";

    @Autowired
    protected MockMvc mvc;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected RoleRepository roleRepository;


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected User createDefaultUser(){
        return createTestUser("Bob", "Doe", USER, "MyPassword123", "USER");
    }

    protected User createDefaultAdmin(){
        return createTestUser("John", "Doe", ADMIN, "MyPassword123", "ADMIN");
    }

    protected User createTestUser(String firstname, String lastname, String username, String password, String role){
        User testUser = new User();

        testUser.setFirstName(firstname);
        testUser.setLastName(lastname);
        testUser.setUsername(username);
        testUser.setPassword(password);

        testUser.getRoles().add(roleRepository.getRoleByName(role));

        return testUser;
    }

    protected User saveUser(User testUser){
        userRepository.save(testUser);

        return testUser;
    }
}
