package es.urjc.cloudapps.forum.application.mapper;

import es.urjc.cloudapps.forum.application.dto.MessageDto;
import es.urjc.cloudapps.forum.application.dto.TopicDetailDto;
import es.urjc.cloudapps.forum.application.dto.TopicDto;
import es.urjc.cloudapps.forum.application.dto.UserDto;
import es.urjc.cloudapps.forum.domain.Message;
import es.urjc.cloudapps.forum.domain.Topic;
import es.urjc.cloudapps.forum.domain.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ResponseMapper {

    public MessageDto toMessageDto(Message message) {
        return new MessageDto(message.getId(), message.getValue());
    }

    public TopicDto toTopicDto(Topic topic) {
        return new TopicDto(topic.getId(), topic.getTitle());
    }

    public UserDto toUserDto(User user) {
        return new UserDto(user.getId(), user.getEmail(), user.getNick());
    }

    public TopicDetailDto toTopicDetailDto(Topic topic) {
        return new TopicDetailDto(
                topic.getId(),
                topic.getTitle(),
                toUserDto(topic.getCreator()),
                topic.getMessages().stream().map(this::toMessageDto).collect(Collectors.toList()));
    }

}
