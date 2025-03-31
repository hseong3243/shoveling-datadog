package org.seong.shovelingdatadog.consumer

import org.seong.shovelingdatadog.service.AcceptMemberMessage
import org.seong.shovelingdatadog.service.MemberService
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class MemberConsumer(
    private val memberService: MemberService
){
    @RabbitListener(queues = ["accept-member"])
    fun acceptMember(message: AcceptMemberMessage) {
        this.memberService.accept(message.memberId)
    }
}

