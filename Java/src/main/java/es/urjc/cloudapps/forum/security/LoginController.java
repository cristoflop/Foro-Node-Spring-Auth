package es.urjc.cloudapps.forum.security;

import es.urjc.cloudapps.forum.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    private final UserComponent userComponent;

    public LoginController(UserComponent userComponent) {
        this.userComponent = userComponent;
    }

    @RequestMapping("/api/logIn")
    public ResponseEntity<User> logIn() {

        if (!userComponent.isLoggedUser()) {
            log.info("Not user logged");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            User loggedUser = userComponent.getLoggedUser();
            log.info("Logged as " + loggedUser.getNick());
            return ResponseEntity.status(HttpStatus.OK).body(loggedUser);
        }
    }

    @RequestMapping("/api/logOut")
    public ResponseEntity<Boolean> logOut(HttpSession session) {

        if (!userComponent.isLoggedUser()) {
            log.info("No user logged");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            session.invalidate();
            log.info("Logged out");
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }
    }

}