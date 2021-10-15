package es.urjc.cloudapps.forum.controller;

import es.urjc.cloudapps.forum.application.TopicService;
import es.urjc.cloudapps.forum.application.dto.TopicDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/topics")
public class TopicRestController {

    private final TopicService topicService;

    public TopicRestController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping("/")
    public ResponseEntity<List<TopicDto>> findAllTopics() {
        List<TopicDto> topics = topicService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(topics);
    }

}
