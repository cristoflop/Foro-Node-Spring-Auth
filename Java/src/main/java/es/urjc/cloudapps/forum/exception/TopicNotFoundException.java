package es.urjc.cloudapps.forum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Topic not found")
public class TopicNotFoundException extends RuntimeException {
}
