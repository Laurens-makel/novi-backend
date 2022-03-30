package student.laurens.novibackend.unit.services.impl;

import lombok.Getter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import student.laurens.novibackend.entities.Blogpost;
import student.laurens.novibackend.entities.User;
import student.laurens.novibackend.repositories.BlogpostRepository;
import student.laurens.novibackend.services.BlogpostService;
import student.laurens.novibackend.services.PermissionPolicy;
import student.laurens.novibackend.unit.services.ParentServiceUnitTestBase;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BlogpostServiceUnitTest extends ParentServiceUnitTestBase<Blogpost> {

    @Override
    protected PermissionPolicy get_expected_is_read_on_child_permitted_admin() {
        return PermissionPolicy.ALLOW;
    }

    @Override
    protected PermissionPolicy get_expected_is_create_on_child_permitted_admin() {
        return PermissionPolicy.ALLOW;
    }

    @Override
    protected PermissionPolicy get_expected_is_update_on_child_permitted_admin() {
        return PermissionPolicy.ALLOW;
    }

    @Override
    protected PermissionPolicy get_expected_is_delete_on_child_permitted_admin() {
        return PermissionPolicy.ALLOW;
    }

    @TestConfiguration
    static class BlogpostServiceTestContextConfiguration {
        @Bean
        @Autowired
        public BlogpostService service(BlogpostRepository repository) {
            return new BlogpostService(repository);
        }
    }

    @Autowired
    private @Getter BlogpostService service;

    @MockBean
    private @Getter BlogpostRepository repository;

    @Override
    protected Blogpost create() {
        return createBlogpost("test", "content");
    }
    @Override
    protected Blogpost create(User owner) {
        Blogpost blog = createBlogpost("test", "content");
        blog.setAuthor(owner);
        return blog;
    }

    @Before
    public void setup(){
        Blogpost example = createBlogpost("Example", "Lorem ipsum");
        Blogpost example2 = createBlogpost("Example", "Lorem ipsum");

        List<Blogpost> blogposts = Arrays.asList(example, example2);

        Mockito.when(repository.findAll()).thenReturn(blogposts);
        Mockito.when(repository.findByTitle(example.getTitle())).thenReturn(example);
        Mockito.when(repository.findByTitle(example2.getTitle())).thenReturn(example2);
    }

    @Test
    public void whenValidBlogpostTitle_thenBlogpostShouldBeFound() {
        String title = "Example";

        Blogpost found = service.getResource(title);

        assertThat(found.getTitle()).isEqualTo(title);
        verifyFindByTitleIsCalledOnce(title);
    }

    @Test
    public void whenAddingUser_ThenRepositorySaveUserIsCalled() {
        Blogpost post = createBlogpost("Example", "content");

        service.createResource(post);
        verifySaveIsCalledOnce(post);
    }

    private void verifyFindByTitleIsCalledOnce(String title) {
        Mockito.verify(repository, VerificationModeFactory.times(1)).findByTitle(title);
        Mockito.reset(repository);
    }

    private Blogpost createBlogpost(String title, String content) {
        Blogpost blogpost = new Blogpost();

        blogpost.setTitle(title);
        blogpost.setContent(content);
        blogpost.setPublished(true);
        blogpost.setAuthor(createUniqueContentCreator());
        blogpost.setId(generateId());

        return blogpost;
    }

}
