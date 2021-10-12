package es.urjc.cloudapps.forum.application;

import es.urjc.cloudapps.forum.repository.MessageRepository;
import es.urjc.cloudapps.forum.repository.TopicRepository;
import es.urjc.cloudapps.forum.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TopicRepository topicRepository;
    private final MessageRepository messageRepository;

    public UserService(UserRepository userRepository,
                       TopicRepository topicRepository,
                       MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
        this.messageRepository = messageRepository;
    }

}
