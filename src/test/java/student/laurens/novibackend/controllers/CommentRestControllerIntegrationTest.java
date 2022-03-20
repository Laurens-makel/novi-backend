package student.laurens.novibackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import student.laurens.novibackend.entities.Blogpost;
import student.laurens.novibackend.entities.Comment;
import student.laurens.novibackend.entities.User;
import student.laurens.novibackend.repositories.CommentRepository;

public class CommentRestControllerIntegrationTest extends ControllerIntegrationTestBase<Comment> {

    @Autowired
    private CommentRepository repository;

    @Override
    protected String getUrlForGet() {
        return "/blogposts/1/comments";
    }

    @Override
    protected String getUrlForGet(Comment resource) {
        Integer id = resource.getId();
        return "/blogposts/1/" + (id == null ? 9999 : id);
    }

    @Override
    protected String getUrlForPut(Comment resource) {
        Integer id = resource.getId();
        return "/blogposts/1/" + (id == null ? 9999 : id);
    }

    @Override
    protected String getUrlForPost() {
        return "/blogposts/1/comments";
    }

    @Override
    protected String getUrlForDelete(Comment resource) {
        Integer id = resource.getId();
        return "/blogposts/1/" + (id == null ? 9999 : id);
    }

    @Override
    protected Comment create() {
        Blogpost post = saveBlogpost(createDefaultBlogpost(createDefaultContentCreator()));
        User author = saveUser(createDefaultUser());

        Comment comment = new Comment();
        comment.setTitle(unique("Title"));
        comment.setContent(unique("Content"));
        comment.setAuthor(author);
        comment.setBlogpost(post);

        return comment;
    }

    @Override
    protected Comment save(Comment resource) {
        repository.save(resource);

        return resource;
    }

    @Override
    protected Comment modify(Comment resource) {
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
    public HttpStatus expectedStatusForPostAsUser() {
        return HttpStatus.CREATED;
    }

    @Override
    public HttpStatus expectedStatusForPostAsContentCreator() {
        return HttpStatus.CREATED;
    }

    @Override
    public HttpStatus expectedStatusForPostAsModerator() {
        return HttpStatus.CREATED;
    }

    @Override
    public HttpStatus expectedStatusForPutAsUser() {
        return HttpStatus.ACCEPTED;
    }

    @Override
    public HttpStatus expectedStatusForPutAsContentCreator() {
        return HttpStatus.ACCEPTED;
    }

    @Override
    public HttpStatus expectedStatusForPutAsModerator() {
        return HttpStatus.ACCEPTED;
    }

    @Override
    public HttpStatus expectedStatusForDeleteAsUser() {
        return HttpStatus.ACCEPTED;
    }

    @Override
    public HttpStatus expectedStatusForDeleteAsContentCreator() {
        return HttpStatus.ACCEPTED;
    }

    @Override
    public HttpStatus expectedStatusForDeleteAsModerator() {
        return HttpStatus.ACCEPTED;
    }
}
