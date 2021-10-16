package es.urjc.cloudapps.forum.repository;

import es.urjc.cloudapps.forum.domain.Message;
import es.urjc.cloudapps.forum.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findAllByCreator(User creator);

}
