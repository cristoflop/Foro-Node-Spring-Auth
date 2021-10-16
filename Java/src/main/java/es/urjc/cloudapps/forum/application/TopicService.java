package es.urjc.cloudapps.forum.application;

import es.urjc.cloudapps.forum.application.dto.*;
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

    public TopicDetailDto addMessageInTopic(NewMessageDto newMessageDto) {
        User user = userRepository
                .findByNick(newMessageDto.getCreator())
                .orElseThrow(UserNotFoundException::new);
        Topic topic = topicRepository
                .findById(newMessageDto.getTopicId())
                .orElseThrow(TopicNotFoundException::new);
        Message unsavedMessage = new Message(newMessageDto.getMessage(), user, topic);
        Message message = messageRepository.save(unsavedMessage);
        return responseMapper.toTopicDetailDto(message.getTopic());
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

    public List<UserMessageDto> findAllMessagesByUser(Long userId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(UserNotFoundException::new);
        return messageRepository
                .findAllByCreator(user)
                .stream()
                .map(responseMapper::toUserMessageDto)
                .collect(Collectors.toList());
    }

    public List<TopicDto> findAllTopicsByUser(Long userId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(UserNotFoundException::new);
        return topicRepository
                .findAllByCreator(user)
                .stream()
                .map(responseMapper::toTopicDto)
                .collect(Collectors.toList());
    }

}
