package com.github.punchat.ts.domain.message;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("direct")
public class TopicDirectMessage extends TopicMessage {

    public TopicDirectMessage(Long messageId, List<Topic> topics, Long senderId, Long receiverId) {
        super(messageId, topics);
        this.senderId = senderId;
        this.receiverId = receiverId;
    }

    @Column(name = "sender_id")
    private Long senderId;

    @Column(name = "receiver_id")
    private Long receiverId;
}
