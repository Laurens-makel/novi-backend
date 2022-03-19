package student.laurens.novibackend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseRestController {
    private final String DELETED_TEXT = "deleted";

    protected ResponseEntity deletedResponse(){
        return new ResponseEntity(createDeletedMessage(), HttpStatus.ACCEPTED);
    }

    protected Map<String, String> createDeletedMessage(){
        return createMessage(DELETED_TEXT);
    }

    protected Map<String, String> createMessage(String text){
        final Map<String, String> message = new HashMap<>();
        message.put("message", text);

        return message;
    }
}
