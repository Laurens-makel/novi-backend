package student.laurens.novibackend.controllers;

import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import student.laurens.novibackend.entities.Blogpost;
import student.laurens.novibackend.entities.User;
import student.laurens.novibackend.repositories.BlogpostRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BlogpostRestControllerIntegrationTest extends ControllerIntegrationTestBase {

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

    private Blogpost createDefaultBlogpost(User author){
        Blogpost blogpost = new Blogpost();

        blogpost.setTitle("Example blogpost");
        blogpost.setContent("Lorem ipsum");
        blogpost.setAuthor(author);

        return blogpost;
    }

    private Blogpost saveBlogpost(Blogpost blogpost){
        repository.save(blogpost);

        return blogpost;
    }
}
