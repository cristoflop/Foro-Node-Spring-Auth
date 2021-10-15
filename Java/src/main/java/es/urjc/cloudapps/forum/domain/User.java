package es.urjc.cloudapps.forum.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Email
    private String email;

    @Column(unique = true)
    private String nick;

    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;

    public User(String nick, String password, String email, String... roles) {
        this.nick = nick;
        this.password = password;
        this.email = email;
        this.roles = roles.length == 0 ?
                Collections.singletonList("ROLE_USER") :
                Arrays.asList(roles);
    }

}
