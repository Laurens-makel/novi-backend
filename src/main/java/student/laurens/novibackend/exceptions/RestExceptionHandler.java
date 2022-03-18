package student.laurens.novibackend.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    final private String DEFAULT_CONTENT_TYPE = MediaType.APPLICATION_JSON_VALUE;

    @ExceptionHandler({ ResourceNotFoundException.class })
    protected ResponseEntity<Object> handleNotFound(final ResourceNotFoundException ex, final WebRequest request) {
        final String accept = parseAcceptHeader(request);

        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.CONTENT_TYPE, accept);

        try {
            final String response = translateError(createError(ex, request), accept);

            return handleExceptionInternal(ex, response, responseHeaders, HttpStatus.NOT_FOUND, request);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return handleExceptionInternal(ex, ex.getMessage(), responseHeaders, HttpStatus.NOT_FOUND, request);
    }

    private Map<String, String> createError(final ResourceNotFoundException exception, final WebRequest request) {
        final Map<String, String> error = new HashMap<>();

        error.put("exception", exception.getClass().getName().split("[.]")[4]);
        error.put("message", exception.getMessage());
        error.put("path", request.getDescription(false).split("=")[1]);
        error.put("resource", exception.getResourceClassName().split("[.]")[4]);

        return error;
    }

    private String translateError(final Map<String, String> error, String accept) throws JsonProcessingException {
        final String response;

        if(MediaType.APPLICATION_JSON_VALUE.equals(accept)) {
            response = new ObjectMapper().writeValueAsString(error);
        } else {
            response = new XmlMapper().writer().withRootName("error").writeValueAsString(error);
        }

        return response;
    }

    private String parseAcceptHeader(WebRequest request){
        String accept = request.getHeader(HttpHeaders.ACCEPT);

        return accept == null ? DEFAULT_CONTENT_TYPE : accept;
    }
}
