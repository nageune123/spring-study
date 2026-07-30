package com.jun.member.repository;

import com.jun.member.domain.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MemoryMemberRepository implements MemberRepository  {

    private final List<Member> members = new ArrayList<>();
    private long sequence = 0L;

    @Override
    public Member save(Member member) {

        member.setId(++sequence);

        members.add(member);

        return member;
    }
    @Override
    public List<Member> findAll() {
        return members;
    }
    @Override
    public Optional<Member> findByName(String name) {
        return members.stream().filter(member -> member.getName().equals(name)).findFirst();

}
}