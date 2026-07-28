package com.jun.member.service;

import com.jun.member.repository.MemberRepository;
import com.jun.member.domain.Member;
import java.util.Optional;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class MemberService  {
    
private final MemberRepository memberRepository;

public MemberService (MemberRepository memberRepository){
 this.memberRepository = memberRepository;
}
public void join(Member member) {
Optional<Member> result = memberRepository.findByName(member.getName());
if (result.isPresent()) {
        throw new IllegalStateException("이미 존재하는 회원입니다.");
    }

    memberRepository.save(member);
}
public List<Member> findMembers() {
    return memberRepository.findAll();
}
}
