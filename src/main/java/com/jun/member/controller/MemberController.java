package com.jun.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {

    @GetMapping("/members/new")
    public String createForm() {
        return "createMemberForm";
    }
}