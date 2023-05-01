package com.springtraining.furnitureshop.exceptions;

import com.springtraining.furnitureshop.util.Constants;
import com.springtraining.furnitureshop.util.Views;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@ControllerAdvice
public class DefaultExceptionHandler {

    public static final String EXCEPTION = "exception";
    public static final String TITLE = "error.title";
    public static final String FILE_SIZE_LIMIT = "error.title.fileSizeLimit";

    @ExceptionHandler(FileSizeLimitExceededException.class)
    public String handleFileException(FileSizeLimitExceededException exception, HttpServletRequest request) {
        log.trace("handleFileException start");
        log.info(Constants.LOGGER_FORMAT, EXCEPTION, exception);
        HttpSession session = request.getSession();
        session.setAttribute(TITLE, FILE_SIZE_LIMIT);
        return Constants.REDIRECT + Views.ERROR_PAGE;
    }
}
