package es.urjc.cloudapps.forum.repository;

import es.urjc.cloudapps.forum.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
