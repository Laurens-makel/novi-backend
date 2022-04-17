package student.laurens.novibackend.integration.controllers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import student.laurens.novibackend.integration.controllers.ControllerIntegrationTestBase;
import student.laurens.novibackend.entities.Tag;
import student.laurens.novibackend.repositories.TagRepository;

public class TagsRestControllerIntegrationTest extends ControllerIntegrationTestBase<Tag> {

    @Autowired
    private TagRepository repository;

    @Override
    protected String getUrlForGet() {
        return "/tags";
    }

    @Override
    protected String getUrlForGet(Tag resource) {
        Integer id = resource.getId();
        return "/tags/" + (id == null ? 9999 : id);
    }

    @Override
    protected String getUrlForPut(Tag resource) {
        Integer id = resource.getId();
        return "/tags/" + (id == null ? 9999 : id);
    }

    @Override
    protected String getUrlForPost() {
        return "/tags";
    }

    @Override
    protected String getUrlForDelete(Tag resource) {
        Integer id = resource.getId();
        return "/tags/" + (id == null ? 9999 : id);
    }

    @Override
    protected Tag create() {
        Tag tag = new Tag();
        tag.setTitle(unique("Example"));

        return tag;
    }

    @Override
    protected Tag save(Tag resource) {
        repository.save(resource);
        return resource;
    }

    @Override
    protected Tag modify(Tag resource) {
        resource.setTitle(unique("Modified"));
        return resource;
    }

    @Override
    public HttpStatus expectedStatusForGetAsUser() {
        return HttpStatus.OK;
    }
    @Override
    public HttpStatus expectedStatusForGetAsContentCreator() {
        return HttpStatus.OK;
    }
    @Override
    public HttpStatus expectedStatusForGetAsModerator() {
        return HttpStatus.OK;
    }
    @Override
    public HttpStatus expectedStatusForGetAsAdmin() {
        return HttpStatus.OK;
    }


    @Override
    protected HttpStatus expectedStatusForPostAsUser() { return HttpStatus.FORBIDDEN;}
    @Override
    protected HttpStatus expectedStatusForPostAsContentCreator() {
        return HttpStatus.CREATED;
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
        return HttpStatus.ACCEPTED;
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
    protected HttpStatus expectedStatusForDeleteAsContentCreator() { return HttpStatus.ACCEPTED; }
    @Override
    protected HttpStatus expectedStatusForDeleteAsModerator() {
        return HttpStatus.FORBIDDEN;
    }


    @Override
    protected HttpStatus expectedStatusForDeleteAsModeratorResourceNotExists() { return HttpStatus.FORBIDDEN;}
    @Override
    protected HttpStatus expectedStatusForDeleteAsUserResourceNotExists() { return HttpStatus.FORBIDDEN;}
    @Override
    protected HttpStatus expectedStatusForDeleteAsContentCreatorResourceNotExists() { return HttpStatus.NOT_FOUND;}
    @Override
    protected HttpStatus expectedStatusForDeleteAsAdminResourceNotExists() { return HttpStatus.NOT_FOUND;}


}
