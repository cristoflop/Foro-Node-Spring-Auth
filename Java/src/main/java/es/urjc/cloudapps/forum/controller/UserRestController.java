package es.urjc.cloudapps.forum.controller;

import es.urjc.cloudapps.forum.application.UserService;
import es.urjc.cloudapps.forum.application.dto.NewUserDto;
import es.urjc.cloudapps.forum.application.dto.UpdateEmailDto;
import es.urjc.cloudapps.forum.application.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> findAllUsers() {
        List<UserDto> users = userService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserDto> findUserById(@PathVariable Long userId) {
        UserDto user = userService.findById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PostMapping("/users")
    public ResponseEntity<UserDto> saveUser(@RequestBody @Valid NewUserDto newUserDto) {
        UserDto user = userService.save(newUserDto);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PatchMapping("/users")
    public ResponseEntity<UserDto> updateUserEmail(@RequestBody @Valid UpdateEmailDto updateEmailDto) {
        UserDto user = userService.updateEmail(updateEmailDto);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
