package es.urjc.cloudapps.forum.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter
@Setter
@AllArgsConstructor
public class NewUserDto {

    private String nick;
    @Email
    private String email;
    private String password;

}
