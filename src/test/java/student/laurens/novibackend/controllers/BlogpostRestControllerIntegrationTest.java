package student.laurens.novibackend.controllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import student.laurens.novibackend.entities.Blogpost;
import student.laurens.novibackend.entities.User;
import student.laurens.novibackend.repositories.BlogpostRepository;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class to test allowed actions on {@link BlogpostRestController}.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
public class BlogpostRestControllerIntegrationTest extends OwnedControllerIntegrationTestBase<Blogpost> {

    @Autowired
    private BlogpostRepository repository;

    @After
    public void after(){
        repository.deleteAll();
        userRepository.deleteAll();
    }

    @Before
    public void before(){
        repository.deleteAll();
        userRepository.deleteAll();
    }

    @Override
    protected String getUrlForGet() {
        return "/blogposts";
    }

    @Override
    protected String getUrlForGet(Blogpost resource) {
        Integer id = resource.getId();
        return "/blogposts/" + (id == null ? 9999 : id);
    }

    @Override
    protected String getUrlForPut(Blogpost resource) {
        Integer id = resource.getId();
        return "/blogposts/" + (id == null ? 9999 : id);
    }

    @Override
    protected String getUrlForPost() {
        return "/blogposts";
    }

    @Override
    protected String getUrlForDelete(Blogpost resource) {
        Integer id = resource.getId();
        return "/blogposts/" + (id == null ? 9999 : id);
    }

    @Override
    protected Blogpost createOwned(User owner) {
        return createDefaultBlogpost(owner);
    }

    @Override
    protected Blogpost createNotOwned() {
        return createDefaultBlogpost(saveUser(createUniqueContentCreator()));
    }

    @Override
    protected Blogpost create() {
        return createDefaultBlogpost(saveUser(createUniqueContentCreator()));
    }

    @Override
    protected Blogpost save(Blogpost resource) {
        saveBlogpost(resource);

        return resource;
    }

