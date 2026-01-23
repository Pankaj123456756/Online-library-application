package com.library.management.service;

import com.library.management.entity.Book;
import com.library.management.entity.BookIssue;
import com.library.management.entity.Member;
import com.library.management.repository.BookIssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookIssueService {

    @Autowired
    private BookIssueRepository bookIssueRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private MemberService memberService;

    public List<BookIssue> getAllBookIssues() {
        return bookIssueRepository.findAll();
    }

    public Optional<BookIssue> getBookIssueById(Long id) {
        return bookIssueRepository.findById(id);
    }

    public BookIssue issueBook(Long bookId, Long memberId, LocalDateTime dueDate, String remarks) {
        Optional<Book> optionalBook = bookService.getBookById(bookId);
        Optional<Member> optionalMember = memberService.getMemberById(memberId);

        if (optionalBook.isEmpty() || optionalMember.isEmpty()) {
            return null;
        }

        Book book = optionalBook.get();
        Member member = optionalMember.get();

        if (!bookService.isBookAvailable(bookId)) {
            throw new RuntimeException("Book is not available for issue");
        }

        if (!memberService.isMemberActive(memberId)) {
            throw new RuntimeException("Member is not active or membership expired");
        }

        BookIssue bookIssue = new BookIssue();
        bookIssue.setBook(book);
        bookIssue.setMember(member);
        bookIssue.setIssueDate(LocalDateTime.now());
        bookIssue.setDueDate(dueDate);
        bookIssue.setRemarks(remarks);
        bookIssue.setReturned(false);

        bookService.decrementAvailableCopies(bookId);

        return bookIssueRepository.save(bookIssue);
    }

    public BookIssue returnBook(Long issueId) {
        Optional<BookIssue> optionalBookIssue = bookIssueRepository.findById(issueId);
        if (optionalBookIssue.isEmpty()) {
            return null;
        }

        BookIssue bookIssue = optionalBookIssue.get();
        if (bookIssue.getReturned()) {
            throw new RuntimeException("Book has already been returned");
        }

        bookIssue.setReturnDate(LocalDateTime.now());
        bookIssue.setReturned(true);

        bookService.incrementAvailableCopies(bookIssue.getBook().getId());

        return bookIssueRepository.save(bookIssue);
    }

    public List<BookIssue> getActiveIssues() {
        return bookIssueRepository.findActiveIssues();
    }

    public List<BookIssue> getOverdueIssues() {
        return bookIssueRepository.findOverdueIssues(LocalDateTime.now());
    }

    public List<BookIssue> getActiveIssuesByBook(Long bookId) {
        return bookIssueRepository.findActiveIssuesByBook(bookId);
    }

    public List<BookIssue> getActiveIssuesByMember(Long memberId) {
        return bookIssueRepository.findActiveIssuesByMember(memberId);
    }

    public long countActiveIssues() {
        return bookIssueRepository.countActiveIssues();
    }

    public boolean hasActiveIssues(Long memberId) {
        List<BookIssue> activeIssues = getActiveIssuesByMember(memberId);
        return !activeIssues.isEmpty();
    }

    public BookIssue saveBookIssue(BookIssue bookIssue) {
        return bookIssueRepository.save(bookIssue);
    }

    public boolean deleteBookIssue(Long id) {
        if (bookIssueRepository.existsById(id)) {
            bookIssueRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
