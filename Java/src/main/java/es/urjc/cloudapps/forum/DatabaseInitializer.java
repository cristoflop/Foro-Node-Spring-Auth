package es.urjc.cloudapps.forum;

import es.urjc.cloudapps.forum.domain.Message;
import es.urjc.cloudapps.forum.domain.Topic;
import es.urjc.cloudapps.forum.domain.User;
import es.urjc.cloudapps.forum.repository.MessageRepository;
import es.urjc.cloudapps.forum.repository.TopicRepository;
import es.urjc.cloudapps.forum.repository.UserRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DatabaseInitializer {

    private final TopicRepository topicRepository;

    private final UserRepository userRepository;

    private final MessageRepository messageRepository;

    public DatabaseInitializer(TopicRepository topicRepository,
                               UserRepository userRepository,
                               MessageRepository messageRepository) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    @PostConstruct
    public void setUp() {

        // Users
        User admin = userRepository.save(new User("admin", "pass", "admin@forum.com",
                "ROLE_USER", "ROLE_ADMIN"));
        User user = userRepository.save(new User("user", "pass", "user@forum.com",
                "ROLE_USER"));

        // Messages
        Message message1 = this.messageRepository.save(new Message("Es algo muy interesante", user));
        Message message2 = this.messageRepository.save(new Message("Me parece algo importante para el futuro", user));
        Message message3 = this.messageRepository.save(new Message("No me interesa nada", user));

        // topics
        this.topicRepository.save(new Topic("Que opinas sobre invertir en criptomonedas??",
                admin, message1, message2, message3));
        this.topicRepository.save(new Topic("Datos sobre tesla",
                user));
        this.topicRepository.save(new Topic("La URJC es una gran universidad segun un estudio de Harvard",
                user));
    }

}