    @Override
    protected Blogpost modify(Blogpost resource) {
        resource.setTitle("MODIFIED"+ new Date().getTime());
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
    protected HttpStatus expectedStatusForGetAsAdminResourceOwned() { return HttpStatus.OK; }
    @Override
    protected HttpStatus expectedStatusForGetAsAdminResourceNotOwned() { return HttpStatus.OK; }
    @Override
    protected HttpStatus expectedStatusForGetAsModeratorResourceOwned() { return HttpStatus.OK; }
    @Override
    protected HttpStatus expectedStatusForGetAsModeratorResourceNotOwned() { return HttpStatus.OK; }
    @Override
    protected HttpStatus expectedStatusForGetAsContentCreatorResourceOwned() { return HttpStatus.OK; }
    @Override
    protected HttpStatus expectedStatusForGetAsContentCreatorResourceNotOwned() { return HttpStatus.OK; }
    @Override
    protected HttpStatus expectedStatusForGetAsUserResourceOwned() { return HttpStatus.OK; }
    @Override
    protected HttpStatus expectedStatusForGetAsUserResourceNotOwned() { return HttpStatus.OK; }

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
    protected HttpStatus expectedStatusForDeleteAsContentCreatorResourceNotExists() { return HttpStatus.NOT_FOUND;}
    @Override
    protected HttpStatus expectedStatusForDeleteAsAdminResourceNotExists() { return HttpStatus.NOT_FOUND;}


    @Override
    protected HttpStatus expectedStatusForPutAsAdminResourceOwned() { return HttpStatus.ACCEPTED;}
    @Override
    protected HttpStatus expectedStatusForPutAsAdminResourceNotOwned() { return HttpStatus.ACCEPTED;}
    @Override
    protected HttpStatus expectedStatusForDeleteAsAdminResourceOwned() { return HttpStatus.ACCEPTED;}
    @Override
    protected HttpStatus expectedStatusForDeleteAsAdminResourceNotOwned() { return HttpStatus.ACCEPTED;}

    @Override
    protected HttpStatus expectedStatusForPutAsContentCreatorResourceOwned() { return HttpStatus.ACCEPTED;}
    @Override
    protected HttpStatus expectedStatusForPutAsContentCreatorResourceNotOwned() { return HttpStatus.FORBIDDEN;}
    @Override
    protected HttpStatus expectedStatusForDeleteAsContentCreatorResourceOwned() {
        return HttpStatus.ACCEPTED;
    }
    @Override
    protected HttpStatus expectedStatusForDeleteAsContentCreatorResourceNotOwned() { return HttpStatus.FORBIDDEN;}

    @Override
    protected HttpStatus expectedStatusForPutAsModeratorResourceOwned() { return HttpStatus.FORBIDDEN;}
    @Override
    protected HttpStatus expectedStatusForPutAsModeratorResourceNotOwned() { return HttpStatus.FORBIDDEN;}
    @Override
    protected HttpStatus expectedStatusForDeleteAsModeratorResourceOwned() { return HttpStatus.FORBIDDEN; }
    @Override
    protected HttpStatus expectedStatusForDeleteAsModeratorResourceNotOwned() { return HttpStatus.FORBIDDEN;}

    @Override
    protected HttpStatus expectedStatusForPutAsUserResourceOwned() { return HttpStatus.FORBIDDEN;}
    @Override
    protected HttpStatus expectedStatusForPutAsUserResourceNotOwned() { return HttpStatus.FORBIDDEN;}
    @Override
    protected HttpStatus expectedStatusForDeleteAsUserResourceOwned() { return HttpStatus.FORBIDDEN; }
    @Override
    protected HttpStatus expectedStatusForDeleteAsUserResourceNotOwned() { return HttpStatus.FORBIDDEN;}

    @Test
    public void getBlogposts_isUnauthorized() throws Exception {
        // given
        saveBlogpost(createDefaultBlogpost(saveUser(createDefaultAdmin())));

        // when
       getBlogposts()

        // then
        .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = USER, roles = {USER_ROLE} )
    public void getBlogposts_AsUser_Ok() throws Exception {
        // given
        saveBlogpost(createDefaultBlogpost(saveUser(createDefaultAdmin())));

        // when
        getBlogposts()

        // then
        .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = ADMIN_ROLE, roles = {ADMIN_ROLE} )
    public void getBlogposts_AsAdmin_Ok() throws Exception {
        // given
        saveBlogpost(createDefaultBlogpost(saveUser(createDefaultAdmin())));

        // when
        getBlogposts()

        // then
        .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void getBlogposts_AsContentCreator_Ok() throws Exception {
        // given
        saveBlogpost(createDefaultBlogpost(saveUser(createDefaultAdmin())));

        // when
        getBlogposts()

        // then
        .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void getBlogposts_AsModerator_Ok() throws Exception {
        // given
        saveBlogpost(createDefaultBlogpost(saveUser(createDefaultContentCreator())));

        // when
        getBlogposts()

        // then
        .andExpect(status().isOk());
    }

    @Test
    public void postBlogpost_isUnauthorized() throws Exception {
        // given
        saveBlogpost(createDefaultBlogpost(saveUser(createDefaultAdmin())));

        // when
        postBlogpost(saveUser(createDefaultUser()))

        // then
        .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = USER, roles = {USER_ROLE} )
    public void postBlogpost_AsUser_Forbidden() throws Exception {
        // when
        postBlogpost(saveUser(createDefaultUser()))

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void postBlogpost_AsModerator_Forbidden() throws Exception {
        // when
        postBlogpost(saveUser(createDefaultModerator()))

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = ADMIN_ROLE, roles = {ADMIN_ROLE} )
    public void postBlogpost_AsAdmin_Ok() throws Exception {
        // when
        postBlogpost(saveUser(createDefaultAdmin()))

        // then
        .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void postBlogpost_AsContentCreator_Ok() throws Exception {
        // when
        postBlogpost(saveUser(createUniqueContentCreator()))

        // then
        .andExpect(status().isCreated());
    }

    @Test
    public void updateBlogpost_isUnauthorized() throws Exception {
        // given
        Blogpost post = saveBlogpost(createDefaultBlogpost(saveUser(createDefaultAdmin())));
        post.setTitle("UPDATED");

        // when
        updateBlogpost(post)

        // then
        .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = USER, roles = {USER_ROLE} )
    public void updateBlogpost_AsUser_Forbidden() throws Exception {
        // given
        Blogpost post = saveBlogpost(createDefaultBlogpost(saveUser(createDefaultAdmin())));
        post.setTitle("UPDATED");

        // when
        updateBlogpost(post)

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = ADMIN, roles = {ADMIN_ROLE} )
    public void updateBlogpost_AsAdmin_Ok() throws Exception {
        // given
        Blogpost post = saveBlogpost(createDefaultBlogpost(saveUser(createDefaultAdmin())));
        post.setTitle("UPDATED");

        // when
        updateBlogpost(post)

        // then
        .andExpect(status().isAccepted());
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void updateBlogpost_AsContentCreator_OwnResource_Ok() throws Exception {
        // given
        User user = saveUser(createDefaultContentCreator());
        Blogpost post = saveBlogpost(createDefaultBlogpost(user));
        post.setTitle("UPDATED");

        // when
        updateBlogpost(post)

        // then
        .andExpect(status().isAccepted());
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void updateBlogpost_AsContentCreator_OtherResource_Forbidden() throws Exception {
        // given
        saveUser(createDefaultContentCreator());
        Blogpost post = saveBlogpost(createDefaultBlogpost(saveUser(createDefaultAdmin())));
        post.setTitle("UPDATED");

        // when
        updateBlogpost(post)

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void updateBlogpost_AsModerator_Forbidden() throws Exception {
        // given
        Blogpost post = saveBlogpost(createDefaultBlogpost(saveUser(createDefaultAdmin())));
        post.setTitle("UPDATED");

        // when
        updateBlogpost(post)

        // then
        .andExpect(status().isForbidden());
    }

    private ResultActions getBlogposts() throws Exception {
        return mvc.perform(get("/blogposts")
                .contentType(MediaType.APPLICATION_JSON));
    }

    private ResultActions postBlogpost(User author) throws Exception {
        return mvc.perform(post("/blogposts")
            .content(asJsonString(createDefaultBlogpost(author)))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON));
    }

    private ResultActions updateBlogpost(Blogpost blogpost) throws Exception {
        return mvc.perform(put("/blogposts/" + blogpost.getId() )
                .content(asJsonString(blogpost))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    }

}
