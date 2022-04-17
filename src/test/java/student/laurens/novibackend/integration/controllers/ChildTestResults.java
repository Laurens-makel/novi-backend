package student.laurens.novibackend.integration.controllers;

import lombok.Getter;
import org.springframework.test.web.servlet.ResultActions;
import student.laurens.novibackend.entities.AbstractEntity;

public class ChildTestResults<R extends AbstractEntity, P extends AbstractEntity> extends TestResults<R> {
    protected final @Getter P parent;

    public ChildTestResults(final ResultActions mvc, final R resource, final P parent){
        super(mvc, resource);
        this.parent = parent;
    }
}
