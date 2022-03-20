package student.laurens.novibackend.controllers;

import org.springframework.http.HttpStatus;
import student.laurens.novibackend.entities.AbstractOwnedEntity;

public abstract class OwnedControllerIntegrationTestBase<R extends AbstractOwnedEntity> extends ControllerIntegrationTestBase<R> {

    public HttpStatus expectedStatusForPutAsAdminResourceOwned() { return HttpStatus.FORBIDDEN;}
    public HttpStatus expectedStatusForPutAsAdminResourceNotOwned() { return HttpStatus.FORBIDDEN;}
    public HttpStatus expectedStatusForDeleteAsAdminResourceOwned() { return HttpStatus.FORBIDDEN;}
    public HttpStatus expectedStatusForDeleteAsAdminResourceNotOwned() { return HttpStatus.FORBIDDEN;}

    public HttpStatus expectedStatusForPutAsContentCreatorResourceOwned() { return HttpStatus.FORBIDDEN;}
    public HttpStatus expectedStatusForPutAsContentCreatorResourceNotOwned() { return HttpStatus.FORBIDDEN;}
    public HttpStatus expectedStatusForDeleteAsContentCreatorResourceOwned() {
        return HttpStatus.FORBIDDEN;
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

