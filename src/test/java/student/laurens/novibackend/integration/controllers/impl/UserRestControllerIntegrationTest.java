package student.laurens.novibackend.integration.controllers.impl;

import org.junit.After;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import student.laurens.novibackend.integration.controllers.OwnedControllerIntegrationTestBase;
import student.laurens.novibackend.controllers.UserRestController;
import student.laurens.novibackend.entities.User;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class to test allowed actions on {@link UserRestController}.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
public class UserRestControllerIntegrationTest extends OwnedControllerIntegrationTestBase<User> {

    @After
    public void after(){
        userRepository.deleteAll();
    }

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

    @Override
    protected User createOwned(User owner) {
        return owner;
    }

    @Override
    protected User createNotOwned() {
        return createUniqueContentCreator();
    }

    @Override
    protected User save(User resource) {
        return saveUser(resource);
    }

    @Override
    protected User modify(User resource) {
        resource.setUsername(unique("MODIFIED"));

        return resource;
    }

    @Override
    protected HttpStatus expectedStatusForGetAsUser() { return HttpStatus.FORBIDDEN; }
    @Override
    protected HttpStatus expectedStatusForGetAsContentCreator() { return HttpStatus.FORBIDDEN; }
    @Override
    protected HttpStatus expectedStatusForGetAsModerator() {
        return HttpStatus.OK;
    }
    @Override
    protected HttpStatus expectedStatusForGetAsAdmin() { return HttpStatus.OK;  }

    @Override
    protected HttpStatus expectedStatusForGetAsAdminResourceOwned() { return HttpStatus.OK; }
    @Override
    protected HttpStatus expectedStatusForGetAsAdminResourceNotOwned() { return HttpStatus.OK; }
    @Override
    protected HttpStatus expectedStatusForGetAsModeratorResourceOwned() { return HttpStatus.OK; }
    @Override
    protected HttpStatus expectedStatusForGetAsModeratorResourceNotOwned() { return HttpStatus.OK; }
    @Override
    protected HttpStatus expectedStatusForGetAsContentCreatorResourceOwned() { return HttpStatus.OK; }
    @Override
    protected HttpStatus expectedStatusForGetAsContentCreatorResourceNotOwned() { return HttpStatus.OK; }
    @Override
    protected HttpStatus expectedStatusForGetAsUserResourceOwned() { return HttpStatus.OK; }
    @Override
    protected HttpStatus expectedStatusForGetAsUserResourceNotOwned() { return HttpStatus.OK; }

    @Override
    protected HttpStatus expectedStatusForPostAsUser() { return HttpStatus.FORBIDDEN;}
    @Override
    protected HttpStatus expectedStatusForPostAsContentCreator() {
        return HttpStatus.FORBIDDEN;
    }
    @Override
    protected HttpStatus expectedStatusForPostAsModerator() {
        return HttpStatus.FORBIDDEN;
    }
    @Override
    protected HttpStatus expectedStatusForPostAsAdmin() { return HttpStatus.CREATED;}


    @Override
    protected HttpStatus expectedStatusForPutAsAdmin() {
        return HttpStatus.ACCEPTED;
    }
    @Override
    protected HttpStatus expectedStatusForPutAsUser() {
        return HttpStatus.FORBIDDEN;
    }
    @Override
    protected HttpStatus expectedStatusForPutAsContentCreator() {
        return HttpStatus.FORBIDDEN;
    }
    @Override
    protected HttpStatus expectedStatusForPutAsModerator() {
        return HttpStatus.FORBIDDEN;
    }


    @Override
    protected HttpStatus expectedStatusForDeleteAsAdmin() {
        return HttpStatus.ACCEPTED;
    }
    @Override
    protected HttpStatus expectedStatusForDeleteAsUser() { return HttpStatus.FORBIDDEN; }
    @Override
    protected HttpStatus expectedStatusForDeleteAsContentCreator() { return HttpStatus.FORBIDDEN; }
    @Override
    protected HttpStatus expectedStatusForDeleteAsModerator() {
        return HttpStatus.FORBIDDEN;
    }


    @Override
    protected HttpStatus expectedStatusForPutAsContentCreatorResourceNotExists() { return HttpStatus.FORBIDDEN;}
    @Override
    protected HttpStatus expectedStatusForPutAsAdminResourceNotExists() { return HttpStatus.FORBIDDEN;}
    @Override
    protected HttpStatus expectedStatusForPutAsUserResourceNotExists() { return HttpStatus.FORBIDDEN;}
    @Override
    protected HttpStatus expectedStatusForPutAsModeratorResourceNotExists() { return HttpStatus.FORBIDDEN;}


