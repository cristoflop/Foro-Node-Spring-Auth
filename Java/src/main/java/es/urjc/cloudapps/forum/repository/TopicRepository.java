package es.urjc.cloudapps.forum.repository;

import es.urjc.cloudapps.forum.domain.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
}