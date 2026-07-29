package com.jun.member.service;
import org.junit.jupiter.api.Assertions;
import com.jun.member.repository.MemberRepository;
import com.jun.member.repository.MemoryMemberRepository;

import org.junit.jupiter.api.Test;
import com.jun.member.domain.Member;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberServiceTest {

   private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final MemberService memberService = new MemberService(memberRepository);

    @Test
    void 회원가입() {
        Member member = new Member();
        member.setName("spring");
        memberService.join(member);
        Member result = memberRepository.findByName(member.getName()).get();    
        Assertions.assertEquals(member, result);

    }
    @Test
    void 회원가입예외(){
        Member member =new Member();
        Member member2 =new Member();
        member.setName("winter");
        member2.setName("winter");
        memberService.join(member);
        IllegalStateException exception =
        assertThrows(IllegalStateException.class, () -> {
            memberService.join(member2);
        });
        Assertions.assertEquals("이미 존재하는 회원입니다.", exception.getMessage());
        System.out.println(exception.getMessage());
    
    }
}