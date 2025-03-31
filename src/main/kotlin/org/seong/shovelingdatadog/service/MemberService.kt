package org.seong.shovelingdatadog.service

import org.seong.shovelingdatadog.domain.Member
import org.seong.shovelingdatadog.repository.MemberJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberRepository: MemberJpaRepository
) {
    @Transactional
    fun save(input: SaveMemberInput): MemberOutput {
        return this.memberRepository.save(
            Member(
                username = input.username,
                password = input.password,
            )
        ).let { MemberOutput.from(it) }
    }

    @Transactional(readOnly = true)
    fun findById(memberId: Long): MemberOutput =
        this.memberRepository.findByIdOrNull(memberId)
            ?.let { MemberOutput.from(it) }
            ?: throw NoSuchElementException("not found member")
}

data class SaveMemberInput(
    val username: String,
    val password: String,
)

data class MemberOutput(
    val memberId: Long,
    val username: String,
    val password: String,
) {
    companion object {
        fun from(member: Member): MemberOutput = MemberOutput(
            memberId = member.memberId!!,
            username = member.username,
            password = member.password
        )
    }
}
