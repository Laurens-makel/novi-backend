package student.laurens.novibackend.integration.controllers.impl;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import student.laurens.novibackend.entities.dto.CommentDto;
import student.laurens.novibackend.entities.dto.mappers.CommentMapper;
import student.laurens.novibackend.integration.controllers.ChildControllerIntegrationTestBase;
import student.laurens.novibackend.entities.Blogpost;
import student.laurens.novibackend.entities.Comment;
import student.laurens.novibackend.entities.User;
import student.laurens.novibackend.repositories.CommentRepository;
import student.laurens.novibackend.repositories.ResourceRepository;

public class CommentRestControllerIntegrationTest extends ChildControllerIntegrationTestBase<Comment, Blogpost, CommentDto> {
    private final @Getter CommentMapper mapper = new CommentMapper();

    @Autowired
    private CommentRepository repository;

    @Override
    protected String getUrlForGetWithParent(Blogpost parentResource) {
        return "/blogposts/"+parentResource.getId()+"/comments";
    }

    @Override
    protected String getUrlForGetWithParent(Blogpost parentResource, Comment resource) {
        Integer parentId = parentResource.getId();
        Integer id = resource.getId();
        return "/blogposts/"+(parentId == null ? 9999 : parentId)+"/comments/" + (id == null ? 9999 : id);
    }

    @Override
    protected String getUrlForPutWithParent(Blogpost parentResource, Comment resource) {
        Integer parentId = parentResource.getId();
        Integer id = resource.getId();
        return "/blogposts/"+(parentId == null ? 9999 : parentId)+"/comments/" + (id == null ? 9999 : id);
    }

    @Override
    protected String getUrlForPostWithParent(Blogpost parentResource) {
        Integer parentId = parentResource.getId();
        return "/blogposts/"+(parentId == null ? 9999 : parentId)+"/comments";
    }

    @Override
    protected String getUrlForDeleteWithParent(Blogpost parentResource, Comment resource) {
        Integer parentId = parentResource.getId();
        Integer id = resource.getId();
        return "/blogposts/"+(parentId == null ? 9999 : parentId)+"/comments/" + (id == null ? 9999 : id);
    }

    @Override
    protected Comment createOwned(Blogpost parentResource, User owner) {
        Comment comment = new Comment();

        comment.setTitle(unique("Title"));
        comment.setContent("Comment Content");
        comment.setAuthor(owner);
        comment.setBlogpost(parentResource);

        return comment;
    }

    @Override
    protected Blogpost modifyParent(Blogpost parentResource) {
        parentResource.setContent("modified content");
        return parentResource;
    }

    @Override
    protected Blogpost createParent() {
        return createDefaultBlogpost(saveUser(createUniqueContentCreator()));
    }

