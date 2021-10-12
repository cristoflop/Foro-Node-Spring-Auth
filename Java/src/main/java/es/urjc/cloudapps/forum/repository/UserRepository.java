package es.urjc.cloudapps.forum.repository;

import es.urjc.cloudapps.forum.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
