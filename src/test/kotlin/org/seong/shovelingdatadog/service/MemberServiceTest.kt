package org.seong.shovelingdatadog.service

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldNotBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class MemberServiceTest : FunSpec() {
    override fun extensions() = listOf(SpringExtension)

    @Autowired
    lateinit var sut: MemberService

    init {
        this.test("save 호출 시 회원이 저장된다.") {
            // given
            val input = SaveMemberInput(
                username = "tester",
                password = "test1234"
            )

            // when
            val result = sut.save(input)

            // then
            sut.findById(result.memberId) shouldNotBe null
        }

        this.test("findById 호출 시 회원을 조회한다.") {
            // given
            val input = SaveMemberInput(
                username = "tester",
                password = "test1234"
            )
            val savedMember = sut.save(input)

            // when
            // then
            shouldNotThrow<NoSuchElementException> { sut.findById(savedMember.memberId) }
        }
    }
}
