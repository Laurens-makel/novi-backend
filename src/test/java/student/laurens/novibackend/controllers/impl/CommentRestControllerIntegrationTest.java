package student.laurens.novibackend.controllers.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import student.laurens.novibackend.controllers.OwnedWithParentControllerIntegrationTestBase;
import student.laurens.novibackend.entities.Blogpost;
import student.laurens.novibackend.entities.Comment;
import student.laurens.novibackend.entities.User;
import student.laurens.novibackend.repositories.CommentRepository;
import student.laurens.novibackend.repositories.ResourceRepository;

public class CommentRestControllerIntegrationTest extends OwnedWithParentControllerIntegrationTestBase<Comment, Blogpost> {

    @Autowired
    private CommentRepository repository;

    @Override
    protected String getUrlForGet() {
        return "/blogposts/1/comments";
    }

    @Override
    protected String getUrlForGet(Comment resource) {
        Integer id = resource.getId();
        return "/blogposts/1/comments/" + (id == null ? 9999 : id);
    }

    @Override
    protected String getUrlForPut(Comment resource) {
        Integer id = resource.getId();
        return "/blogposts/1/comments/" + (id == null ? 9999 : id);
    }

    @Override
    protected String getUrlForPost() {
        return "/blogposts/1/comments";
    }

    @Override
    protected String getUrlForDelete(Comment resource) {
        Integer id = resource.getId();
        return "/blogposts/1/comments/" + (id == null ? 9999 : id);
    }

    @Override
    protected ResourceRepository<Comment> getRepository() {
        return repository;
    }

    @Override
    protected ResourceRepository<Blogpost> getParentRepository() {
        return blogpostRepository;
    }

    @Override
    protected Blogpost createParent() {
        return createDefaultBlogpost(saveUser(createUniqueContentCreator()));
    }

    @Override
    protected Blogpost saveParent(Blogpost parentResource) {
        return null;
    }

    @Override
    protected Blogpost modifyParent(Blogpost parentResource) {
        return null;
    }

    @Override
    protected Blogpost createOwnedParent(User owner) {
        return createDefaultBlogpost(owner);
    }

    @Override
    protected Blogpost createNotOwnedParent() {
        return createParent();
    }

    @Override
    protected Comment createOwned(User owner) {
        return create(owner, saveBlogpost(createParent()) );
    }

    @Override
    protected Comment createNotOwned() {
        return create();
    }

    @Override
    protected Comment create() {
        Blogpost post = saveBlogpost(createParent());
        User author = saveUser(createUniqueContentCreator());

        return create(author, post);
    }

    protected Comment create(User author, Blogpost post) {
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
        resource.setContent(unique("modified"));
        return resource;
    }

