package es.urjc.cloudapps.forum.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String title;

    @ManyToOne
    private User creator;

    @OneToMany
    private List<Message> messages;


    public Topic(String title, User creator) {
        this.title = title;
        this.creator = creator;
    }

}
