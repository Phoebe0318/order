package com.example.order.dao;

import com.example.order.pojo.MemberDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberDao extends JpaRepository<MemberDo, Long> {
    @Query(value = "SELECT COUNT(*) > 0 FROM member WHERE email = :email", nativeQuery = true)
    long existsByEmail(@Param("email") String email);
}