    @Override
    protected Blogpost createOwnedParent(User owner) {
        return createDefaultBlogpost(owner);
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
    protected HttpStatus expectedStatusForGetAsUser() { return HttpStatus.OK; }
    @Override
    protected HttpStatus expectedStatusForGetAsContentCreator() { return HttpStatus.OK; }
    @Override
    protected HttpStatus expectedStatusForGetAsModerator() { return HttpStatus.OK; }
    @Override
    protected HttpStatus expectedStatusForGetAsAdmin() { return HttpStatus.OK;}

    @Override
    protected HttpStatus expectedStatusForGetAsUserParentNotOwnedChildNotExists(){ return HttpStatus.NOT_FOUND;}
    @Override
    protected HttpStatus expectedStatusForGetAsContentCreatorParentNotOwnedChildNotExists(){ return HttpStatus.NOT_FOUND;};
    @Override
    protected HttpStatus expectedStatusForGetAsModeratorParentNotOwnedChildNotExists(){ return HttpStatus.NOT_FOUND;};
    @Override
    protected HttpStatus expectedStatusForGetAsAdminParentNotOwnedChildNotExists(){ return HttpStatus.NOT_FOUND;};

    @Override
    protected HttpStatus expectedStatusForGetAsUserParentNotExistsChildOwned() {
        return HttpStatus.NOT_FOUND;
    }
    @Override
    protected HttpStatus expectedStatusForGetAsContentCreatorParentNotExistsChildOwned() { return HttpStatus.NOT_FOUND;  }
    @Override
    protected HttpStatus expectedStatusForGetAsModeratorParentNotExistsChildOwned() {
        return HttpStatus.NOT_FOUND;
    }
    @Override
    protected HttpStatus expectedStatusForGetAsAdminParentNotExistsChildOwned() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    protected HttpStatus expectedStatusForGetAsUserParentNotOwnedChildOwned() {
        return HttpStatus.OK;
    }
    @Override
    protected HttpStatus expectedStatusForGetAsContentCreatorParentNotOwnedChildOwned() {
        return HttpStatus.OK;
    }
    @Override
    protected HttpStatus expectedStatusForGetAsModeratorParentNotOwnedChildOwned() {
        return HttpStatus.OK;
    }
    @Override
    protected HttpStatus expectedStatusForGetAsAdminParentNotOwnedChildOwned() {
        return HttpStatus.OK;
    }


    @Override
    protected HttpStatus expectedStatusForPostAsUserParentNotExistsChildOwned() {
        return HttpStatus.NOT_FOUND;
    }
    @Override
    protected HttpStatus expectedStatusForPostAsContentCreatorParentNotExistsChildOwned() { return HttpStatus.NOT_FOUND;  }
    @Override
    protected HttpStatus expectedStatusForPostAsModeratorParentNotExistsChildOwned() {
        return HttpStatus.NOT_FOUND;
    }
    @Override
    protected HttpStatus expectedStatusForPostAsAdminParentNotExistsChildOwned() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    protected HttpStatus expectedStatusForPostAsUserParentNotOwnedChildOwned() {
        return HttpStatus.CREATED;
    }
    @Override
    protected HttpStatus expectedStatusForPostAsContentCreatorParentNotOwnedChildOwned() {
        return HttpStatus.CREATED;
    }
    @Override
    protected HttpStatus expectedStatusForPostAsModeratorParentNotOwnedChildOwned() {
        return HttpStatus.CREATED;
    }
    @Override
    protected HttpStatus expectedStatusForPostAsAdminParentNotOwnedChildOwned() {
        return HttpStatus.CREATED;
    }


    @Override
    protected HttpStatus expectedStatusForPutAsUserParentNotExistsChildOwned() {
        return HttpStatus.NOT_FOUND;
    }
    @Override
    protected HttpStatus expectedStatusForPutAsContentCreatorParentNotExistsChildOwned() { return HttpStatus.NOT_FOUND;  }
    @Override
    protected HttpStatus expectedStatusForPutAsModeratorParentNotExistsChildOwned() {
        return HttpStatus.NOT_FOUND;
    }
    @Override
    protected HttpStatus expectedStatusForPutAsAdminParentNotExistsChildOwned() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    protected HttpStatus expectedStatusForPutAsUserParentNotOwnedChildNotExists(){ return HttpStatus.NOT_FOUND;}
    @Override
    protected HttpStatus expectedStatusForPutAsContentCreatorParentNotOwnedChildNotExists(){ return HttpStatus.NOT_FOUND;};
    @Override
    protected HttpStatus expectedStatusForPutAsModeratorParentNotOwnedChildNotExists(){ return HttpStatus.NOT_FOUND;};
    @Override
    protected HttpStatus expectedStatusForPutAsAdminParentNotOwnedChildNotExists(){ return HttpStatus.NOT_FOUND;};

    @Override
    protected HttpStatus expectedStatusForPutAsUserParentNotOwnedChildOwned() {
        return HttpStatus.ACCEPTED;
    }
    @Override
    protected HttpStatus expectedStatusForPutAsContentCreatorParentNotOwnedChildOwned() {
        return HttpStatus.ACCEPTED;
    }
    @Override
    protected HttpStatus expectedStatusForPutAsModeratorParentNotOwnedChildOwned() {
        return HttpStatus.ACCEPTED;
    }
    @Override
    protected HttpStatus expectedStatusForPutAsAdminParentNotOwnedChildOwned() {
        return HttpStatus.ACCEPTED;
    }

    @Override
    protected HttpStatus expectedStatusForPutAsUserParentNotOwnedChildNotOwned() {
        return HttpStatus.FORBIDDEN;
    }
    @Override
    protected HttpStatus expectedStatusForPutAsContentCreatorParentNotOwnedChildNotOwned() {  return HttpStatus.FORBIDDEN;  }
    @Override
    protected HttpStatus expectedStatusForPutAsModeratorParentNotOwnedChildNotOwned() {
        return HttpStatus.ACCEPTED;
    }
    @Override
    protected HttpStatus expectedStatusForPutAsAdminParentNotOwnedChildNotOwned() {
        return HttpStatus.ACCEPTED;
    }

    @Override
    protected HttpStatus expectedStatusForPutAsUserParentOwnedChildNotOwned() {
        return HttpStatus.FORBIDDEN;
    }
    @Override
    protected HttpStatus expectedStatusForPutAsContentCreatorParentOwnedChildNotOwned() {
        return HttpStatus.FORBIDDEN;
    }
    @Override
    protected HttpStatus expectedStatusForPutAsModeratorParentOwnedChildNotOwned() {
        return HttpStatus.ACCEPTED;
    }
    @Override
    protected HttpStatus expectedStatusForPutAsAdminParentOwnedChildNotOwned() {
        return HttpStatus.ACCEPTED;
    }

    @Override
    protected HttpStatus expectedStatusForPutAsUserParentOwnedChildOwned() {
        return HttpStatus.ACCEPTED;
    }
    @Override
    protected HttpStatus expectedStatusForPutAsContentCreatorParentOwnedChildOwned() {
        return HttpStatus.ACCEPTED;
    }
    @Override
    protected HttpStatus expectedStatusForPutAsModeratorParentOwnedChildOwned() {
        return HttpStatus.ACCEPTED;
    }
    @Override
    protected HttpStatus expectedStatusForPutAsAdminParentOwnedChildOwned() {
        return HttpStatus.ACCEPTED;
    }



    @Override
    protected HttpStatus expectedStatusForDeleteAsUserParentNotExistsChildOwned() {
        return HttpStatus.NOT_FOUND;
    }
    @Override
    protected HttpStatus expectedStatusForDeleteAsContentCreatorParentNotExistsChildOwned() { return HttpStatus.NOT_FOUND;  }
    @Override
    protected HttpStatus expectedStatusForDeleteAsModeratorParentNotExistsChildOwned() {
        return HttpStatus.NOT_FOUND;
    }
    @Override
    protected HttpStatus expectedStatusForDeleteAsAdminParentNotExistsChildOwned() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    protected HttpStatus expectedStatusForDeleteAsUserParentNotOwnedChildNotExists(){ return HttpStatus.NOT_FOUND;}
    @Override
    protected HttpStatus expectedStatusForDeleteAsContentCreatorParentNotOwnedChildNotExists(){ return HttpStatus.NOT_FOUND;};
    @Override
    protected HttpStatus expectedStatusForDeleteAsModeratorParentNotOwnedChildNotExists(){ return HttpStatus.NOT_FOUND;};
    @Override
    protected HttpStatus expectedStatusForDeleteAsAdminParentNotOwnedChildNotExists(){ return HttpStatus.NOT_FOUND;};

    @Override
    protected HttpStatus expectedStatusForDeleteAsUserParentNotOwnedChildOwned() {
        return HttpStatus.ACCEPTED;
    }
    @Override
    protected HttpStatus expectedStatusForDeleteAsContentCreatorParentNotOwnedChildOwned() { return HttpStatus.ACCEPTED;}
    @Override
    protected HttpStatus expectedStatusForDeleteAsModeratorParentNotOwnedChildOwned() {
        return HttpStatus.ACCEPTED;
    }
    @Override
    protected HttpStatus expectedStatusForDeleteAsAdminParentNotOwnedChildOwned() {
        return HttpStatus.ACCEPTED;
    }

    @Override
    protected HttpStatus expectedStatusForDeleteAsUserParentNotOwnedChildNotOwned() {
        return HttpStatus.FORBIDDEN;
    }
    @Override
    protected HttpStatus expectedStatusForDeleteAsContentCreatorParentNotOwnedChildNotOwned() { return HttpStatus.FORBIDDEN;}
    @Override
    protected HttpStatus expectedStatusForDeleteAsModeratorParentNotOwnedChildNotOwned() {
        return HttpStatus.ACCEPTED;
    }
    @Override
    protected HttpStatus expectedStatusForDeleteAsAdminParentNotOwnedChildNotOwned() {
        return HttpStatus.ACCEPTED;
    }

    @Override
    protected HttpStatus expectedStatusForDeleteAsUserParentOwnedChildNotOwned() {
        return HttpStatus.FORBIDDEN;
    }
    @Override
    protected HttpStatus expectedStatusForDeleteAsContentCreatorParentOwnedChildNotOwned() { return HttpStatus.ACCEPTED; }
    @Override
    protected HttpStatus expectedStatusForDeleteAsModeratorParentOwnedChildNotOwned() {
        return HttpStatus.ACCEPTED;
    }
    @Override
    protected HttpStatus expectedStatusForDeleteAsAdminParentOwnedChildNotOwned() {
        return HttpStatus.ACCEPTED;
    }

    @Override
    protected HttpStatus expectedStatusForDeleteAsUserParentOwnedChildOwned() {
        return HttpStatus.ACCEPTED;
    }
    @Override
    protected HttpStatus expectedStatusForDeleteAsContentCreatorParentOwnedChildOwned() {
        return HttpStatus.ACCEPTED;
    }
    @Override
    protected HttpStatus expectedStatusForDeleteAsModeratorParentOwnedChildOwned() {
        return HttpStatus.ACCEPTED;
    }
    @Override
    protected HttpStatus expectedStatusForDeleteAsAdminParentOwnedChildOwned() {
        return HttpStatus.ACCEPTED;
    }

//    @Override
//    protected HttpStatus expectedStatusForGetAsAdmin() {
//        return HttpStatus.OK;
//    }
//    @Override
//    public HttpStatus expectedStatusForGetAsUser() {
//        return HttpStatus.OK;
//    }
//    @Override
//    public HttpStatus expectedStatusForGetAsContentCreator() {
//        return HttpStatus.OK;
//    }
//    @Override
//    protected HttpStatus expectedStatusForGetAsModerator() { return HttpStatus.OK; }
//
//    @Override
//    protected HttpStatus expectedStatusForPostAsAdmin() {
//        return HttpStatus.CREATED;
//    }
//    @Override
//    protected HttpStatus expectedStatusForPostAsUser() {
//        return HttpStatus.CREATED;
//    }
//    @Override
//    protected HttpStatus expectedStatusForPostAsContentCreator() {
//        return HttpStatus.CREATED;
//    }
//    @Override
//    protected HttpStatus expectedStatusForPostAsModerator() {
//        return HttpStatus.CREATED;
//    }
//
//    @Override
//    protected HttpStatus expectedStatusForPutAsAdmin() {
//        return HttpStatus.ACCEPTED;
//    }
//    @Override
//    protected HttpStatus expectedStatusForPutAsUser() {
//        return HttpStatus.FORBIDDEN;
//    }
//    @Override
//    protected HttpStatus expectedStatusForPutAsContentCreator() {
//        return HttpStatus.FORBIDDEN;
//    }
//    @Override
//    protected HttpStatus expectedStatusForPutAsModerator() {
//        return HttpStatus.FORBIDDEN;
//    }
//
//    @Override
//    protected HttpStatus expectedStatusForPutAsContentCreatorResourceNotExists() {
//        return HttpStatus.NOT_FOUND;
//    }
//    @Override
//    protected HttpStatus expectedStatusForPutAsAdminResourceNotExists() {
//        return HttpStatus.NOT_FOUND;
//    }
//    @Override
//    protected HttpStatus expectedStatusForPutAsUserResourceNotExists() {
//        return HttpStatus.NOT_FOUND;
//    }
//    @Override
//    protected HttpStatus expectedStatusForPutAsModeratorResourceNotExists() {
//        return HttpStatus.NOT_FOUND;
//    }
//
//    @Override
//    protected HttpStatus expectedStatusForDeleteAsAdmin() {
//        return HttpStatus.ACCEPTED;
//    }
//    @Override
//    public HttpStatus expectedStatusForDeleteAsUser() {
//        return HttpStatus.FORBIDDEN;
//    }
//    @Override
//    public HttpStatus expectedStatusForDeleteAsContentCreator() { return HttpStatus.FORBIDDEN; }
//    @Override
//    public HttpStatus expectedStatusForDeleteAsModerator() {
//        return HttpStatus.FORBIDDEN;
//    }
//
//    @Override
//    protected HttpStatus expectedStatusForDeleteAsModeratorResourceNotExists() {
//        return HttpStatus.NOT_FOUND;
//    }
//    @Override
//    protected HttpStatus expectedStatusForDeleteAsUserResourceNotExists() {
//        return HttpStatus.NOT_FOUND;
//    }
//    @Override
//    protected HttpStatus expectedStatusForDeleteAsContentCreatorResourceNotExists() {
//        return HttpStatus.NOT_FOUND;
//    }
//    @Override
//    protected HttpStatus expectedStatusForDeleteAsAdminResourceNotExists() {
//        return HttpStatus.NOT_FOUND;
//    }

//    @Override
//    protected HttpStatus expectedStatusForGetAsAdminResourceOwned() {
//        return HttpStatus.OK;
//    }
//    @Override
//    protected HttpStatus expectedStatusForGetAsAdminResourceNotOwned() {
//        return HttpStatus.OK;
//    }
//
//    @Override
//    protected HttpStatus expectedStatusForGetAsModeratorResourceOwned() {
//        return HttpStatus.OK;
//    }
//    @Override
//    protected HttpStatus expectedStatusForGetAsModeratorResourceNotOwned() {
//        return HttpStatus.OK;
//    }
//    @Override
//    protected HttpStatus expectedStatusForGetAsContentCreatorResourceOwned() {
//        return HttpStatus.OK;
//    }
//    @Override
//    protected HttpStatus expectedStatusForGetAsContentCreatorResourceNotOwned() {
//        return HttpStatus.OK;
//    }
//
//    @Override
//    protected HttpStatus expectedStatusForGetAsUserResourceOwned() {
//        return HttpStatus.OK;
//    }
//    @Override
//    protected HttpStatus expectedStatusForGetAsUserResourceNotOwned() {
//        return HttpStatus.OK;
//    }
//
//    @Override
//    protected HttpStatus expectedStatusForPutAsAdminResourceOwned() {
//        return HttpStatus.ACCEPTED;
//    }
//    @Override
//    protected HttpStatus expectedStatusForPutAsAdminResourceNotOwned() {
//        return HttpStatus.ACCEPTED;
//    }
//    @Override
//    protected HttpStatus expectedStatusForDeleteAsAdminResourceOwned() {
//        return HttpStatus.ACCEPTED;
//    }
//    @Override
//    protected HttpStatus expectedStatusForDeleteAsAdminResourceNotOwned() {
//        return HttpStatus.ACCEPTED;
//    }
//
//    @Override
//    protected HttpStatus expectedStatusForPutAsContentCreatorResourceOwned() {
//        return HttpStatus.ACCEPTED;
//    }
//    @Override
//    protected HttpStatus expectedStatusForPutAsContentCreatorResourceNotOwned() {
//        return HttpStatus.FORBIDDEN;
//    }
//    @Override
//    protected HttpStatus expectedStatusForDeleteAsContentCreatorResourceOwned() {
//        return HttpStatus.ACCEPTED;
//    }
//    @Override
//    protected HttpStatus expectedStatusForDeleteAsContentCreatorResourceNotOwned() {
//        return HttpStatus.FORBIDDEN;
//    }
//
//    @Override
//    protected HttpStatus expectedStatusForPutAsModeratorResourceOwned() {
//        return HttpStatus.ACCEPTED;
//    }
//    @Override
//    protected HttpStatus expectedStatusForPutAsModeratorResourceNotOwned() {
//        return HttpStatus.FORBIDDEN;
//    }
//    @Override
//    protected HttpStatus expectedStatusForDeleteAsModeratorResourceOwned() {
//        return HttpStatus.ACCEPTED;
//    }
//    @Override
//    protected HttpStatus expectedStatusForDeleteAsModeratorResourceNotOwned() {
//        return HttpStatus.FORBIDDEN;
//    }
//
//    @Override
//    protected HttpStatus expectedStatusForPutAsUserResourceOwned() {
//        return HttpStatus.ACCEPTED;
//    }
//    @Override
//    protected HttpStatus expectedStatusForPutAsUserResourceNotOwned() {
//        return HttpStatus.FORBIDDEN;
//    }
//    @Override
//    protected HttpStatus expectedStatusForDeleteAsUserResourceOwned() {
//        return HttpStatus.ACCEPTED;
//    }
//    @Override
//    protected HttpStatus expectedStatusForDeleteAsUserResourceNotOwned() {
//        return HttpStatus.FORBIDDEN;
//    }

}
