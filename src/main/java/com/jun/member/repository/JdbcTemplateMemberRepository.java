package com.jun.member.repository;

import com.jun.member.domain.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import java.sql.Statement;

@Repository
public class JdbcTemplateMemberRepository implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateMemberRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

   @Override
public Member save(Member member) {
    String sql = "insert into member(name) values(?)";

    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(connection -> {
        PreparedStatement pstmt = connection.prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS
        );

        pstmt.setString(1, member.getName());

        return pstmt;
    }, keyHolder);

    member.setId(keyHolder.getKey().longValue());

    return member;
}

    @Override
    public List<Member> findAll() {
         String sql = "select * from member";

    return jdbcTemplate.query(
            sql,
            memberRowMapper()
    );
    }
    @Override
    public Optional<Member> findByName(String name) {
        String sql = "select * from member where name = ?";

    List<Member> result = jdbcTemplate.query(
            sql,    
            memberRowMapper(),
            name
    );

    return result.stream().findAny();
    }

    private RowMapper<Member> memberRowMapper() {

        return (rs, rowNum) -> {
        Member member = new Member();
        member.setId(rs.getLong("id"));
        member.setName(rs.getString("name"));
        return member;
    };
}
}