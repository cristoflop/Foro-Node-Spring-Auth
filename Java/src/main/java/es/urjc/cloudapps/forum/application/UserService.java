package es.urjc.cloudapps.forum.application;

import es.urjc.cloudapps.forum.application.dto.NewUserDto;
import es.urjc.cloudapps.forum.application.dto.UpdateEmailDto;
import es.urjc.cloudapps.forum.application.dto.UserDto;
import es.urjc.cloudapps.forum.application.mapper.ResponseMapper;
import es.urjc.cloudapps.forum.domain.User;
import es.urjc.cloudapps.forum.exception.UserAlreadyExistsException;
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
public class UserService {

    private final UserRepository userRepository;
    private final TopicRepository topicRepository;
    private final MessageRepository messageRepository;

    private final ResponseMapper responseMapper;

    public UserService(UserRepository userRepository,
                       TopicRepository topicRepository,
                       MessageRepository messageRepository,
                       ResponseMapper responseMapper) {
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
        this.messageRepository = messageRepository;
        this.responseMapper = responseMapper;
    }

    public List<UserDto> findAll() {
        return userRepository
                .findAll()
                .stream()
                .map(responseMapper::toUserDto)
                .collect(Collectors.toList());
    }

    public UserDto findById(Long userId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(UserNotFoundException::new);
        return responseMapper.toUserDto(user);
    }

    public UserDto save(NewUserDto newUserDto) {
        if (userRepository.findByNick(newUserDto.getNick()).isPresent())
            throw new UserAlreadyExistsException();
        User unsavedUser = new User(newUserDto.getNick(), newUserDto.getPassword(), newUserDto.getEmail());
        User user = userRepository.save(unsavedUser);
        return responseMapper.toUserDto(user);
    }

    public UserDto updateEmail(UpdateEmailDto updateEmailDto) {
        User user = userRepository
                .findById(updateEmailDto.getUserId())
                .orElseThrow(UserNotFoundException::new);
        user.setEmail(updateEmailDto.getEmail());
        User updatedUser = userRepository.save(user);
        return responseMapper.toUserDto(updatedUser);
    }

}
