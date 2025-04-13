package org.seong.shovelingdatadog.scheduler

import org.seong.shovelingdatadog.service.AcceptMemberMessage
import org.seong.shovelingdatadog.service.MemberService
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
@EnableScheduling
class MemberScheduler(
    private val memberService: MemberService,
    private val rabbitTemplate: RabbitTemplate
) {
    @Scheduled(cron = "0 * * * * *")
    fun accept() {
        this.memberService.findAllJoinedMember()
            .forEach {
                rabbitTemplate.convertAndSend(
                    "accept-member", "", AcceptMemberMessage(it.memberId)
                )
            }
    }
}
