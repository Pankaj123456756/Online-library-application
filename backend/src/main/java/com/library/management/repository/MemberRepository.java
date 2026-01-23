package com.library.management.repository;

import com.library.management.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    
    Optional<Member> findByEmail(String email);
    
    boolean existsByEmail(String email);
    
    @Query("SELECT m FROM Member m WHERE m.active = true")
    List<Member> findActiveMembers();
    
    @Query("SELECT m FROM Member m WHERE m.expiryDate < CURRENT_DATE AND m.active = true")
    List<Member> findExpiredMembers();
    
    @Query("SELECT m FROM Member m WHERE " +
           "LOWER(m.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(m.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(m.phone) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Member> searchMembers(@Param("keyword") String keyword, Pageable pageable);
    
    @Query("SELECT COUNT(m) FROM Member m WHERE m.active = true")
    long countActiveMembers();
}
