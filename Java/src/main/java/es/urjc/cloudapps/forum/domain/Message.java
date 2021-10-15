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

    public Message(String value, User creator) {
        this.value = value;
        this.creator = creator;
    }

}
