package edu.xored.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
@RestController
public class GlobalControllerExceptionHandler {
    @ExceptionHandler(Exception.class)
    public void handleException(Exception ex) {

    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "File is too big")
    private class FileIsTooBigException extends RuntimeException {
    }

}
