package es.urjc.cloudapps.forum.repository;

import es.urjc.cloudapps.forum.domain.Topic;
import es.urjc.cloudapps.forum.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    List<Topic> findAllByCreator(User creator);

}
