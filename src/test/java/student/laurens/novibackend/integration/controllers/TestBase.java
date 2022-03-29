package student.laurens.novibackend.integration.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.After;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import student.laurens.novibackend.NoviBackendApplication;
import student.laurens.novibackend.entities.AbstractEntity;
import student.laurens.novibackend.entities.Blogpost;
import student.laurens.novibackend.entities.User;
import student.laurens.novibackend.repositories.BlogpostRepository;
import student.laurens.novibackend.repositories.RoleRepository;
import student.laurens.novibackend.repositories.UserRepository;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

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
public abstract class TestBase <R extends AbstractEntity>  {
    protected Logger log = LoggerFactory.getLogger(ControllerIntegrationTestBase.class);

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

    @Autowired
    protected UserRepository userRepository;

    @After
    public void base_after(){
        log.debug("Deleting all users from repository.");

        userRepository.deleteAll();
    }
//
//    @Before
//    public void base_before(){
//        log.debug("Deleting all users from repository.");
//
//        userRepository.deleteAll();
//    }

    @Autowired
    protected RoleRepository roleRepository;

    @Autowired
    protected BlogpostRepository blogpostRepository;

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
    abstract protected String getUrlForGet(final R resource);

    /**
     * Implement this method by a resource specific implementation to return the uri pattern for updating a specific instance of the resource.
     *
     * @return URL to call.
     */
    abstract protected String getUrlForPut(final R resource);

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
    abstract protected String getUrlForDelete(final R resource);

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
    abstract protected R save(final R resource);

    /**
     * Implement this method by a resource specific implementation to modify a sample instances of the resource.
     *
     * @return Sample instance of resource.
     */
    abstract protected R modify(final R resource);


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
     * Override this method by a resource specific implementation to send DELETE messages with JSON as default data format.
     *
     * @return ResultActions instance which contains the response of the DELETE call.
     */
    public ResultActions defaultJsonTestForDelete() throws Exception {
        return deleteAsJson(modify(save(create())));
    }

    /**
     * Override this method by a resource specific implementation to send DELETE messages with XML as default data format.
     *
     * @return ResultActions instance which contains the response of the DELETE call.
     */
    public ResultActions defaultXmlTestForDelete() throws Exception {
        return deleteAsXml(save(create()));
    }

    /**
     * Override this method by a resource specific implementation to send DELETE messages with JSON as default data format.
     * This DELETE call will be sent to a resource which does not exist.
     *
     * @return ResultActions instance which contains the response of the DELETE call.
     */
    public ResultActions defaultJsonTestForDeleteNonExistingResource() throws Exception {
        return deleteAsJson(modify(create()));
    }

    /**
     * Override this method by a resource specific implementation to send DELETE messages with XML as default data format.
     *
     * @return ResultActions instance which contains the response of the DELETE call.
     */
    public ResultActions defaultXmlTestForDeleteNonExistingResource() throws Exception {
        return deleteAsXml(modify(create()));
    }

    /* Request body parsing */

    public String asString(final MediaType contentType, final Object obj){
        log.info("Parsing request body to String for contentType ["+contentType+"]");

        return contentType.equals(MediaType.APPLICATION_JSON) ? asJsonString(obj) : asXmlString(obj);
    }

