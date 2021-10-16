package es.urjc.cloudapps.forum.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String value;

    @ManyToOne
    private User creator;

    @ManyToOne
    private Topic topic;

    public Message(String value, User creator, Topic topic) {
        this.value = value;
        this.creator = creator;
        this.topic = topic;
    }

}
