package student.laurens.novibackend.integration.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Helper class to provide a default way of validating an API response.
 *
 * @author Laurens Mäkel
 * @version 1.0, March 2022
 */
public class ResponseValidator {

    private ResultActions mvc;
    private HttpStatus expectedStatus;
    private String expectedContentType;

    public ResponseValidator(ResultActions mvc, HttpStatus expectedStatus, String expectedContentType){
        this.mvc = mvc;
        this.expectedStatus = expectedStatus;
        this.expectedContentType = expectedContentType;
    }

    public void validate() throws Exception {
        mvc.andExpect(status().is(expectedStatus.value()));
        if(expectedStatus.is2xxSuccessful() || expectedStatus.equals(HttpStatus.NOT_FOUND)){
            expectContentTypeResponse(expectedContentType);
        }
    }
    private ResultActions expectContentTypeResponse(String contentType) throws Exception {
        return mvc.andExpect(header().string(HttpHeaders.CONTENT_TYPE, contentType));
    }
}
