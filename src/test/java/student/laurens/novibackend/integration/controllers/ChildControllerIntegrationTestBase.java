package student.laurens.novibackend.integration.controllers;

import org.junit.After;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import student.laurens.novibackend.entities.AbstractOwnedEntity;
import student.laurens.novibackend.entities.User;
import student.laurens.novibackend.entities.dto.ResourceDto;
import student.laurens.novibackend.repositories.ResourceRepository;

/**
 * Base class to provide default methods for testing RestControllers which expose HTTP method for resources which have a parent resource.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
public abstract class ChildControllerIntegrationTestBase<R extends AbstractOwnedEntity, P extends AbstractOwnedEntity, D extends ResourceDto>
        extends IntegrationTestBase<R,D> {


    abstract protected ResourceRepository<R> getRepository();
    abstract protected ResourceRepository<P> getParentRepository();

    @After
    public void base_after(){
        log.debug("Deleting all users from repository.");
        getRepository().deleteAll();
        getParentRepository().deleteAll();
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

    abstract protected HttpStatus expectedStatusForGetAsUser();
    abstract protected HttpStatus expectedStatusForGetAsContentCreator();
    abstract protected HttpStatus expectedStatusForGetAsModerator();
    abstract protected HttpStatus expectedStatusForGetAsAdmin();

    abstract protected HttpStatus expectedStatusForGetAsUserParentNotExistsChildOwned();
    abstract protected HttpStatus expectedStatusForGetAsContentCreatorParentNotExistsChildOwned();
    abstract protected HttpStatus expectedStatusForGetAsModeratorParentNotExistsChildOwned();
    abstract protected HttpStatus expectedStatusForGetAsAdminParentNotExistsChildOwned();

    abstract protected HttpStatus expectedStatusForGetAsUserParentNotOwnedChildNotExists();
    abstract protected HttpStatus expectedStatusForGetAsContentCreatorParentNotOwnedChildNotExists();
    abstract protected HttpStatus expectedStatusForGetAsModeratorParentNotOwnedChildNotExists();
    abstract protected HttpStatus expectedStatusForGetAsAdminParentNotOwnedChildNotExists();

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

    abstract protected HttpStatus expectedStatusForPutAsUserParentNotOwnedChildNotExists();
    abstract protected HttpStatus expectedStatusForPutAsContentCreatorParentNotOwnedChildNotExists();
    abstract protected HttpStatus expectedStatusForPutAsModeratorParentNotOwnedChildNotExists();
    abstract protected HttpStatus expectedStatusForPutAsAdminParentNotOwnedChildNotExists();

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

    abstract protected HttpStatus expectedStatusForDeleteAsUserParentNotOwnedChildNotExists();
    abstract protected HttpStatus expectedStatusForDeleteAsContentCreatorParentNotOwnedChildNotExists();
    abstract protected HttpStatus expectedStatusForDeleteAsModeratorParentNotOwnedChildNotExists();
    abstract protected HttpStatus expectedStatusForDeleteAsAdminParentNotOwnedChildNotExists();

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

    protected ResultActions getAsJson(final P parentResource) throws Exception {
        return executeGet(parentResource, DEFAULT_JSON_ACCEPT);
    }
    protected ResultActions getAsXml(final P parentResource) throws Exception {
        return executeGet(parentResource, DEFAULT_XML_ACCEPT);
    }
    protected ResultActions getAsJson(final P parentResource, final R resource) throws Exception {
        return executeGet(parentResource, resource, DEFAULT_JSON_ACCEPT);
    }
    protected ResultActions getAsXml(final P parentResource, final R resource) throws Exception {
        return executeGet(parentResource, resource, DEFAULT_XML_ACCEPT);
    }
    protected ResultActions executeGet(final P parentResource, final MediaType accept) throws Exception {
        return getResource(getUrlForGetWithParent(parentResource), accept);
    }
    protected ResultActions executeGet(final P parentResource, final R resource, final MediaType accept) throws Exception {
        return getResource(getUrlForGetWithParent(parentResource, resource), accept);
    }
    public ChildTestResults<R,P> defaultXmlTestForGetAll(boolean isOwned, boolean isParentOwned, User user) throws Exception {
        P parentResource = saveParent( isParentOwned ? createOwnedParent(user) : createNotOwnedParent() ) ;
        R resource = save( isOwned ? createOwned(parentResource, user) : createNotOwned(parentResource) );

        ResultActions mvc = getAsXml(parentResource);

        return new ChildTestResults<R,P>(mvc, resource, parentResource);
    }
    public ChildTestResults<R,P> defaultJsonTestForGetAll(boolean isOwned, boolean isParentOwned, User user) throws Exception {
        P parentResource = saveParent( isParentOwned ? createOwnedParent(user) : createNotOwnedParent() ) ;
        R resource = save( isOwned ? createOwned(parentResource, user) : createNotOwned(parentResource) );

        ResultActions mvc = getAsJson(parentResource);

        return new ChildTestResults<R,P>(mvc, resource, parentResource);
    }
    public ChildTestResults<R,P> defaultXmlTestForGet(boolean isOwned, boolean isParentOwned, User user) throws Exception {
        P parentResource = saveParent( isParentOwned ? createOwnedParent(user) : createNotOwnedParent() ) ;
        R resource = save( isOwned ? createOwned(parentResource, user) : createNotOwned(parentResource) );

        ResultActions mvc = getAsXml(parentResource, resource);

         return new ChildTestResults<R,P>(mvc, resource, parentResource);
    }
    public ChildTestResults<R,P>  defaultJsonTestForGet(boolean isOwned, boolean isParentOwned, User user) throws Exception {
        P parentResource = saveParent( isParentOwned ? createOwnedParent(user) : createNotOwnedParent() ) ;
        R resource = save( isOwned ? createOwned(parentResource, user) : createNotOwned(parentResource) );

        ResultActions mvc = getAsJson(parentResource, resource);

         return new ChildTestResults<R,P>(mvc, resource, parentResource);
    }
    public ChildTestResults<R,P>  defaultXmlTestForGetNotExists(boolean isOwned, boolean isParentOwned, User user) throws Exception {
        P parentResource = saveParent( isParentOwned ? createOwnedParent(user) : createNotOwnedParent() );
        R resource = isOwned ? createOwned(parentResource, user) : createNotOwned(parentResource);

        ResultActions mvc = getAsXml(parentResource, resource);

         return new ChildTestResults<R,P>(mvc, resource, parentResource);
    }
    public ChildTestResults<R,P> defaultJsonTestForGetNotExists(boolean isOwned, boolean isParentOwned, User user) throws Exception {
        P parentResource = saveParent( isParentOwned ? createOwnedParent(user) : createNotOwnedParent() );
        R resource = isOwned ? createOwned(parentResource, user) : createNotOwned(parentResource);

        ResultActions mvc = getAsJson(parentResource, resource);

         return new ChildTestResults<R,P>(mvc, resource, parentResource);
    }
    public ChildTestResults<R,P> defaultXmlTestForGetParentNotExists(boolean isOwned, boolean isParentOwned, User user) throws Exception {
        P parentResource = saveParent( isParentOwned ? createOwnedParent(user) : createNotOwnedParent() );
        R resource = save( isOwned ? createOwned(parentResource, user) : createNotOwned(parentResource) );

        ResultActions mvc = getAsXml(createNotOwnedParent(), resource);

         return new ChildTestResults<R,P>(mvc, resource, parentResource);
    }
    public ChildTestResults<R,P> defaultJsonTestForGetParentNotExists(boolean isOwned, boolean isParentOwned, User user) throws Exception {
        P parentResource = saveParent( isParentOwned ? createOwnedParent(user) : createNotOwnedParent() );
        R resource = save( isOwned ? createOwned(parentResource, user) : createNotOwned(parentResource) );

        ResultActions mvc = getAsJson(createNotOwnedParent(), resource);

         return new ChildTestResults<R,P>(mvc, resource, parentResource);
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
    public ChildTestResults<R,P> defaultXmlTestForPost(boolean isOwned, boolean isParentOwned, User user) throws Exception {
        P parentResource = saveParent( isParentOwned ? createOwnedParent(user) : createNotOwnedParent() ) ;
        R resource = save(isOwned ? createOwned(parentResource, user) : createNotOwned(parentResource));

        ResultActions mvc = postAsXml(parentResource, resource);

         return new ChildTestResults<R,P>(mvc, resource, parentResource);
    }
    public ChildTestResults<R,P> defaultJsonTestForPost(boolean isOwned, boolean isParentOwned, User user) throws Exception {
        P parentResource = saveParent( isParentOwned ? createOwnedParent(user) : createNotOwnedParent() ) ;
        R resource = isOwned ? createOwned(parentResource, user) : createNotOwned(parentResource);

        ResultActions mvc = postAsJson(parentResource, resource);

         return new ChildTestResults<R,P>(mvc, resource, parentResource);
    }
    public ChildTestResults<R,P> defaultXmlTestForPostParentNotExists(boolean isOwned, boolean isParentOwned, User user) throws Exception {
        P parentResource = saveParent( isParentOwned ? createOwnedParent(user) : createNotOwnedParent() ) ;
        R resource = save(isOwned ? createOwned(parentResource, user) : createNotOwned(parentResource));

        ResultActions mvc = postAsXml(createNotOwnedParent(), resource);

         return new ChildTestResults<R,P>(mvc, resource, parentResource);
    }
    public ChildTestResults<R,P> defaultJsonTestForPostParentNotExists(boolean isOwned, boolean isParentOwned, User user) throws Exception {
        P parentResource = saveParent( isParentOwned ? createOwnedParent(user) : createNotOwnedParent() ) ;
        R resource = isOwned ? createOwned(parentResource, user) : createNotOwned(parentResource);

        ResultActions mvc = postAsJson(createNotOwnedParent(), resource);

         return new ChildTestResults<R,P>(mvc, resource, parentResource);
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
    public ChildTestResults<R,P> defaultXmlTestForPut(boolean isOwned, boolean isParentOwned, User user) throws Exception {
        P parentResource = saveParent( isParentOwned ? createOwnedParent(user) : createNotOwnedParent() ) ;
        R resource = modify(save( isOwned ? createOwned(parentResource, user) : createNotOwned(parentResource) ));

        ResultActions mvc = putAsXml(parentResource, resource);

         return new ChildTestResults<R,P>(mvc, resource, parentResource);

    }
    public ChildTestResults<R,P> defaultJsonTestForPut(boolean isOwned, boolean isParentOwned, User user) throws Exception {
        P parentResource = saveParent( isParentOwned ? createOwnedParent(user) : createNotOwnedParent() ) ;
        R resource = modify(save( isOwned ? createOwned(parentResource, user) : createNotOwned(parentResource) ));

        ResultActions mvc =  putAsJson(parentResource, resource);

         return new ChildTestResults<R,P>(mvc, resource, parentResource);
    }
    public ChildTestResults<R,P> defaultXmlTestForPutNotExists(boolean isOwned, boolean isParentOwned, User user) throws Exception {
        P parentResource = saveParent( isParentOwned ? createOwnedParent(user) : createNotOwnedParent() ) ;
        R resource = modify( isOwned ? createOwned(parentResource, user) : createNotOwned(parentResource) );

        ResultActions mvc = putAsXml(parentResource, resource);

         return new ChildTestResults<R,P>(mvc, resource, parentResource);
    }
    public ChildTestResults<R,P> defaultJsonTestForPutNotExists(boolean isOwned, boolean isParentOwned, User user) throws Exception {
        P parentResource = saveParent( isParentOwned ? createOwnedParent(user) : createNotOwnedParent() ) ;
        R resource = modify( isOwned ? createOwned(parentResource, user) : createNotOwned(parentResource) );

        ResultActions mvc = putAsJson(parentResource, resource);

         return new ChildTestResults<R,P>(mvc, resource, parentResource);
    }
    public ChildTestResults<R,P> defaultXmlTestForPutParentNotExists(boolean isOwned, boolean isParentOwned, User user) throws Exception {
        P parentResource = saveParent( isParentOwned ? createOwnedParent(user) : createNotOwnedParent() ) ;
        R resource = modify(save( isOwned ? createOwned(parentResource, user) : createNotOwned(parentResource) ));

        ResultActions mvc = putAsXml(createNotOwnedParent(), resource);

         return new ChildTestResults<R,P>(mvc, resource, parentResource);
    }
    public ChildTestResults<R,P> defaultJsonTestForPutParentNotExists(boolean isOwned, boolean isParentOwned, User user) throws Exception {
        P parentResource = saveParent( isParentOwned ? createOwnedParent(user) : createNotOwnedParent() ) ;
        R resource = modify(save( isOwned ? createOwned(parentResource, user) : createNotOwned(parentResource) ));

        ResultActions mvc = putAsJson(createNotOwnedParent(), resource);

         return new ChildTestResults<R,P>(mvc, resource, parentResource);
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
    public ChildTestResults<R,P> defaultXmlTestForDelete(boolean isOwned, boolean isParentOwned, User user) throws Exception {
        P parentResource = saveParent( isParentOwned ? createOwnedParent(user) : createNotOwnedParent() ) ;
        R resource = save( isOwned ? createOwned(parentResource, user) : createNotOwned(parentResource) );

        ResultActions mvc = deleteAsXml(parentResource, resource);

         return new ChildTestResults<R,P>(mvc, resource, parentResource);
    }
    public ChildTestResults<R,P> defaultJsonTestForDelete(boolean isOwned, boolean isParentOwned, User user) throws Exception {
        P parentResource = saveParent( isParentOwned ? createOwnedParent(user) : createNotOwnedParent() ) ;
        R resource = save( isOwned ? createOwned(parentResource, user) : createNotOwned(parentResource) );

        ResultActions mvc = deleteAsJson(parentResource, resource);

         return new ChildTestResults<R,P>(mvc, resource, parentResource);
    }
    public ChildTestResults<R,P> defaultXmlTestForDeleteNotExists(boolean isOwned, boolean isParentOwned, User user) throws Exception {
        P parentResource = saveParent( isParentOwned ? createOwnedParent(user) : createNotOwnedParent() ) ;
        R resource =  isOwned ? createOwned(parentResource, user) : createNotOwned(parentResource) ;

        ResultActions mvc = deleteAsXml(parentResource, resource);

         return new ChildTestResults<R,P>(mvc, resource, parentResource);
    }
    public ChildTestResults<R,P> defaultJsonTestForDeleteNotExists(boolean isOwned, boolean isParentOwned, User user) throws Exception {
        P parentResource = saveParent( isParentOwned ? createOwnedParent(user) : createNotOwnedParent() ) ;
        R resource = isOwned ? createOwned(parentResource, user) : createNotOwned(parentResource) ;

        ResultActions mvc = deleteAsJson(parentResource, resource);

         return new ChildTestResults<R,P>(mvc, resource, parentResource);
    }
    public ChildTestResults<R,P> defaultXmlTestForDeleteParentNotExists(boolean isOwned, boolean isParentOwned, User user) throws Exception {
        P parentResource = saveParent( isParentOwned ? createOwnedParent(user) : createNotOwnedParent() ) ;
        R resource = save( isOwned ? createOwned(parentResource, user) : createNotOwned(parentResource) );

        ResultActions mvc = deleteAsXml(createNotOwnedParent(), resource);

         return new ChildTestResults<R,P>(mvc, resource, parentResource);
    }
    public ChildTestResults<R,P> defaultJsonTestForDeleteParentNotExists(boolean isOwned, boolean isParentOwned, User user) throws Exception {
        P parentResource = saveParent( isParentOwned ? createOwnedParent(user) : createNotOwnedParent() ) ;
        R resource = save( isOwned ? createOwned(parentResource, user) : createNotOwned(parentResource) );

        ResultActions mvc = deleteAsJson(createNotOwnedParent(), resource);

         return new ChildTestResults<R,P>(mvc, resource, parentResource);
    }

    @Test
    @WithMockUser(value = USER)
    public void getJsonAsUser() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForGetAsUser();
        ChildTestResults<R,P> result = defaultJsonTestForGetAll(true, false, user);
        ResultActions mvc = result.getMvc();
        R resource = result.getResource();
        P parent = result.getParent();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            validateJsonArrayLengthGreaterThan(mvc, 1);
            validateJsonLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
        }
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE})
    public void getJsonAsContentCreator() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForGetAsContentCreator();
        ChildTestResults<R,P> result = defaultJsonTestForGetAll(true, false, user);
        ResultActions mvc = result.getMvc();
        R resource = result.getResource();
        P parent = result.getParent();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            validateJsonArrayLengthGreaterThan(mvc, 1);
            validateJsonLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
        }
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE})
    public void getJsonAsModerator() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForGetAsModerator();
        ChildTestResults<R,P> result = defaultJsonTestForGetAll(true, false, user);
        ResultActions mvc = result.getMvc();
        R resource = result.getResource();
        P parent = result.getParent();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            validateJsonArrayLengthGreaterThan(mvc, 1);
            validateJsonLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
        }
    }

    @Test
    @WithUserDetails(value = ADMIN, userDetailsServiceBeanName = "appUserDetailsService")
    public void getJsonAsAdmin() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForGetAsAdmin();
        ChildTestResults<R,P> result = defaultJsonTestForGetAll(true, false, user);
        ResultActions mvc = result.getMvc();
        R resource = result.getResource();
        P parent = result.getParent();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            validateJsonArrayLengthGreaterThan(mvc, 1);
            validateJsonLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
        }
    }

    @Test
    @WithMockUser(value = USER)
    public void getJsonAsUserParentNotOwnedChildNotExists() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForGetAsUserParentNotOwnedChildNotExists();
        ChildTestResults<R,P> result = defaultJsonTestForGetNotExists(true, false, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateJsonLink(mvc, "PUT", getUrlForPutWithParent(parent, resource));
            validateJsonLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE})
    public void getJsonAsContentCreatorParentNotOwnedChildNotExists() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForGetAsContentCreatorParentNotOwnedChildNotExists();
        ChildTestResults<R,P> result = defaultJsonTestForGetNotExists(true, false, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateJsonLink(mvc, "PUT", getUrlForPutWithParent(parent, resource));
            validateJsonLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE})
    public void getJsonAsModeratorParentNotOwnedChildNotExists() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForGetAsModeratorParentNotOwnedChildNotExists();
        ChildTestResults<R,P> result = defaultJsonTestForGetNotExists(true, false, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateJsonLink(mvc, "PUT", getUrlForPutWithParent(parent, resource));
            validateJsonLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithUserDetails(value = ADMIN, userDetailsServiceBeanName = "appUserDetailsService")
    public void getJsonAsAdminParentNotOwnedChildNotExists() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForGetAsAdminParentNotOwnedChildNotExists();
        ChildTestResults<R,P> result = defaultJsonTestForGetNotExists(true, false, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateJsonLink(mvc, "PUT", getUrlForPutWithParent(parent, resource));
            validateJsonLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithMockUser(value = USER)
    public void getJsonAsUserParentNotExistsChildOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForGetAsUserParentNotExistsChildOwned();
        ChildTestResults<R,P> result = defaultJsonTestForGetParentNotExists(true, false, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateJsonLink(mvc, "PUT", getUrlForPutWithParent(parent, resource));
            validateJsonLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE})
    public void getJsonAsContentCreatorParentNotExistsChildOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForGetAsContentCreatorParentNotExistsChildOwned();
        ChildTestResults<R,P> result = defaultJsonTestForGetParentNotExists(true, false, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateJsonLink(mvc, "PUT", getUrlForPutWithParent(parent, resource));
            validateJsonLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE})
    public void getJsonAsModeratorParentNotExistsChildOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForGetAsModeratorParentNotExistsChildOwned();
        ChildTestResults<R,P> result = defaultJsonTestForGetParentNotExists(true, false, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateJsonLink(mvc, "PUT", getUrlForPutWithParent(parent, resource));
            validateJsonLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithUserDetails(value = ADMIN, userDetailsServiceBeanName = "appUserDetailsService")
    public void getJsonAsAdminParentNotExistsChildOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForGetAsAdminParentNotExistsChildOwned();
        ChildTestResults<R,P> result = defaultJsonTestForGetParentNotExists(true, false, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateJsonLink(mvc, "PUT", getUrlForPutWithParent(parent, resource));
            validateJsonLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithMockUser(value = USER)
    public void getJsonAsUserParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForGetAsUserParentNotOwnedChildOwned();
        ChildTestResults<R,P> result = defaultJsonTestForGet(true, false, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateJsonLink(mvc, "PUT", getUrlForPutWithParent(parent, resource));
            validateJsonLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE})
    public void getJsonAsContentCreatorParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForGetAsContentCreatorParentNotOwnedChildOwned();
        ChildTestResults<R,P> result = defaultJsonTestForGet(true, false, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateJsonLink(mvc, "PUT", getUrlForPutWithParent(parent, resource));
            validateJsonLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE})
    public void getJsonAsModeratorParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForGetAsModeratorParentNotOwnedChildOwned();
        ChildTestResults<R,P> result = defaultJsonTestForGet(true, false, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateJsonLink(mvc, "PUT", getUrlForPutWithParent(parent, resource));
            validateJsonLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithUserDetails(value = ADMIN, userDetailsServiceBeanName = "appUserDetailsService")
    public void getJsonAsAdminParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForGetAsAdminParentNotOwnedChildOwned();
        ChildTestResults<R,P> result = defaultJsonTestForGet(true, false, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateJsonLink(mvc, "PUT", getUrlForPutWithParent(parent, resource));
            validateJsonLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithMockUser(value = USER)
    public void postJsonAsUserParentNotExistsChildOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForPostAsUserParentNotExistsChildOwned();
        ChildTestResults<R,P> result = defaultJsonTestForPostParentNotExists(true, false, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            resource.setId(extractJsonId(mvc));
            P parent = result.getParent();

            validateJsonLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
            validateJsonLink(mvc, "PUT", getUrlForPutWithParent(parent, resource));
            validateJsonLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE})
    public void postJsonAsContentCreatorParentNotExistsChildOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForPostAsContentCreatorParentNotExistsChildOwned();
        ChildTestResults<R,P> result = defaultJsonTestForPostParentNotExists(true, false, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            resource.setId(extractJsonId(mvc));
            P parent = result.getParent();

            validateJsonLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
            validateJsonLink(mvc, "PUT", getUrlForPutWithParent(parent, resource));
            validateJsonLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE})
    public void postJsonAsModeratorParentNotExistsChildOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForPostAsModeratorParentNotExistsChildOwned();
        ChildTestResults<R,P> result = defaultJsonTestForPostParentNotExists(true, false, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            resource.setId(extractJsonId(mvc));
            P parent = result.getParent();

            validateJsonLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
            validateJsonLink(mvc, "PUT", getUrlForPutWithParent(parent, resource));
            validateJsonLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithUserDetails(value = ADMIN, userDetailsServiceBeanName = "appUserDetailsService")
    public void postJsonAsAdminParentNotExistsChildOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForPostAsAdminParentNotExistsChildOwned();
        ChildTestResults<R,P> result = defaultJsonTestForPostParentNotExists(true, false, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            resource.setId(extractJsonId(mvc));
            P parent = result.getParent();

            validateJsonLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
            validateJsonLink(mvc, "PUT", getUrlForPutWithParent(parent, resource));
            validateJsonLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithMockUser(value = USER)
    public void postJsonAsUserParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForPostAsUserParentNotOwnedChildOwned();
        ChildTestResults<R,P> result = defaultJsonTestForPost(true, false, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            resource.setId(extractJsonId(mvc));
            P parent = result.getParent();

            validateJsonLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
            validateJsonLink(mvc, "PUT", getUrlForPutWithParent(parent, resource));
            validateJsonLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE})
    public void postJsonAsContentCreatorParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForPostAsContentCreatorParentNotOwnedChildOwned();
        ChildTestResults<R,P> result = defaultJsonTestForPost(true, false, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            resource.setId(extractJsonId(mvc));
            P parent = result.getParent();

            validateJsonLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
            validateJsonLink(mvc, "PUT", getUrlForPutWithParent(parent, resource));
            validateJsonLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE})
    public void postJsonAsModeratorParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForPostAsModeratorParentNotOwnedChildOwned();
        ChildTestResults<R,P> result = defaultJsonTestForPost(true, false, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            resource.setId(extractJsonId(mvc));
            P parent = result.getParent();

            validateJsonLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
            validateJsonLink(mvc, "PUT", getUrlForPutWithParent(parent, resource));
            validateJsonLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithUserDetails(value = ADMIN, userDetailsServiceBeanName = "appUserDetailsService")
    public void postJsonAsAdminParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForPostAsAdminParentNotOwnedChildOwned();
        ChildTestResults<R,P> result = defaultJsonTestForPost(true, false, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            resource.setId(extractJsonId(mvc));
            P parent = result.getParent();

            validateJsonLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
            validateJsonLink(mvc, "PUT", getUrlForPutWithParent(parent, resource));
            validateJsonLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithMockUser(value = USER)
    public void putJsonAsUserParentNotOwnedChildNotExists() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForPutAsUserParentNotOwnedChildNotExists();
        ChildTestResults<R,P> result = defaultJsonTestForPutNotExists(true, false, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
            validateJsonLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateJsonLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE})
    public void putJsonAsContentCreatorParentNotOwnedChildNotExists() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForPutAsContentCreatorParentNotOwnedChildNotExists();
        ChildTestResults<R,P> result = defaultJsonTestForPutNotExists(true, false, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
            validateJsonLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateJsonLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE})
    public void putJsonAsModeratorParentNotOwnedChildNotExists() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForPutAsModeratorParentNotOwnedChildNotExists();
        ChildTestResults<R,P> result = defaultJsonTestForPutNotExists(true, false, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
            validateJsonLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateJsonLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithUserDetails(value = ADMIN, userDetailsServiceBeanName = "appUserDetailsService")
    public void putJsonAsAdminParentNotOwnedChildNotExists() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForPutAsAdminParentNotOwnedChildNotExists();
        ChildTestResults<R,P> result = defaultJsonTestForPutNotExists(true, false, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
            validateJsonLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateJsonLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithMockUser(value = USER)
    public void putJsonAsUserParentNotExistsChildOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForPutAsUserParentNotExistsChildOwned();
        ChildTestResults<R,P> result = defaultJsonTestForPutParentNotExists(true, false, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
            validateJsonLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateJsonLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE})
    public void putJsonAsContentCreatorParentNotExistsChildOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForPutAsContentCreatorParentNotExistsChildOwned();
        ChildTestResults<R,P> result = defaultJsonTestForPutParentNotExists(true, false, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
            validateJsonLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateJsonLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE})
    public void putJsonAsModeratorParentNotExistsChildOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForPutAsModeratorParentNotExistsChildOwned();
        ChildTestResults<R,P> result = defaultJsonTestForPutParentNotExists(true, false, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
            validateJsonLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateJsonLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithUserDetails(value = ADMIN, userDetailsServiceBeanName = "appUserDetailsService")
    public void putJsonAsAdminParentNotExistsChildOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForPutAsAdminParentNotExistsChildOwned();
        ChildTestResults<R,P> result = defaultJsonTestForPutParentNotExists(true, false, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
            validateJsonLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateJsonLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithMockUser(value = USER)
    public void putJsonAsUserParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForPutAsUserParentNotOwnedChildOwned();
        ChildTestResults<R,P> result = defaultJsonTestForPut(true, false, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
            validateJsonLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateJsonLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE})
    public void putJsonAsContentCreatorParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForPutAsContentCreatorParentNotOwnedChildOwned();
        ChildTestResults<R,P> result = defaultJsonTestForPut(true, false, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
            validateJsonLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateJsonLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE})
    public void putJsonAsModeratorParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForPutAsModeratorParentNotOwnedChildOwned();
        ChildTestResults<R,P> result = defaultJsonTestForPut(true, false, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
            validateJsonLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateJsonLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithUserDetails(value = ADMIN, userDetailsServiceBeanName = "appUserDetailsService")
    public void putJsonAsAdminParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForPutAsAdminParentNotOwnedChildOwned();
        ChildTestResults<R,P> result = defaultJsonTestForPut(true, false, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
            validateJsonLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateJsonLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }
    @Test
    @WithMockUser(value = USER)
    public void putJsonAsUserParentNotOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForPutAsUserParentNotOwnedChildNotOwned();
        ChildTestResults<R,P> result = defaultJsonTestForPut(false, false, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
            validateJsonLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateJsonLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE})
    public void putJsonAsContentCreatorParentNotOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForPutAsContentCreatorParentNotOwnedChildNotOwned();
        ChildTestResults<R,P> result = defaultJsonTestForPut(false, false, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
            validateJsonLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateJsonLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE})
    public void putJsonAsModeratorParentNotOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForPutAsModeratorParentNotOwnedChildNotOwned();
        ChildTestResults<R,P> result = defaultJsonTestForPut(false, false, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
            validateJsonLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateJsonLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithUserDetails(value = ADMIN, userDetailsServiceBeanName = "appUserDetailsService")
    public void putJsonAsAdminParentNotOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForPutAsAdminParentNotOwnedChildNotOwned();
        ChildTestResults<R,P> result = defaultJsonTestForPut(false, false, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
            validateJsonLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateJsonLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }


    @Test
    @WithMockUser(value = USER)
    public void putJsonAsUserParentOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForPutAsUserParentOwnedChildNotOwned();
        ChildTestResults<R,P> result = defaultJsonTestForPut(false, true, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
            validateJsonLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateJsonLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE})
    public void putJsonAsContentCreatorParentOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForPutAsContentCreatorParentOwnedChildNotOwned();
        ChildTestResults<R,P> result = defaultJsonTestForPut(false, true, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
            validateJsonLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateJsonLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE})
    public void putJsonAsModeratorParentOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForPutAsModeratorParentOwnedChildNotOwned();
        ChildTestResults<R,P> result = defaultJsonTestForPut(false, true, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
            validateJsonLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateJsonLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithUserDetails(value = ADMIN, userDetailsServiceBeanName = "appUserDetailsService")
    public void putJsonAsAdminParentOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForPutAsAdminParentOwnedChildNotOwned();
        ChildTestResults<R,P> result = defaultJsonTestForPut(false, true, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
            validateJsonLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateJsonLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithMockUser(value = USER)
    public void putJsonAsUserParentOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForPutAsUserParentOwnedChildOwned();
        ChildTestResults<R,P> result = defaultJsonTestForPut(true, true, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
            validateJsonLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateJsonLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE})
    public void putJsonAsContentCreatorParentOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForPutAsContentCreatorParentOwnedChildOwned();
        ChildTestResults<R,P> result = defaultJsonTestForPut(true, true, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
            validateJsonLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateJsonLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE})
    public void putJsonAsModeratorParentOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForPutAsModeratorParentOwnedChildOwned();
        ChildTestResults<R,P> result = defaultJsonTestForPut(true, true, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
            validateJsonLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateJsonLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithUserDetails(value = ADMIN, userDetailsServiceBeanName = "appUserDetailsService")
    public void putJsonAsAdminParentOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForPutAsAdminParentOwnedChildOwned();
        ChildTestResults<R,P> result = defaultJsonTestForPut(true, true, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
            validateJsonLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateJsonLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithMockUser(value = USER)
    public void deleteJsonAsUserParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForDeleteAsUserParentNotOwnedChildOwned();
        ChildTestResults<R,P> result = defaultJsonTestForDelete(true, false, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "GET All", getUrlForGetWithParent(parent));
        }
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE})
    public void deleteJsonAsContentCreatorParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsContentCreatorParentNotOwnedChildOwned();
        ChildTestResults<R,P> result = defaultJsonTestForDelete(true, false, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "GET All", getUrlForGetWithParent(parent));
        }
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE})
    public void deleteJsonAsModeratorParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsModeratorParentNotOwnedChildOwned();
        ChildTestResults<R,P> result = defaultJsonTestForDelete(true, false, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "GET All", getUrlForGetWithParent(parent));
        }
    }

    @Test
    @WithUserDetails(value = ADMIN, userDetailsServiceBeanName = "appUserDetailsService")
    public void deleteJsonAsAdminParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForDeleteAsAdminParentNotOwnedChildOwned();
        ChildTestResults<R,P> result = defaultJsonTestForDelete(true, false, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "GET All", getUrlForGetWithParent(parent));
        }
    }

    @Test
    @WithMockUser(value = USER)
    public void deleteJsonAsUserParentNotOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForDeleteAsUserParentNotOwnedChildNotOwned();
        ChildTestResults<R,P> result = defaultJsonTestForDelete(false, false, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "GET All", getUrlForGetWithParent(parent));
        }
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE})
    public void deleteJsonAsContentCreatorParentNotOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsContentCreatorParentNotOwnedChildNotOwned();
        ChildTestResults<R,P> result = defaultJsonTestForDelete(false, false, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "GET All", getUrlForGetWithParent(parent));
        }
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE})
    public void deleteJsonAsModeratorParentNotOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsModeratorParentNotOwnedChildNotOwned();
        ChildTestResults<R,P> result = defaultJsonTestForDelete(false, false, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "GET All", getUrlForGetWithParent(parent));
        }
    }

    @Test
    @WithUserDetails(value = ADMIN, userDetailsServiceBeanName = "appUserDetailsService")
    public void deleteJsonAsAdminParentNotOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForDeleteAsAdminParentNotOwnedChildNotOwned();
        ChildTestResults<R,P> result = defaultJsonTestForDelete(false, false, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "GET All", getUrlForGetWithParent(parent));
        }
    }


    @Test
    @WithMockUser(value = USER)
    public void deleteJsonAsUserParentOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForDeleteAsUserParentOwnedChildNotOwned();
        ChildTestResults<R,P> result = defaultJsonTestForDelete(false, true, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "GET All", getUrlForGetWithParent(parent));
        }
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE})
    public void deleteJsonAsContentCreatorParentOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsContentCreatorParentOwnedChildNotOwned();
        ChildTestResults<R,P> result = defaultJsonTestForDelete(false, true, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "GET All", getUrlForGetWithParent(parent));
        }
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE})
    public void deleteJsonAsModeratorParentOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsModeratorParentOwnedChildNotOwned();
        ChildTestResults<R,P> result = defaultJsonTestForDelete(false, true, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "GET All", getUrlForGetWithParent(parent));
        }
    }

    @Test
    @WithUserDetails(value = ADMIN, userDetailsServiceBeanName = "appUserDetailsService")
    public void deleteJsonAsAdminParentOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForDeleteAsAdminParentOwnedChildNotOwned();
        ChildTestResults<R,P> result = defaultJsonTestForDelete(false, true, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "GET All", getUrlForGetWithParent(parent));
        }
    }

    @Test
    @WithMockUser(value = USER)
    public void deleteJsonAsUserParentOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForDeleteAsUserParentOwnedChildOwned();
        ChildTestResults<R,P> result = defaultJsonTestForDelete(true, true, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "GET All", getUrlForGetWithParent(parent));
        }
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE})
    public void deleteJsonAsContentCreatorParentOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsContentCreatorParentOwnedChildOwned();
        ChildTestResults<R,P> result = defaultJsonTestForDelete(true, true, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "GET All", getUrlForGetWithParent(parent));
        }
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE})
    public void deleteJsonAsModeratorParentOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsModeratorParentOwnedChildOwned();
        ChildTestResults<R,P> result = defaultJsonTestForDelete(true, true, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "GET All", getUrlForGetWithParent(parent));
        }
    }

    @Test
    @WithUserDetails(value = ADMIN, userDetailsServiceBeanName = "appUserDetailsService")
    public void deleteJsonAsAdminParentOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForDeleteAsAdminParentOwnedChildOwned();
        ChildTestResults<R,P> result = defaultJsonTestForDelete(true, true, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "GET All", getUrlForGetWithParent(parent));
        }
    }


    @Test
    @WithMockUser(value = USER)
    public void getXmlAsUserParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForGetAsUserParentNotOwnedChildOwned();
        ChildTestResults<R,P> result = defaultXmlTestForGet(true, false, user);

        ResultActions mvc = result.getMvc();

        validateXmlUtf8Response(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateXmlLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateXmlLink(mvc, "PUT", getUrlForPutWithParent(parent, resource));
            validateXmlLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE})
    public void getXmlAsContentCreatorParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForGetAsContentCreatorParentNotOwnedChildOwned();
        ChildTestResults<R,P> result = defaultXmlTestForGet(true, false, user);

        ResultActions mvc = result.getMvc();

        validateXmlUtf8Response(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateXmlLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateXmlLink(mvc, "PUT", getUrlForPutWithParent(parent, resource));
            validateXmlLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE})
    public void getXmlAsModeratorParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForGetAsModeratorParentNotOwnedChildOwned();
        ChildTestResults<R,P> result = defaultXmlTestForGet(true, false, user);

        ResultActions mvc = result.getMvc();

        validateXmlUtf8Response(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateXmlLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateXmlLink(mvc, "PUT", getUrlForPutWithParent(parent, resource));
            validateXmlLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithUserDetails(value = ADMIN, userDetailsServiceBeanName = "appUserDetailsService")
    public void getXmlAsAdminParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForGetAsAdminParentNotOwnedChildOwned();
        ChildTestResults<R,P> result = defaultXmlTestForGet(true, false, user);

        ResultActions mvc = result.getMvc();

        validateXmlUtf8Response(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateXmlLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateXmlLink(mvc, "PUT", getUrlForPutWithParent(parent, resource));
            validateXmlLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithMockUser(value = USER)
    public void postXmlAsUserParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForPostAsUserParentNotOwnedChildOwned();
        ChildTestResults<R,P> result = defaultXmlTestForPost(true, false, user);

        ResultActions mvc = result.getMvc();

        validateXmlUtf8Response(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            resource.setId(extractXmlId(mvc));
            P parent = result.getParent();

            validateXmlLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
            validateXmlLink(mvc, "PUT", getUrlForPutWithParent(parent, resource));
            validateXmlLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE})
    public void postXmlAsContentCreatorParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForPostAsContentCreatorParentNotOwnedChildOwned();
        ChildTestResults<R,P> result = defaultXmlTestForPost(true, false, user);

        ResultActions mvc = result.getMvc();

        validateXmlUtf8Response(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            resource.setId(extractXmlId(mvc));
            P parent = result.getParent();

            validateXmlLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
            validateXmlLink(mvc, "PUT", getUrlForPutWithParent(parent, resource));
            validateXmlLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE})
    public void postXmlAsModeratorParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForPostAsModeratorParentNotOwnedChildOwned();
        ChildTestResults<R,P> result = defaultXmlTestForPost(true, false, user);

        ResultActions mvc = result.getMvc();

        validateXmlUtf8Response(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            resource.setId(extractXmlId(mvc));
            P parent = result.getParent();

            validateXmlLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
            validateXmlLink(mvc, "PUT", getUrlForPutWithParent(parent, resource));
            validateXmlLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithUserDetails(value = ADMIN, userDetailsServiceBeanName = "appUserDetailsService")
    public void postXmlAsAdminParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForPostAsAdminParentNotOwnedChildOwned();
        ChildTestResults<R,P> result = defaultXmlTestForPost(true, false, user);

        ResultActions mvc = result.getMvc();

        validateXmlUtf8Response(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            resource.setId(extractXmlId(mvc));
            P parent = result.getParent();

            validateXmlLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
            validateXmlLink(mvc, "PUT", getUrlForPutWithParent(parent, resource));
            validateXmlLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithMockUser(value = USER)
    public void putXmlAsUserParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForPutAsUserParentNotOwnedChildOwned();
        ChildTestResults<R,P> result = defaultXmlTestForPut(true, false, user);

        ResultActions mvc = result.getMvc();

        validateXmlUtf8Response(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateXmlLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
            validateXmlLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateXmlLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE})
    public void putXmlAsContentCreatorParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForPutAsContentCreatorParentNotOwnedChildOwned();
        ChildTestResults<R,P> result = defaultXmlTestForPut(true, false, user);

        ResultActions mvc = result.getMvc();

        validateXmlUtf8Response(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateXmlLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
            validateXmlLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateXmlLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE})
    public void putXmlAsModeratorParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForPutAsModeratorParentNotOwnedChildOwned();
        ChildTestResults<R,P> result = defaultXmlTestForPut(true, false, user);

        ResultActions mvc = result.getMvc();

        validateXmlUtf8Response(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateXmlLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
            validateXmlLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateXmlLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithUserDetails(value = ADMIN, userDetailsServiceBeanName = "appUserDetailsService")
    public void putXmlAsAdminParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForPutAsAdminParentNotOwnedChildOwned();
        ChildTestResults<R,P> result = defaultXmlTestForPut(true, false, user);

        ResultActions mvc = result.getMvc();

        validateXmlUtf8Response(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateXmlLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
            validateXmlLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateXmlLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithMockUser(value = USER)
    public void putXmlAsUserParentNotOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForPutAsUserParentNotOwnedChildNotOwned();
        ChildTestResults<R,P> result = defaultXmlTestForPut(false, false, user);

        ResultActions mvc = result.getMvc();

        validateXmlUtf8Response(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateXmlLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
            validateXmlLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateXmlLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE})
    public void putXmlAsContentCreatorParentNotOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForPutAsContentCreatorParentNotOwnedChildNotOwned();
        ChildTestResults<R,P> result = defaultXmlTestForPut(false, false, user);

        ResultActions mvc = result.getMvc();

        validateXmlUtf8Response(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateXmlLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
            validateXmlLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateXmlLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE})
    public void putXmlAsModeratorParentNotOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForPutAsModeratorParentNotOwnedChildNotOwned();
        ChildTestResults<R,P> result = defaultXmlTestForPut(false, false, user);

        ResultActions mvc = result.getMvc();

        validateXmlUtf8Response(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateXmlLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
            validateXmlLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateXmlLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithUserDetails(value = ADMIN, userDetailsServiceBeanName = "appUserDetailsService")
    public void putXmlAsAdminParentNotOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForPutAsAdminParentNotOwnedChildNotOwned();
        ChildTestResults<R,P> result = defaultXmlTestForPut(false, false, user);

        ResultActions mvc = result.getMvc();

        validateXmlUtf8Response(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateXmlLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
            validateXmlLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateXmlLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }


    @Test
    @WithMockUser(value = USER)
    public void putXmlAsUserParentOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForPutAsUserParentOwnedChildNotOwned();
        ChildTestResults<R,P> result = defaultXmlTestForPut(false, true, user);

        ResultActions mvc = result.getMvc();

        validateXmlUtf8Response(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateXmlLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
            validateXmlLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateXmlLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE})
    public void putXmlAsContentCreatorParentOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForPutAsContentCreatorParentOwnedChildNotOwned();
        ChildTestResults<R,P> result = defaultXmlTestForPut(false, true, user);

        ResultActions mvc = result.getMvc();

        validateXmlUtf8Response(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateXmlLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
            validateXmlLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateXmlLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE})
    public void putXmlAsModeratorParentOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForPutAsModeratorParentOwnedChildNotOwned();
        ChildTestResults<R,P> result = defaultXmlTestForPut(false, true, user);

        ResultActions mvc = result.getMvc();

        validateXmlUtf8Response(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateXmlLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
            validateXmlLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateXmlLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithUserDetails(value = ADMIN, userDetailsServiceBeanName = "appUserDetailsService")
    public void putXmlAsAdminParentOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForPutAsAdminParentOwnedChildNotOwned();
        ChildTestResults<R,P> result = defaultXmlTestForPut(false, true, user);

        ResultActions mvc = result.getMvc();

        validateXmlUtf8Response(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateXmlLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
            validateXmlLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateXmlLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithMockUser(value = USER)
    public void putXmlAsUserParentOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForPutAsUserParentOwnedChildOwned();
        ChildTestResults<R,P> result = defaultXmlTestForPut(true, true, user);

        ResultActions mvc = result.getMvc();

        validateXmlUtf8Response(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateXmlLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
            validateXmlLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateXmlLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE})
    public void putXmlAsContentCreatorParentOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForPutAsContentCreatorParentOwnedChildOwned();
        ChildTestResults<R,P> result = defaultXmlTestForPut(true, true, user);

        ResultActions mvc = result.getMvc();

        validateXmlUtf8Response(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateXmlLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
            validateXmlLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateXmlLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE})
    public void putXmlAsModeratorParentOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForPutAsModeratorParentOwnedChildOwned();
        ChildTestResults<R,P> result = defaultXmlTestForPut(true, true, user);

        ResultActions mvc = result.getMvc();

        validateXmlUtf8Response(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateXmlLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
            validateXmlLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateXmlLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithUserDetails(value = ADMIN, userDetailsServiceBeanName = "appUserDetailsService")
    public void putXmlAsAdminParentOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForPutAsAdminParentOwnedChildOwned();
        ChildTestResults<R,P> result = defaultXmlTestForPut(true, true, user);

        ResultActions mvc = result.getMvc();

        validateXmlUtf8Response(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateXmlLink(mvc, "GET", getUrlForGetWithParent(parent, resource));
            validateXmlLink(mvc, "POST", getUrlForPostWithParent(parent));
            validateXmlLink(mvc, "DELETE", getUrlForDeleteWithParent(parent, resource));
        }
    }

    @Test
    @WithMockUser(value = USER)
    public void deleteJsonAsUserParentNotOwnedChildNotExists() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForDeleteAsUserParentNotOwnedChildNotExists();
        ChildTestResults<R,P> result = defaultJsonTestForDeleteNotExists(true, false, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "GET All", getUrlForGetWithParent(parent));
        }
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE})
    public void deleteJsonAsContentCreatorParentNotOwnedChildNotExists() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsContentCreatorParentNotOwnedChildNotExists();
        ChildTestResults<R,P> result = defaultJsonTestForDeleteNotExists(true, false, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "GET All", getUrlForGetWithParent(parent));
        }
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE})
    public void deleteJsonAsModeratorParentNotOwnedChildNotExists() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsModeratorParentNotOwnedChildNotExists();
        ChildTestResults<R,P> result = defaultJsonTestForDeleteNotExists(true, false, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "GET All", getUrlForGetWithParent(parent));
        }
    }

    @Test
    @WithUserDetails(value = ADMIN, userDetailsServiceBeanName = "appUserDetailsService")
    public void deleteJsonAsAdminParentNotOwnedChildNotExists() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForDeleteAsAdminParentNotOwnedChildNotExists();
        ChildTestResults<R,P> result = defaultJsonTestForDeleteNotExists(true, false, user);

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "GET All", getUrlForGetWithParent(parent));
        }
    }

    @Test
    @WithMockUser(value = USER)
    public void deleteJsonAsUserParentNotExistsChildOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForDeleteAsUserParentNotExistsChildOwned();
        ChildTestResults<R,P> result = defaultJsonTestForDeleteParentNotExists(true, false, user);;

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "GET All", getUrlForGetWithParent(parent));
        }
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE})
    public void deleteJsonAsContentCreatorParentNotExistsChildOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsContentCreatorParentNotExistsChildOwned();
        ChildTestResults<R,P> result = defaultJsonTestForDeleteParentNotExists(true, false, user);;

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "GET All", getUrlForGetWithParent(parent));
        }
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE})
    public void deleteJsonAsModeratorParentNotExistsChildOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsModeratorParentNotExistsChildOwned();
        ChildTestResults<R,P> result = defaultJsonTestForDeleteParentNotExists(true, false, user);;

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "GET All", getUrlForGetWithParent(parent));
        }
    }

    @Test
    @WithUserDetails(value = ADMIN, userDetailsServiceBeanName = "appUserDetailsService")
    public void deleteJsonAsAdminParentNotExistsChildOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForDeleteAsAdminParentNotExistsChildOwned();
        ChildTestResults<R,P> result = defaultJsonTestForDeleteParentNotExists(true, false, user);;

        ResultActions mvc = result.getMvc();

        validateJsonResponse(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateJsonLink(mvc, "GET All", getUrlForGetWithParent(parent));
        }
    }

    @Test
    @WithMockUser(value = USER)
    public void deleteXmlAsUserParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForDeleteAsUserParentNotOwnedChildOwned();
        ChildTestResults<R,P> result = defaultXmlTestForDelete(true, false, user);

        ResultActions mvc = result.getMvc();

        validateXmlUtf8Response(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateXmlLink(mvc, "GET All", getUrlForGetWithParent(parent));
        }
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE})
    public void deleteXmlAsContentCreatorParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsContentCreatorParentNotOwnedChildOwned();
        ChildTestResults<R,P> result = defaultXmlTestForDelete(true, false, user);

        ResultActions mvc = result.getMvc();

        validateXmlUtf8Response(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateXmlLink(mvc, "GET All", getUrlForGetWithParent(parent));
        }
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE})
    public void deleteXmlAsModeratorParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsModeratorParentNotOwnedChildOwned();
        ChildTestResults<R,P> result = defaultXmlTestForDelete(true, false, user);

        ResultActions mvc = result.getMvc();

        validateXmlUtf8Response(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateXmlLink(mvc, "GET All", getUrlForGetWithParent(parent));
        }
    }

    @Test
    @WithUserDetails(value = ADMIN, userDetailsServiceBeanName = "appUserDetailsService")
    public void deleteXmlAsAdminParentNotOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForDeleteAsAdminParentNotOwnedChildOwned();
        ChildTestResults<R,P> result = defaultXmlTestForDelete(true, false, user);

        ResultActions mvc = result.getMvc();

        validateXmlUtf8Response(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateXmlLink(mvc, "GET All", getUrlForGetWithParent(parent));
        }
    }

    @Test
    @WithMockUser(value = USER)
    public void deleteXmlAsUserParentNotOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForDeleteAsUserParentNotOwnedChildNotOwned();
        ChildTestResults<R,P> result = defaultXmlTestForDelete(false, false, user);

        ResultActions mvc = result.getMvc();

        validateXmlUtf8Response(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateXmlLink(mvc, "GET All", getUrlForGetWithParent(parent));
        }
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE})
    public void deleteXmlAsContentCreatorParentNotOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsContentCreatorParentNotOwnedChildNotOwned();
        ChildTestResults<R,P> result = defaultXmlTestForDelete(false, false, user);

        ResultActions mvc = result.getMvc();

        validateXmlUtf8Response(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateXmlLink(mvc, "GET All", getUrlForGetWithParent(parent));
        }
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE})
    public void deleteXmlAsModeratorParentNotOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsModeratorParentNotOwnedChildNotOwned();
        ChildTestResults<R,P> result = defaultXmlTestForDelete(false, false, user);

        ResultActions mvc = result.getMvc();

        validateXmlUtf8Response(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateXmlLink(mvc, "GET All", getUrlForGetWithParent(parent));
        }
    }

    @Test
    @WithUserDetails(value = ADMIN, userDetailsServiceBeanName = "appUserDetailsService")
    public void deleteXmlAsAdminParentNotOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForDeleteAsAdminParentNotOwnedChildNotOwned();
        ChildTestResults<R,P> result = defaultXmlTestForDelete(false, false, user);

        ResultActions mvc = result.getMvc();

        validateXmlUtf8Response(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateXmlLink(mvc, "GET All", getUrlForGetWithParent(parent));
        }
    }


    @Test
    @WithMockUser(value = USER)
    public void deleteXmlAsUserParentOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForDeleteAsUserParentOwnedChildNotOwned();
        ChildTestResults<R,P> result = defaultXmlTestForDelete(false, true, user);

        ResultActions mvc = result.getMvc();

        validateXmlUtf8Response(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateXmlLink(mvc, "GET All", getUrlForGetWithParent(parent));
        }
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE})
    public void deleteXmlAsContentCreatorParentOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsContentCreatorParentOwnedChildNotOwned();
        ChildTestResults<R,P> result = defaultXmlTestForDelete(false, true, user);

        ResultActions mvc = result.getMvc();

        validateXmlUtf8Response(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateXmlLink(mvc, "GET All", getUrlForGetWithParent(parent));
        }
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE})
    public void deleteXmlAsModeratorParentOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsModeratorParentOwnedChildNotOwned();
        ChildTestResults<R,P> result = defaultXmlTestForDelete(false, true, user);

        ResultActions mvc = result.getMvc();

        validateXmlUtf8Response(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateXmlLink(mvc, "GET All", getUrlForGetWithParent(parent));
        }
    }

    @Test
    @WithUserDetails(value = ADMIN, userDetailsServiceBeanName = "appUserDetailsService")
    public void deleteXmlAsAdminParentOwnedChildNotOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForDeleteAsAdminParentOwnedChildNotOwned();
        ChildTestResults<R,P> result = defaultXmlTestForDelete(false, true, user);

        ResultActions mvc = result.getMvc();

        validateXmlUtf8Response(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateXmlLink(mvc, "GET All", getUrlForGetWithParent(parent));
        }
    }

    @Test
    @WithMockUser(value = USER)
    public void deleteXmlAsUserParentOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForDeleteAsUserParentOwnedChildOwned();
        ChildTestResults<R,P> result = defaultXmlTestForDelete(true, true, user);

        ResultActions mvc = result.getMvc();

        validateXmlUtf8Response(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateXmlLink(mvc, "GET All", getUrlForGetWithParent(parent));
        }
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE})
    public void deleteXmlAsContentCreatorParentOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsContentCreatorParentOwnedChildOwned();
        ChildTestResults<R,P> result = defaultXmlTestForDelete(true, true, user);

        ResultActions mvc = result.getMvc();

        validateXmlUtf8Response(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateXmlLink(mvc, "GET All", getUrlForGetWithParent(parent));
        }
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE})
    public void deleteXmlAsModeratorParentOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsModeratorParentOwnedChildOwned();
        ChildTestResults<R,P> result = defaultXmlTestForDelete(true, true, user);

        ResultActions mvc = result.getMvc();

        validateXmlUtf8Response(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateXmlLink(mvc, "GET All", getUrlForGetWithParent(parent));
        }
    }

    @Test
    @WithUserDetails(value = ADMIN, userDetailsServiceBeanName = "appUserDetailsService")
    public void deleteXmlAsAdminParentOwnedChildOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForDeleteAsAdminParentOwnedChildOwned();
        ChildTestResults<R,P> result = defaultXmlTestForDelete(true, true, user);

        ResultActions mvc = result.getMvc();

        validateXmlUtf8Response(mvc, expectedStatus);
        if(expectedStatus.is2xxSuccessful()){
            R resource = result.getResource();
            P parent = result.getParent();

            validateXmlLink(mvc, "GET All", getUrlForGetWithParent(parent));
        }
    }
}
