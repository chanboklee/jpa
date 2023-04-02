package com.lee.jpa.service;

import com.lee.jpa.domain.Member;
import com.lee.jpa.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void 회원가입(){
        // given
        Member member = new Member("kim");

        // when
        Long saveId = memberService.join(member);

        // then
        Assertions.assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test
    void 중복회원예외(){
        // given
        Member member1 = new Member("kim");
        Member member2 = new Member("kim");

        // when
        memberService.join(member1);

        // then
        Assertions.assertThrows(IllegalStateException.class, () -> {
            memberService.join(member2);
        });
    }
}