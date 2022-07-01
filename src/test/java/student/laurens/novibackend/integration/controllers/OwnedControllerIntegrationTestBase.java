package student.laurens.novibackend.integration.controllers;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithUserDetails;
import student.laurens.novibackend.entities.AbstractOwnedEntity;
import student.laurens.novibackend.entities.User;
import student.laurens.novibackend.entities.dto.ResourceDto;

/**
 * Base class to provide default methods for testing RestControllers which expose HTTP method for resources which are owned.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
public abstract class OwnedControllerIntegrationTestBase<R extends AbstractOwnedEntity, D extends ResourceDto> extends ControllerIntegrationTestBase<R,D> {

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
    public TestResults<R> defaultJsonTestForGet(final boolean isOwned, final User user) throws Exception {
        R resource = save( isOwned ? createOwned(user) : createNotOwned() );
        return new TestResults<>(getAsJson(resource), resource);
    }

    /**
     * Override this method by a resource specific implementation to send DELETE messages with JSON as default data format.
     *
     * @return ResultActions instance which contains the response of the DELETE call.
     */
    public TestResults<R> defaultXmlTestForGet(final boolean isOwned, final User user) throws Exception {
        R resource = save( isOwned ? createOwned(user) : createNotOwned() );
        return new TestResults<>(getAsXml(resource), resource);
    }

    /**
     * Override this method by a resource specific implementation to send DELETE messages with JSON as default data format.
     *
     * @return ResultActions instance which contains the response of the DELETE call.
     */
    public TestResults<R> defaultJsonTestForPut(final boolean isOwned, final User user) throws Exception {
        R resource = modify(save( isOwned ? createOwned(user) : createNotOwned() ));
        return new TestResults<>(updateAsJson(resource), resource);
    }

    /**
     * Override this method by a resource specific implementation to send DELETE messages with JSON as default data format.
     *
     * @return ResultActions instance which contains the response of the DELETE call.
     */
    public TestResults<R> defaultXmlTestForPut(final boolean isOwned, final User user) throws Exception {
        R resource = modify(save( isOwned ? createOwned(user) : createNotOwned() ));
        return new TestResults<>(updateAsXml(resource), resource);
    }

    /**
     * Override this method by a resource specific implementation to send DELETE messages with JSON as default data format.
     *
     * @return ResultActions instance which contains the response of the DELETE call.
     */
    public TestResults<R> defaultJsonTestForDelete(final boolean isOwned, final User user) throws Exception {
        R resource = modify(save( isOwned ? createOwned(user) : createNotOwned() ));
        return new TestResults<>(deleteAsJson(resource), resource);
    }

    /**
     * Override this method by a resource specific implementation to send DELETE messages with JSON as default data format.
     *
     * @return ResultActions instance which contains the response of the DELETE call.
     */
    public TestResults<R> defaultXmlTestForDelete(final boolean isOwned, final User user) throws Exception {
        R resource = modify(save( isOwned ? createOwned(user) : createNotOwned() ));
        return new TestResults<>(deleteAsXml(resource), resource);
    }

    @Test
    @WithUserDetails(value = USER, userDetailsServiceBeanName = "appUserDetailsService")
    public void getXmlAsUserResourceNotOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForGetAsUserResourceNotOwned();
        TestResults<R> results = defaultXmlTestForGet(false, user);

        validateXmlUtf8Response(results.getMvc(), expectedStatus);;
    }

    @Test
    @WithUserDetails(value = USER, userDetailsServiceBeanName = "appUserDetailsService")
    public void getJsonAsUserResourceNotOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForGetAsUserResourceNotOwned();
        TestResults<R> results = defaultJsonTestForGet(false, user);

        validateJsonResponse(results.getMvc(), expectedStatus);
    }

    @Test
    @WithUserDetails(value = USER, userDetailsServiceBeanName = "appUserDetailsService")
    public void getXmlAsUserResourceOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForGetAsUserResourceOwned();
        TestResults<R> results = defaultXmlTestForGet(true, user);

        validateXmlUtf8Response(results.getMvc(), expectedStatus);
    }

    @Test
    @WithUserDetails(value = USER, userDetailsServiceBeanName = "appUserDetailsService")
    public void getJsonAsUserResourceOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForGetAsUserResourceOwned();
        TestResults<R> results = defaultJsonTestForGet(true, user);

        validateJsonResponse(results.getMvc(), expectedStatus);
    }

    @Test
    @WithUserDetails(value = CONTENT_CREATOR, userDetailsServiceBeanName = "appUserDetailsService")
    public void getXmlAsContentCreatorResourceNotOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForGetAsContentCreatorResourceNotOwned();
        TestResults<R> results = defaultXmlTestForGet(false, user);

        validateXmlUtf8Response(results.getMvc(), expectedStatus);
    }

    @Test
    @WithUserDetails(value = CONTENT_CREATOR, userDetailsServiceBeanName = "appUserDetailsService")
    public void getJsonAsContentCreatorResourceNotOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForGetAsContentCreatorResourceNotOwned();
        TestResults<R> results = defaultJsonTestForGet(false, user);

        validateJsonResponse(results.getMvc(), expectedStatus);
    }

    @Test
    @WithUserDetails(value = CONTENT_CREATOR, userDetailsServiceBeanName = "appUserDetailsService")
    public void getXmlAsContentCreatorResourceOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForGetAsContentCreatorResourceOwned();
        TestResults<R> results = defaultXmlTestForGet(true, user);

        validateXmlUtf8Response(results.getMvc(), expectedStatus);
    }

    @Test
    @WithUserDetails(value = CONTENT_CREATOR, userDetailsServiceBeanName = "appUserDetailsService")
    public void getJsonAsContentCreatorResourceOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForGetAsContentCreatorResourceOwned();
        TestResults<R> results = defaultJsonTestForGet(true, user);

        validateJsonResponse(results.getMvc(), expectedStatus);
    }

    @Test
    @WithUserDetails(value = MODERATOR, userDetailsServiceBeanName = "appUserDetailsService")
    public void getXmlAsModeratorResourceNotOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForGetAsModeratorResourceNotOwned();
        TestResults<R> results = defaultXmlTestForGet(false, user);

        validateXmlUtf8Response(results.getMvc(), expectedStatus);
    }

    @Test
    @WithUserDetails(value = MODERATOR, userDetailsServiceBeanName = "appUserDetailsService")
    public void getJsonAsModeratorResourceNotOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForGetAsModeratorResourceNotOwned();
        TestResults<R> results = defaultJsonTestForGet(false, user);

        validateJsonResponse(results.getMvc(), expectedStatus);
    }

    @Test
    @WithUserDetails(value = MODERATOR, userDetailsServiceBeanName = "appUserDetailsService")
    public void getXmlAsModeratorResourceOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForGetAsModeratorResourceOwned();
        TestResults<R> results = defaultXmlTestForGet(true, user);

        validateXmlUtf8Response(results.getMvc(), expectedStatus);
    }

    @Test
    @WithUserDetails(value = MODERATOR, userDetailsServiceBeanName = "appUserDetailsService")
    public void getJsonAsModeratorResourceOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForGetAsModeratorResourceOwned();
        TestResults<R> results = defaultJsonTestForGet(true, user);

        validateJsonResponse(results.getMvc(), expectedStatus);
    }

    @Test
    @WithUserDetails(value = ADMIN, userDetailsServiceBeanName = "appUserDetailsService")
    public void getXmlAsAdminResourceNotOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForGetAsAdminResourceNotOwned();
        TestResults<R> results = defaultXmlTestForGet(false, user);

        validateXmlUtf8Response(results.getMvc(), expectedStatus);
    }

    @Test
    @WithUserDetails(value = ADMIN, userDetailsServiceBeanName = "appUserDetailsService")
    public void getJsonAsAdminResourceNotOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForGetAsAdminResourceNotOwned();
        TestResults<R> results = defaultJsonTestForGet(false, user);

        validateJsonResponse(results.getMvc(), expectedStatus);
    }

    @Test
    @WithUserDetails(value = ADMIN, userDetailsServiceBeanName = "appUserDetailsService")
    public void getXmlAsAdminResourceOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForGetAsAdminResourceOwned();
        TestResults<R> results = defaultXmlTestForGet(true, user);

        validateXmlUtf8Response(results.getMvc(), expectedStatus);
    }

    @Test
    @WithUserDetails(value = ADMIN, userDetailsServiceBeanName = "appUserDetailsService")
    public void getJsonAsAdminResourceOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForGetAsAdminResourceOwned();
        TestResults<R> results = defaultJsonTestForGet(true, user);

        validateJsonResponse(results.getMvc(), expectedStatus);
    }

    @Test
    @WithUserDetails(value = ADMIN, userDetailsServiceBeanName = "appUserDetailsService")
    public void putJsonAsAdminResourceOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForPutAsAdminResourceOwned();
        TestResults<R> results = defaultJsonTestForPut(true, user);

        validateJsonResponse(results.getMvc(), expectedStatus);
    }

    @Test
    @WithUserDetails(value = ADMIN, userDetailsServiceBeanName = "appUserDetailsService")
    public void putXmlAsAdminResourceOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForPutAsAdminResourceOwned();
        TestResults<R> results = defaultXmlTestForPut(true, user);

        validateXmlUtf8Response(results.getMvc(), expectedStatus);
    }


    @Test
    @WithUserDetails(value = ADMIN, userDetailsServiceBeanName = "appUserDetailsService")
    public void putJsonAsAdminResourceNotOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForPutAsAdminResourceNotOwned();
        TestResults<R> results = defaultJsonTestForPut(false, user);

        validateJsonResponse(results.getMvc(), expectedStatus);
    }

    @Test
    @WithUserDetails(value = ADMIN, userDetailsServiceBeanName = "appUserDetailsService")
    public void putXmlAsAdminResourceNotOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForPutAsAdminResourceNotOwned();
        TestResults<R> results = defaultXmlTestForPut(false, user);

        validateXmlUtf8Response(results.getMvc(), expectedStatus);
    }

    @Test
    @WithUserDetails(value = ADMIN, userDetailsServiceBeanName = "appUserDetailsService")
    public void deleteJsonAsAdminResourceOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForDeleteAsAdminResourceOwned();
        TestResults<R> results = defaultJsonTestForDelete(true, saveUser(createUniqueContentCreator()));

        validateJsonResponse(results.getMvc(), expectedStatus);
    }

    @Test
    @WithUserDetails(value = ADMIN, userDetailsServiceBeanName = "appUserDetailsService")
    public void deleteXmlAsAdminResourceOwned() throws Exception {
        User user = saveUser(createUniqueContentCreator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsAdminResourceOwned();
        TestResults<R> results = defaultXmlTestForDelete(true, user);

        validateXmlUtf8Response(results.getMvc(), expectedStatus);
    }

    @Test
    @WithUserDetails(value = ADMIN, userDetailsServiceBeanName = "appUserDetailsService")
    public void deleteJsonAsAdminResourceNotOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForDeleteAsAdminResourceNotOwned();
        TestResults<R> results = defaultJsonTestForDelete(false, user);

        validateJsonResponse(results.getMvc(), expectedStatus);
    }

    @Test
    @WithUserDetails(value = ADMIN, userDetailsServiceBeanName = "appUserDetailsService")
    public void deleteXmlAsAdminResourceNotOwned() throws Exception {
        User user = saveUser(createDefaultAdmin());

        HttpStatus expectedStatus = expectedStatusForDeleteAsAdminResourceNotOwned();
        TestResults<R> results = defaultXmlTestForDelete(false, user);

        validateXmlUtf8Response(results.getMvc(), expectedStatus);
    }

    @Test
    @WithUserDetails(value = CONTENT_CREATOR, userDetailsServiceBeanName = "appUserDetailsService")
    public void putJsonAsContentCreatorResourceOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForPutAsContentCreatorResourceOwned();
        TestResults<R> results = defaultJsonTestForPut(true, user);

        validateJsonResponse(results.getMvc(), expectedStatus);
    }

    @Test
    @WithUserDetails(value = CONTENT_CREATOR, userDetailsServiceBeanName = "appUserDetailsService")
    public void putXmlAsContentCreatorResourceOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForPutAsContentCreatorResourceOwned();
        TestResults<R> results = defaultXmlTestForPut(true, user);

        validateXmlUtf8Response(results.getMvc(), expectedStatus);
    }


    @Test
    @WithUserDetails(value = CONTENT_CREATOR, userDetailsServiceBeanName = "appUserDetailsService")
    public void putJsonAsContentCreatorResourceNotOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForPutAsContentCreatorResourceNotOwned();
        TestResults<R> results = defaultJsonTestForPut(false, user);

        validateJsonResponse(results.getMvc(), expectedStatus);
    }

    @Test
    @WithUserDetails(value = CONTENT_CREATOR, userDetailsServiceBeanName = "appUserDetailsService")
    public void putXmlAsContentCreatorResourceNotOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForPutAsContentCreatorResourceNotOwned();
        TestResults<R> results = defaultXmlTestForPut(false, user);

        validateJsonResponse(results.getMvc(), expectedStatus);
    }

    @Test
    @WithUserDetails(value = CONTENT_CREATOR, userDetailsServiceBeanName = "appUserDetailsService")
    public void deleteJsonAsContentCreatorResourceOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsContentCreatorResourceOwned();
        TestResults<R> results = defaultJsonTestForDelete(true, user);

        validateJsonResponse(results.getMvc(), expectedStatus);
    }

    @Test
    @WithUserDetails(value = CONTENT_CREATOR, userDetailsServiceBeanName = "appUserDetailsService")
    public void deleteXmlAsContentCreatorResourceOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsContentCreatorResourceOwned();
        TestResults<R> results = defaultXmlTestForDelete(true, user);

        validateXmlUtf8Response(results.getMvc(), expectedStatus);
    }

    @Test
    @WithUserDetails(value = CONTENT_CREATOR, userDetailsServiceBeanName = "appUserDetailsService")
    public void deleteJsonAsContentCreatorResourceNotOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsContentCreatorResourceNotOwned();
        TestResults<R> results = defaultJsonTestForDelete(false, user);

        validateJsonResponse(results.getMvc(), expectedStatus);
    }

    @Test
    @WithUserDetails(value = CONTENT_CREATOR, userDetailsServiceBeanName = "appUserDetailsService")
    public void deleteXmlAsContentCreatorResourceNotOwned() throws Exception {
        User user = saveUser(createDefaultContentCreator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsContentCreatorResourceNotOwned();
        TestResults<R> results = defaultXmlTestForDelete(false, user);

        validateXmlUtf8Response(results.getMvc(), expectedStatus);
    }

    @Test
    @WithUserDetails(value = MODERATOR, userDetailsServiceBeanName = "appUserDetailsService")
    public void putJsonAsModeratorResourceOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForPutAsModeratorResourceOwned();
        TestResults<R> results = defaultJsonTestForPut(true, user);

        validateJsonResponse(results.getMvc(), expectedStatus);
    }

    @Test
    @WithUserDetails(value = MODERATOR, userDetailsServiceBeanName = "appUserDetailsService")
    public void putXmlAsModeratorResourceOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForPutAsModeratorResourceOwned();
        TestResults<R> results = defaultXmlTestForPut(true, user);

        validateXmlUtf8Response(results.getMvc(), expectedStatus);
    }


    @Test
    @WithUserDetails(value = MODERATOR, userDetailsServiceBeanName = "appUserDetailsService")
    public void putJsonAsModeratorResourceNotOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForPutAsModeratorResourceNotOwned();
        TestResults<R> results = defaultJsonTestForPut(false, user);

        validateJsonResponse(results.getMvc(), expectedStatus);
    }

    @Test
    @WithUserDetails(value = MODERATOR, userDetailsServiceBeanName = "appUserDetailsService")
    public void putXmlAsModeratorResourceNotOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForPutAsModeratorResourceNotOwned();
        TestResults<R> results = defaultXmlTestForPut(false, user);

        validateJsonResponse(results.getMvc(), expectedStatus);
    }

    @Test
    @WithUserDetails(value = MODERATOR, userDetailsServiceBeanName = "appUserDetailsService")
    public void deleteJsonAsModeratorResourceOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsModeratorResourceOwned();
        TestResults<R> results = defaultJsonTestForDelete(true, user);

        validateJsonResponse(results.getMvc(), expectedStatus);
    }

    @Test
    @WithUserDetails(value = MODERATOR, userDetailsServiceBeanName = "appUserDetailsService")
    public void deleteXmlAsModeratorResourceOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsModeratorResourceOwned();
        TestResults<R> results = defaultXmlTestForDelete(true, user);

        validateXmlUtf8Response(results.getMvc(), expectedStatus);
    }


    @Test
    @WithUserDetails(value = MODERATOR, userDetailsServiceBeanName = "appUserDetailsService")
    public void deleteJsonAsModeratorResourceNotOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsModeratorResourceNotOwned();
        TestResults<R> results = defaultJsonTestForDelete(false, user);

        validateJsonResponse(results.getMvc(), expectedStatus);
    }

    @Test
    @WithUserDetails(value = MODERATOR, userDetailsServiceBeanName = "appUserDetailsService")
    public void deleteXmlAsModeratorResourceNotOwned() throws Exception {
        User user = saveUser(createDefaultModerator());

        HttpStatus expectedStatus = expectedStatusForDeleteAsModeratorResourceNotOwned();
        TestResults<R> results = defaultXmlTestForDelete(false, user);

        validateXmlUtf8Response(results.getMvc(), expectedStatus);
    }

    @Test
    @WithUserDetails(value = USER, userDetailsServiceBeanName = "appUserDetailsService")
    public void putJsonAsUserResourceOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForPutAsUserResourceOwned();
        TestResults<R> results = defaultJsonTestForPut(true, user);

        validateJsonResponse(results.getMvc(), expectedStatus);
    }

    @Test
    @WithUserDetails(value = USER, userDetailsServiceBeanName = "appUserDetailsService")
    public void putXmlAsUserResourceOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForPutAsUserResourceOwned();
        TestResults<R> results = defaultXmlTestForPut(true, user);

        validateXmlUtf8Response(results.getMvc(), expectedStatus);
    }


    @Test
    @WithUserDetails(value = USER, userDetailsServiceBeanName = "appUserDetailsService")
    public void putJsonAsUserResourceNotOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForPutAsUserResourceNotOwned();
        TestResults<R> results = defaultJsonTestForPut(false, user);

        validateJsonResponse(results.getMvc(), expectedStatus);
    }

    @Test
    @WithUserDetails(value = USER, userDetailsServiceBeanName = "appUserDetailsService")
    public void putXmlAsUserResourceNotOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForPutAsUserResourceNotOwned();
        TestResults<R> results = defaultXmlTestForPut(false, user);

        validateJsonResponse(results.getMvc(), expectedStatus);
    }

    @Test
    @WithUserDetails(value = USER, userDetailsServiceBeanName = "appUserDetailsService")
    public void deleteJsonAsUserResourceOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForDeleteAsUserResourceOwned();
        TestResults<R> results = defaultJsonTestForDelete(true, user);

        validateJsonResponse(results.getMvc(), expectedStatus);
    }

    @Test
    @WithUserDetails(value = USER, userDetailsServiceBeanName = "appUserDetailsService")
    public void deleteXmlAsUserResourceOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForDeleteAsUserResourceOwned();
        TestResults<R> results = defaultXmlTestForDelete(true, user);

        validateXmlUtf8Response(results.getMvc(), expectedStatus);
    }


    @Test
    @WithUserDetails(value = USER, userDetailsServiceBeanName = "appUserDetailsService")
    public void deleteJsonAsUserResourceNotOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForDeleteAsUserResourceNotOwned();
        TestResults<R> results = defaultJsonTestForDelete(false, user);

        validateJsonResponse(results.getMvc(), expectedStatus);
    }

    @Test
    @WithUserDetails(value = USER, userDetailsServiceBeanName = "appUserDetailsService")
    public void deleteXmlAsUserResourceNotOwned() throws Exception {
        User user = saveUser(createDefaultUser());

        HttpStatus expectedStatus = expectedStatusForDeleteAsUserResourceNotOwned();
        TestResults<R> results = defaultXmlTestForDelete(false, user);

        validateXmlUtf8Response(results.getMvc(), expectedStatus);
    }
}