    @Override
    protected HttpStatus expectedStatusForGetAsAdmin() {
        return HttpStatus.OK;
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
    protected HttpStatus expectedStatusForPostAsAdmin() {
        return HttpStatus.CREATED;
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
    protected HttpStatus expectedStatusForPutAsAdmin() {
        return HttpStatus.ACCEPTED;
    }

    @Override
    public HttpStatus expectedStatusForPutAsUser() {
        return HttpStatus.FORBIDDEN;
    }

    @Override
    public HttpStatus expectedStatusForPutAsContentCreator() {
        return HttpStatus.FORBIDDEN;
    }

    @Override
    public HttpStatus expectedStatusForPutAsModerator() {
        return HttpStatus.FORBIDDEN;
    }

    @Override
    protected HttpStatus expectedStatusForPutAsContentCreatorResourceNotExists() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    protected HttpStatus expectedStatusForPutAsAdminResourceNotExists() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    protected HttpStatus expectedStatusForPutAsUserResourceNotExists() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    protected HttpStatus expectedStatusForPutAsModeratorResourceNotExists() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    protected HttpStatus expectedStatusForDeleteAsAdmin() {
        return HttpStatus.ACCEPTED;
    }

    @Override
    public HttpStatus expectedStatusForDeleteAsUser() {
        return HttpStatus.FORBIDDEN;
    }

    @Override
    public HttpStatus expectedStatusForDeleteAsContentCreator() { return HttpStatus.FORBIDDEN; }

    @Override
    public HttpStatus expectedStatusForDeleteAsModerator() {
        return HttpStatus.FORBIDDEN;
    }

    @Override
    protected HttpStatus expectedStatusForDeleteAsModeratorResourceNotExists() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    protected HttpStatus expectedStatusForDeleteAsUserResourceNotExists() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    protected HttpStatus expectedStatusForDeleteAsContentCreatorResourceNotExists() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    protected HttpStatus expectedStatusForDeleteAsAdminResourceNotExists() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    protected HttpStatus expectedStatusForGetAsAdminResourceOwned() {
        return HttpStatus.OK;
    }

    @Override
    protected HttpStatus expectedStatusForGetAsAdminResourceNotOwned() {
        return HttpStatus.OK;
    }

    @Override
    protected HttpStatus expectedStatusForGetAsModeratorResourceOwned() {
        return HttpStatus.OK;
    }

    @Override
    protected HttpStatus expectedStatusForGetAsModeratorResourceNotOwned() {
        return HttpStatus.OK;
    }

    @Override
    protected HttpStatus expectedStatusForGetAsContentCreatorResourceOwned() {
        return HttpStatus.OK;
    }

    @Override
    protected HttpStatus expectedStatusForGetAsContentCreatorResourceNotOwned() {
        return HttpStatus.OK;
    }

    @Override
    protected HttpStatus expectedStatusForGetAsUserResourceOwned() {
        return HttpStatus.OK;
    }

    @Override
    protected HttpStatus expectedStatusForGetAsUserResourceNotOwned() {
        return HttpStatus.OK;
    }

    @Override
    protected HttpStatus expectedStatusForPutAsAdminResourceOwned() {
        return HttpStatus.ACCEPTED;
    }

    @Override
    protected HttpStatus expectedStatusForPutAsAdminResourceNotOwned() {
        return HttpStatus.ACCEPTED;
    }

    @Override
    protected HttpStatus expectedStatusForDeleteAsAdminResourceOwned() {
        return HttpStatus.ACCEPTED;
    }

    @Override
    protected HttpStatus expectedStatusForDeleteAsAdminResourceNotOwned() {
        return HttpStatus.ACCEPTED;
    }

    @Override
    protected HttpStatus expectedStatusForPutAsContentCreatorResourceOwned() {
        return HttpStatus.ACCEPTED;
    }

    @Override
    protected HttpStatus expectedStatusForPutAsContentCreatorResourceNotOwned() {
        return HttpStatus.FORBIDDEN;
    }

    @Override
    protected HttpStatus expectedStatusForDeleteAsContentCreatorResourceOwned() {
        return HttpStatus.ACCEPTED;
    }

    @Override
    protected HttpStatus expectedStatusForDeleteAsContentCreatorResourceNotOwned() {
        return HttpStatus.FORBIDDEN;
    }

    @Override
    protected HttpStatus expectedStatusForPutAsModeratorResourceOwned() {
        return HttpStatus.ACCEPTED;
    }

    @Override
    protected HttpStatus expectedStatusForPutAsModeratorResourceNotOwned() {
        return HttpStatus.FORBIDDEN;
    }

    @Override
    protected HttpStatus expectedStatusForDeleteAsModeratorResourceOwned() {
        return HttpStatus.ACCEPTED;
    }

    @Override
    protected HttpStatus expectedStatusForDeleteAsModeratorResourceNotOwned() {
        return HttpStatus.FORBIDDEN;
    }

    @Override
    protected HttpStatus expectedStatusForPutAsUserResourceOwned() {
        return HttpStatus.ACCEPTED;
    }

    @Override
    protected HttpStatus expectedStatusForPutAsUserResourceNotOwned() {
        return HttpStatus.FORBIDDEN;
    }

    @Override
    protected HttpStatus expectedStatusForDeleteAsUserResourceOwned() {
        return HttpStatus.ACCEPTED;
    }

    @Override
    protected HttpStatus expectedStatusForDeleteAsUserResourceNotOwned() {
        return HttpStatus.FORBIDDEN;
    }

}
