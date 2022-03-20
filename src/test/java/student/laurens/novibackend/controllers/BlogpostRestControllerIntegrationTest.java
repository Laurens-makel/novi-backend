package student.laurens.novibackend.controllers;

import org.junit.After;
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
public class BlogpostRestControllerIntegrationTest extends ControllerIntegrationTestBase<Blogpost> {

    @Autowired
    private BlogpostRepository repository;

    @After
    public void after(){
        repository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void getBlogposts_isUnauthorized() throws Exception {
        // given
        saveBlogpost(createDefaultBlogpost(createDefaultAdmin()));

        // when
       getBlogposts()

        // then
        .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = USER, roles = {USER_ROLE} )
    public void getBlogposts_AsUser_Ok() throws Exception {
        // given
        saveBlogpost(createDefaultBlogpost(createDefaultAdmin()));

        // when
        getBlogposts()

        // then
        .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = ADMIN_ROLE, roles = {ADMIN_ROLE} )
    public void getBlogposts_AsAdmin_Ok() throws Exception {
        // given
        saveBlogpost(createDefaultBlogpost(createDefaultAdmin()));

        // when
        getBlogposts()

        // then
        .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void getBlogposts_AsContentCreator_Ok() throws Exception {
        // given
        saveBlogpost(createDefaultBlogpost(createDefaultAdmin()));

        // when
        getBlogposts()

        // then
        .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void getBlogposts_AsModerator_Ok() throws Exception {
        // given
        saveBlogpost(createDefaultBlogpost(createDefaultContentCreator()));

        // when
        getBlogposts()

        // then
        .andExpect(status().isOk());
    }

    @Test
    public void postBlogpost_isUnauthorized() throws Exception {
        // given
        saveBlogpost(createDefaultBlogpost(createDefaultAdmin()));

        // when
        postBlogpost()

        // then
        .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = USER, roles = {USER_ROLE} )
    public void postBlogpost_AsUser_Forbidden() throws Exception {
        // given
        saveBlogpost(createDefaultBlogpost(createDefaultAdmin()));

        // when
        postBlogpost()

        // then
        .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void postBlogpost_AsModer_Forbidden() throws Exception {
        // given
        saveBlogpost(createDefaultBlogpost(createDefaultAdmin()));

        // when
        postBlogpost()

                // then
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = ADMIN_ROLE, roles = {ADMIN_ROLE} )
    public void postBlogpost_AsAdmin_Ok() throws Exception {
        // given
        saveBlogpost(createDefaultBlogpost(createDefaultAdmin()));

        // when
        postBlogpost()

        // then
        .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void postBlogpost_AsContentCreator_Ok() throws Exception {
        // given
        saveBlogpost(createDefaultBlogpost(createDefaultAdmin()));

        // when
        postBlogpost()

        // then
        .andExpect(status().isCreated());
    }

    @Test
    public void updateBlogpost_isUnauthorized() throws Exception {
        // given
        Blogpost post = saveBlogpost(createDefaultBlogpost(createDefaultAdmin()));
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
        Blogpost post = saveBlogpost(createDefaultBlogpost(createDefaultAdmin()));
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
        Blogpost post = saveBlogpost(createDefaultBlogpost(createDefaultAdmin()));
        post.setTitle("UPDATED");

        // when
        updateBlogpost(post)

        // then
        .andExpect(status().isAccepted());
    }

    @Test
    @WithMockUser(value = CONTENT_CREATOR, roles = {CONTENT_CREATOR_ROLE} )
    public void updateBlogpost_AsContentCreator_Ok() throws Exception {
        // given
        Blogpost post = saveBlogpost(createDefaultBlogpost(createDefaultAdmin()));
        post.setTitle("UPDATED");

        // when
        updateBlogpost(post)

        // then
        .andExpect(status().isAccepted());
    }

    @Test
    @WithMockUser(value = MODERATOR, roles = {MODERATOR_ROLE} )
    public void updateBlogpost_AsModerator_Forbidden() throws Exception {
        // given
        Blogpost post = saveBlogpost(createDefaultBlogpost(createDefaultAdmin()));
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

    private ResultActions postBlogpost() throws Exception {
        return mvc.perform(post("/blogposts")
            .content(asJsonString(createDefaultBlogpost(createDefaultContentCreator())))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON));
    }

    private ResultActions updateBlogpost(Blogpost blogpost) throws Exception {
        return mvc.perform(put("/blogposts/" + blogpost.getId() )
                .content(asJsonString(blogpost))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
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
    protected Blogpost create() {
        return createDefaultBlogpost(createDefaultContentCreator());
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
    public HttpStatus expectedStatusForGetAsUser() {
        return HttpStatus.OK;
    }

    @Override
    public HttpStatus expectedStatusForGetAsContentCreator() {
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
    public HttpStatus expectedStatusForGetAsModerator() {
        return HttpStatus.OK;
    }
}
