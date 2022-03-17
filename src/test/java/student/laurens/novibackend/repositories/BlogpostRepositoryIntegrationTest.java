package student.laurens.novibackend.repositories;

import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import student.laurens.novibackend.entities.Blogpost;

import static org.assertj.core.api.Assertions.assertThat;

public class BlogpostRepositoryIntegrationTest extends RepositoryIntegrationTestBase {

    @Autowired
    private BlogpostRepository repository;

    @After
    public void after(){
        repository.deleteAll();
    }

    @Test
    public void whenFindByTitlte_thenReturnBlogPost() {
        // given
        Blogpost blogpost = createBlogpost("Example", "Example content");

        // when
        Blogpost found = repository.findByTitle(blogpost.getTitle());

        // then
        assertThat(found.getId()).isEqualTo(blogpost.getId());
    }


    @Test
    public void whenAddBlogpost_thenReturnBlogpost() {
        // given
        Blogpost blogpost = new Blogpost();

        blogpost.setTitle("Example");
        blogpost.setContent("content");
        blogpost.setPublished(true);
        blogpost.setAuthor(createTestUser("Bob", "Marley", "MARLEY", "MyPassword123"));

        // when
        repository.save(blogpost);
        Blogpost found = entityManager.find(Blogpost.class, blogpost.getId());

        // then
        assertThat(found.getTitle()).isEqualTo(blogpost.getTitle());
    }

    private Blogpost createBlogpost(String title, String content){
        Blogpost blogpost = new Blogpost();

        blogpost.setTitle(title);
        blogpost.setContent(content);
        blogpost.setPublished(true);
        blogpost.setAuthor(createTestUser("Bob", "Marley", "MARLEY", "MyPassword123"));

        entityManager.persistAndFlush(blogpost);

        return blogpost;
    }

}
