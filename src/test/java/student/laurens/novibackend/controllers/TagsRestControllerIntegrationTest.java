package student.laurens.novibackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import student.laurens.novibackend.entities.Tag;
import student.laurens.novibackend.repositories.TagRepository;

public class TagsRestControllerIntegrationTest extends ControllerIntegrationTestBase<Tag>  {

    @Autowired
    private TagRepository repository;

    @Override
    protected String getUrlForGet() {
        return "/tags";
    }

    @Override
    protected String getUrlForGet(Tag resource) {
        return "/tags";
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
    public HttpStatus expectedStatusForPostAsContentCreator() {
        return HttpStatus.CREATED;
    }

    @Override
    public HttpStatus expectedStatusForPutAsContentCreator() {
        return HttpStatus.ACCEPTED;
    }

    @Override
    public HttpStatus expectedStatusForDeleteAsContentCreator() {
        return HttpStatus.ACCEPTED;
    }

    @Override
    public HttpStatus expectedStatusForDeleteAsContentCreatorResourceNotExists() { return HttpStatus.NOT_FOUND;}

}
