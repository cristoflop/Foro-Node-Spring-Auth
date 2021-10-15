package es.urjc.cloudapps.forum.application;

import es.urjc.cloudapps.forum.application.dto.MessageTopicDto;
import es.urjc.cloudapps.forum.application.dto.NewTopicDto;
import es.urjc.cloudapps.forum.application.dto.TopicDetailDto;
import es.urjc.cloudapps.forum.application.dto.TopicDto;
import es.urjc.cloudapps.forum.application.mapper.ResponseMapper;
import es.urjc.cloudapps.forum.domain.Message;
import es.urjc.cloudapps.forum.domain.Topic;
import es.urjc.cloudapps.forum.domain.User;
import es.urjc.cloudapps.forum.exception.TopicCannotBeDeletedException;
import es.urjc.cloudapps.forum.exception.TopicNotFoundException;
import es.urjc.cloudapps.forum.exception.UserNotFoundException;
import es.urjc.cloudapps.forum.repository.MessageRepository;
import es.urjc.cloudapps.forum.repository.TopicRepository;
import es.urjc.cloudapps.forum.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
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
        return topicRepository
                .findAll()
                .stream()
                .map(responseMapper::toTopicDto)
                .collect(Collectors.toList());
    }

    public TopicDetailDto findById(Long topicId) {
        Topic topic = topicRepository
                .findById(topicId)
                .orElseThrow(TopicNotFoundException::new);
        return responseMapper.toTopicDetailDto(topic);
    }

    public TopicDto save(NewTopicDto newTopicDto) {
        User user = userRepository
                .findByNick(newTopicDto.getCreator())
                .orElseThrow(UserNotFoundException::new);
        Topic unsavedTopic = new Topic(newTopicDto.getTitle(), user);
        Topic topic = topicRepository.save(unsavedTopic);
        return responseMapper.toTopicDto(topic);
    }

    public TopicDetailDto addMessageInTopic(MessageTopicDto messageTopicDto) {
        User user = userRepository
                .findByNick(messageTopicDto.getCreator())
                .orElseThrow(UserNotFoundException::new);
        Topic topic = topicRepository
                .findById(messageTopicDto.getTopicId())
                .orElseThrow(TopicNotFoundException::new);
        Message unsavedMessage = new Message(messageTopicDto.getMessage(), user);
        Message message = messageRepository.save(unsavedMessage);
        topic.getMessages().add(message);
        Topic updatedTopic = topicRepository.save(topic);
        return responseMapper.toTopicDetailDto(updatedTopic);
    }

    public void deleteMessage(Long messageId) {
        messageRepository.deleteById(messageId);
    }

    public void deleteTopic(Long topicId) {
        Topic topic = topicRepository
                .findById(topicId)
                .orElseThrow(TopicNotFoundException::new);
        if (!topic.getMessages().isEmpty())
            throw new TopicCannotBeDeletedException();
        topicRepository.delete(topic);
    }

}
