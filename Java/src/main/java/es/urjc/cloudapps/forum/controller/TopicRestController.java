package es.urjc.cloudapps.forum.controller;

import es.urjc.cloudapps.forum.application.TopicService;
import es.urjc.cloudapps.forum.application.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TopicRestController {

    private final TopicService topicService;

    public TopicRestController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping("/topics")
    public ResponseEntity<List<TopicDto>> findAllTopics() {
        List<TopicDto> topics = topicService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(topics);
    }

    @GetMapping("/topics/{topicId}")
    public ResponseEntity<TopicDetailDto> findTopicById(@PathVariable Long topicId) {
        TopicDetailDto topic = topicService.findById(topicId);
        return ResponseEntity.status(HttpStatus.OK).body(topic);
    }

    @PostMapping("/topics")
    public ResponseEntity<TopicDto> saveTopic(@RequestBody NewTopicDto newTopicDto) {
        TopicDto topic = topicService.save(newTopicDto);
        return ResponseEntity.status(HttpStatus.OK).body(topic);
    }

    @DeleteMapping("/topics/{topicId}")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long topicId) {
        topicService.deleteTopic(topicId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/topics/user/{userId}")
    public ResponseEntity<List<TopicDto>> findAllTopicsByUser(@PathVariable Long userId) {
        List<TopicDto> topics = topicService.findAllTopicsByUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(topics);
    }

    @PostMapping("/messages")
    public ResponseEntity<TopicDetailDto> addMessageInTopic(@RequestBody NewMessageDto newMessageDto) {
        TopicDetailDto topic = topicService.addMessageInTopic(newMessageDto);
        return ResponseEntity.status(HttpStatus.OK).body(topic);
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long messageId) {
        topicService.deleteMessage(messageId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/messages/user/{userId}")
    public ResponseEntity<List<UserMessageDto>> findAllMessgesByUser(@PathVariable Long userId) {
        List<UserMessageDto> messages = topicService.findAllMessagesByUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(messages);
    }

}
