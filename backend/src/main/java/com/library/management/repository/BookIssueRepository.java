package com.library.management.repository;

import com.library.management.entity.BookIssue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookIssueRepository extends JpaRepository<BookIssue, Long> {
    
    @Query("SELECT bi FROM BookIssue bi WHERE bi.returned = false")
    List<BookIssue> findActiveIssues();
    
    @Query("SELECT bi FROM BookIssue bi WHERE bi.returned = false AND bi.dueDate < :currentDate")
    List<BookIssue> findOverdueIssues(@Param("currentDate") LocalDateTime currentDate);
    
    @Query("SELECT bi FROM BookIssue bi WHERE bi.book.id = :bookId AND bi.returned = false")
    List<BookIssue> findActiveIssuesByBook(@Param("bookId") Long bookId);
    
    @Query("SELECT bi FROM BookIssue bi WHERE bi.member.id = :memberId AND bi.returned = false")
    List<BookIssue> findActiveIssuesByMember(@Param("memberId") Long memberId);
    
    @Query("SELECT COUNT(bi) FROM BookIssue bi WHERE bi.returned = false")
    long countActiveIssues();
}
