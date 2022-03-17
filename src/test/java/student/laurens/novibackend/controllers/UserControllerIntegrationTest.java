package student.laurens.novibackend.controllers;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import student.laurens.novibackend.NoviBackendApplication;
import student.laurens.novibackend.users.RoleRepository;
import student.laurens.novibackend.users.User;
import student.laurens.novibackend.users.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = NoviBackendApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integration-test.properties")
public class UserControllerIntegrationTest {

    private final String USER = "DefaultUser";
    private final String USER_ROLE = "USER";

    private final String ADMIN = "DefaultAdmin";
    private final String ADMIN_ROLE = "ADMIN";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @After
    public void after(){
        repository.deleteAll();
    }

    @Test
    public void getCurrentUser_isUnauthorized() throws Exception {
        // given
        createDefaultUser();

        // when
        mvc.perform(get("/user")
                        .contentType(MediaType.APPLICATION_JSON))

        // then
         .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = USER, roles = {USER_ROLE} )
    public void getCurrentUser_AsUser_Ok() throws Exception {
        // given
        createDefaultUser();

        // when
        mvc.perform(get("/user")
                        .contentType(MediaType.APPLICATION_JSON))

        // then
        .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void getCurrentUser_AsAdmin_Ok() throws Exception {
        // given
        createDefaultAdmin();

        // when
        mvc.perform(get("/user")
                        .contentType(MediaType.APPLICATION_JSON))

        // then
        .andExpect(status().isOk());
    }

    @Test
    public void getUsers_isUnauthorized() throws Exception {
        // given
        createDefaultUser();

        // when
        mvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))

        // then
        .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = USER, roles = {USER_ROLE} )
    public void getUsers_AsUser_Forbidden() throws Exception {
        // given
        createDefaultUser();

        // when
        mvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void getUsers_AsAdmin_Ok() throws Exception {
        // given
        createDefaultAdmin();

        // when
        mvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))

        // then
        .andExpect(status().isOk());
    }

    private void createTestUser(String firstname, String lastname, String username, String password, String role){
        User testUser = new User();

        testUser.setFirstName(firstname);
        testUser.setLastName(lastname);
        testUser.setUsername(username);
        testUser.setPassword(password);

        testUser.getRoles().add(roleRepository.getRoleByName(role));

        repository.save(testUser);
    }

    private void createDefaultUser(){
        createTestUser("Bob", "Doe", USER, "MyPassword123", "USER");
    }

    private void createDefaultAdmin(){
        createTestUser("John", "Doe", ADMIN, "MyPassword123", "ADMIN");
    }
}
