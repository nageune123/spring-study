package com.jun.member.repository;

import com.jun.member.domain.Member;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {

    private final List<Member> members = new ArrayList<>();
    private long sequence = 0L;

    public Member save(Member member) {

        member.setId(++sequence);

        members.add(member);

        return member;
    }

    public List<Member> findAll() {
        return members;
    }
}