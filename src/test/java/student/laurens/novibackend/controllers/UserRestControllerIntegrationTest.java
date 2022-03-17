package student.laurens.novibackend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = NoviBackendApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integration-test.properties")
public class UserRestControllerIntegrationTest {

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
    public void postUser_isUnauthorized() throws Exception {
        // given
        saveUser(createDefaultUser());

        // when
        mvc.perform(post("/users")
            .content(asJsonString(createTestUser("DJ", "Tiesto", "DJ_TIESTO", "MyPassword123", "USER")))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))

        // then
        .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = USER, roles = {USER_ROLE} )
    public void postUser_AsUser_Forbidden() throws Exception {
        // given
        saveUser(createDefaultUser());

        // when
        mvc.perform(post("/users")
            .content(asJsonString(createTestUser("DJ", "Tiesto", "DJ_TIESTO", "MyPassword123", "USER")))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void postUser_AsAdmin_Created() throws Exception {
        // given
        saveUser(createDefaultUser());

        // when
        mvc.perform(post("/users")
            .content(asJsonString(createTestUser("DJ", "Tiesto", "DJ_TIESTO", "MyPassword123", "USER")))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))

        // then
        .andExpect(status().isCreated());
    }

    @Test
    public void getCurrentUser_isUnauthorized() throws Exception {
        // given
        saveUser(createDefaultUser());

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
        saveUser(createDefaultUser());

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
        saveUser(createDefaultAdmin());

        // when
        mvc.perform(get("/user")
            .contentType(MediaType.APPLICATION_JSON))

        // then
        .andExpect(status().isOk());
    }

    @Test
    public void getUsers_isUnauthorized() throws Exception {
        // given
        saveUser(createDefaultUser());

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
        saveUser(createDefaultUser());

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
        saveUser(createDefaultAdmin());

        // when
        mvc.perform(get("/users")
            .contentType(MediaType.APPLICATION_JSON))

        // then
        .andExpect(status().isOk());
    }

    private User createTestUser(String firstname, String lastname, String username, String password, String role){
        User testUser = new User();

        testUser.setFirstName(firstname);
        testUser.setLastName(lastname);
        testUser.setUsername(username);
        testUser.setPassword(password);

        testUser.getRoles().add(roleRepository.getRoleByName(role));

        return testUser;
    }

    private User saveUser(User testUser){
        repository.save(testUser);

        return testUser;
    }

    private User createDefaultUser(){
        return createTestUser("Bob", "Doe", USER, "MyPassword123", "USER");
    }

    private User createDefaultAdmin(){
        return createTestUser("John", "Doe", ADMIN, "MyPassword123", "ADMIN");
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
