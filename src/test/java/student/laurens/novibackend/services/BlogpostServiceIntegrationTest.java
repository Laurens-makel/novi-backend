package student.laurens.novibackend.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import student.laurens.novibackend.entities.Blogpost;
import student.laurens.novibackend.repositories.BlogpostRepository;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BlogpostServiceIntegrationTest extends ServiceIntegrationTestBase {

    @TestConfiguration
    static class BlogpostServiceeTestContextConfiguration {
        @Bean
        public BlogpostService service() {
            return new BlogpostService();
        }
    }

    @Autowired
    private BlogpostService service;

    @MockBean
    private BlogpostRepository repository;

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

        Blogpost found = service.findByTitle(title);

        assertThat(found.getTitle()).isEqualTo(title);
        verifyFindByTitleIsCalledOnce(title);
    }

    @Test
    public void whenAddingUser_ThenRepositorySaveUserIsCalled() {
        Blogpost post = createBlogpost("Example", "content");

        service.addBlogpost(post);
        verifySaveIsCalledOnce(post);
    }

    private void verifySaveIsCalledOnce(Blogpost post) {
        Mockito.verify(repository, VerificationModeFactory.times(1)).save(post);
        Mockito.reset(repository);
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
        blogpost.setAuthor(createTestUser("Bob", "Marley", "MARLEY", "MyPassword123"));

        return blogpost;
    }

}
