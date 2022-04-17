package student.laurens.novibackend.integration.controllers;

import lombok.Getter;
import org.springframework.test.web.servlet.ResultActions;
import student.laurens.novibackend.entities.AbstractEntity;

public class TestResults<R extends AbstractEntity> {
    protected final @Getter R resource;
    protected final @Getter ResultActions mvc;

    public TestResults(final ResultActions mvc, final R resource){
        this.mvc = mvc;
        this.resource = resource;
    }
}
