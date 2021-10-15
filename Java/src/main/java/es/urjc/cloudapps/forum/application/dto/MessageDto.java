package es.urjc.cloudapps.forum.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MessageDto {

    private Long id;
    private String value;

}
