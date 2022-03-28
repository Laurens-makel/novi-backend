package student.laurens.novibackend.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;
import student.laurens.novibackend.NoviBackendApplication;
import student.laurens.novibackend.entities.AbstractEntity;
import student.laurens.novibackend.entities.Role;
import student.laurens.novibackend.entities.User;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Base class to provide default methods for testing RestControllers.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NoviBackendApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integration-test.properties")
public abstract class ControllerIntegrationTestBase<R extends AbstractEntity> extends TestBase<R>{

    /**
     * Implement this method and provide a value for this specific test case.
     *
     * @return The expected HttpStatus when a {@link User} with admin {@link Role} calls GET endpoint for specified resource.
     */
    abstract protected HttpStatus expectedStatusForGetAsAdmin();

    /**
     * Implement this method and provide a value for this specific test case.
     *
     * @return The expected HttpStatus when a {@link User} with user {@link Role} calls GET endpoint for specified resource.
     */
    abstract protected HttpStatus expectedStatusForGetAsUser();

    /**
     * Implement this method and provide a value for this specific test case.
     *
     * @return The expected HttpStatus when a {@link User} with content creator {@link Role} calls GET endpoint for specified resource.
     */
    abstract protected HttpStatus expectedStatusForGetAsContentCreator();

    /**
     * Implement this method and provide a value for this specific test case.
     *
     * @return The expected HttpStatus when a {@link User} with moderator {@link Role} calls GET endpoint for specified resource.
     */
    abstract protected HttpStatus expectedStatusForGetAsModerator();

    /**
     * Implement this method and provide a value for this specific test case.
     *
     * @return The expected HttpStatus when a {@link User} with admin {@link Role} calls POST endpoint for specified resource.
     */
    abstract protected HttpStatus expectedStatusForPostAsAdmin();

    /**
     * Implement this method and provide a value for this specific test case.
     *
     * @return The expected HttpStatus when a {@link User} with user {@link Role} calls POST endpoint for specified resource.
     */
    abstract protected HttpStatus expectedStatusForPostAsUser();

    /**
     * Implement this method and provide a value for this specific test case.
     *
     * @return The expected HttpStatus when a {@link User} with content creator {@link Role} calls POST endpoint for specified resource.
     */
    abstract protected HttpStatus expectedStatusForPostAsContentCreator();

    /**
     * Implement this method and provide a value for this specific test case.
     *
     * @return The expected HttpStatus when a {@link User} with moderator {@link Role} calls POST endpoint for specified resource.
     */
    abstract protected HttpStatus expectedStatusForPostAsModerator();

    /**
     * Implement this method and provide a value for this specific test case.
     *
     * @return The expected HttpStatus when a {@link User} with admin {@link Role} calls PUT endpoint for specified resource.
     */
    abstract protected HttpStatus expectedStatusForPutAsAdmin();

    /**
     * Implement this method and provide a value for this specific test case.
     *
     * @return The expected HttpStatus when a {@link User} with user {@link Role} calls PUT endpoint for specified resource.
     */
    abstract protected HttpStatus expectedStatusForPutAsUser();

    /**
     * Implement this method and provide a value for this specific test case.
     *
     * @return The expected HttpStatus when a {@link User} with content creator {@link Role} calls PUT endpoint for specified resource.
     */
    abstract protected HttpStatus expectedStatusForPutAsContentCreator();

    /**
     * Implement this method and provide a value for this specific test case.
     *
     * @return The expected HttpStatus when a {@link User} with moderator {@link Role} calls PUT endpoint for specified resource.
     */
    abstract protected HttpStatus expectedStatusForPutAsModerator();

    abstract protected HttpStatus expectedStatusForPutAsContentCreatorResourceNotExists();
    abstract protected HttpStatus expectedStatusForPutAsAdminResourceNotExists();
    abstract protected HttpStatus expectedStatusForPutAsUserResourceNotExists();
    abstract protected HttpStatus expectedStatusForPutAsModeratorResourceNotExists();

    /**
     * Implement this method and provide a value for this specific test case.
     *
     * @return The expected HttpStatus when a {@link User} with admin {@link Role} calls DELETE endpoint for specified resource.
     */
    abstract protected HttpStatus expectedStatusForDeleteAsAdmin();

    /**
     * Implement this method and provide a value for this specific test case.
     *
     * @return The expected HttpStatus when a {@link User} with user {@link Role} calls DELETE endpoint for specified resource.
     */
    abstract protected HttpStatus expectedStatusForDeleteAsUser();

