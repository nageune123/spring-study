package com.jun.member.repository;

import com.jun.member.domain.Member;
import org.springframework.jdbc.datasource.DataSourceUtils;

import java.sql.Statement;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class JdbcMemberRepository implements MemberRepository {

    private final DataSource dataSource;

    public JdbcMemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Member> findAll() {
        // TODO Auto-generated method stub
        return null;
    }
        @Override
    public Optional<Member> findByName(String name) {
        
    String sql = "select * from member where name = ?";

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
        conn = getConnection();

        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, name);

        rs = pstmt.executeQuery();

        if (rs.next()) {
            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));

            return Optional.of(member);
        }

        return Optional.empty();

    } catch (SQLException e) {
        throw new IllegalStateException(e);

    } finally {
        close(conn, pstmt, rs);
    }
}

    @Override
    public Member save(Member member) {
    String sql = "insert into member(name) values(?)";

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
        conn = getConnection();

        pstmt = conn.prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS
        );

        pstmt.setString(1, member.getName());
        pstmt.executeUpdate();

        rs = pstmt.getGeneratedKeys();

        if (rs.next()) {
            member.setId(rs.getLong(1));
        } else {
            throw new SQLException("회원 ID 조회 실패");
        }

        return member;

    } catch (SQLException e) {
        throw new IllegalStateException(e);

    } finally {
        close(conn, pstmt, rs);
    }
}
    private Connection getConnection() {
    return DataSourceUtils.getConnection(dataSource);
}

private void close(
        Connection conn,
        PreparedStatement pstmt,
        ResultSet rs
) {
    try {
        if (rs != null) {
            rs.close();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    try {
        if (pstmt != null) {
            pstmt.close();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    if (conn != null) {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}

}