package student.laurens.novibackend.integration.controllers;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import student.laurens.novibackend.entities.AbstractOwnedEntity;
import student.laurens.novibackend.entities.User;

/**
 * Base class to provide default methods for testing RestControllers which expose HTTP method for resources which are owned.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
public abstract class OwnedControllerIntegrationTestBase<R extends AbstractOwnedEntity> extends ControllerIntegrationTestBase<R> {

    /**
     * Implement this method by a resource specific implementation to create sample owned instances of the resource.
     *-
     * @return Sample instance of resource.
     */
    abstract protected R createOwned(User owner);
    protected R createNotOwned(){
        return createOwned(saveUser(createUniqueContentCreator()));
    }

    @Override
    protected R create(){
        return createNotOwned();
    }

    abstract protected HttpStatus expectedStatusForGetAsAdminResourceOwned();
    abstract protected HttpStatus expectedStatusForGetAsAdminResourceNotOwned();
    abstract protected HttpStatus expectedStatusForGetAsModeratorResourceOwned();
    abstract protected HttpStatus expectedStatusForGetAsModeratorResourceNotOwned();
    abstract protected HttpStatus expectedStatusForGetAsContentCreatorResourceOwned();
    abstract protected HttpStatus expectedStatusForGetAsContentCreatorResourceNotOwned();
    abstract protected HttpStatus expectedStatusForGetAsUserResourceOwned();
    abstract protected HttpStatus expectedStatusForGetAsUserResourceNotOwned();

    abstract protected HttpStatus expectedStatusForPutAsAdminResourceOwned();
    abstract protected HttpStatus expectedStatusForPutAsAdminResourceNotOwned();
    abstract protected HttpStatus expectedStatusForDeleteAsAdminResourceOwned();
    abstract protected HttpStatus expectedStatusForDeleteAsAdminResourceNotOwned();

    abstract protected HttpStatus expectedStatusForPutAsContentCreatorResourceOwned();
    abstract protected HttpStatus expectedStatusForPutAsContentCreatorResourceNotOwned();
    abstract protected HttpStatus expectedStatusForDeleteAsContentCreatorResourceOwned();
    abstract protected HttpStatus expectedStatusForDeleteAsContentCreatorResourceNotOwned();

    abstract protected HttpStatus expectedStatusForPutAsModeratorResourceOwned();
    abstract protected HttpStatus expectedStatusForPutAsModeratorResourceNotOwned();
    abstract protected HttpStatus expectedStatusForDeleteAsModeratorResourceOwned();
    abstract protected HttpStatus expectedStatusForDeleteAsModeratorResourceNotOwned();

    abstract protected HttpStatus expectedStatusForPutAsUserResourceOwned();
    abstract protected HttpStatus expectedStatusForPutAsUserResourceNotOwned();
    abstract protected HttpStatus expectedStatusForDeleteAsUserResourceOwned();
    abstract protected HttpStatus expectedStatusForDeleteAsUserResourceNotOwned();

    /**
     * Override this method by a resource specific implementation to send DELETE messages with JSON as default data format.
     *
     * @return ResultActions instance which contains the response of the DELETE call.
     */
    public ResultActions defaultJsonTestForGet(boolean isOwned, User user) throws Exception {
        return getAsJson(save( isOwned ? createOwned(user) : createNotOwned() ));
    }

    /**
     * Override this method by a resource specific implementation to send DELETE messages with JSON as default data format.
     *
     * @return ResultActions instance which contains the response of the DELETE call.
     */
    public ResultActions defaultXmlTestForGet(boolean isOwned, User user) throws Exception {
        return getAsXml(save( isOwned ? createOwned(user) : createNotOwned() ));
    }

    /**
     * Override this method by a resource specific implementation to send DELETE messages with JSON as default data format.
     *
     * @return ResultActions instance which contains the response of the DELETE call.
     */
    public ResultActions defaultJsonTestForPut(boolean isOwned, User user) throws Exception {
        return updateAsJson(modify(save( isOwned ? createOwned(user) : createNotOwned() )));
    }

    /**
     * Override this method by a resource specific implementation to send DELETE messages with JSON as default data format.
     *
     * @return ResultActions instance which contains the response of the DELETE call.
     */
    public ResultActions defaultXmlTestForPut(boolean isOwned, User user) throws Exception {
        return updateAsXml(modify(save( isOwned ? createOwned(user) : createNotOwned() )));
    }

    /**
     * Override this method by a resource specific implementation to send DELETE messages with JSON as default data format.
     *
     * @return ResultActions instance which contains the response of the DELETE call.
     */
    public ResultActions defaultJsonTestForDelete(final boolean isOwned, final User user) throws Exception {
        return deleteAsJson(modify(save( isOwned ? createOwned(user) : createNotOwned() )));
    }

    /**
     * Override this method by a resource specific implementation to send DELETE messages with JSON as default data format.
     *
     * @return ResultActions instance which contains the response of the DELETE call.
     */
    public ResultActions defaultXmlTestForDelete(final boolean isOwned, final User user) throws Exception {
        return deleteAsXml(modify(save( isOwned ? createOwned(user) : createNotOwned() )));
    }

    @Test
    @WithMockUser(value = USER, roles = {USER_ROLE} )
    public void getXmlAsUserResourceNotOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForGetAsUserResourceNotOwned();
        ResultActions mvc = defaultXmlTestForGet(false, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = USER, roles = {USER_ROLE} )
    public void getJsonAsUserResourceNotOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForGetAsUserResourceNotOwned();
        ResultActions mvc = defaultJsonTestForGet(false, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = USER, roles = {USER_ROLE} )
    public void getXmlAsUserResourceOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForGetAsUserResourceOwned();
        ResultActions mvc = defaultXmlTestForGet(true, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = USER, roles = {USER_ROLE} )
    public void getJsonAsUserResourceOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForGetAsUserResourceOwned();
        ResultActions mvc = defaultJsonTestForGet(true, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void getXmlAsContentCreatorResourceNotOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForGetAsContentCreatorResourceNotOwned();
        ResultActions mvc = defaultXmlTestForGet(false, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void getJsonAsContentCreatorResourceNotOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForGetAsContentCreatorResourceNotOwned();
        ResultActions mvc = defaultJsonTestForGet(false, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void getXmlAsContentCreatorResourceOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForGetAsContentCreatorResourceOwned();
        ResultActions mvc = defaultXmlTestForGet(true, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void getJsonAsContentCreatorResourceOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForGetAsContentCreatorResourceOwned();
        ResultActions mvc = defaultJsonTestForGet(true, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void getXmlAsModeratorResourceNotOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForGetAsModeratorResourceNotOwned();
        ResultActions mvc = defaultXmlTestForGet(false, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void getJsonAsModeratorResourceNotOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForGetAsModeratorResourceNotOwned();
        ResultActions mvc = defaultJsonTestForGet(false, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void getXmlAsModeratorResourceOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForGetAsModeratorResourceOwned();
        ResultActions mvc = defaultXmlTestForGet(true, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void getJsonAsModeratorResourceOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForGetAsModeratorResourceOwned();
        ResultActions mvc = defaultJsonTestForGet(true, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void getXmlAsAdminResourceNotOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForGetAsAdminResourceNotOwned();
        ResultActions mvc = defaultXmlTestForGet(false, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void getJsonAsAdminResourceNotOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForGetAsAdminResourceNotOwned();
        ResultActions mvc = defaultJsonTestForGet(false, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void getXmlAsAdminResourceOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForGetAsAdminResourceOwned();
        ResultActions mvc = defaultXmlTestForGet(true, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void getJsonAsAdminResourceOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForGetAsAdminResourceOwned();
        ResultActions mvc = defaultJsonTestForGet(true, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void putJsonAsAdminResourceOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForPutAsAdminResourceOwned();
        ResultActions mvc = defaultJsonTestForPut(true, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void putXmlAsAdminResourceOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForPutAsAdminResourceOwned();
        ResultActions mvc = defaultXmlTestForPut(true, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }


    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void putJsonAsAdminResourceNotOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForPutAsAdminResourceNotOwned();
        ResultActions mvc = defaultJsonTestForPut(false, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void putXmlAsAdminResourceNotOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForPutAsAdminResourceNotOwned();
        ResultActions mvc = defaultXmlTestForPut(false, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void deleteJsonAsAdminResourceOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForDeleteAsAdminResourceOwned();
        ResultActions mvc = defaultJsonTestForDelete(true, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void deleteXmlAsAdminResourceOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForDeleteAsAdminResourceOwned();
        ResultActions mvc = defaultXmlTestForDelete(true, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void deleteJsonAsAdminResourceNotOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForDeleteAsAdminResourceNotOwned();
        ResultActions mvc = defaultJsonTestForDelete(false, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void deleteXmlAsAdminResourceNotOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForDeleteAsAdminResourceNotOwned();
        ResultActions mvc = defaultXmlTestForDelete(false, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void putJsonAsContentCreatorResourceOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForPutAsContentCreatorResourceOwned();
        ResultActions mvc = defaultJsonTestForPut(true, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void putXmlAsContentCreatorResourceOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForPutAsContentCreatorResourceOwned();
        ResultActions mvc = defaultXmlTestForPut(true, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }


    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void putJsonAsContentCreatorResourceNotOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForPutAsContentCreatorResourceNotOwned();
        ResultActions mvc = defaultJsonTestForPut(false, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void putXmlAsContentCreatorResourceNotOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForPutAsContentCreatorResourceNotOwned();
        ResultActions mvc = defaultXmlTestForPut(false, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void deleteJsonAsContentCreatorResourceOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsContentCreatorResourceOwned();
        ResultActions mvc = defaultJsonTestForDelete(true, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void deleteXmlAsContentCreatorResourceOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsContentCreatorResourceOwned();
        ResultActions mvc = defaultXmlTestForDelete(true, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void deleteJsonAsContentCreatorResourceNotOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsContentCreatorResourceNotOwned();
        ResultActions mvc = defaultJsonTestForDelete(false, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void deleteXmlAsContentCreatorResourceNotOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsContentCreatorResourceNotOwned();
        ResultActions mvc = defaultXmlTestForDelete(false, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void putJsonAsModeratorResourceOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForPutAsModeratorResourceOwned();
        ResultActions mvc = defaultJsonTestForPut(true, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void putXmlAsModeratorResourceOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForPutAsModeratorResourceOwned();
        ResultActions mvc = defaultXmlTestForPut(true, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }


    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void putJsonAsModeratorResourceNotOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForPutAsModeratorResourceNotOwned();
        ResultActions mvc = defaultJsonTestForPut(false, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void putXmlAsModeratorResourceNotOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForPutAsModeratorResourceNotOwned();
        ResultActions mvc = defaultXmlTestForPut(false, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void deleteJsonAsModeratorResourceOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsModeratorResourceOwned();
        ResultActions mvc = defaultJsonTestForDelete(true, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void deleteXmlAsModeratorResourceOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsModeratorResourceOwned();
        ResultActions mvc = defaultXmlTestForDelete(true, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }


    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void deleteJsonAsModeratorResourceNotOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsModeratorResourceNotOwned();
        ResultActions mvc = defaultJsonTestForDelete(false, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void deleteXmlAsModeratorResourceNotOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsModeratorResourceNotOwned();
        ResultActions mvc = defaultXmlTestForDelete(false, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = USER, roles = {USER_ROLE} )
    public void putJsonAsUserResourceOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForPutAsUserResourceOwned();
        ResultActions mvc = defaultJsonTestForPut(true, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = USER, roles = {USER_ROLE} )
    public void putXmlAsUserResourceOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForPutAsUserResourceOwned();
        ResultActions mvc = defaultXmlTestForPut(true, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }


    @Test
    @WithMockUser(value = USER, roles = {USER_ROLE} )
    public void putJsonAsUserResourceNotOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForPutAsUserResourceNotOwned();
        ResultActions mvc = defaultJsonTestForPut(false, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = USER, roles = {USER_ROLE} )
    public void putXmlAsUserResourceNotOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForPutAsUserResourceNotOwned();
        ResultActions mvc = defaultXmlTestForPut(false, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = USER, roles = {USER_ROLE} )
    public void deleteJsonAsUserResourceOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForDeleteAsUserResourceOwned();
        ResultActions mvc = defaultJsonTestForDelete(true, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = USER, roles = {USER_ROLE} )
    public void deleteXmlAsUserResourceOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForDeleteAsUserResourceOwned();
        ResultActions mvc = defaultXmlTestForDelete(true, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }


    @Test
    @WithMockUser(value = USER, roles = {USER_ROLE} )
    public void deleteJsonAsUserResourceNotOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForDeleteAsUserResourceNotOwned();
        ResultActions mvc = defaultJsonTestForDelete(false, user);

        validateJsonResponse(mvc, expectedStatus);
    }

    @Test
    @WithMockUser(value = USER, roles = {USER_ROLE} )
    public void deleteXmlAsUserResourceNotOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForDeleteAsUserResourceNotOwned();
        ResultActions mvc = defaultXmlTestForDelete(false, user);

        validateXmlUtf8Response(mvc, expectedStatus);
    }
}

