package api.maxchat.maxchat.controller;

import api.maxchat.maxchat.excp.AppBadException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handle(AppBadException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
