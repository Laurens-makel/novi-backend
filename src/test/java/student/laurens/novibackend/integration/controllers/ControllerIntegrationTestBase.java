package student.laurens.novibackend.integration.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;
import student.laurens.novibackend.NoviBackendApplication;
import student.laurens.novibackend.entities.AbstractEntity;
import student.laurens.novibackend.entities.Role;
import student.laurens.novibackend.entities.User;
import student.laurens.novibackend.entities.dto.ResourceDto;

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
public abstract class ControllerIntegrationTestBase<R extends AbstractEntity, D extends ResourceDto> extends IntegrationTestBase<R,D> {

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
        TestResults<R> results = defaultJsonTestForGet();
        ResultActions mvc = results.getMvc();
        mvc.andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER, userDetailsServiceBeanName = "appUserDetailsService")
    public void getJsonAsUser() throws Exception {
        saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForGetAsUser();
        TestResults<R> results = defaultJsonTestForGet();
        ResultActions mvc = results.getMvc();

        ResponseValidator validator = new ResponseValidator(mvc, expectedStatus, DEFAULT_JSON_ACCEPT_VALUE);

        validator.validate();
        if(expectedStatus.is2xxSuccessful()){
            R resource = results.getResource();
            validateJsonArrayLengthGreaterThan(mvc, 1);
            validateJsonLink(mvc, "GET " + resource.getId(), getUrlForGet(resource));
        }
    }

    @Test
    @WithUserDetails(value = USER, userDetailsServiceBeanName = "appUserDetailsService")
    public void getXmlAsUser() throws Exception {
        saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForGetAsUser();
        TestResults<R> results = defaultXmlTestForGet();
        ResultActions mvc = results.getMvc();

        ResponseValidator validator = new ResponseValidator(mvc, expectedStatus, DEFAULT_XML_ACCEPT_UTF8_VALUE);

        validator.validate();
        if(expectedStatus.is2xxSuccessful()){
            R resource = results.getResource();

            validateXmlArrayLengthGreaterThan(mvc, 1);
            validateXmlLink(mvc, "GET " + resource.getId(), getUrlForGet(resource));
        }
    }

    @Test
    @WithUserDetails(value = CONTENT_CREATOR, userDetailsServiceBeanName = "appUserDetailsService")
    public void getJsonAsContentCreator() throws Exception {
        saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForGetAsContentCreator();
        TestResults<R> results = defaultJsonTestForGet();
        ResultActions mvc = results.getMvc();

        ResponseValidator validator = new ResponseValidator(mvc, expectedStatus, DEFAULT_JSON_ACCEPT_VALUE);

        validator.validate();
        if(expectedStatus.is2xxSuccessful()){
            R resource = results.getResource();
            validateJsonArrayLengthGreaterThan(mvc, 1);
            validateJsonLink(mvc, "GET " + resource.getId(), getUrlForGet(resource));
        }
    }

    @Test
    @WithUserDetails(value = CONTENT_CREATOR, userDetailsServiceBeanName = "appUserDetailsService")
    public void getXmlAsContentCreator() throws Exception {
        saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForGetAsContentCreator();
        TestResults<R> results = defaultXmlTestForGet();
        ResultActions mvc = results.getMvc();

        ResponseValidator validator = new ResponseValidator(mvc, expectedStatus, DEFAULT_XML_ACCEPT_UTF8_VALUE);

        validator.validate();
        if(expectedStatus.is2xxSuccessful()){
            R resource = results.getResource();

            validateXmlArrayLengthGreaterThan(mvc, 1);
            validateXmlLink(mvc, "GET " + resource.getId(), getUrlForGet(resource));
        }
    }

    @Test
    @WithUserDetails(value = MODERATOR, userDetailsServiceBeanName = "appUserDetailsService")
    public void getJsonAsModerator() throws Exception {
        saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForGetAsModerator();
        TestResults<R> results = defaultJsonTestForGet();
        ResultActions mvc = results.getMvc();

        ResponseValidator validator = new ResponseValidator(mvc, expectedStatus, DEFAULT_JSON_ACCEPT_VALUE);

        validator.validate();
        if(expectedStatus.is2xxSuccessful()){
            R resource = results.getResource();
            validateJsonArrayLengthGreaterThan(mvc, 1);
            validateJsonLink(mvc, "GET " + resource.getId(), getUrlForGet(resource));
        }
    }

    @Test
    @WithUserDetails(value = MODERATOR, userDetailsServiceBeanName = "appUserDetailsService")
    public void getXmlAsModerator() throws Exception {
        saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForGetAsModerator();
        TestResults<R> results = defaultXmlTestForGet();
        ResultActions mvc = results.getMvc();

        ResponseValidator validator = new ResponseValidator(mvc, expectedStatus, DEFAULT_XML_ACCEPT_UTF8_VALUE);

        validator.validate();
        if(expectedStatus.is2xxSuccessful()){
            R resource = results.getResource();

            validateXmlArrayLengthGreaterThan(mvc, 1);
            validateXmlLink(mvc, "GET " + resource.getId(), getUrlForGet(resource));
        }
    }

    @Test
    @WithUserDetails( value = ADMIN, userDetailsServiceBeanName = "appUserDetailsService")
    public void getJsonAsAdmin() throws Exception {
        saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForGetAsAdmin();
        TestResults<R> results = defaultJsonTestForGet();
        ResultActions mvc = results.getMvc();

        ResponseValidator validator = new ResponseValidator(mvc, expectedStatus, DEFAULT_JSON_ACCEPT_VALUE);

        validator.validate();
        if(expectedStatus.is2xxSuccessful()){
            R resource = results.getResource();
            validateJsonArrayLengthGreaterThan(mvc, 1);
            validateJsonLink(mvc, "GET " + resource.getId(), getUrlForGet(resource));
        }
    }

    @Test
    @WithUserDetails(value = ADMIN, userDetailsServiceBeanName = "appUserDetailsService")
    public void getXmlAsAdmin() throws Exception {
        saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForGetAsAdmin();
        TestResults<R> results = defaultXmlTestForGet();
        ResultActions mvc = results.getMvc();

        ResponseValidator validator = new ResponseValidator(mvc, expectedStatus, DEFAULT_XML_ACCEPT_UTF8_VALUE);

        validator.validate();
        if(expectedStatus.is2xxSuccessful()){
            R resource = results.getResource();

            validateXmlArrayLengthGreaterThan(mvc, 1);
            validateXmlLink(mvc, "GET " + resource.getId(), getUrlForGet(resource));
        }
    }

    @Test
    @WithUserDetails(value = USER, userDetailsServiceBeanName = "appUserDetailsService")
    public void postJsonAsUser() throws Exception {
        saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForPostAsUser();
        TestResults<R> results = defaultJsonTestForPost();
        ResultActions mvc = results.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = results.getResource();
            resource.setId(extractJsonId(mvc));

            validateJsonLink(mvc, "GET", getUrlForGet(resource));
            validateJsonLink(mvc, "PUT", getUrlForPut(resource));
            validateJsonLink(mvc, "DELETE", getUrlForDelete(resource));
        }
    }

    @Test
    @WithUserDetails(value = USER, userDetailsServiceBeanName = "appUserDetailsService")
    public void postXmlAsUser() throws Exception {
        saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForPostAsUser();
        TestResults<R> results = defaultXmlTestForPost();
        ResultActions mvc = results.getMvc();

        validateXmlUtf8Response(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = results.getResource();
            resource.setId(extractXmlId(mvc));

            validateXmlLink(mvc, "GET", getUrlForGet(resource));
            validateXmlLink(mvc, "PUT", getUrlForPut(resource));
            validateXmlLink(mvc, "DELETE", getUrlForDelete(resource));
        }
    }


    @Test
    @WithUserDetails(value = CONTENT_CREATOR, userDetailsServiceBeanName = "appUserDetailsService")
    public void postXmlAsContentCreator() throws Exception {
        saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForPostAsContentCreator();
        TestResults<R> results = defaultXmlTestForPost();
        ResultActions mvc = results.getMvc();

        validateXmlUtf8Response(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = results.getResource();
            resource.setId(extractXmlId(mvc));

            validateXmlLink(mvc, "GET", getUrlForGet(resource));
            validateXmlLink(mvc, "PUT", getUrlForPut(resource));
            validateXmlLink(mvc, "DELETE", getUrlForDelete(resource));
        }
    }

    @Test
    @WithUserDetails(value = CONTENT_CREATOR, userDetailsServiceBeanName = "appUserDetailsService")
    public void postJsonAsContentCreator() throws Exception {
        saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForPostAsContentCreator();
        TestResults<R> results = defaultJsonTestForPost();
        ResultActions mvc = results.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = results.getResource();
            resource.setId(extractJsonId(mvc));

            validateJsonLink(mvc, "GET", getUrlForGet(resource));
            validateJsonLink(mvc, "PUT", getUrlForPut(resource));
            validateJsonLink(mvc, "DELETE", getUrlForDelete(resource));
        }
    }

    @Test
    @WithUserDetails(value = MODERATOR, userDetailsServiceBeanName = "appUserDetailsService")
    public void postXmlAsModerator() throws Exception {
        saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForPostAsModerator();
        TestResults<R> results = defaultXmlTestForPost();
        ResultActions mvc = results.getMvc();

        validateXmlUtf8Response(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = results.getResource();
            resource.setId(extractXmlId(mvc));

            validateXmlLink(mvc, "GET", getUrlForGet(resource));
            validateXmlLink(mvc, "PUT", getUrlForPut(resource));
            validateXmlLink(mvc, "DELETE", getUrlForDelete(resource));
        }
    }

    @Test
    @WithUserDetails(value = MODERATOR, userDetailsServiceBeanName = "appUserDetailsService")
    public void postJsonAsModerator() throws Exception {
        saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForPostAsModerator();
        TestResults<R> results = defaultJsonTestForPost();
        ResultActions mvc = results.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = results.getResource();
            resource.setId(extractJsonId(mvc));

            validateJsonLink(mvc, "GET", getUrlForGet(resource));
            validateJsonLink(mvc, "PUT", getUrlForPut(resource));
            validateJsonLink(mvc, "DELETE", getUrlForDelete(resource));
        }
    }

    @Test
    @WithUserDetails(value = ADMIN, userDetailsServiceBeanName = "appUserDetailsService")
    public void postJsonAsAdmin() throws Exception {
        saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForPostAsAdmin();
        TestResults<R> results = defaultJsonTestForPost();
        ResultActions mvc = results.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = results.getResource();
            resource.setId(extractJsonId(mvc));

            validateJsonLink(mvc, "GET", getUrlForGet(resource));
            validateJsonLink(mvc, "PUT", getUrlForPut(resource));
            validateJsonLink(mvc, "DELETE", getUrlForDelete(resource));
        }
    }

    @Test
    @WithUserDetails(value = ADMIN, userDetailsServiceBeanName = "appUserDetailsService")
    public void postXmlAsAdmin() throws Exception {
        saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForPostAsAdmin();
        TestResults<R> results = defaultXmlTestForPost();
        ResultActions mvc = results.getMvc();

        validateXmlUtf8Response(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = results.getResource();
            resource.setId(extractXmlId(mvc));

            validateXmlLink(mvc, "GET", getUrlForGet(resource));
            validateXmlLink(mvc, "PUT", getUrlForPut(resource));
            validateXmlLink(mvc, "DELETE", getUrlForDelete(resource));
        }
    }

    @Test
    @WithUserDetails(value = USER, userDetailsServiceBeanName = "appUserDetailsService")
    public void putJsonAsUser() throws Exception {
        saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForPutAsUser();
        TestResults<R> results = defaultJsonTestForPut();
        ResultActions mvc = results.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = results.getResource();

            validateJsonLink(mvc, "GET", getUrlForGet(resource));
            validateJsonLink(mvc, "POST", getUrlForPost());
            validateJsonLink(mvc, "DELETE", getUrlForDelete(resource));
        }
    }

    @Test
    @WithUserDetails(value = USER, userDetailsServiceBeanName = "appUserDetailsService")
    public void putXmlAsUser() throws Exception {
        saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForPutAsUser();
        TestResults<R> results = defaultXmlTestForPut();
        ResultActions mvc = results.getMvc();

        validateXmlUtf8Response(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = results.getResource();

            validateXmlLink(mvc, "GET", getUrlForGet(resource));
            validateXmlLink(mvc, "POST", getUrlForPost());
            validateXmlLink(mvc, "DELETE", getUrlForDelete(resource));
        }
    }

    @Test
    @WithUserDetails(value = CONTENT_CREATOR, userDetailsServiceBeanName = "appUserDetailsService")
    public void putJsonAsContentCreator() throws Exception {
        saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForPutAsContentCreator();
        TestResults<R> results = defaultJsonTestForPut();
        ResultActions mvc = results.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = results.getResource();

            validateJsonLink(mvc, "GET", getUrlForGet(resource));
            validateJsonLink(mvc, "POST", getUrlForPost());
            validateJsonLink(mvc, "DELETE", getUrlForDelete(resource));
        }
    }

    @Test
    @WithUserDetails(value = CONTENT_CREATOR, userDetailsServiceBeanName = "appUserDetailsService")
    public void putXmlAsContentCreator() throws Exception {
        saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForPutAsContentCreator();
        TestResults<R> results = defaultXmlTestForPut();
        ResultActions mvc = results.getMvc();

        validateXmlUtf8Response(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = results.getResource();

            validateXmlLink(mvc, "GET", getUrlForGet(resource));
            validateXmlLink(mvc, "POST", getUrlForPost());
            validateXmlLink(mvc, "DELETE", getUrlForDelete(resource));
        }
    }

    @Test
    @WithUserDetails(value = MODERATOR, userDetailsServiceBeanName = "appUserDetailsService")
    public void putJsonAsModerator() throws Exception {
        saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForPutAsModerator();
        TestResults<R> results = defaultJsonTestForPut();
        ResultActions mvc = results.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = results.getResource();

            validateJsonLink(mvc, "GET", getUrlForGet(resource));
            validateJsonLink(mvc, "POST", getUrlForPost());
            validateJsonLink(mvc, "DELETE", getUrlForDelete(resource));
        }
    }

    @Test
    @WithUserDetails(value = MODERATOR, userDetailsServiceBeanName = "appUserDetailsService")
    public void putXmlAsModerator() throws Exception {
        saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForPutAsModerator();
        TestResults<R> results = defaultXmlTestForPut();
        ResultActions mvc = results.getMvc();

        validateXmlUtf8Response(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = results.getResource();

            validateXmlLink(mvc, "GET", getUrlForGet(resource));
            validateXmlLink(mvc, "POST", getUrlForPost());
            validateXmlLink(mvc, "DELETE", getUrlForDelete(resource));
        }
    }

    @Test
    @WithUserDetails(value = ADMIN, userDetailsServiceBeanName = "appUserDetailsService")
    public void putJsonAsAdmin() throws Exception {
        saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForPutAsAdmin();
        TestResults<R> results = defaultJsonTestForPut();
        ResultActions mvc = results.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = results.getResource();

            validateJsonLink(mvc, "GET", getUrlForGet(resource));
            validateJsonLink(mvc, "POST", getUrlForPost());
            validateJsonLink(mvc, "DELETE", getUrlForDelete(resource));
        }
    }

    @Test
    @WithUserDetails(value = ADMIN, userDetailsServiceBeanName = "appUserDetailsService")
    public void putXmlAsAdmin() throws Exception {
        saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForPutAsAdmin();
        TestResults<R> results = defaultXmlTestForPut();
        ResultActions mvc = results.getMvc();

        validateXmlUtf8Response(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = results.getResource();

            validateXmlLink(mvc, "GET", getUrlForGet(resource));
            validateXmlLink(mvc, "POST", getUrlForPost());
            validateXmlLink(mvc, "DELETE", getUrlForDelete(resource));
        }
    }

    @Test
    @WithUserDetails(value = USER, userDetailsServiceBeanName = "appUserDetailsService")
    public void deleteJsonAsUser() throws Exception {
        saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForDeleteAsUser();
        TestResults<R> results = defaultJsonTestForDelete();
        ResultActions mvc = results.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            validateJsonLink(mvc, "GET All", getUrlForGet());
        }
    }

    @Test
    @WithUserDetails(value = USER, userDetailsServiceBeanName = "appUserDetailsService")
    public void deleteXmlAsUser() throws Exception {
        saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForDeleteAsUser();
        TestResults<R> results = defaultXmlTestForDelete();
        ResultActions mvc = results.getMvc();

        validateXmlUtf8Response(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            validateXmlLink(mvc, "GET All", getUrlForGet());
        }
    }

    @Test
    @WithUserDetails(value = USER, userDetailsServiceBeanName = "appUserDetailsService")
    public void deleteNonExistingResourceJsonAsUser() throws Exception {
        saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForDeleteAsUserResourceNotExists();
        TestResults<R> results = defaultJsonTestForDeleteNonExistingResource();
        ResultActions mvc = results.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            validateJsonLink(mvc, "GET All", getUrlForGet());
        }
    }

    @Test
    @WithUserDetails(value = USER, userDetailsServiceBeanName = "appUserDetailsService")
    public void deleteNonExistingResourceXmlAsUser() throws Exception {
        saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForDeleteAsUserResourceNotExists();
        TestResults<R> results = defaultXmlTestForDeleteNonExistingResource();
        ResultActions mvc = results.getMvc();

        validateXmlResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            validateXmlLink(mvc, "GET All", getUrlForGet());
        }
    }

    @Test
    @WithUserDetails(value = CONTENT_CREATOR, userDetailsServiceBeanName = "appUserDetailsService")
    public void deleteJsonAsContentCreator() throws Exception {
        saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsContentCreator();
        TestResults<R> results = defaultJsonTestForDelete();
        ResultActions mvc = results.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            validateJsonLink(mvc, "GET All", getUrlForGet());
        }
    }

    @Test
    @WithUserDetails(value = CONTENT_CREATOR, userDetailsServiceBeanName = "appUserDetailsService")
    public void deleteXmlAsContentCreator() throws Exception {
        saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsContentCreator();
        TestResults<R> results = defaultXmlTestForDelete();
        ResultActions mvc = results.getMvc();

        validateXmlUtf8Response(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            validateXmlLink(mvc, "GET All", getUrlForGet());
        }
    }

    @Test
    @WithUserDetails(value = CONTENT_CREATOR, userDetailsServiceBeanName = "appUserDetailsService")
    public void deleteNonExistingResourceJsonAsContentCreator() throws Exception {
        saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsContentCreatorResourceNotExists();
        TestResults<R> results = defaultJsonTestForDeleteNonExistingResource();
        ResultActions mvc = results.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            validateJsonLink(mvc, "GET All", getUrlForGet());
        }
    }

    @Test
    @WithUserDetails(value = CONTENT_CREATOR, userDetailsServiceBeanName = "appUserDetailsService")
    public void deleteNonExistingResourceXmlAsContentCreator() throws Exception {
        saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsContentCreatorResourceNotExists();
        TestResults<R> results = defaultXmlTestForDeleteNonExistingResource();
        ResultActions mvc = results.getMvc();

        validateXmlResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            validateXmlLink(mvc, "GET All", getUrlForGet());
        }
    }

    @Test
    @WithUserDetails(value = MODERATOR, userDetailsServiceBeanName = "appUserDetailsService")
    public void deleteJsonAsModerator() throws Exception {
        saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsModerator();
        TestResults<R> results = defaultJsonTestForDelete();
        ResultActions mvc = results.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            validateJsonLink(mvc, "GET All", getUrlForGet());
        }
    }

    @Test
    @WithUserDetails(value = MODERATOR, userDetailsServiceBeanName = "appUserDetailsService")
    public void deleteXmlAsModerator() throws Exception {
        saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsModerator();
        TestResults<R> results = defaultXmlTestForDelete();
        ResultActions mvc = results.getMvc();

        validateXmlUtf8Response(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            validateXmlLink(mvc, "GET All", getUrlForGet());
        }
    }

    @Test
    @WithUserDetails(value = MODERATOR, userDetailsServiceBeanName = "appUserDetailsService")
    public void deleteNonExistingResourceJsonAsModerator() throws Exception {
        saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsModeratorResourceNotExists();
        TestResults<R> results = defaultJsonTestForDeleteNonExistingResource();
        ResultActions mvc = results.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            validateJsonLink(mvc, "GET All", getUrlForGet());
        }
    }

    @Test
    @WithUserDetails(value = MODERATOR, userDetailsServiceBeanName = "appUserDetailsService")
    public void deleteNonExistingResourceXmlAsModerator() throws Exception {
        saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsModeratorResourceNotExists();
        TestResults<R> results = defaultXmlTestForDeleteNonExistingResource();
        ResultActions mvc = results.getMvc();

        validateXmlResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            validateXmlLink(mvc, "GET All", getUrlForGet());
        }
    }

    @Test
    @WithUserDetails(value = ADMIN, userDetailsServiceBeanName = "appUserDetailsService")
    public void deleteJsonAsAdmin() throws Exception {
        saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForDeleteAsAdmin();
        TestResults<R> results = defaultJsonTestForDelete();
        ResultActions mvc = results.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            validateJsonLink(mvc, "GET All", getUrlForGet());
        }
    }

    @Test
    @WithUserDetails(value = ADMIN, userDetailsServiceBeanName = "appUserDetailsService")
    public void deleteXmlAsAdmin() throws Exception {
        saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForDeleteAsAdmin();
        TestResults<R> results = defaultXmlTestForDelete();
        ResultActions mvc = results.getMvc();

        validateXmlUtf8Response(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            validateXmlLink(mvc, "GET All", getUrlForGet());
        }
    }

    @Test
    @WithUserDetails(value = ADMIN, userDetailsServiceBeanName = "appUserDetailsService")
    public void deleteNonExistingResourceJsonAsAdmin() throws Exception {
        saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForDeleteAsAdminResourceNotExists();
        TestResults<R> results = defaultJsonTestForDeleteNonExistingResource();
        ResultActions mvc = results.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            validateJsonLink(mvc, "GET All", getUrlForGet());
        }
    }

    @Test
    @WithUserDetails(value = ADMIN, userDetailsServiceBeanName = "appUserDetailsService")
    public void deleteNonExistingResourceXmlAsAdmin() throws Exception {
        saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForDeleteAsAdminResourceNotExists();
        TestResults<R> results = defaultXmlTestForDeleteNonExistingResource();
        ResultActions mvc = results.getMvc();

        validateXmlResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            validateXmlLink(mvc, "GET All", getUrlForGet());
        }
    }

}
