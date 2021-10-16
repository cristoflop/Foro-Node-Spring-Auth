package es.urjc.cloudapps.forum.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "User with comments or topics cannot be deleted")
public class UserCannotBeDeletedException extends RuntimeException {
}
