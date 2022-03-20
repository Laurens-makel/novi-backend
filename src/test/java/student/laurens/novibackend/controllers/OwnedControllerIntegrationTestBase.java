package student.laurens.novibackend.controllers;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import student.laurens.novibackend.entities.AbstractOwnedEntity;
import student.laurens.novibackend.entities.User;

public abstract class OwnedControllerIntegrationTestBase<R extends AbstractOwnedEntity> extends ControllerIntegrationTestBase<R> {


    /**
     * Implement this method by a resource specific implementation to create sample owned instances of the resource.
     *
     * @return Sample instance of resource.
     */
    abstract protected R createOwned(User owner);

    /**
     * Implement this method by a resource specific implementation to create sample not-owned instances of the resource.
     *
     * @return Sample instance of resource.
     */
    abstract protected R createNotOwned();


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

    public HttpStatus expectedStatusForPutAsAdminResourceOwned() { return HttpStatus.ACCEPTED;}
    public HttpStatus expectedStatusForPutAsAdminResourceNotOwned() { return HttpStatus.ACCEPTED;}

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

    public HttpStatus expectedStatusForDeleteAsAdminResourceOwned() { return HttpStatus.ACCEPTED;}

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

    public HttpStatus expectedStatusForDeleteAsAdminResourceNotOwned() { return HttpStatus.ACCEPTED;}

    /**
     * Override this method by a resource specific implementation to send DELETE messages with JSON as default data format.
     *
     * @return ResultActions instance which contains the response of the DELETE call.
     */
    public ResultActions defaultJsonTestForDelete(boolean isOwned, User user) throws Exception {
        return deleteAsJson(modify(save( isOwned ? createOwned(user) : createNotOwned() )));
    }

    /**
     * Override this method by a resource specific implementation to send DELETE messages with JSON as default data format.
     *
     * @return ResultActions instance which contains the response of the DELETE call.
     */
    public ResultActions defaultXmlTestForDelete(boolean isOwned, User user) throws Exception {
        return deleteAsXml(modify(save( isOwned ? createOwned(user) : createNotOwned() )));
    }

    public HttpStatus expectedStatusForPutAsContentCreatorResourceOwned() { return HttpStatus.ACCEPTED;}
    public HttpStatus expectedStatusForPutAsContentCreatorResourceNotOwned() { return HttpStatus.FORBIDDEN;}
    public HttpStatus expectedStatusForDeleteAsContentCreatorResourceOwned() {
        return HttpStatus.ACCEPTED;
    }
    public HttpStatus expectedStatusForDeleteAsContentCreatorResourceNotOwned() { return HttpStatus.FORBIDDEN;}

    public HttpStatus expectedStatusForPutAsModeratorResourceOwned() { return HttpStatus.FORBIDDEN;}
    public HttpStatus expectedStatusForPutAsModeratorResourceNotOwned() { return HttpStatus.FORBIDDEN;}
    public HttpStatus expectedStatusForDeleteAsModeratorResourceOwned() { return HttpStatus.FORBIDDEN; }
    public HttpStatus expectedStatusForDeleteAsModeratorResourceNotOwned() { return HttpStatus.FORBIDDEN;}

    public HttpStatus expectedStatusForPutAsUserResourceOwned() { return HttpStatus.FORBIDDEN;}
    public HttpStatus expectedStatusForPutAsUserResourceNotOwned() { return HttpStatus.FORBIDDEN;}
    public HttpStatus expectedStatusForDeleteAsUserResourceOwned() { return HttpStatus.FORBIDDEN; }
    public HttpStatus expectedStatusForDeleteAsUserResourceNotOwned() { return HttpStatus.FORBIDDEN;}

}

