package com.github.punchat.dto.messaging.invite;

import com.github.punchat.dto.messaging.channel.BroadcastChannelResponse;
import com.github.punchat.dto.messaging.member.MemberResponse;
import com.github.punchat.dto.messaging.role.RoleResponse;
import com.github.punchat.dto.messaging.user.UserDto;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ChannelInvitationResponse {
    private Long id;
    private MemberResponse sender;
    private BroadcastChannelResponse channel;
    private UserDto recipient;
    private RoleResponse role;
    private State state;
}
