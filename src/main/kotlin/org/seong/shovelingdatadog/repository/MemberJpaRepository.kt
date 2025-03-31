package org.seong.shovelingdatadog.repository

import org.seong.shovelingdatadog.domain.Member
import org.seong.shovelingdatadog.domain.MemberStatus
import org.springframework.data.jpa.repository.JpaRepository

interface MemberJpaRepository: JpaRepository<Member, Long> {

    fun findAllByStatus(status: MemberStatus): List<Member>
}
