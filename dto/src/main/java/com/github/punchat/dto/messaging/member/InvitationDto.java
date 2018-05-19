package com.github.punchat.dto.messaging.member;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InvitationDto {
    private Long userId;
    private Long role;
}