    /**
     * Implement this method and provide a value for this specific test case.
     *
     * @return The expected HttpStatus when a {@link User} with content creator {@link Role} calls DELETE endpoint for specified resource.
     */
    abstract protected HttpStatus expectedStatusForDeleteAsContentCreator();

    /**
     * Implement this method and provide a value for this specific test case.
     *
     * @return The expected HttpStatus when a {@link User} with moderator {@link Role} calls DELETE endpoint for specified resource.
     */
    abstract protected HttpStatus expectedStatusForDeleteAsModerator();

    abstract protected HttpStatus expectedStatusForDeleteAsModeratorResourceNotExists();
    abstract protected HttpStatus expectedStatusForDeleteAsUserResourceNotExists();
    abstract protected HttpStatus expectedStatusForDeleteAsContentCreatorResourceNotExists();
    abstract protected HttpStatus expectedStatusForDeleteAsAdminResourceNotExists();

    @Test
    public void getIsUnAuthorized() throws Exception {
        defaultJsonTestForGet().andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = USER)
    public void getJsonAsUser() throws Exception {
        saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForGetAsUser();
        ResultActions mvc = defaultJsonTestForGet();

        ResponseValidator validator = new ResponseValidator(mvc, expectedStatus, DEFAULT_JSON_ACCEPT_VALUE);

        validator.validate();
    }

