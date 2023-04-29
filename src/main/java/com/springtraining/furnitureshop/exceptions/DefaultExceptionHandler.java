package com.springtraining.furnitureshop.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@ControllerAdvice
public class DefaultExceptionHandler {
    private static final String ERROR_PAGE = "errorPage";

    @ExceptionHandler(FileSizeLimitExceededException.class)
    public String handleFileException(FileSizeLimitExceededException exception, HttpServletRequest request) {
        log.trace("handleFileException start");
        log.info("exception: ", exception);
        HttpSession session = request.getSession();
        log.info("session: "+ session);
        session.setAttribute("error.title", "error.title.fileSizeLimit");
        return "redirect:/" + ERROR_PAGE;
    }
}
