package es.urjc.cloudapps.forum.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NewTopicDto {

    private String title;
    private String creator;

}
