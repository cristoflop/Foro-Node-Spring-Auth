package es.urjc.cloudapps.forum.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(1)
public class RestSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserRepositoryAuthProvider userRepoAuthProvider;

    public RestSecurityConfig(UserRepositoryAuthProvider userRepoAuthProvider) {
        this.userRepoAuthProvider = userRepoAuthProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.antMatcher("/api/**");

        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/logIn").authenticated();

        // URLs that need authentication to access to it
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/topics/**").hasRole("USER");
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/topics/{topicId}").hasRole("USER");
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/messages/**").hasRole("USER");

        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/topics/**").hasRole("ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/messages/**").hasRole("ADMIN");

        // Other URLs can be accessed without authentication
        http.authorizeRequests().anyRequest().permitAll();

        // Disable CSRF protection (it is difficult to implement with ng2)
        http.csrf().disable();

        // Use Http Basic Authentication
        http.httpBasic();

        // Do not redirect when logout
        http.logout().logoutSuccessHandler((rq, rs, a) -> {
        });
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        // Database authentication provider
        auth.authenticationProvider(userRepoAuthProvider);
    }
}