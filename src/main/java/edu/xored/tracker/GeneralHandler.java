package edu.xored.tracker;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.apache.tomcat.util.http.fileupload.FileUploadBase.SizeLimitExceededException;

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class GeneralHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ SizeLimitExceededException.class, Exception.class })
    public String handleGeneralException(final Exception exception) {
        return "File is too BIG";
    }
}
