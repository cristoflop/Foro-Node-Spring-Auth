package es.urjc.cloudapps.forum.security;


import es.urjc.cloudapps.forum.domain.User;
import es.urjc.cloudapps.forum.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserRepositoryAuthProvider implements AuthenticationProvider {

    private final UserRepository userRepository;

    private final UserComponent userComponent;

    public UserRepositoryAuthProvider(UserRepository userRepository, UserComponent userComponent) {
        this.userRepository = userRepository;
        this.userComponent = userComponent;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        User user = userRepository
                .findByNick(username)
                .orElse(null);

        if (user == null) {
            throw new BadCredentialsException("User not found");
        }

        if (!password.equals(user.getPassword())) {
            throw new BadCredentialsException("Wrong password");
        } else {

            userComponent.setLoggedUser(user);

            List<GrantedAuthority> roles = user.getRoles()
                    .stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            return new UsernamePasswordAuthenticationToken(username, password, roles);
        }
    }

    @Override
    public boolean supports(Class<?> authenticationObject) {
        return true;
    }
}
