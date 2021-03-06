package com.github.punchat.stomp.feign

import com.github.punchat.dto.messaging.member.MemberResponse
import com.github.punchat.dto.messaging.message.BroadcastMessageResponse
import com.github.punchat.dto.messaging.message.DirectMessageResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@FeignClient("messaging")
@RestController
interface MessagingClient {
    @GetMapping("/messages/direct/{id}")
    DirectMessageResponse getDirectMessageById(@PathVariable("id") Long id)

    @GetMapping("/messages/broadcast/{id}")
    BroadcastMessageResponse getBroadcastMessageById(@PathVariable("id") Long id)

    @GetMapping("/channels/{id}/members")
    List<MemberResponse> getAllMembers(@PathVariable("id") Long id)
}
