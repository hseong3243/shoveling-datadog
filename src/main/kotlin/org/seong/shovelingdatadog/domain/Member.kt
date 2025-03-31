package org.seong.shovelingdatadog.domain

import jakarta.persistence.*

@Entity
@Table(name = "member")
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var memberId: Long? = null,
    val username: String,
    val password: String,
) {
}
