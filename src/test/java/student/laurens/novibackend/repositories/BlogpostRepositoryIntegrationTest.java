package student.laurens.novibackend.repositories;

import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import student.laurens.novibackend.entities.Blogpost;
import student.laurens.novibackend.entities.User;

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
