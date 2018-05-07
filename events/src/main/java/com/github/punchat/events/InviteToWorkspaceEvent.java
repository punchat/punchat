package com.github.punchat.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InviteToWorkspaceEvent {
    private long senderUserId;
    private String email;
    private LocalDateTime creationTime;
}