    @Test
    @WithMockUser(value = USER)
    public void getXmlAsUser() throws Exception {
        saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForGetAsUser();
        ResultActions mvc = defaultXmlTestForGet();

        ResponseValidator validator = new ResponseValidator(mvc, expectedStatus, DEFAULT_XML_ACCEPT_UTF8_VALUE);

        validator.validate();
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void getJsonAsContentCreator() throws Exception {
        saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForGetAsContentCreator();
        ResultActions mvc = defaultJsonTestForGet();

        ResponseValidator validator = new ResponseValidator(mvc, expectedStatus, DEFAULT_JSON_ACCEPT_VALUE);

        validator.validate();
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void getXmlAsContentCreator() throws Exception {
        saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForGetAsContentCreator();
        ResultActions mvc = defaultXmlTestForGet();

        ResponseValidator validator = new ResponseValidator(mvc, expectedStatus, DEFAULT_XML_ACCEPT_UTF8_VALUE);

        validator.validate();
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void getJsonAsModerator() throws Exception {
        saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForGetAsModerator();
        ResultActions mvc = defaultJsonTestForGet();

        ResponseValidator validator = new ResponseValidator(mvc, expectedStatus, DEFAULT_JSON_ACCEPT_VALUE);

        validator.validate();
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void getXmlAsModerator() throws Exception {
        saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForGetAsModerator();
        ResultActions mvc = defaultXmlTestForGet();

        ResponseValidator validator = new ResponseValidator(mvc, expectedStatus, DEFAULT_XML_ACCEPT_UTF8_VALUE);

        validator.validate();
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void getJsonAsAdmin() throws Exception {
        saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForGetAsAdmin();
        ResultActions mvc = defaultJsonTestForGet();

        ResponseValidator validator = new ResponseValidator(mvc, expectedStatus, DEFAULT_JSON_ACCEPT_VALUE);

        validator.validate();
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void getXmlAsAdmin() throws Exception {
        saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForGetAsAdmin();
        ResultActions mvc = defaultXmlTestForGet();

        ResponseValidator validator = new ResponseValidator(mvc, expectedStatus, DEFAULT_XML_ACCEPT_UTF8_VALUE);

        validator.validate();
    }

    @Test
    @WithMockUser(value = USER)
    public void postJsonAsUser() throws Exception {
        saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForPostAsUser();
        ResultActions mvc = defaultJsonTestForPost();

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = USER)
    public void postXmlAsUser() throws Exception {
        saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForPostAsUser();
        ResultActions mvc = defaultXmlTestForPost();

        validateXmlUtf8Response(mvc, expectedStatus);
    }


    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void postXmlAsContentCreator() throws Exception {
        saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForPostAsContentCreator();
        ResultActions mvc = defaultXmlTestForPost();

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void postJsonAsContentCreator() throws Exception {
        saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForPostAsContentCreator();
        ResultActions mvc = defaultJsonTestForPost();

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void postXmlAsModerator() throws Exception {
        saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForPostAsModerator();
        ResultActions mvc = defaultXmlTestForPost();

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void postJsonAsModerator() throws Exception {
        saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForPostAsModerator();
        ResultActions mvc = defaultJsonTestForPost();

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void postJsonAsAdmin() throws Exception {
        saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForPostAsAdmin();
        ResultActions mvc = defaultJsonTestForPost();

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void postXmlAsAdmin() throws Exception {
        saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForPostAsAdmin();
        ResultActions mvc = defaultXmlTestForPost();

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = USER)
    public void putJsonAsUser() throws Exception {
        saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForPutAsUser();
        ResultActions mvc = defaultJsonTestForPut();

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = USER)
    public void putXmlAsUser() throws Exception {
        saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForPutAsUser();
        ResultActions mvc = defaultXmlTestForPut();

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void putJsonAsContentCreator() throws Exception {
        saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForPutAsContentCreator();
        ResultActions mvc = defaultJsonTestForPut();

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void putXmlAsContentCreator() throws Exception {
        saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForPutAsContentCreator();
        ResultActions mvc = defaultXmlTestForPut();

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void putJsonAsModerator() throws Exception {
        saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForPutAsModerator();
        ResultActions mvc = defaultJsonTestForPut();

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void putXmlAsModerator() throws Exception {
        saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForPutAsModerator();
        ResultActions mvc = defaultXmlTestForPut();

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void putJsonAsAdmin() throws Exception {
        saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForPutAsAdmin();
        ResultActions mvc = defaultJsonTestForPut();

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void putXmlAsAdmin() throws Exception {
        saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForPutAsAdmin();
        ResultActions mvc = defaultXmlTestForPut();

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = USER)
    public void deleteJsonAsUser() throws Exception {
        saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForDeleteAsUser();
        ResultActions mvc = defaultJsonTestForDelete();

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = USER)
    public void deleteXmlAsUser() throws Exception {
        saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForDeleteAsUser();
        ResultActions mvc = defaultXmlTestForDelete();

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = USER)
    public void deleteNonExistingResourceJsonAsUser() throws Exception {
        saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForDeleteAsUserResourceNotExists();
        ResultActions mvc = defaultJsonTestForDeleteNonExistingResource();

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = USER)
    public void deleteNonExistingResourceXmlAsUser() throws Exception {
        saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForDeleteAsUserResourceNotExists();
        ResultActions mvc = defaultXmlTestForDeleteNonExistingResource();

        validateXmlResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void deleteJsonAsContentCreator() throws Exception {
        saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsContentCreator();
        ResultActions mvc = defaultJsonTestForDelete();

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void deleteXmlAsContentCreator() throws Exception {
        saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsContentCreator();
        ResultActions mvc = defaultXmlTestForDelete();

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void deleteNonExistingResourceJsonAsContentCreator() throws Exception {
        saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsContentCreatorResourceNotExists();
        ResultActions mvc = defaultJsonTestForDeleteNonExistingResource();

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void deleteNonExistingResourceXmlAsContentCreator() throws Exception {
        saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsContentCreatorResourceNotExists();
        ResultActions mvc = defaultXmlTestForDeleteNonExistingResource();

        validateXmlResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void deleteJsonAsModerator() throws Exception {
        saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsModerator();
        ResultActions mvc = defaultJsonTestForDelete();

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void deleteXmlAsModerator() throws Exception {
        saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsModerator();
        ResultActions mvc = defaultXmlTestForDelete();

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void deleteNonExistingResourceJsonAsModerator() throws Exception {
        saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsModeratorResourceNotExists();
        ResultActions mvc = defaultJsonTestForDeleteNonExistingResource();

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void deleteNonExistingResourceXmlAsModerator() throws Exception {
        saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsModeratorResourceNotExists();
        ResultActions mvc = defaultXmlTestForDeleteNonExistingResource();

        validateXmlResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void deleteJsonAsAdmin() throws Exception {
        saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForDeleteAsAdmin();
        ResultActions mvc = defaultJsonTestForDelete();

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void deleteXmlAsAdmin() throws Exception {
        saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForDeleteAsAdmin();
        ResultActions mvc = defaultXmlTestForDelete();

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void deleteNonExistingResourceJsonAsAdmin() throws Exception {
        saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForDeleteAsAdminResourceNotExists();
        ResultActions mvc = defaultJsonTestForDeleteNonExistingResource();

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void deleteNonExistingResourceXmlAsAdmin() throws Exception {
        saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForDeleteAsAdminResourceNotExists();
        ResultActions mvc = defaultXmlTestForDeleteNonExistingResource();

        validateXmlResponse(mvc, expectedStatus);
    }

}
