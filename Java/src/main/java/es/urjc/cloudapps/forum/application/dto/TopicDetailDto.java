package es.urjc.cloudapps.forum.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class TopicDetailDto {

    private Long id;
    private String title;
    private UserDto creator;
    private List<MessageDto> messages;

}
