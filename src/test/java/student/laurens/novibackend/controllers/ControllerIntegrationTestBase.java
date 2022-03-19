package student.laurens.novibackend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import student.laurens.novibackend.NoviBackendApplication;
import student.laurens.novibackend.entities.AbstractEntity;
import student.laurens.novibackend.entities.Role;
import student.laurens.novibackend.entities.User;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
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
public abstract class ControllerIntegrationTestBase<R extends AbstractEntity> {

    protected final String USER = "DefaultUser";
    protected final String USER_ROLE = "USER";

    protected final String ADMIN = "DefaultAdmin";
    protected final String ADMIN_ROLE = "ADMIN";

    protected final String CONTENT_CREATOR = "DefaultContentCreator";
    protected final String CONTENT_CREATOR_ROLE = "CONTENT_CREATOR";

    protected final String MODERATOR = "DefaultModerator";
    protected final String MODERATOR_ROLE = "MODERATOR";

    protected final MediaType DEFAULT_JSON_ACCEPT = MediaType.APPLICATION_JSON_UTF8;
    protected final String DEFAULT_JSON_ACCEPT_VALUE = MediaType.APPLICATION_JSON_UTF8_VALUE;

    protected final MediaType DEFAULT_JSON_CONTENT_TYPE = MediaType.APPLICATION_JSON;
    protected final String DEFAULT_JSON_CONTENT_TYPE_VALUE = MediaType.APPLICATION_JSON_VALUE;

    /*
        java.lang.AssertionError: Response header 'Content-Type'
        Expected :application/xml
        Actual   :application/xml;charset=UTF-8
     */
    protected final MediaType DEFAULT_XML_ACCEPT = MediaType.APPLICATION_XML;
    protected final String DEFAULT_XML_ACCEPT_VALUE = MediaType.APPLICATION_XML_VALUE;
    protected final String DEFAULT_XML_ACCEPT_UTF8_VALUE = DEFAULT_XML_ACCEPT_VALUE + ";charset=UTF-8";

    protected final MediaType DEFAULT_XML_CONTENT_TYPE = MediaType.APPLICATION_XML;
    protected final String DEFAULT_XML_CONTENT_TYPE_VALUE = MediaType.APPLICATION_XML_VALUE;

    @Autowired
    protected MockMvc mvc;


    /* Request body parsing */

