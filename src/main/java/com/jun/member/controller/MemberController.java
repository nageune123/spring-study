package com.jun.member.controller;

import com.jun.member.domain.Member;
import com.jun.member.repository.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {

    private final MemberRepository memberRepository;

    public MemberController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "createMemberForm";
    }
    @GetMapping("/members")
public String list() {
    return "memberList";
}

    @PostMapping("/members")
    public String create(MemberForm form) {

        Member member = new Member();
        member.setName(form.getName());

        memberRepository.save(member);

        System.out.println("저장된 회원 번호: " + member.getId());
        System.out.println("저장된 회원 이름: " + member.getName());

        return "redirect:/";
    }
}