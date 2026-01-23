package com.library.management.controller;

import com.library.management.entity.BookIssue;
import com.library.management.service.BookIssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/book-issues")
@CrossOrigin(origins = "http://localhost:3000")
public class BookIssueController {

    @Autowired
    private BookIssueService bookIssueService;

    @GetMapping
    public ResponseEntity<List<BookIssue>> getAllBookIssues() {
        List<BookIssue> issues = bookIssueService.getAllBookIssues();
        return ResponseEntity.ok(issues);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookIssue> getBookIssueById(@PathVariable Long id) {
        Optional<BookIssue> issue = bookIssueService.getBookIssueById(id);
        return issue.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/active")
    public ResponseEntity<List<BookIssue>> getActiveIssues() {
        List<BookIssue> issues = bookIssueService.getActiveIssues();
        return ResponseEntity.ok(issues);
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<BookIssue>> getOverdueIssues() {
        List<BookIssue> issues = bookIssueService.getOverdueIssues();
        return ResponseEntity.ok(issues);
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<BookIssue>> getIssuesByMember(@PathVariable Long memberId) {
        List<BookIssue> issues = bookIssueService.getActiveIssuesByMember(memberId);
        return ResponseEntity.ok(issues);
    }

    @PostMapping("/issue")
    public ResponseEntity<?> issueBook(
            @RequestParam Long bookId,
            @RequestParam Long memberId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dueDate,
            @RequestParam(required = false) String remarks) {
        
        try {
            BookIssue issue = bookIssueService.issueBook(bookId, memberId, dueDate, remarks);
            return ResponseEntity.status(HttpStatus.CREATED).body(issue);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/return/{issueId}")
    public ResponseEntity<?> returnBook(@PathVariable Long issueId) {
        try {
            BookIssue issue = bookIssueService.returnBook(issueId);
            return ResponseEntity.ok(issue);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
