package student.laurens.novibackend.unit.services.impl;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import student.laurens.novibackend.entities.Tag;
import student.laurens.novibackend.repositories.TagRepository;
import student.laurens.novibackend.unit.services.ServiceUnitTestBase;
import student.laurens.novibackend.services.TagService;

public class TagsServiceUnitTest extends ServiceUnitTestBase<Tag> {

    @TestConfiguration
    static class TagServiceTestContextConfiguration {
        @Bean
        public TagService service() {
            return new TagService();
        }
    }

    @Autowired
    private @Getter TagService service;

    @MockBean
    private @Getter TagRepository repository;

    @Override
    protected Tag create() {
        Tag tag = new Tag();
        tag.setTitle("Example tag");
        return tag;
    }
}
