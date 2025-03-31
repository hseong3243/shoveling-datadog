package org.seong.shovelingdatadog.scheduler

import org.seong.shovelingdatadog.service.MemberService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class MemberScheduler(
    private val memberService: MemberService,
) {
    @Scheduled(cron = "0/10 * * * * *")
    fun accept() {
        this.memberService.accept()
    }
}
