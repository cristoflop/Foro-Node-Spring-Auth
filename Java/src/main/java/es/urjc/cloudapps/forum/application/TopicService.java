package es.urjc.cloudapps.forum.application;

import es.urjc.cloudapps.forum.application.mapper.ResponseMapper;
import es.urjc.cloudapps.forum.application.response.TopicDto;
import es.urjc.cloudapps.forum.repository.MessageRepository;
import es.urjc.cloudapps.forum.repository.TopicRepository;
import es.urjc.cloudapps.forum.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicService {

    private final TopicRepository topicRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    private final ResponseMapper responseMapper;

    public TopicService(TopicRepository topicRepository,
                        MessageRepository messageRepository,
                        UserRepository userRepository,
                        ResponseMapper responseMapper) {
        this.topicRepository = topicRepository;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.responseMapper = responseMapper;
    }

    public List<TopicDto> findAll() {
        return topicRepository.findAll()
                .stream()
                .map(responseMapper::toTopicDto)
                .collect(Collectors.toList());
    }

}