    public String asJsonString(final Object obj) {
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

    protected User createDefaultUser(){
        return createTestUser("Bob", "Doe", USER, "MyPassword123", "USER");
    }

    protected User createDefaultAdmin(){
        return createTestUser("John", "Doe", ADMIN, "MyPassword123", "ADMIN");
    }

    protected User createDefaultContentCreator(){
        return createTestUser("Kanye", "West", CONTENT_CREATOR, "MyPassword123", "CONTENT_CREATOR");
    }

    protected User createUniqueContentCreator(){
        return createTestUser("Kanye", "West", unique(CONTENT_CREATOR), "MyPassword123", "CONTENT_CREATOR");
    }

    protected User createDefaultModerator(){
        return createTestUser("Kanye", "West", MODERATOR, "MyPassword123", "MODERATOR");
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
        log.debug("Saving testUser ["+testUser.getUsername()+"] to repository.");

        userRepository.save(testUser);

        return userRepository.getUserByUsername(testUser.getUsername());
    }

    protected ResultActions deleteAsJson(final R resource) throws Exception {
        return executeDelete(resource, DEFAULT_JSON_ACCEPT);
    }

    protected ResultActions deleteAsXml(final R resource) throws Exception {
        return executeDelete(resource, DEFAULT_XML_ACCEPT);
    }

    protected ResultActions executeDelete(final R resource, final MediaType accept) throws Exception {
        return deleteResource(getUrlForDelete(resource), accept);
    }

    protected ResultActions getAsJson() throws Exception {
        return executeGet(DEFAULT_JSON_ACCEPT);
    }

    protected ResultActions getAsXml() throws Exception {
        return executeGet(DEFAULT_XML_ACCEPT);
    }

    protected ResultActions executeGet(final MediaType accept) throws Exception {
        return getResource(getUrlForGet(), accept);
    }

    protected ResultActions getAsJson(final R resource) throws Exception {
        return executeGet(resource, DEFAULT_JSON_ACCEPT);
    }

    protected ResultActions getAsXml(final R resource) throws Exception {
        return executeGet(resource, DEFAULT_XML_ACCEPT);
    }

    protected ResultActions executeGet(final R resource, final MediaType accept) throws Exception {
        return getResource(getUrlForGet(resource), accept);
    }

    protected ResultActions postAsJson(R resource) throws Exception {
        return executePost(resource, DEFAULT_JSON_CONTENT_TYPE, DEFAULT_JSON_ACCEPT);
    }

    protected ResultActions postAsXml(R resource) throws Exception {
        return executePost(resource, DEFAULT_XML_CONTENT_TYPE, DEFAULT_XML_ACCEPT);
    }

    protected ResultActions executePost(final R resource, final MediaType contentType, final MediaType accept) throws Exception {
        return postResource(getUrlForPost(), resource, accept, contentType);
    }

    protected ResultActions updateAsJson(final R resource) throws Exception {
        return executePut(resource, DEFAULT_JSON_CONTENT_TYPE, DEFAULT_JSON_ACCEPT);
    }

    protected ResultActions updateAsXml(final R resource) throws Exception {
        return executePut(resource, DEFAULT_XML_CONTENT_TYPE, DEFAULT_XML_ACCEPT);
    }

    protected ResultActions executePut(final R resource, final MediaType contentType, final MediaType accept) throws Exception {
        return putResource(getUrlForPut(resource), resource, accept, contentType);
    }

    /* Execute HTTP calls */

    protected ResultActions putResource(final String url, final R resource, final MediaType accept, final MediaType contentType) throws Exception {
        return mvc.perform(put(url)
                .content(asString(contentType, resource))
                .contentType(contentType)
                .accept(accept));
    }

    protected ResultActions postResource(final String url, R resource, final MediaType accept, final MediaType contentType) throws Exception {
        return mvc.perform(post(url)
                .content(asString(contentType, resource))
                .contentType(contentType)
                .accept(accept));
    }

    protected ResultActions getResource(final String url, final MediaType accept) throws Exception {
        return mvc.perform(get(url)
                .accept(accept));
    }

    protected ResultActions deleteResource(final String url, final MediaType accept) throws Exception {
        return mvc.perform(delete(url)
                .accept(accept));
    }

    /* Expects on response */

    public ResultActions expectXmlResponse(final ResultActions mvc) throws Exception {
        return expectContentTypeResponse(mvc, DEFAULT_XML_ACCEPT_VALUE);
    }

    public ResultActions expectXmlUtf8Response(final ResultActions mvc) throws Exception {
        return expectContentTypeResponse(mvc, DEFAULT_XML_ACCEPT_UTF8_VALUE);
    }

    public ResultActions expectJsonResponse(final ResultActions mvc) throws Exception {
        return expectContentTypeResponse(mvc, DEFAULT_JSON_ACCEPT_VALUE);
    }

    protected ResultActions expectContentTypeResponse(final ResultActions mvc, final String contentType) throws Exception {
        return mvc.andExpect(header().string(HttpHeaders.CONTENT_TYPE, contentType));
    }

    protected void validateXmlUtf8Response(final ResultActions mvc, final HttpStatus expectedStatus) throws Exception {
        validateContentTypeResponse(mvc, expectedStatus, DEFAULT_XML_ACCEPT_UTF8_VALUE);
    }

    protected void validateJsonResponse(final ResultActions mvc, final HttpStatus expectedStatus) throws Exception {
        validateContentTypeResponse(mvc, expectedStatus, DEFAULT_JSON_ACCEPT_VALUE);
    }

    protected void validateXmlResponse(final ResultActions mvc, final HttpStatus expectedStatus) throws Exception {
        validateContentTypeResponse(mvc, expectedStatus, DEFAULT_XML_ACCEPT_VALUE);
    }

    protected void validateContentTypeResponse(final ResultActions mvc, final HttpStatus expectedStatus, final String contentType) throws Exception {
        new ResponseValidator(mvc, expectedStatus, contentType).validate();
    }

    protected String unique(String text){
        return text + new Date().getTime();
    }

    protected Blogpost createDefaultBlogpost(User author){
        Blogpost blogpost = new Blogpost();

        blogpost.setTitle("Example blogpost");
        blogpost.setContent("Lorem ipsum");
        blogpost.setAuthor(author);

        return blogpost;
    }

    protected Blogpost saveBlogpost(Blogpost post){
        blogpostRepository.save(post);

        return post;
    }

}
