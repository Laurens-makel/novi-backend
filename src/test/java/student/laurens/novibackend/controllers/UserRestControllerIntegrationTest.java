package student.laurens.novibackend.controllers;

import org.junit.After;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import student.laurens.novibackend.entities.User;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class to test allowed actions on {@link UserRestController}.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
public class UserRestControllerIntegrationTest extends ControllerIntegrationTestBase<User> {

    @Override
    protected String getUrlForGet() {
        return "/users";
    }

    @Override
    protected String getUrlForGet(User resource) {
        return "/user";
    }

    @Override
    protected String getUrlForPut(User resource) {
        Integer id = resource.getUid();

        return "/users/" + (id == null ? 9999 : id);
    }

    @Override
    protected String getUrlForPost() {
        return "/users";
    }

    @Override
    protected String getUrlForDelete(User resource) {
        Integer id = resource.getUid();

        return "/users/" + (id == null ? 9999 : id);
    }

    @After
    public void after(){
        userRepository.deleteAll();
    }

    @Test
    public void postUser_isUnauthorized() throws Exception {
        // given
        saveUser(createDefaultUser());

        // when
        postAsJson(createTestUser("DJ", "Tiesto", "DJ_TIESTO", "MyPassword123", "USER"))

        // then
        .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = USER, roles = {USER_ROLE} )
    public void postUser_AsUser_Forbidden() throws Exception {
        // given
        saveUser(createDefaultUser());

        // when
        postAsJson(createTestUser("DJ", "Tiesto", "DJ_TIESTO", "MyPassword123", "USER"))

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void postUser_AsContentCreator_Forbidden() throws Exception {
        // given
        saveUser(createDefaultUser());

        // when
        postAsJson(createTestUser("DJ", "Tiesto", "DJ_TIESTO", "MyPassword123", "USER"))

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void postUser_AsModerator_Forbidden() throws Exception {
        // given
        saveUser(createDefaultUser());

        // when
        postAsJson(createTestUser("DJ", "Tiesto", "DJ_TIESTO", "MyPassword123", "USER"))

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void postUser_AsAdmin_JSON_Created() throws Exception {
        // given
        saveUser(createDefaultUser());

        // when
        ResultActions mvc = postAsJson(createTestUser("DJ", "Tiesto", "DJ_TIESTO", "MyPassword123", "USER"))

        // then
        .andExpect(status().isCreated());

        expectJsonResponse(mvc);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void postUser_AsAdmin_XML_Created() throws Exception {
        // given
        saveUser(createDefaultUser());

        // when
        ResultActions mvc = postAsXml(createTestUser("DJ", "Tiesto", "DJ_TIESTO", "MyPassword123", "USER"))

        // then
        .andExpect(status().isCreated());

        expectXmlUtf8Response(mvc);
    }

    @Test
    public void getCurrentUser_isUnauthorized() throws Exception {
        // given
        saveUser(createDefaultUser());

        // when
        getAsJson(null)

        // then
         .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = USER, roles = {USER_ROLE} )
    public void getCurrentUser_AsUser_JSON_Ok() throws Exception {
        // given
        saveUser(createDefaultUser());

        // when
        ResultActions mvc = getAsJson(null)

        // then
        .andExpect(status().isOk());

        expectJsonResponse(mvc);
    }

    @Test
    @WithMockUser(value = USER, roles = {USER_ROLE} )
    public void getCurrentUser_AsUser_XML_Ok() throws Exception {
        // given
        saveUser(createDefaultUser());

        // when
        ResultActions mvc = getAsXml(null)

        // then
        .andExpect(status().isOk());

        expectXmlUtf8Response(mvc);
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void getCurrentUser_AsContentCreator_JSON_Ok() throws Exception {
        // given
        saveUser(createDefaultContentCreator());

        // when
        ResultActions mvc = getAsJson(null)

        // then
        .andExpect(status().isOk());

        expectJsonResponse(mvc);
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void getCurrentUser_AsContentCreator_XML_Ok() throws Exception {
        // given
        saveUser(createDefaultContentCreator());

        // when
        ResultActions mvc = getAsXml(null)

        // then
        .andExpect(status().isOk());

        expectXmlUtf8Response(mvc);
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void getCurrentUser_AsModerator_JSON_Ok() throws Exception {
        // given
        saveUser(createDefaultModerator());

        // when
        ResultActions mvc = getAsJson(null)

        // then
        .andExpect(status().isOk());

        expectJsonResponse(mvc);
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void getCurrentUser_AsModerator_XML_Ok() throws Exception {
        // given
        saveUser(createDefaultModerator());

        // when
        ResultActions mvc = getAsXml(null)

        // then
        .andExpect(status().isOk());

        expectXmlUtf8Response(mvc);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void getCurrentUser_AsAdmin_JSON_Ok() throws Exception {
        // given
        saveUser(createDefaultAdmin());

        // when
        ResultActions mvc = getAsJson(null)

        // then
        .andExpect(status().isOk());

        expectJsonResponse(mvc);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void getCurrentUser_AsAdmin_XML_Ok() throws Exception {
        // given
        saveUser(createDefaultAdmin());

        // when
        ResultActions mvc = getAsXml(null)

        // then
        .andExpect(status().isOk());

        expectXmlUtf8Response(mvc);
    }

    @Test
    public void getUsers_isUnauthorized() throws Exception {
        // given
        saveUser(createDefaultUser());

        // when
        getAsJson()

        // then
        .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = USER, roles = {USER_ROLE} )
    public void getUsers_AsUser_Forbidden() throws Exception {
        // given
        saveUser(createDefaultUser());

        // when
        getAsJson()

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void getUsers_AsContentCreator_Forbidden() throws Exception {
        // given
        saveUser(createDefaultUser());

        // when
        getAsJson()

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void getUsers_AsModerator_JSON_Ok() throws Exception {
        // given
        saveUser(createDefaultAdmin());

        // when
        ResultActions mvc = getAsJson()

        // then
        .andExpect(status().isOk());

        expectJsonResponse(mvc);
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void getUsers_AsModerator_XML_Ok() throws Exception {
        // given
        saveUser(createDefaultAdmin());

        // when
        ResultActions mvc = getAsXml()

        // then
        .andExpect(status().isOk());

        expectXmlUtf8Response(mvc);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void getUsers_AsAdmin_JSON_Ok() throws Exception {
        // given
        saveUser(createDefaultAdmin());

        // when
        ResultActions mvc = getAsJson()

        // then
        .andExpect(status().isOk());

        expectJsonResponse(mvc);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void getUsers_AsAdmin_XML_Ok() throws Exception {
        // given
        saveUser(createDefaultAdmin());

        // when
        ResultActions mvc = getAsXml()

        // then
        .andExpect(status().isOk());

        expectXmlUtf8Response(mvc);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void deleteUser_AsAdmin_JSON_Ok() throws Exception {
        // given
        User user = saveUser(createTestUser("Jan", "Smit", "SMIT", "MyPassword123", "USER"));

        // when
        ResultActions mvc = deleteAsJson(user)

        // then
        .andExpect(status().isAccepted());

        expectJsonResponse(mvc);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void deleteUser_AsAdmin_XML_Ok() throws Exception {
        // given
        User user = saveUser(createTestUser("Jan", "Smit", "SMIT2", "MyPassword123", "USER"));

        // when
        ResultActions mvc = deleteAsXml(user)

        // then
        .andExpect(status().isAccepted());

        expectXmlUtf8Response(mvc);
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void deleteUser_AsContentCreator_Forbidden() throws Exception {
        // given
        User user = saveUser(createTestUser("Jan", "Smit", "SMIT", "MyPassword123", "USER"));

        // when
        deleteAsJson(user)

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void deleteUser_AsModerator_Forbidden() throws Exception {
        // given
        User user = saveUser(createTestUser("Jan", "Smit", "SMIT", "MyPassword123", "USER"));

        // when
        deleteAsJson(user)

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = USER, roles = {USER_ROLE} )
    public void deleteUser_AsUser_Forbidden() throws Exception {
        // given
        User user = saveUser(createTestUser("Jan", "Smit", "SMIT", "MyPassword123", "USER"));

        // when
        deleteAsJson(user)

        // then
        .andExpect(status().isForbidden());
    }


    @Test
    public void deleteUser_isUnauthorized() throws Exception {
        // given
        User user = saveUser(createTestUser("Jan", "Smit", "SMIT", "MyPassword123", "USER"));

        // when
        deleteAsJson(user)

        // then
        .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void deleteNonExistingUser_AsAdmin_AcceptJSON_NotFound() throws Exception {
        // when
        ResultActions mvc = deleteAsJson(createTestUser("Jan", "Smit", "SMIT", "MyPassword123", "USER"))

        // then
        .andExpect(status().isNotFound())
        .andDo(print());

        expectJsonResponse(mvc);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void deleteNonExistingUser_AsAdmin_AcceptXML_NotFound() throws Exception {
        // when
        ResultActions mvc = deleteAsXml(createTestUser("Jan", "Smit", "SMIT", "MyPassword123", "USER"))

        // then
        .andExpect(status().isNotFound())
        .andDo(print());

        expectXmlResponse(mvc);
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void deleteNonExistingUser_AsContentCreator_Forbidden() throws Exception {
        // when
        deleteAsJson(createTestUser("Jan", "Smit", "SMIT", "MyPassword123", "USER"))

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void deleteNonExistingUser_AsModerator_Forbidden() throws Exception {
        // when
        deleteAsJson(createTestUser("Jan", "Smit", "SMIT", "MyPassword123", "USER"))

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = USER, roles = {USER_ROLE} )
    public void deleteNonExistingUser_AsUser_Forbidden() throws Exception {
        // when
        deleteAsJson(createTestUser("Jan", "Smit", "SMIT", "MyPassword123", "USER"))

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    public void deleteNonExistingUser_isUnauthorized() throws Exception {
        // when
        deleteAsJson(createTestUser("Jan", "Smit", "SMIT", "MyPassword123", "USER"))

        // then
        .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void updateUser_AsAdmin_JSON_Ok() throws Exception {
        // given
        User user = saveUser(createTestUser("Kayne", "West", "WEST", "MyPassword123", "USER"));

        user.setUsername("UPDATED_USERNAME");

        // when
        ResultActions mvc = updateAsJson(user)

        // then
        .andExpect(status().isAccepted());

        expectJsonResponse(mvc);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void updateUser_AsAdmin_XML_Ok() throws Exception {
        // given
        User user = saveUser(createTestUser("Kayne", "West", "WEST", "MyPassword123", "USER"));

        user.setUsername("UPDATED_USERNAME");

        // when
        ResultActions mvc = updateAsXml(user)

        // then
        .andExpect(status().isAccepted());

        expectXmlUtf8Response(mvc);
    }

    @Test
    @WithMockUser(value = USER, roles = {USER_ROLE} )
    public void updateUser_AsUser_Forbidden() throws Exception {
        // given
        User user = saveUser(createTestUser("Kayne", "West", "WEST", "MyPassword123", "USER"));

        user.setUsername("UPDATED_USERNAME");

        // when
        updateAsJson(user)

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
        updateAsJson(user)

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
        updateAsJson(user)

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    public void updateUser_isUnauthorized() throws Exception {
        // given
        User user = saveUser(createTestUser("Kayne", "West", "WEST", "MyPassword123", "USER"));

        user.setUsername("UPDATED_USERNAME");

        // when
        updateAsJson(user)

        // then
        .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void updateNonExistingUser_AsAdmin_AcceptJSON_NotFound() throws Exception {
        // given
        User user = createTestUser("Kayne", "West", "WEST", "MyPassword123", "USER");

        // when
        ResultActions mvc = updateAsJson(user)

        // then
        .andExpect(status().isNotFound())
        .andDo(print());

        expectJsonResponse(mvc);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void updateNonExistingUser_AsAdmin_AcceptXML_NotFound() throws Exception {
        // given
        User user = createTestUser("Kayne", "West", "WEST", "MyPassword123", "USER");

        // when
        ResultActions mvc = updateAsXml(user)

        // then
        .andExpect(status().isNotFound())
        .andDo(print());

        expectXmlResponse(mvc);
    }

    @Test
    @WithMockUser(value = USER, roles = {USER_ROLE} )
    public void updateNonExistingUser_AsUser_Forbidden() throws Exception {
        // given
        User user = createTestUser("Kayne", "West", "WEST", "MyPassword123", "USER");

        // when
        updateAsJson(user)

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void updateNonExistingUser_AsContentCreator_Forbidden() throws Exception {
        // given
        User user = createTestUser("Kayne", "West", "WEST", "MyPassword123", "USER");

        // when
        updateAsJson(user)

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void updateNonExistingUser_AsModerator_Forbidden() throws Exception {
        // given
        User user = createTestUser("Kayne", "West", "WEST", "MyPassword123", "USER");

        // when
        updateAsJson(user)

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    public void updateNonExistingUser_isUnauthorized() throws Exception {
        // given
        User user = createTestUser("Kayne", "West", "WEST", "MyPassword123", "USER");

        // when
        updateAsJson(user)

        // then
        .andExpect(status().isUnauthorized());
    }

    @Override
    protected User create() {
        return createDefaultUser();
    }

    @Override
    protected User save(User resource) {
        return saveUser(resource);
    }

    @Override
    protected User modify(User resource) {
        resource.setUsername("MODIFIED");

        return resource;
    }

    @Override
    public HttpStatus expectedStatusForGetAsModerator() {
        return HttpStatus.OK;
    }
}
