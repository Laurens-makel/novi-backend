package student.laurens.novibackend.controllers;

import org.junit.After;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import student.laurens.novibackend.entities.User;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserRestControllerIntegrationTest extends ControllerIntegrationTestBase {

    @After
    public void after(){
        userRepository.deleteAll();
    }

    @Test
    public void postUser_isUnauthorized() throws Exception {
        // given
        saveUser(createDefaultUser());

        // when
        postUser()

        // then
        .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = USER, roles = {USER_ROLE} )
    public void postUser_AsUser_Forbidden() throws Exception {
        // given
        saveUser(createDefaultUser());

        // when
        postUser()

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void postUser_AsContentCreator_Forbidden() throws Exception {
        // given
        saveUser(createDefaultUser());

        // when
        postUser()

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void postUser_AsModerator_Forbidden() throws Exception {
        // given
        saveUser(createDefaultUser());

        // when
        postUser()

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void postUser_AsAdmin_Created() throws Exception {
        // given
        saveUser(createDefaultUser());

        // when
       postUser()

        // then
        .andExpect(status().isCreated());
    }

    private ResultActions postUser() throws Exception {
        return mvc.perform(post("/users")
                .content(asJsonString(createTestUser("DJ", "Tiesto", "DJ_TIESTO", "MyPassword123", "USER")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getCurrentUser_isUnauthorized() throws Exception {
        // given
        saveUser(createDefaultUser());

        // when
        getCurrentUser()

        // then
         .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = USER, roles = {USER_ROLE} )
    public void getCurrentUser_AsUser_Ok() throws Exception {
        // given
        saveUser(createDefaultUser());

        // when
        getCurrentUser()
        // then
        .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void getCurrentUser_AsContentCreator_Ok() throws Exception {
        // given
        saveUser(createDefaultContentCreator());

        // when
        getCurrentUser()

        // then
        .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void getCurrentUser_AsModerator_Ok() throws Exception {
        // given
        saveUser(createDefaultModerator());

        // when
        getCurrentUser()

        // then
        .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void getCurrentUser_AsAdmin_Ok() throws Exception {
        // given
        saveUser(createDefaultAdmin());

        // when
        getCurrentUser()

        // then
        .andExpect(status().isOk());
    }

    private ResultActions getCurrentUser() throws Exception {
        return mvc.perform(get("/user")
                .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getUsers_isUnauthorized() throws Exception {
        // given
        saveUser(createDefaultUser());

        // when
        getUsers()

        // then
        .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = USER, roles = {USER_ROLE} )
    public void getUsers_AsUser_Forbidden() throws Exception {
        // given
        saveUser(createDefaultUser());

        // when
        getUsers()

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void getUsers_AsContentCreator_Forbidden() throws Exception {
        // given
        saveUser(createDefaultUser());

        // when
        getUsers()

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void getUsers_AsModerator_Ok() throws Exception {
        // given
        saveUser(createDefaultAdmin());

        // when
        getUsers()

        // then
        .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void getUsers_AsAdmin_Ok() throws Exception {
        // given
        saveUser(createDefaultAdmin());

        // when
       getUsers()

        // then
        .andExpect(status().isOk());
    }

    private ResultActions getUsers() throws Exception {
        return mvc.perform(get("/users")
                .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void deleteUser_AsAdmin_Ok() throws Exception {
        // given
        User user = saveUser(createTestUser("Jan", "Smit", "SMIT", "MyPassword123", "USER"));

        // when
        deleteUser(user)

        // then
        .andExpect(status().isAccepted());
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void deleteUser_AsContentCreator_Forbidden() throws Exception {
        // given
        User user = saveUser(createTestUser("Jan", "Smit", "SMIT", "MyPassword123", "USER"));

        // when
        deleteUser(user)

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void deleteUser_AsModerator_Forbidden() throws Exception {
        // given
        User user = saveUser(createTestUser("Jan", "Smit", "SMIT", "MyPassword123", "USER"));

        // when
        deleteUser(user)

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = USER, roles = {USER_ROLE} )
    public void deleteUser_AsUser_Forbidden() throws Exception {
        // given
        User user = saveUser(createTestUser("Jan", "Smit", "SMIT", "MyPassword123", "USER"));

        // when
        deleteUser(user)

        // then
        .andExpect(status().isForbidden());
    }


    @Test
    public void deleteUser_AsUser_isUnauthorized() throws Exception {
        // given
        User user = saveUser(createTestUser("Jan", "Smit", "SMIT", "MyPassword123", "USER"));

        // when
        deleteUser(user)

        // then
        .andExpect(status().isUnauthorized());
    }

    private ResultActions deleteUser(User user) throws Exception {
        return mvc.perform(delete("/users/" + userRepository.getUserByUsername(user.getUsername()).getUid())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void updateUser_AsAdmin_Ok() throws Exception {
        // given
        User user = saveUser(createTestUser("Kayne", "West", "WEST", "MyPassword123", "USER"));

        user.setUsername("UPDATED_USERNAME");

        // when
        updateUser(user)

        // then
        .andExpect(status().isAccepted());
    }

    @Test
    @WithMockUser(value = USER, roles = {USER_ROLE} )
    public void updateUser_AsUser_Forbidden() throws Exception {
        // given
        User user = saveUser(createTestUser("Kayne", "West", "WEST", "MyPassword123", "USER"));

        user.setUsername("UPDATED_USERNAME");

        // when
        updateUser(user)

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void updateUser_AsContentCreator_Forbidden() throws Exception {
        // given
        User user = saveUser(createTestUser("Kayne", "West", "WEST", "MyPassword123", "USER"));

        user.setUsername("UPDATED_USERNAME");

        // when
        updateUser(user)

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void updateUser_AsModerator_Forbidden() throws Exception {
        // given
        User user = saveUser(createTestUser("Kayne", "West", "WEST", "MyPassword123", "USER"));

        user.setUsername("UPDATED_USERNAME");

        // when
        updateUser(user)

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    public void updateUser_isUnauthorized() throws Exception {
        // given
        User user = saveUser(createTestUser("Kayne", "West", "WEST", "MyPassword123", "USER"));

        user.setUsername("UPDATED_USERNAME");

        // when
        updateUser(user)

        // then
        .andExpect(status().isUnauthorized());
    }

    private ResultActions updateUser(User user) throws Exception {
        return mvc.perform(put("/users/" + user.getUid())
                .content(asJsonString(user))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    }

}
