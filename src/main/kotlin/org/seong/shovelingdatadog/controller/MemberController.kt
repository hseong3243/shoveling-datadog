package org.seong.shovelingdatadog.controller

import org.seong.shovelingdatadog.domain.MemberStatus
import org.seong.shovelingdatadog.service.MemberOutput
import org.seong.shovelingdatadog.service.MemberService
import org.seong.shovelingdatadog.service.SaveMemberInput
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class MemberController(
    private val memberService: MemberService,
) {

    @PostMapping("/members")
    fun saveMember(@RequestBody request: SaveMemberRequest) {
        return this.memberService.save(request.toInput()).let {
            SaveMemberResponse.from(it)
        }
    }

    @GetMapping("/member/{memberId}")
    fun getMember(@PathVariable("memberId") memberId: Long) {
        return this.memberService.findById(memberId).let {
            GetMemberResponse.from(it)
        }
    }
}

data class SaveMemberRequest(
    val username: String,
    val password: String,
) {
    fun toInput(): SaveMemberInput = SaveMemberInput(username, password)
}

data class SaveMemberResponse(
    val memberId: Long
) {
    companion object {
        fun from(output: MemberOutput) = SaveMemberResponse(
            memberId = output.memberId,
        )
    }

}

data class GetMemberResponse(
    val memberId: Long,
    val username: String,
    val password: String,
    val status: MemberStatus,
) {
    companion object {
        fun from(output: MemberOutput) = GetMemberResponse(
            memberId = output.memberId,
            username = output.username,
            password = output.password,
            status = output.status
        )
    }
}