    @Override
    protected HttpStatus expectedStatusForDeleteAsModeratorResourceNotExists() { return HttpStatus.FORBIDDEN;}
    @Override
    protected HttpStatus expectedStatusForDeleteAsUserResourceNotExists() { return HttpStatus.FORBIDDEN;}
    @Override
    protected HttpStatus expectedStatusForDeleteAsContentCreatorResourceNotExists() { return HttpStatus.FORBIDDEN;}
    @Override
    protected HttpStatus expectedStatusForDeleteAsAdminResourceNotExists() { return HttpStatus.NOT_FOUND;}


    @Override
    protected HttpStatus expectedStatusForPutAsContentCreatorResourceOwned(){ return HttpStatus.ACCEPTED; }
    @Override
    protected HttpStatus expectedStatusForDeleteAsContentCreatorResourceOwned(){ return HttpStatus.FORBIDDEN; }
    @Override
    protected HttpStatus expectedStatusForPutAsAdminResourceOwned() { return HttpStatus.ACCEPTED;}
    @Override
    protected HttpStatus expectedStatusForPutAsAdminResourceNotOwned() { return HttpStatus.ACCEPTED;}

    @Override
    protected HttpStatus expectedStatusForDeleteAsAdminResourceOwned() { return HttpStatus.ACCEPTED;}
    @Override
    protected HttpStatus expectedStatusForPutAsModeratorResourceOwned(){ return HttpStatus.ACCEPTED; }
    @Override
    protected HttpStatus expectedStatusForDeleteAsAdminResourceNotOwned() { return HttpStatus.ACCEPTED;}
    @Override
    protected HttpStatus expectedStatusForPutAsContentCreatorResourceNotOwned() { return HttpStatus.FORBIDDEN;}

    @Override
    protected HttpStatus expectedStatusForDeleteAsContentCreatorResourceNotOwned() { return HttpStatus.FORBIDDEN;}
    @Override
    protected HttpStatus expectedStatusForPutAsModeratorResourceNotOwned() { return HttpStatus.FORBIDDEN;}
    @Override
    protected HttpStatus expectedStatusForDeleteAsModeratorResourceOwned() { return HttpStatus.FORBIDDEN; }
    @Override
    protected HttpStatus expectedStatusForDeleteAsModeratorResourceNotOwned() { return HttpStatus.FORBIDDEN;}

    @Override
    protected HttpStatus expectedStatusForPutAsUserResourceOwned(){ return HttpStatus.ACCEPTED; }
    @Override
    protected HttpStatus expectedStatusForPutAsUserResourceNotOwned() { return HttpStatus.FORBIDDEN;}
    @Override
    protected HttpStatus expectedStatusForDeleteAsUserResourceOwned() { return HttpStatus.FORBIDDEN; }
    @Override
    protected HttpStatus expectedStatusForDeleteAsUserResourceNotOwned() { return HttpStatus.FORBIDDEN;}

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
        saveUser(createDefaultAdmin());
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
        saveUser(createDefaultAdmin());
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
        saveUser(createDefaultAdmin());
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
        saveUser(createDefaultAdmin());
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
        saveUser(createDefaultAdmin());
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
        saveUser(createDefaultAdmin());
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
    public void updateOwnUser_AsUser_isAccepted() throws Exception {
        // given
        User user = saveUser(createDefaultUser());

        user.setUsername("UPDATED_USERNAME");

        // when
        updateAsJson(user)

        // then
        .andExpect(status().isAccepted());
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void updateOwnUser_AsContentCreator_isAccepted() throws Exception {
        // given
        User user = saveUser(createDefaultContentCreator());

        user.setUsername("UPDATED_USERNAME");

        // when
        updateAsJson(user)

        // then
        .andExpect(status().isAccepted());
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void updateOwnUser_AsModerator_IsAccepted() throws Exception {
        // given
        User user = saveUser(createDefaultModerator());

        user.setUsername("UPDATED_USERNAME");

        // when
        updateAsJson(user)

        // then
        .andExpect(status().isAccepted());
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
        saveUser(createDefaultAdmin());
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
        saveUser(createDefaultAdmin());
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
    public void updateNonExistingUser_AsUser_NotFound() throws Exception {
        // given
        saveUser(createDefaultUser());
        User user = createTestUser("Kayne", "West", "WEST", "MyPassword123", "USER");

        // when
        updateAsJson(user)

        // then
        .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void updateNonExistingUser_AsContentCreator_isNotFound() throws Exception {
        // given
        saveUser(createUniqueContentCreator());
        User user = createTestUser("Kayne", "West", "WEST", "MyPassword123", "USER");

        // when
        updateAsJson(user)

        // then
        .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void updateNonExistingUser_AsModerator_isNotFound() throws Exception {
        // given
        saveUser(createDefaultModerator());
        User user = createTestUser("Kayne", "West", "WEST", "MyPassword123", "USER");

        // when
        updateAsJson(user)

        // then
        .andExpect(status().isNotFound());
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

}
