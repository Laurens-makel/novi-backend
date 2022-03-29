package student.laurens.novibackend.services.impl;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import student.laurens.novibackend.entities.Blogpost;
import student.laurens.novibackend.entities.Comment;
import student.laurens.novibackend.entities.User;
import student.laurens.novibackend.repositories.CommentRepository;
import student.laurens.novibackend.services.BlogpostService;
import student.laurens.novibackend.services.ChildServiceIntegrationTestBase;
import student.laurens.novibackend.services.CommentService;

public class CommentServiceIntegrationTest extends ChildServiceIntegrationTestBase<Comment, Blogpost> {

    @TestConfiguration
    static class CommentServiceTestContextConfiguration {
        @Bean
        public CommentService service() {
            return new CommentService();
        }
    }

    @Autowired
    private @Getter CommentService service;

    @MockBean
    private @Getter BlogpostService parentService;

    @MockBean
    private @Getter CommentRepository repository;

    @Override
    protected Blogpost createParent() {
        return createBlogpost();
    }

    @Override
    protected Blogpost createOwnedParent(User owner) {
        Blogpost blogpost = createBlogpost();
        blogpost.setAuthor(owner);
        return blogpost;
    }

    @Override
    protected Comment createOwned(Blogpost parentResource, User owner) {
        Comment comment = create();
        comment.setAuthor(owner);
        comment.setBlogpost(parentResource);

        return comment;
    }

    @Override
    protected Comment create() {
        Comment comment = new Comment();
        comment.setId(generateId());
        comment.setTitle("Example comment");
        comment.setContent("Example content");

        return comment;
    }

    private Blogpost createBlogpost() {
        Blogpost blogpost = new Blogpost();

        blogpost.setTitle(unique("title"));
        blogpost.setContent(unique("content"));
        blogpost.setPublished(true);
        blogpost.setId(generateId());

        return blogpost;
    }
}
