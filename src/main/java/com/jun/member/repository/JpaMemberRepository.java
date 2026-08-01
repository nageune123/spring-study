package com.jun.member.repository;

import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.jun.member.domain.Member;


@Repository
public class JpaMemberRepository implements MemberRepository {

    private final EntityManager em;
        public JpaMemberRepository(EntityManager em) {
            this.em = em ;
        }
        @Override
        public List<Member> findAll() {
            // TODO Auto-generated method stub
            return null;
        }
       @Override
        public Optional<Member> findByName(String name) {
            List<Member> result = em.createQuery(
            "select m from Member m where m.name = :name",
            Member.class)
            .setParameter("name", name)
            .getResultList();

                return result.stream().findAny();
        }
        @Override
        public Member save(Member member) {
            // TODO Auto-generated method stub
            em.persist(member);

            return member;
        }
        
}
