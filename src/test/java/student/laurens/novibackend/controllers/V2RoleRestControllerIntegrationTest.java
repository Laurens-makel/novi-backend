package student.laurens.novibackend.controllers;

import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import student.laurens.novibackend.entities.Role;
import student.laurens.novibackend.repositories.RoleRepository;

import java.util.Date;

public class V2RoleRestControllerIntegrationTest extends ControllerIntegrationTestBase<Role> {

    @Override
    protected String getUrlForGet() {
        return "/roles";
    }

    @Override
    protected String getUrlForGet(Role resource) {
        return null;
    }

    @Override
    protected String getUrlForPut(Role resource) {
        Integer id = resource.getId();
        return "/roles/" + (id == null ? 9999 : id);
    }

    @Override
    protected String getUrlForPost() {
        return "/roles";
    }

    @Override
    protected String getUrlForDelete(Role resource) {
        Integer id = resource.getId();
        return "/roles/" + (id == null ? 9999 : id);
    }

    @Autowired
    private RoleRepository repository;

    @After
    public void after(){
        Role role = repository.getRoleByName("TEST_ROLE");
        if(role != null){
            repository.delete(role);
        }
    }

    @Override
    protected Role create() {
        return createRole("SAMPLE" + new Date().getTime());
    }

    private Role createRole(String rolename){
        Role role = new Role();
        role.setName(rolename);

        return role;
    }

    private Role saveRole(Role role){
        repository.save(role);

        return role;
    }

    @Override
    protected Role save(Role resource) {
        return saveRole(resource);
    }

    @Override
    protected Role modify(Role resource) {
        resource.setName("MODIFIED" + new Date().getTime());

        return resource;
    }

    @Override
    protected HttpStatus expectedStatusForGetAsUser() {
        return HttpStatus.OK;
    }
    @Override
    protected HttpStatus expectedStatusForGetAsContentCreator() {
        return HttpStatus.OK;
    }
    @Override
    protected HttpStatus expectedStatusForGetAsModerator() {
        return HttpStatus.OK;
    }
    @Override
    protected HttpStatus expectedStatusForGetAsAdmin() {
        return HttpStatus.OK;
    }


    @Override
    protected HttpStatus expectedStatusForPostAsUser() { return HttpStatus.FORBIDDEN;}
    @Override
    protected HttpStatus expectedStatusForPostAsContentCreator() {
        return HttpStatus.FORBIDDEN;
    }
    @Override
    protected HttpStatus expectedStatusForPostAsModerator() {
        return HttpStatus.FORBIDDEN;
    }
    @Override
    protected HttpStatus expectedStatusForPostAsAdmin() { return HttpStatus.CREATED;}


    @Override
    protected HttpStatus expectedStatusForPutAsAdmin() {
        return HttpStatus.ACCEPTED;
    }
    @Override
    protected HttpStatus expectedStatusForPutAsUser() {
        return HttpStatus.FORBIDDEN;
    }
    @Override
    protected HttpStatus expectedStatusForPutAsContentCreator() {
        return HttpStatus.FORBIDDEN;
    }
    @Override
    protected HttpStatus expectedStatusForPutAsModerator() {
        return HttpStatus.FORBIDDEN;
    }


    @Override
    protected HttpStatus expectedStatusForPutAsContentCreatorResourceNotExists() { return HttpStatus.FORBIDDEN;}
    @Override
    protected HttpStatus expectedStatusForPutAsAdminResourceNotExists() { return HttpStatus.FORBIDDEN;}
    @Override
    protected HttpStatus expectedStatusForPutAsUserResourceNotExists() { return HttpStatus.FORBIDDEN;}
    @Override
    protected HttpStatus expectedStatusForPutAsModeratorResourceNotExists() { return HttpStatus.FORBIDDEN;}


    @Override
    protected HttpStatus expectedStatusForDeleteAsAdmin() {
        return HttpStatus.ACCEPTED;
    }
    @Override
    protected HttpStatus expectedStatusForDeleteAsUser() { return HttpStatus.FORBIDDEN; }
    @Override
    protected HttpStatus expectedStatusForDeleteAsContentCreator() { return HttpStatus.FORBIDDEN; }
    @Override
    protected HttpStatus expectedStatusForDeleteAsModerator() {
        return HttpStatus.FORBIDDEN;
    }


    @Override
    protected HttpStatus expectedStatusForDeleteAsModeratorResourceNotExists() { return HttpStatus.FORBIDDEN;}
    @Override
    protected HttpStatus expectedStatusForDeleteAsUserResourceNotExists() { return HttpStatus.FORBIDDEN;}
    @Override
    protected HttpStatus expectedStatusForDeleteAsContentCreatorResourceNotExists() { return HttpStatus.FORBIDDEN;}
    @Override
    protected HttpStatus expectedStatusForDeleteAsAdminResourceNotExists() { return HttpStatus.NOT_FOUND;}

}
