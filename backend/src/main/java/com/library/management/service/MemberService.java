package com.library.management.service;

import com.library.management.entity.Member;
import com.library.management.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Page<Member> getMembers(Pageable pageable) {
        return memberRepository.findAll(pageable);
    }

    public Optional<Member> getMemberById(Long id) {
        return memberRepository.findById(id);
    }

    public Optional<Member> getMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public Member saveMember(Member member) {
        if (member.getMembershipDate() == null) {
            member.setMembershipDate(LocalDateTime.now());
        }
        return memberRepository.save(member);
    }

    public Member updateMember(Long id, Member memberDetails) {
        Optional<Member> optionalMember = memberRepository.findById(id);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            member.setName(memberDetails.getName());
            member.setEmail(memberDetails.getEmail());
            member.setPhone(memberDetails.getPhone());
            member.setAddress(memberDetails.getAddress());
            member.setMembershipType(memberDetails.getMembershipType());
            member.setExpiryDate(memberDetails.getExpiryDate());
            member.setActive(memberDetails.getActive());
            return memberRepository.save(member);
        }
        return null;
    }

    public boolean deleteMember(Long id) {
        if (memberRepository.existsById(id)) {
            memberRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Member> getActiveMembers() {
        return memberRepository.findActiveMembers();
    }

    public List<Member> getExpiredMembers() {
        return memberRepository.findExpiredMembers();
    }

    public Page<Member> searchMembers(String keyword, Pageable pageable) {
        return memberRepository.searchMembers(keyword, pageable);
    }

    public boolean existsByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    public long countActiveMembers() {
        return memberRepository.countActiveMembers();
    }

    public boolean isMemberActive(Long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        return optionalMember.map(member -> 
            member.getActive() && (member.getExpiryDate() == null || 
            member.getExpiryDate().isAfter(LocalDateTime.now()))
        ).orElse(false);
    }

    public void deactivateExpiredMembers() {
        List<Member> expiredMembers = getExpiredMembers();
        expiredMembers.forEach(member -> {
            member.setActive(false);
            memberRepository.save(member);
        });
    }
}
