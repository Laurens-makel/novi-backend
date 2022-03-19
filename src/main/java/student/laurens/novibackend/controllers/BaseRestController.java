package student.laurens.novibackend.controllers;

import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseRestController {
    private final String DELETED_TEXT = "deleted";

    protected Map<String, String> createDeletedMessage(){
        return createMessage(DELETED_TEXT);
    }

    protected Map<String, String> createMessage(String text){
        final Map<String, String> message = new HashMap<>();
        message.put("message", text);

        return message;
    }
}
