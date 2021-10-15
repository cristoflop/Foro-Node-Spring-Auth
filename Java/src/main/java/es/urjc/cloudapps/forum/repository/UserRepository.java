package es.urjc.cloudapps.forum.repository;

import es.urjc.cloudapps.forum.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByNick(String nick);

}