    public static String asString(final MediaType contentType, final Object obj){
        return contentType.equals(MediaType.APPLICATION_JSON) ? asJsonString(obj) : asXmlString(obj);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String asXmlString(final Object obj) {
        try {
            return new XmlMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /* Prepare HTTP calls with media types for content type and accept headers. */

    /**
     * Implement this method by a resource specific implementation to return the uri pattern for retrieving a list of the resource.
     *
     * @return URL to call.
     */
    abstract protected String getUrlForGet();

    /**
     * Implement this method by a resource specific implementation to return the uri pattern for retrieving a specific instance of the resource.
     *
     * @return URL to call.
     */
    abstract protected String getUrlForGet(R resource);

    /**
     * Implement this method by a resource specific implementation to return the uri pattern for updating a specific instance of the resource.
     *
     * @return URL to call.
     */
    abstract protected String getUrlForPut(R resource);

    /**
     * Implement this method by a resource specific implementation to return the uri pattern for creating a new instance of the resource.
     *
     * @return URL to call.
     */
    abstract protected String getUrlForPost();

    /**
     * Implement this method by a resource specific implementation to return the uri pattern for deleting a specific instance of the resource.
     *
     * @return URL to call.
     */
    abstract protected String getUrlForDelete(R resource);

    protected ResultActions deleteAsJson(R resource) throws Exception {
        return executeDelete(resource, DEFAULT_JSON_ACCEPT);
    }

    protected ResultActions deleteAsXml(R resource) throws Exception {
        return executeDelete(resource, DEFAULT_XML_ACCEPT);
    }

    protected ResultActions executeDelete(R resource, MediaType accept) throws Exception {
        return deleteResource(getUrlForDelete(resource), accept);
    }

    protected ResultActions getAsJson() throws Exception {
        return executeGet(DEFAULT_JSON_ACCEPT);
    }

    protected ResultActions getAsXml() throws Exception {
        return executeGet(DEFAULT_XML_ACCEPT);
    }

    protected ResultActions executeGet(MediaType accept) throws Exception {
        return getResource(getUrlForGet(), accept);
    }

    protected ResultActions getAsJson(R resource) throws Exception {
        return executeGet(resource, DEFAULT_JSON_ACCEPT);
    }

    protected ResultActions getAsXml(R resource) throws Exception {
        return executeGet(resource, DEFAULT_XML_ACCEPT);
    }

    protected ResultActions executeGet(R resource, MediaType accept) throws Exception {
        return getResource(getUrlForGet(resource), accept);
    }

    protected ResultActions postAsJson(R resource) throws Exception {
        return executePost(resource, DEFAULT_JSON_CONTENT_TYPE, DEFAULT_JSON_ACCEPT);
    }

    protected ResultActions postAsXml(R resource) throws Exception {
        return executePost(resource, DEFAULT_XML_CONTENT_TYPE, DEFAULT_XML_ACCEPT);
    }

    protected ResultActions executePost(R resource, MediaType contentType, MediaType accept) throws Exception {
        return postResource(getUrlForPost(), resource, accept, contentType);
    }

    protected ResultActions updateAsJson(R resource) throws Exception {
        return executePut(resource, DEFAULT_JSON_CONTENT_TYPE, DEFAULT_JSON_ACCEPT);
    }

    protected ResultActions updateAsXml(R resource) throws Exception {
        return executePut(resource, DEFAULT_XML_CONTENT_TYPE, DEFAULT_XML_ACCEPT);
    }

    protected ResultActions executePut(R resource, MediaType contentType, MediaType accept) throws Exception {
        return putResource(getUrlForPut(resource), resource, accept, contentType);
    }

    /* Execute HTTP calls */

    protected ResultActions putResource(String url, R resource, MediaType accept, MediaType contentType) throws Exception {
        return mvc.perform(put(url)
                .content(asString(contentType, resource))
                .contentType(contentType)
                .accept(accept));
    }

    protected ResultActions postResource(String url, R resource, MediaType accept, MediaType contentType) throws Exception {
        return mvc.perform(post(url)
                .content(asString(contentType, resource))
                .contentType(contentType)
                .accept(accept));
    }

    protected ResultActions getResource(String url, MediaType accept) throws Exception {
        return mvc.perform(get(url)
                .accept(accept));
    }

    protected ResultActions deleteResource(String url, MediaType accept) throws Exception {
        return mvc.perform(delete(url)
                .accept(accept));
    }

    /* Expects on response */

    public ResultActions expectXmlResponse(ResultActions mvc) throws Exception {
        return expectContentTypeResponse(mvc, DEFAULT_XML_ACCEPT_VALUE);
    }

    public ResultActions expectXmlUtf8Response(ResultActions mvc) throws Exception {
        return expectContentTypeResponse(mvc, DEFAULT_XML_ACCEPT_UTF8_VALUE);
    }

    public ResultActions expectJsonResponse(ResultActions mvc) throws Exception {
        return expectContentTypeResponse(mvc, DEFAULT_JSON_ACCEPT_VALUE);
    }

    private ResultActions expectContentTypeResponse(ResultActions mvc, String contentType) throws Exception {
        return mvc.andExpect(header().string(HttpHeaders.CONTENT_TYPE, contentType));
    }

    /* Default testing */

    /**
     * Implement this method by a resource specific implementation to create sample instances of the resource.
     *
     * @return Sample instance of resource.
     */
    abstract protected R create();

    /**
     * Implement this method by a resource specific implementation to save into the repository a sample instances of the resource.
     *
     * @return Sample instance of resource.
     */
    abstract protected R save(R resource);

    /**
     * Implement this method by a resource specific implementation to modify a sample instances of the resource.
     *
     * @return Sample instance of resource.
     */
    abstract protected R modify(R resource);

    /**
     * Override this method by a resource specific implementation to send GET messages with JSON as default data format.
     *
     * @return ResultActions instance which contains the response of the GET call.
     */
    public ResultActions defaultJsonTestForGet() throws Exception {
        return getAsJson();
    }

    /**
     * Override this method by a resource specific implementation to send GET messages with XML as default data format.
     *
     * @return ResultActions instance which contains the response of the GET call.
     */
    public ResultActions defaultXmlTestForGet() throws Exception {
        return getAsXml();
    }

    /**
     * Override this method by a resource specific implementation to override the default value of HttpStatus.FORBIDDEN.
     *
     * @return The expected HttpStatus when a {@link User} with admin {@link Role} calls GET endpoint for specified resource.
     */
    public HttpStatus expectedStatusForGetAsAdmin() {
        return HttpStatus.OK;
    }

    /**
     * Override this method by a resource specific implementation to override the default value of HttpStatus.FORBIDDEN.
     *
     * @return The expected HttpStatus when a {@link User} with user {@link Role} calls GET endpoint for specified resource.
     */
    public HttpStatus expectedStatusForGetAsUser() {
        return HttpStatus.FORBIDDEN;
    }

    /**
     * Override this method by a resource specific implementation to override the default value of HttpStatus.FORBIDDEN.
     *
     * @return The expected HttpStatus when a {@link User} with content creator {@link Role} calls GET endpoint for specified resource.
     */
    public HttpStatus expectedStatusForGetAsContentCreator() {
        return HttpStatus.FORBIDDEN;
    }

    /**
     * Override this method by a resource specific implementation to override the default value of HttpStatus.FORBIDDEN.
     *
     * @return The expected HttpStatus when a {@link User} with moderator {@link Role} calls GET endpoint for specified resource.
     */
    public HttpStatus expectedStatusForGetAsModerator() {
        return HttpStatus.FORBIDDEN;
    }

    @Test
    public void getIsUnAuthorized() throws Exception {
        defaultJsonTestForGet().andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = USER)
    public void getJsonAsUser() throws Exception {
        HttpStatus expectedStatus = expectedStatusForGetAsUser();
        ResultActions mvc = defaultJsonTestForGet();

        ResponseValidator validator = new ResponseValidator(mvc, expectedStatus, DEFAULT_JSON_ACCEPT_VALUE);

        validator.validate();
    }

    @Test
    @WithMockUser(value = USER)
    public void getXmlAsUser() throws Exception {
        HttpStatus expectedStatus = expectedStatusForGetAsUser();
        ResultActions mvc = defaultXmlTestForGet();

        ResponseValidator validator = new ResponseValidator(mvc, expectedStatus, DEFAULT_XML_ACCEPT_UTF8_VALUE);

        validator.validate();
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void getJsonAsContentCreator() throws Exception {
        HttpStatus expectedStatus = expectedStatusForGetAsContentCreator();
        ResultActions mvc = defaultJsonTestForGet();

        ResponseValidator validator = new ResponseValidator(mvc, expectedStatus, DEFAULT_JSON_ACCEPT_VALUE);

        validator.validate();
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void getXmlAsContentCreator() throws Exception {
        HttpStatus expectedStatus = expectedStatusForGetAsContentCreator();
        ResultActions mvc = defaultXmlTestForGet();

        ResponseValidator validator = new ResponseValidator(mvc, expectedStatus, DEFAULT_XML_ACCEPT_UTF8_VALUE);

        validator.validate();
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void getJsonAsModerator() throws Exception {
        HttpStatus expectedStatus = expectedStatusForGetAsModerator();
        ResultActions mvc = defaultJsonTestForGet();

        ResponseValidator validator = new ResponseValidator(mvc, expectedStatus, DEFAULT_JSON_ACCEPT_VALUE);

        validator.validate();
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void getXmlAsModerator() throws Exception {
        HttpStatus expectedStatus = expectedStatusForGetAsModerator();
        ResultActions mvc = defaultXmlTestForGet();

        ResponseValidator validator = new ResponseValidator(mvc, expectedStatus, DEFAULT_XML_ACCEPT_UTF8_VALUE);

        validator.validate();
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void getJsonAsAdmin() throws Exception {
        HttpStatus expectedStatus = expectedStatusForGetAsAdmin();
        ResultActions mvc = defaultJsonTestForGet();

        ResponseValidator validator = new ResponseValidator(mvc, expectedStatus, DEFAULT_JSON_ACCEPT_VALUE);

        validator.validate();
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void getXmlAsAdmin() throws Exception {
        HttpStatus expectedStatus = expectedStatusForGetAsAdmin();
        ResultActions mvc = defaultXmlTestForGet();

        ResponseValidator validator = new ResponseValidator(mvc, expectedStatus, DEFAULT_XML_ACCEPT_UTF8_VALUE);

        validator.validate();
    }

    /**
     * Override this method by a resource specific implementation to send POST messages with JSON as default data format.
     *
     * @return ResultActions instance which contains the response of the POST call.
     */
    public ResultActions defaultJsonTestForPost() throws Exception {
        return postAsJson(create());
    }

    /**
     * Override this method by a resource specific implementation to send POST messages with XML as default data format.
     *
     * @return ResultActions instance which contains the response of the POST call.
     */
    public ResultActions defaultXmlTestForPost() throws Exception {
        return postAsXml(create());
    }

    /**
     * Override this method by a resource specific implementation to override the default value of HttpStatus.CREATED.
     *
     * @return The expected HttpStatus when a {@link User} with admin {@link Role} calls POST endpoint for specified resource.
     */
    public HttpStatus expectedStatusForPostAsAdmin() {
        return HttpStatus.CREATED;
    }

    /**
     * Override this method by a resource specific implementation to override the default value of HttpStatus.FORBIDDEN.
     *
     * @return The expected HttpStatus when a {@link User} with user {@link Role} calls POST endpoint for specified resource.
     */
    public HttpStatus expectedStatusForPostAsUser() {
        return HttpStatus.FORBIDDEN;
    }

    /**
     * Override this method by a resource specific implementation to override the default value of HttpStatus.FORBIDDEN.
     *
     * @return The expected HttpStatus when a {@link User} with content creator {@link Role} calls POST endpoint for specified resource.
     */
    public HttpStatus expectedStatusForPostAsContentCreator() {
        return HttpStatus.FORBIDDEN;
    }

    /**
     * Override this method by a resource specific implementation to override the default value of HttpStatus.FORBIDDEN.
     *
     * @return The expected HttpStatus when a {@link User} with moderator {@link Role} calls POST endpoint for specified resource.
     */
    public HttpStatus expectedStatusForPostAsModerator() {
        return HttpStatus.FORBIDDEN;
    }

    @Test
    @WithMockUser(value = USER)
    public void postJsonAsUser() throws Exception {
        HttpStatus expectedStatus = expectedStatusForPostAsUser();
        ResultActions mvc = defaultJsonTestForPost();

        ResponseValidator validator = new ResponseValidator(mvc, expectedStatus, DEFAULT_JSON_ACCEPT_VALUE);

        validator.validate();
    }

    @Test
    @WithMockUser(value = USER)
    public void postXmlAsUser() throws Exception {
        HttpStatus expectedStatus = expectedStatusForPostAsUser();
        ResultActions mvc = defaultXmlTestForPost();

        ResponseValidator validator = new ResponseValidator(mvc, expectedStatus, DEFAULT_XML_ACCEPT_UTF8_VALUE);

        validator.validate();
    }


    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void postXmlAsContentCreator() throws Exception {
        HttpStatus expectedStatus = expectedStatusForPostAsContentCreator();
        ResultActions mvc = defaultXmlTestForPost();

        ResponseValidator validator = new ResponseValidator(mvc, expectedStatus, DEFAULT_XML_ACCEPT_UTF8_VALUE);

        validator.validate();
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void postJsonAsContentCreator() throws Exception {
        HttpStatus expectedStatus = expectedStatusForPostAsContentCreator();
        ResultActions mvc = defaultJsonTestForPost();

        ResponseValidator validator = new ResponseValidator(mvc, expectedStatus, DEFAULT_JSON_ACCEPT_VALUE);

        validator.validate();
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void postXmlAsModerator() throws Exception {
        HttpStatus expectedStatus = expectedStatusForPostAsModerator();
        ResultActions mvc = defaultXmlTestForPost();

        ResponseValidator validator = new ResponseValidator(mvc, expectedStatus, DEFAULT_XML_ACCEPT_UTF8_VALUE);

        validator.validate();
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void postJsonAsModerator() throws Exception {
        HttpStatus expectedStatus = expectedStatusForPostAsModerator();
        ResultActions mvc = defaultJsonTestForPost();

        ResponseValidator validator = new ResponseValidator(mvc, expectedStatus, DEFAULT_JSON_ACCEPT_VALUE);

        validator.validate();
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void postJsonAsAdmin() throws Exception {
        HttpStatus expectedStatus = expectedStatusForPostAsAdmin();
        ResultActions mvc = defaultJsonTestForPost();

        ResponseValidator validator = new ResponseValidator(mvc, expectedStatus, DEFAULT_JSON_ACCEPT_VALUE);

        validator.validate();
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void postXmlAsAdmin() throws Exception {
        HttpStatus expectedStatus = expectedStatusForPostAsAdmin();
        ResultActions mvc = defaultXmlTestForPost();

        ResponseValidator validator = new ResponseValidator(mvc, expectedStatus, DEFAULT_XML_ACCEPT_UTF8_VALUE);

        validator.validate();
    }

    /**
     * Override this method by a resource specific implementation to send PUT messages with JSON as default data format.
     *
     * @return ResultActions instance which contains the response of the PUT call.
     */
    public ResultActions defaultJsonTestForPut() throws Exception {
        return updateAsJson(modify(save(create())));
    }

    /**
     * Override this method by a resource specific implementation to send PUT messages with XML as default data format.
     *
     * @return ResultActions instance which contains the response of the PUT call.
     */
    public ResultActions defaultXmlTestForPut() throws Exception {
        return updateAsXml(modify(save(create())));
    }

    /**
     * Override this method by a resource specific implementation to override the default value of HttpStatus.ACCEPTED.
     *
     * @return The expected HttpStatus when a {@link User} with admin {@link Role} calls PUT endpoint for specified resource.
     */
    public HttpStatus expectedStatusForPutAsAdmin() {
        return HttpStatus.ACCEPTED;
    }

    /**
     * Override this method by a resource specific implementation to override the default value of HttpStatus.FORBIDDEN.
     *
     * @return The expected HttpStatus when a {@link User} with user {@link Role} calls PUT endpoint for specified resource.
     */
    public HttpStatus expectedStatusForPutAsUser() {
        return HttpStatus.FORBIDDEN;
    }

    /**
     * Override this method by a resource specific implementation to override the default value of HttpStatus.FORBIDDEN.
     *
     * @return The expected HttpStatus when a {@link User} with content creator {@link Role} calls PUT endpoint for specified resource.
     */
    public HttpStatus expectedStatusForPutAsContentCreator() {
        return HttpStatus.FORBIDDEN;
    }

    /**
     * Override this method by a resource specific implementation to override the default value of HttpStatus.FORBIDDEN.
     *
     * @return The expected HttpStatus when a {@link User} with moderator {@link Role} calls PUT endpoint for specified resource.
     */
    public HttpStatus expectedStatusForPutAsModerator() {
        return HttpStatus.FORBIDDEN;
    }

    @Test
    @WithMockUser(value = USER)
    public void putJsonAsUser() throws Exception {
        HttpStatus expectedStatus = expectedStatusForPutAsUser();
        ResultActions mvc = defaultJsonTestForPut();

        ResponseValidator validator = new ResponseValidator(mvc, expectedStatus, DEFAULT_JSON_ACCEPT_VALUE);

        validator.validate();
    }

    @Test
    @WithMockUser(value = USER)
    public void putXmlAsUser() throws Exception {
        HttpStatus expectedStatus = expectedStatusForPutAsUser();
        ResultActions mvc = defaultXmlTestForPut();

        ResponseValidator validator = new ResponseValidator(mvc, expectedStatus, DEFAULT_XML_ACCEPT_UTF8_VALUE);

        validator.validate();
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void putJsonAsContentCreator() throws Exception {
        HttpStatus expectedStatus = expectedStatusForPutAsContentCreator();
        ResultActions mvc = defaultJsonTestForPut();

        ResponseValidator validator = new ResponseValidator(mvc, expectedStatus, DEFAULT_JSON_ACCEPT_VALUE);

        validator.validate();
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void putXmlAsContentCreator() throws Exception {
        HttpStatus expectedStatus = expectedStatusForPutAsContentCreator();
        ResultActions mvc = defaultXmlTestForPut();

        ResponseValidator validator = new ResponseValidator(mvc, expectedStatus, DEFAULT_XML_ACCEPT_UTF8_VALUE);

        validator.validate();
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void putJsonAsModerator() throws Exception {
        HttpStatus expectedStatus = expectedStatusForPutAsModerator();
        ResultActions mvc = defaultJsonTestForPut();

        ResponseValidator validator = new ResponseValidator(mvc, expectedStatus, DEFAULT_JSON_ACCEPT_VALUE);

        validator.validate();
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void putXmlAsModerator() throws Exception {
        HttpStatus expectedStatus = expectedStatusForPutAsModerator();
        ResultActions mvc = defaultXmlTestForPut();

        ResponseValidator validator = new ResponseValidator(mvc, expectedStatus, DEFAULT_XML_ACCEPT_UTF8_VALUE);

        validator.validate();
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void putJsonAsAdmin() throws Exception {
        HttpStatus expectedStatus = expectedStatusForPutAsAdmin();
        ResultActions mvc = defaultJsonTestForPut();

        ResponseValidator validator = new ResponseValidator(mvc, expectedStatus, DEFAULT_JSON_ACCEPT_VALUE);

        validator.validate();
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void putXmlAsAdmin() throws Exception {
        HttpStatus expectedStatus = expectedStatusForPutAsAdmin();
        ResultActions mvc = defaultXmlTestForPut();

        ResponseValidator validator = new ResponseValidator(mvc, expectedStatus, DEFAULT_XML_ACCEPT_UTF8_VALUE);

        validator.validate();
    }

    /**
     * Override this method by a resource specific implementation to send DELETE messages with JSON as default data format.
     *
     * @return ResultActions instance which contains the response of the DELETE call.
     */
    public ResultActions defaultJsonTestForDelete() throws Exception {
        return updateAsJson(modify(save(create())));
    }

    /**
     * Override this method by a resource specific implementation to send DELETE messages with XML as default data format.
     *
     * @return ResultActions instance which contains the response of the DELETE call.
     */
    public ResultActions defaultXmlTestForDelete() throws Exception {
        return updateAsXml(save(create()));
    }

    /**
     * Override this method by a resource specific implementation to override the default value of HttpStatus.ACCEPTED.
     *
     * @return The expected HttpStatus when a {@link User} with admin {@link Role} calls DELETE endpoint for specified resource.
     */
    public HttpStatus expectedStatusForDeleteAsAdmin() {
        return HttpStatus.ACCEPTED;
    }

    /**
     * Override this method by a resource specific implementation to override the default value of HttpStatus.FORBIDDEN.
     *
     * @return The expected HttpStatus when a {@link User} with user {@link Role} calls DELETE endpoint for specified resource.
     */
    public HttpStatus expectedStatusForDeleteAsUser() { return HttpStatus.FORBIDDEN; }

    /**
     * Override this method by a resource specific implementation to override the default value of HttpStatus.FORBIDDEN.
     *
     * @return The expected HttpStatus when a {@link User} with content creator {@link Role} calls DELETE endpoint for specified resource.
     */
    public HttpStatus expectedStatusForDeleteAsContentCreator() {
        return HttpStatus.FORBIDDEN;
    }

    /**
     * Override this method by a resource specific implementation to override the default value of HttpStatus.FORBIDDEN.
     *
     * @return The expected HttpStatus when a {@link User} with moderator {@link Role} calls DELETE endpoint for specified resource.
     */
    public HttpStatus expectedStatusForDeleteAsModerator() {
        return HttpStatus.FORBIDDEN;
    }

    @Test
    @WithMockUser(value = USER)
    public void deleteJsonAsUser() throws Exception {
        HttpStatus expectedStatus = expectedStatusForDeleteAsUser();
        ResultActions mvc = defaultJsonTestForDelete();

        ResponseValidator validator = new ResponseValidator(mvc, expectedStatus, DEFAULT_JSON_ACCEPT_VALUE);

        validator.validate();
    }

    @Test
    @WithMockUser(value = USER)
    public void deleteXmlAsUser() throws Exception {
        HttpStatus expectedStatus = expectedStatusForDeleteAsUser();
        ResultActions mvc = defaultXmlTestForDelete();

        ResponseValidator validator = new ResponseValidator(mvc, expectedStatus, DEFAULT_XML_ACCEPT_UTF8_VALUE);

        validator.validate();
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void deleteJsonAsContentCreator() throws Exception {
        HttpStatus expectedStatus = expectedStatusForDeleteAsContentCreator();
        ResultActions mvc = defaultJsonTestForDelete();

        ResponseValidator validator = new ResponseValidator(mvc, expectedStatus, DEFAULT_JSON_ACCEPT_VALUE);

        validator.validate();
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void deleteXmlAsContentCreator() throws Exception {
        HttpStatus expectedStatus = expectedStatusForDeleteAsContentCreator();
        ResultActions mvc = defaultXmlTestForDelete();

        ResponseValidator validator = new ResponseValidator(mvc, expectedStatus, DEFAULT_XML_ACCEPT_UTF8_VALUE);

        validator.validate();
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void deleteJsonAsModerator() throws Exception {
        HttpStatus expectedStatus = expectedStatusForDeleteAsModerator();
        ResultActions mvc = defaultJsonTestForDelete();

        ResponseValidator validator = new ResponseValidator(mvc, expectedStatus, DEFAULT_JSON_ACCEPT_VALUE);

        validator.validate();
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void deleteXmlAsModerator() throws Exception {
        HttpStatus expectedStatus = expectedStatusForDeleteAsModerator();
        ResultActions mvc = defaultXmlTestForDelete();

        ResponseValidator validator = new ResponseValidator(mvc, expectedStatus, DEFAULT_XML_ACCEPT_UTF8_VALUE);

        validator.validate();
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void deleteJsonAsAdmin() throws Exception {
        HttpStatus expectedStatus = expectedStatusForDeleteAsAdmin();
        ResultActions mvc = defaultJsonTestForDelete();

        ResponseValidator validator = new ResponseValidator(mvc, expectedStatus, DEFAULT_JSON_ACCEPT_VALUE);

        validator.validate();
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void deleteXmlAsAdmin() throws Exception {
        HttpStatus expectedStatus = expectedStatusForDeleteAsAdmin();
        ResultActions mvc = defaultXmlTestForDelete();

        ResponseValidator validator = new ResponseValidator(mvc, expectedStatus, DEFAULT_XML_ACCEPT_UTF8_VALUE);

        validator.validate();
    }
}
