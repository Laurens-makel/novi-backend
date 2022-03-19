package student.laurens.novibackend.exceptions;

import student.laurens.novibackend.entities.Blogpost;

public class BlogpostNotFoundException extends ResourceNotFoundException {

    public BlogpostNotFoundException(final Integer identifier) {
        super(Blogpost.class, identifier);
    }

}
