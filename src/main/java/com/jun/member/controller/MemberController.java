package com.jun.member.controller;

import com.jun.member.domain.Member;
import com.jun.member.repository.MemberRepository;
import com.jun.member.service.MemberService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;


@Controller
public class MemberController { 

    private final MemberService memberService;
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "createMemberForm";
    }
@GetMapping("/members")
public String list(org.springframework.ui.Model model) {

  model.addAttribute("members", memberService.findMembers());

    return "memberList";
}

    @PostMapping("/members")
    public String create(MemberForm form) {

        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);

        System.out.println("저장된 회원 번호: " + member.getId());
        System.out.println("저장된 회원 이름: " + member.getName());

        return "redirect:/";
    }
}