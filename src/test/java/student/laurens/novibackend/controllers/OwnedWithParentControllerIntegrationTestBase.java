package student.laurens.novibackend.controllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import student.laurens.novibackend.entities.AbstractOwnedEntity;
import student.laurens.novibackend.entities.User;
import student.laurens.novibackend.repositories.ResourceRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public abstract class OwnedWithParentControllerIntegrationTestBase<R extends AbstractOwnedEntity, P extends AbstractOwnedEntity>
        extends TestBase<R> {


    abstract protected ResourceRepository<R> getRepository();
    abstract protected ResourceRepository<P> getParentRepository();

    @After
    public void base_after(){
        log.debug("Deleting all users from repository.");
        getRepository().deleteAll();
        getParentRepository().deleteAll();

        userRepository.deleteAll();
    }

    @Before
    public void base_before(){
        log.debug("Deleting all users from repository.");
        getRepository().deleteAll();
        getParentRepository().deleteAll();

        userRepository.deleteAll();
    }

    @Override
    protected String getUrlForGet () {
        return getUrlForGetWithParent(createParent());
    }
    abstract protected String getUrlForGetWithParent(P parentResource);

    @Override
    protected String getUrlForGet(R resource){
        return getUrlForGetWithParent(createParent(), resource);
    }
    abstract protected String getUrlForGetWithParent(P parentResource, R resource);

    @Override
    protected String getUrlForPost() {
        return getUrlForPostWithParent(createParent());
    }
    abstract protected String getUrlForPostWithParent(P parentResource);

    @Override
    protected String getUrlForPut(R resource){
        return getUrlForPutWithParent(createParent(), resource);
    }
    abstract protected String getUrlForPutWithParent(P parentResource, R resource);

    @Override
    protected String getUrlForDelete(R resource){
        return getUrlForDeleteWithParent(createParent(), resource);
    }
    abstract protected String getUrlForDeleteWithParent(P parentResource, R resource);


    /**
     * Implement this method by a resource specific implementation to create sample owned instances of the resource.
     *-
     * @return Sample instance of resource.
     */
    abstract protected R createOwned(P parentResource, User owner);
    protected R createNotOwned(P parentResource){
        return createOwned(parentResource, saveUser(createUniqueContentCreator()) );
    }
    @Override
    protected R create(){
        return createNotOwned(createNotOwnedParent());
    }

    protected P saveParent(final P parentResource){
        getParentRepository().save(parentResource);
        return parentResource;
    }

    /**
     * Implement this method by a resource specific implementation to modify a sample instances of the parent resource.
     *
     * @return Sample instance of parent resource.
     */
    abstract protected P modifyParent(final P parentResource);

    /**
     * Implement this method by a resource specific implementation to create sample instances of the parent resource.
     *
     * @return Sample instance of parent resource.
     */
    abstract protected P createParent();

    /**
     * Implement this method by a resource specific implementation to create sample owned instances of the parent resource.
     *-
     * @return Sample instance of parent resource.
     */
    abstract protected P createOwnedParent(User owner);
    protected P createNotOwnedParent(){
        return createOwnedParent(saveUser(createUniqueContentCreator()));
    }


    abstract protected HttpStatus expectedStatusForGetAsUserParentNotExistsChildOwned();
    abstract protected HttpStatus expectedStatusForGetAsContentCreatorParentNotExistsChildOwned();
    abstract protected HttpStatus expectedStatusForGetAsModeratorParentNotExistsChildOwned();
    abstract protected HttpStatus expectedStatusForGetAsAdminParentNotExistsChildOwned();

    abstract protected HttpStatus expectedStatusForGetAsUserParentNotOwnedChildOwned();
    abstract protected HttpStatus expectedStatusForGetAsContentCreatorParentNotOwnedChildOwned();
    abstract protected HttpStatus expectedStatusForGetAsModeratorParentNotOwnedChildOwned();
    abstract protected HttpStatus expectedStatusForGetAsAdminParentNotOwnedChildOwned();


    abstract protected HttpStatus expectedStatusForPostAsUserParentNotExistsChildOwned();
    abstract protected HttpStatus expectedStatusForPostAsContentCreatorParentNotExistsChildOwned();
    abstract protected HttpStatus expectedStatusForPostAsModeratorParentNotExistsChildOwned();
    abstract protected HttpStatus expectedStatusForPostAsAdminParentNotExistsChildOwned();

    abstract protected HttpStatus expectedStatusForPostAsUserParentNotOwnedChildOwned();
    abstract protected HttpStatus expectedStatusForPostAsContentCreatorParentNotOwnedChildOwned();
    abstract protected HttpStatus expectedStatusForPostAsModeratorParentNotOwnedChildOwned();
    abstract protected HttpStatus expectedStatusForPostAsAdminParentNotOwnedChildOwned();


    abstract protected HttpStatus expectedStatusForPutAsUserParentNotExistsChildOwned();
    abstract protected HttpStatus expectedStatusForPutAsContentCreatorParentNotExistsChildOwned();
    abstract protected HttpStatus expectedStatusForPutAsModeratorParentNotExistsChildOwned();
    abstract protected HttpStatus expectedStatusForPutAsAdminParentNotExistsChildOwned();

    abstract protected HttpStatus expectedStatusForPutAsUserParentNotOwnedChildOwned();
    abstract protected HttpStatus expectedStatusForPutAsContentCreatorParentNotOwnedChildOwned();
    abstract protected HttpStatus expectedStatusForPutAsModeratorParentNotOwnedChildOwned();
    abstract protected HttpStatus expectedStatusForPutAsAdminParentNotOwnedChildOwned();

    abstract protected HttpStatus expectedStatusForPutAsUserParentNotOwnedChildNotOwned();
    abstract protected HttpStatus expectedStatusForPutAsContentCreatorParentNotOwnedChildNotOwned();
    abstract protected HttpStatus expectedStatusForPutAsModeratorParentNotOwnedChildNotOwned();
    abstract protected HttpStatus expectedStatusForPutAsAdminParentNotOwnedChildNotOwned();

    abstract protected HttpStatus expectedStatusForPutAsUserParentOwnedChildNotOwned();
    abstract protected HttpStatus expectedStatusForPutAsContentCreatorParentOwnedChildNotOwned();
    abstract protected HttpStatus expectedStatusForPutAsModeratorParentOwnedChildNotOwned();
    abstract protected HttpStatus expectedStatusForPutAsAdminParentOwnedChildNotOwned();

    abstract protected HttpStatus expectedStatusForPutAsUserParentOwnedChildOwned();
    abstract protected HttpStatus expectedStatusForPutAsContentCreatorParentOwnedChildOwned();
    abstract protected HttpStatus expectedStatusForPutAsModeratorParentOwnedChildOwned();
    abstract protected HttpStatus expectedStatusForPutAsAdminParentOwnedChildOwned();


    abstract protected HttpStatus expectedStatusForDeleteAsUserParentNotExistsChildOwned();
    abstract protected HttpStatus expectedStatusForDeleteAsContentCreatorParentNotExistsChildOwned();
    abstract protected HttpStatus expectedStatusForDeleteAsModeratorParentNotExistsChildOwned();
    abstract protected HttpStatus expectedStatusForDeleteAsAdminParentNotExistsChildOwned();

    abstract protected HttpStatus expectedStatusForDeleteAsUserParentNotOwnedChildOwned();
    abstract protected HttpStatus expectedStatusForDeleteAsContentCreatorParentNotOwnedChildOwned();
    abstract protected HttpStatus expectedStatusForDeleteAsModeratorParentNotOwnedChildOwned();
    abstract protected HttpStatus expectedStatusForDeleteAsAdminParentNotOwnedChildOwned();

    abstract protected HttpStatus expectedStatusForDeleteAsUserParentNotOwnedChildNotOwned();
    abstract protected HttpStatus expectedStatusForDeleteAsContentCreatorParentNotOwnedChildNotOwned();
    abstract protected HttpStatus expectedStatusForDeleteAsModeratorParentNotOwnedChildNotOwned();
    abstract protected HttpStatus expectedStatusForDeleteAsAdminParentNotOwnedChildNotOwned();

    abstract protected HttpStatus expectedStatusForDeleteAsUserParentOwnedChildNotOwned();
    abstract protected HttpStatus expectedStatusForDeleteAsContentCreatorParentOwnedChildNotOwned();
    abstract protected HttpStatus expectedStatusForDeleteAsModeratorParentOwnedChildNotOwned();
    abstract protected HttpStatus expectedStatusForDeleteAsAdminParentOwnedChildNotOwned();

    abstract protected HttpStatus expectedStatusForDeleteAsUserParentOwnedChildOwned();
    abstract protected HttpStatus expectedStatusForDeleteAsContentCreatorParentOwnedChildOwned();
    abstract protected HttpStatus expectedStatusForDeleteAsModeratorParentOwnedChildOwned();
    abstract protected HttpStatus expectedStatusForDeleteAsAdminParentOwnedChildOwned();

    protected ResultActions getAsJson(final P parentResource, final R resource) throws Exception {
        return executeGet(parentResource, resource, DEFAULT_JSON_ACCEPT);
    }
    protected ResultActions getAsXml(final P parentResource, final R resource) throws Exception {
        return executeGet(parentResource, resource, DEFAULT_XML_ACCEPT);
    }
    protected ResultActions executeGet(final P parentResource, final R resource, final MediaType accept) throws Exception {
        return getResource(getUrlForGetWithParent(parentResource, resource), accept);
    }
    public ResultActions defaultXmlTestForGet(boolean isOwned, boolean isParentOwned, User user) throws Exception {
        P parentResource = saveParent( isParentOwned ? createOwnedParent(user) : createNotOwnedParent() ) ;
        R resource = save( isOwned ? createOwned(parentResource, user) : createNotOwned(parentResource) );

        return getAsXml(parentResource, resource);
    }
    public ResultActions defaultJsonTestForGet(boolean isOwned, boolean isParentOwned, User user) throws Exception {
        P parentResource = saveParent( isParentOwned ? createOwnedParent(user) : createNotOwnedParent() ) ;
        R resource = save( isOwned ? createOwned(parentResource, user) : createNotOwned(parentResource) );

        return getAsJson(parentResource, resource);
    }
    public ResultActions defaultXmlTestForGetParentNotExists(boolean isOwned, boolean isParentOwned, User user) throws Exception {
        P parentResource = saveParent( isParentOwned ? createOwnedParent(user) : createNotOwnedParent() );
        R resource = save( isOwned ? createOwned(parentResource, user) : createNotOwned(parentResource) );

        return getAsXml(createNotOwnedParent(), resource);
    }
    public ResultActions defaultJsonTestForGetParentNotExists(boolean isOwned, boolean isParentOwned, User user) throws Exception {
        P parentResource = saveParent( isParentOwned ? createOwnedParent(user) : createNotOwnedParent() );
        R resource = save( isOwned ? createOwned(parentResource, user) : createNotOwned(parentResource) );

        return getAsJson(createNotOwnedParent(), resource);
    }

    protected ResultActions postAsJson(final P parentResource, final R resource) throws Exception {
        return executePost(parentResource, resource, DEFAULT_JSON_CONTENT_TYPE, DEFAULT_JSON_ACCEPT);
    }
    protected ResultActions postAsXml(final P parentResource, final R resource) throws Exception {
        return executePost(parentResource, resource, DEFAULT_XML_CONTENT_TYPE, DEFAULT_XML_ACCEPT);
    }
    protected ResultActions executePost(final P parentResource, final R resource, final MediaType contentType, final MediaType accept) throws Exception {
        return postResource(getUrlForPostWithParent(parentResource), resource, accept, contentType);
    }
    public ResultActions defaultXmlTestForPost(boolean isOwned, boolean isParentOwned, User user) throws Exception {
        P parentResource = saveParent( isParentOwned ? createOwnedParent(user) : createNotOwnedParent() ) ;
        R resource = save(isOwned ? createOwned(parentResource, user) : createNotOwned(parentResource));

        return postAsXml(parentResource, resource);
    }
    public ResultActions defaultJsonTestForPost(boolean isOwned, boolean isParentOwned, User user) throws Exception {
        P parentResource = saveParent( isParentOwned ? createOwnedParent(user) : createNotOwnedParent() ) ;
        R resource = isOwned ? createOwned(parentResource, user) : createNotOwned(parentResource);

        return postAsJson(parentResource, resource);
    }
    public ResultActions defaultXmlTestForPostParentNotExists(boolean isOwned, boolean isParentOwned, User user) throws Exception {
        P parentResource = saveParent( isParentOwned ? createOwnedParent(user) : createNotOwnedParent() ) ;
        R resource = save(isOwned ? createOwned(parentResource, user) : createNotOwned(parentResource));

        return postAsXml(createNotOwnedParent(), resource);
    }
    public ResultActions defaultJsonTestForPostParentNotExists(boolean isOwned, boolean isParentOwned, User user) throws Exception {
        P parentResource = saveParent( isParentOwned ? createOwnedParent(user) : createNotOwnedParent() ) ;
        R resource = isOwned ? createOwned(parentResource, user) : createNotOwned(parentResource);

        return postAsJson(createNotOwnedParent(), resource);
    }

    protected ResultActions putAsJson(final P parentResource, final R resource) throws Exception {
        return executePut(parentResource, resource, DEFAULT_JSON_CONTENT_TYPE, DEFAULT_JSON_ACCEPT);
    }
    protected ResultActions putAsXml(final P parentResource, final R resource) throws Exception {
        return executePut(parentResource, resource, DEFAULT_XML_CONTENT_TYPE, DEFAULT_XML_ACCEPT);
    }
    protected ResultActions executePut(final P parentResource, final R resource, final MediaType contentType, final MediaType accept) throws Exception {
        return putResource(getUrlForPutWithParent(parentResource, resource), resource, accept, contentType);
    }
    public ResultActions defaultXmlTestForPut(boolean isOwned, boolean isParentOwned, User user) throws Exception {
        P parentResource = saveParent( isParentOwned ? createOwnedParent(user) : createNotOwnedParent() ) ;
        R resource = modify(save( isOwned ? createOwned(parentResource, user) : createNotOwned(parentResource) ));

        return putAsXml(parentResource, resource);
    }
    public ResultActions defaultJsonTestForPut(boolean isOwned, boolean isParentOwned, User user) throws Exception {
        P parentResource = saveParent( isParentOwned ? createOwnedParent(user) : createNotOwnedParent() ) ;
        R resource = modify(save( isOwned ? createOwned(parentResource, user) : createNotOwned(parentResource) ));

        return putAsJson(parentResource, resource);
    }
    public ResultActions defaultXmlTestForPutParentNotExists(boolean isOwned, boolean isParentOwned, User user) throws Exception {
        P parentResource = saveParent( isParentOwned ? createOwnedParent(user) : createNotOwnedParent() ) ;
        R resource = modify(save( isOwned ? createOwned(parentResource, user) : createNotOwned(parentResource) ));

        return putAsXml(createNotOwnedParent(), resource);
    }
    public ResultActions defaultJsonTestForPutParentNotExists(boolean isOwned, boolean isParentOwned, User user) throws Exception {
        P parentResource = saveParent( isParentOwned ? createOwnedParent(user) : createNotOwnedParent() ) ;
        R resource = modify(save( isOwned ? createOwned(parentResource, user) : createNotOwned(parentResource) ));

        return putAsJson(createNotOwnedParent(), resource);
    }

    protected ResultActions deleteAsJson(final P parentResource, final R resource) throws Exception {
        return executeDelete(parentResource, resource, DEFAULT_JSON_CONTENT_TYPE, DEFAULT_JSON_ACCEPT);
    }
    protected ResultActions deleteAsXml(final P parentResource, final R resource) throws Exception {
        return executeDelete(parentResource, resource, DEFAULT_XML_CONTENT_TYPE, DEFAULT_XML_ACCEPT);
    }
    protected ResultActions executeDelete(final P parentResource, final R resource, final MediaType contentType, final MediaType accept) throws Exception {
        return deleteResource(getUrlForDeleteWithParent(parentResource, resource), accept);
    }
    public ResultActions defaultXmlTestForDelete(boolean isOwned, boolean isParentOwned, User user) throws Exception {
        P parentResource = saveParent( isParentOwned ? createOwnedParent(user) : createNotOwnedParent() ) ;
        R resource = save( isOwned ? createOwned(parentResource, user) : createNotOwned(parentResource) );

        return deleteAsXml(parentResource, resource);
    }
    public ResultActions defaultJsonTestForDelete(boolean isOwned, boolean isParentOwned, User user) throws Exception {
        P parentResource = saveParent( isParentOwned ? createOwnedParent(user) : createNotOwnedParent() ) ;
        R resource = save( isOwned ? createOwned(parentResource, user) : createNotOwned(parentResource) );

        return deleteAsJson(parentResource, resource);
    }
    public ResultActions defaultXmlTestForDeleteParentNotExists(boolean isOwned, boolean isParentOwned, User user) throws Exception {
        P parentResource = saveParent( isParentOwned ? createOwnedParent(user) : createNotOwnedParent() ) ;
        R resource = save( isOwned ? createOwned(parentResource, user) : createNotOwned(parentResource) );

        return deleteAsXml(createNotOwnedParent(), resource);
    }
    public ResultActions defaultJsonTestForDeleteParentNotExists(boolean isOwned, boolean isParentOwned, User user) throws Exception {
        P parentResource = saveParent( isParentOwned ? createOwnedParent(user) : createNotOwnedParent() ) ;
        R resource = save( isOwned ? createOwned(parentResource, user) : createNotOwned(parentResource) );

        return deleteAsJson(createNotOwnedParent(), resource);
    }

    @Test
    @WithMockUser(value = USER)
    public void getJsonAsUserParentNotExistsChildOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForGetAsUserParentNotExistsChildOwned();
        ResultActions mvc = defaultJsonTestForGetParentNotExists(true, false, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE})
    public void getJsonAsContentCreatorParentNotExistsChildOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForGetAsContentCreatorParentNotExistsChildOwned();
        ResultActions mvc = defaultJsonTestForGetParentNotExists(true, false, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE})
    public void getJsonAsModeratorParentNotExistsChildOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForGetAsModeratorParentNotExistsChildOwned();
        ResultActions mvc = defaultJsonTestForGetParentNotExists(true, false, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE})
    public void getJsonAsAdminParentNotExistsChildOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForGetAsAdminParentNotExistsChildOwned();
        ResultActions mvc = defaultJsonTestForGetParentNotExists(true, false, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = USER)
    public void getJsonAsUserParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForGetAsUserParentNotOwnedChildOwned();
        ResultActions mvc = defaultJsonTestForGet(true, false, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE})
    public void getJsonAsContentCreatorParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForGetAsContentCreatorParentNotOwnedChildOwned();
        ResultActions mvc = defaultJsonTestForGet(true, false, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE})
    public void getJsonAsModeratorParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForGetAsModeratorParentNotOwnedChildOwned();
        ResultActions mvc = defaultJsonTestForGet(true, false, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE})
    public void getJsonAsAdminParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForGetAsAdminParentNotOwnedChildOwned();
        ResultActions mvc = defaultJsonTestForGet(true, false, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = USER)
    public void postJsonAsUserParentNotExistsChildOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForPostAsUserParentNotExistsChildOwned();
        ResultActions mvc = defaultJsonTestForPostParentNotExists(true, false, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE})
    public void postJsonAsContentCreatorParentNotExistsChildOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForPostAsContentCreatorParentNotExistsChildOwned();
        ResultActions mvc = defaultJsonTestForPostParentNotExists(true, false, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE})
    public void postJsonAsModeratorParentNotExistsChildOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForPostAsModeratorParentNotExistsChildOwned();
        ResultActions mvc = defaultJsonTestForPostParentNotExists(true, false, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE})
    public void postJsonAsAdminParentNotExistsChildOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForPostAsAdminParentNotExistsChildOwned();
        ResultActions mvc = defaultJsonTestForPostParentNotExists(true, false, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = USER)
    public void postJsonAsUserParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForPostAsUserParentNotOwnedChildOwned();
        ResultActions mvc = defaultJsonTestForPost(true, false, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE})
    public void postJsonAsContentCreatorParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForPostAsContentCreatorParentNotOwnedChildOwned();
        ResultActions mvc = defaultJsonTestForPost(true, false, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE})
    public void postJsonAsModeratorParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForPostAsModeratorParentNotOwnedChildOwned();
        ResultActions mvc = defaultJsonTestForPost(true, false, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE})
    public void postJsonAsAdminParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForPostAsAdminParentNotOwnedChildOwned();
        ResultActions mvc = defaultJsonTestForPost(true, false, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = USER)
    public void putJsonAsUserParentNotExistsChildOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForPutAsUserParentNotExistsChildOwned();
        ResultActions mvc = defaultJsonTestForPutParentNotExists(true, false, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE})
    public void putJsonAsContentCreatorParentNotExistsChildOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForPutAsContentCreatorParentNotExistsChildOwned();
        ResultActions mvc = defaultJsonTestForPutParentNotExists(true, false, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE})
    public void putJsonAsModeratorParentNotExistsChildOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForPutAsModeratorParentNotExistsChildOwned();
        ResultActions mvc = defaultJsonTestForPutParentNotExists(true, false, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE})
    public void putJsonAsAdminParentNotExistsChildOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForPutAsAdminParentNotExistsChildOwned();
        ResultActions mvc = defaultJsonTestForPutParentNotExists(true, false, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = USER)
    public void putJsonAsUserParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForPutAsUserParentNotOwnedChildOwned();
        ResultActions mvc = defaultJsonTestForPut(true, false, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE})
    public void putJsonAsContentCreatorParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForPutAsContentCreatorParentNotOwnedChildOwned();
        ResultActions mvc = defaultJsonTestForPut(true, false, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE})
    public void putJsonAsModeratorParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForPutAsModeratorParentNotOwnedChildOwned();
        ResultActions mvc = defaultJsonTestForPut(true, false, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE})
    public void putJsonAsAdminParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForPutAsAdminParentNotOwnedChildOwned();
        ResultActions mvc = defaultJsonTestForPut(true, false, user);

        validateJsonResponse(mvc, expectedStatus);
    }
    @Test
    @WithMockUser(value = USER)
    public void putJsonAsUserParentNotOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForPutAsUserParentNotOwnedChildNotOwned();
        ResultActions mvc = defaultJsonTestForPut(false, false, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE})
    public void putJsonAsContentCreatorParentNotOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForPutAsContentCreatorParentNotOwnedChildNotOwned();
        ResultActions mvc = defaultJsonTestForPut(false, false, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE})
    public void putJsonAsModeratorParentNotOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForPutAsModeratorParentNotOwnedChildNotOwned();
        ResultActions mvc = defaultJsonTestForPut(false, false, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE})
    public void putJsonAsAdminParentNotOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForPutAsAdminParentNotOwnedChildNotOwned();
        ResultActions mvc = defaultJsonTestForPut(false, false, user);

        validateJsonResponse(mvc, expectedStatus);
    }


    @Test
    @WithMockUser(value = USER)
    public void putJsonAsUserParentOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForPutAsUserParentOwnedChildNotOwned();
        ResultActions mvc = defaultJsonTestForPut(false, true, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE})
    public void putJsonAsContentCreatorParentOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForPutAsContentCreatorParentOwnedChildNotOwned();
        ResultActions mvc = defaultJsonTestForPut(false, true, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE})
    public void putJsonAsModeratorParentOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForPutAsModeratorParentOwnedChildNotOwned();
        ResultActions mvc = defaultJsonTestForPut(false, true, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE})
    public void putJsonAsAdminParentOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForPutAsAdminParentOwnedChildNotOwned();
        ResultActions mvc = defaultJsonTestForPut(false, true, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = USER)
    public void putJsonAsUserParentOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForPutAsUserParentOwnedChildOwned();
        ResultActions mvc = defaultJsonTestForPut(true, true, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE})
    public void putJsonAsContentCreatorParentOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForPutAsContentCreatorParentOwnedChildOwned();
        ResultActions mvc = defaultJsonTestForPut(true, true, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE})
    public void putJsonAsModeratorParentOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForPutAsModeratorParentOwnedChildOwned();
        ResultActions mvc = defaultJsonTestForPut(true, true, user).andDo(print());

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE})
    public void putJsonAsAdminParentOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForPutAsAdminParentOwnedChildOwned();
        ResultActions mvc = defaultJsonTestForPut(true, true, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = USER)
    public void deleteJsonAsUserParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForDeleteAsUserParentNotOwnedChildOwned();
        ResultActions mvc = defaultJsonTestForDelete(true, false, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE})
    public void deleteJsonAsContentCreatorParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsContentCreatorParentNotOwnedChildOwned();
        ResultActions mvc = defaultJsonTestForDelete(true, false, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE})
    public void deleteJsonAsModeratorParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsModeratorParentNotOwnedChildOwned();
        ResultActions mvc = defaultJsonTestForDelete(true, false, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE})
    public void deleteJsonAsAdminParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForDeleteAsAdminParentNotOwnedChildOwned();
        ResultActions mvc = defaultJsonTestForDelete(true, false, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = USER)
    public void deleteJsonAsUserParentNotOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForDeleteAsUserParentNotOwnedChildNotOwned();
        ResultActions mvc = defaultJsonTestForDelete(false, false, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE})
    public void deleteJsonAsContentCreatorParentNotOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsContentCreatorParentNotOwnedChildNotOwned();
        ResultActions mvc = defaultJsonTestForDelete(false, false, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE})
    public void deleteJsonAsModeratorParentNotOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsModeratorParentNotOwnedChildNotOwned();
        ResultActions mvc = defaultJsonTestForDelete(false, false, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE})
    public void deleteJsonAsAdminParentNotOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForDeleteAsAdminParentNotOwnedChildNotOwned();
        ResultActions mvc = defaultJsonTestForDelete(false, false, user);

        validateJsonResponse(mvc, expectedStatus);
    }


    @Test
    @WithMockUser(value = USER)
    public void deleteJsonAsUserParentOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForDeleteAsUserParentOwnedChildNotOwned();
        ResultActions mvc = defaultJsonTestForDelete(false, true, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE})
    public void deleteJsonAsContentCreatorParentOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsContentCreatorParentOwnedChildNotOwned();
        ResultActions mvc = defaultJsonTestForDelete(false, true, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE})
    public void deleteJsonAsModeratorParentOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsModeratorParentOwnedChildNotOwned();
        ResultActions mvc = defaultJsonTestForDelete(false, true, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE})
    public void deleteJsonAsAdminParentOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForDeleteAsAdminParentOwnedChildNotOwned();
        ResultActions mvc = defaultJsonTestForDelete(false, true, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = USER)
    public void deleteJsonAsUserParentOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForDeleteAsUserParentOwnedChildOwned();
        ResultActions mvc = defaultJsonTestForDelete(true, true, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE})
    public void deleteJsonAsContentCreatorParentOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsContentCreatorParentOwnedChildOwned();
        ResultActions mvc = defaultJsonTestForDelete(true, true, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE})
    public void deleteJsonAsModeratorParentOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsModeratorParentOwnedChildOwned();
        ResultActions mvc = defaultJsonTestForDelete(true, true, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE})
    public void deleteJsonAsAdminParentOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForDeleteAsAdminParentOwnedChildOwned();
        ResultActions mvc = defaultJsonTestForDelete(true, true, user);

        validateJsonResponse(mvc, expectedStatus);
    }


    @Test
    @WithMockUser(value = USER)
    public void getXmlAsUserParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForGetAsUserParentNotOwnedChildOwned();
        ResultActions mvc = defaultXmlTestForGet(true, false, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE})
    public void getXmlAsContentCreatorParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForGetAsContentCreatorParentNotOwnedChildOwned();
        ResultActions mvc = defaultXmlTestForGet(true, false, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE})
    public void getXmlAsModeratorParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForGetAsModeratorParentNotOwnedChildOwned();
        ResultActions mvc = defaultXmlTestForGet(true, false, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE})
    public void getXmlAsAdminParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForGetAsAdminParentNotOwnedChildOwned();
        ResultActions mvc = defaultXmlTestForGet(true, false, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = USER)
    public void postXmlAsUserParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForPostAsUserParentNotOwnedChildOwned();
        ResultActions mvc = defaultXmlTestForPost(true, false, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE})
    public void postXmlAsContentCreatorParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForPostAsContentCreatorParentNotOwnedChildOwned();
        ResultActions mvc = defaultXmlTestForPost(true, false, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE})
    public void postXmlAsModeratorParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForPostAsModeratorParentNotOwnedChildOwned();
        ResultActions mvc = defaultXmlTestForPost(true, false, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE})
    public void postXmlAsAdminParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForPostAsAdminParentNotOwnedChildOwned();
        ResultActions mvc = defaultXmlTestForPost(true, false, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = USER)
    public void putXmlAsUserParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForPutAsUserParentNotOwnedChildOwned();
        ResultActions mvc = defaultXmlTestForPut(true, false, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE})
    public void putXmlAsContentCreatorParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForPutAsContentCreatorParentNotOwnedChildOwned();
        ResultActions mvc = defaultXmlTestForPut(true, false, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE})
    public void putXmlAsModeratorParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForPutAsModeratorParentNotOwnedChildOwned();
        ResultActions mvc = defaultXmlTestForPut(true, false, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE})
    public void putXmlAsAdminParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForPutAsAdminParentNotOwnedChildOwned();
        ResultActions mvc = defaultXmlTestForPut(true, false, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }
    @Test
    @WithMockUser(value = USER)
    public void putXmlAsUserParentNotOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForPutAsUserParentNotOwnedChildNotOwned();
        ResultActions mvc = defaultXmlTestForPut(false, false, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE})
    public void putXmlAsContentCreatorParentNotOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForPutAsContentCreatorParentNotOwnedChildNotOwned();
        ResultActions mvc = defaultXmlTestForPut(false, false, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE})
    public void putXmlAsModeratorParentNotOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForPutAsModeratorParentNotOwnedChildNotOwned();
        ResultActions mvc = defaultXmlTestForPut(false, false, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE})
    public void putXmlAsAdminParentNotOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForPutAsAdminParentNotOwnedChildNotOwned();
        ResultActions mvc = defaultXmlTestForPut(false, false, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }


    @Test
    @WithMockUser(value = USER)
    public void putXmlAsUserParentOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForPutAsUserParentOwnedChildNotOwned();
        ResultActions mvc = defaultXmlTestForPut(false, true, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE})
    public void putXmlAsContentCreatorParentOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForPutAsContentCreatorParentOwnedChildNotOwned();
        ResultActions mvc = defaultXmlTestForPut(false, true, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE})
    public void putXmlAsModeratorParentOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForPutAsModeratorParentOwnedChildNotOwned();
        ResultActions mvc = defaultXmlTestForPut(false, true, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE})
    public void putXmlAsAdminParentOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForPutAsAdminParentOwnedChildNotOwned();
        ResultActions mvc = defaultXmlTestForPut(false, true, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = USER)
    public void putXmlAsUserParentOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForPutAsUserParentOwnedChildOwned();
        ResultActions mvc = defaultXmlTestForPut(true, true, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE})
    public void putXmlAsContentCreatorParentOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForPutAsContentCreatorParentOwnedChildOwned();
        ResultActions mvc = defaultXmlTestForPut(true, true, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE})
    public void putXmlAsModeratorParentOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForPutAsModeratorParentOwnedChildOwned();
        ResultActions mvc = defaultXmlTestForPut(true, true, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE})
    public void putXmlAsAdminParentOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForPutAsAdminParentOwnedChildOwned();
        ResultActions mvc = defaultXmlTestForPut(true, true, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = USER)
    public void deleteJsonAsUserParentNotExistsChildOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForDeleteAsUserParentNotExistsChildOwned();
        ResultActions mvc = defaultJsonTestForDeleteParentNotExists(true, false, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE})
    public void deleteJsonAsContentCreatorParentNotExistsChildOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsContentCreatorParentNotExistsChildOwned();
        ResultActions mvc = defaultJsonTestForDeleteParentNotExists(true, false, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE})
    public void deleteJsonAsModeratorParentNotExistsChildOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsModeratorParentNotExistsChildOwned();
        ResultActions mvc = defaultJsonTestForDeleteParentNotExists(true, false, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE})
    public void deleteJsonAsAdminParentNotExistsChildOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForDeleteAsAdminParentNotExistsChildOwned();
        ResultActions mvc = defaultJsonTestForDeleteParentNotExists(true, false, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = USER)
    public void deleteXmlAsUserParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForDeleteAsUserParentNotOwnedChildOwned();
        ResultActions mvc = defaultXmlTestForDelete(true, false, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE})
    public void deleteXmlAsContentCreatorParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsContentCreatorParentNotOwnedChildOwned();
        ResultActions mvc = defaultXmlTestForDelete(true, false, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE})
    public void deleteXmlAsModeratorParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsModeratorParentNotOwnedChildOwned();
        ResultActions mvc = defaultXmlTestForDelete(true, false, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE})
    public void deleteXmlAsAdminParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForDeleteAsAdminParentNotOwnedChildOwned();
        ResultActions mvc = defaultXmlTestForDelete(true, false, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = USER)
    public void deleteXmlAsUserParentNotOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForDeleteAsUserParentNotOwnedChildNotOwned();
        ResultActions mvc = defaultXmlTestForDelete(false, false, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE})
    public void deleteXmlAsContentCreatorParentNotOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsContentCreatorParentNotOwnedChildNotOwned();
        ResultActions mvc = defaultXmlTestForDelete(false, false, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE})
    public void deleteXmlAsModeratorParentNotOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsModeratorParentNotOwnedChildNotOwned();
        ResultActions mvc = defaultXmlTestForDelete(false, false, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE})
    public void deleteXmlAsAdminParentNotOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForDeleteAsAdminParentNotOwnedChildNotOwned();
        ResultActions mvc = defaultXmlTestForDelete(false, false, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }


    @Test
    @WithMockUser(value = USER)
    public void deleteXmlAsUserParentOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForDeleteAsUserParentOwnedChildNotOwned();
        ResultActions mvc = defaultXmlTestForDelete(false, true, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE})
    public void deleteXmlAsContentCreatorParentOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsContentCreatorParentOwnedChildNotOwned();
        ResultActions mvc = defaultXmlTestForDelete(false, true, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE})
    public void deleteXmlAsModeratorParentOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsModeratorParentOwnedChildNotOwned();
        ResultActions mvc = defaultXmlTestForDelete(false, true, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE})
    public void deleteXmlAsAdminParentOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForDeleteAsAdminParentOwnedChildNotOwned();
        ResultActions mvc = defaultXmlTestForDelete(false, true, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = USER)
    public void deleteXmlAsUserParentOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForDeleteAsUserParentOwnedChildOwned();
        ResultActions mvc = defaultXmlTestForDelete(true, true, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE})
    public void deleteXmlAsContentCreatorParentOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsContentCreatorParentOwnedChildOwned();
        ResultActions mvc = defaultXmlTestForDelete(true, true, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE})
    public void deleteXmlAsModeratorParentOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsModeratorParentOwnedChildOwned();
        ResultActions mvc = defaultXmlTestForDelete(true, true, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE})
    public void deleteXmlAsAdminParentOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForDeleteAsAdminParentOwnedChildOwned();
        ResultActions mvc = defaultXmlTestForDelete(true, true, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }
}
