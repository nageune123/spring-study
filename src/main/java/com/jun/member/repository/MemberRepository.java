package com.jun.member.repository;

import com.jun.member.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);

    List<Member> findAll();

    Optional<Member> findByName(String name);
}