package com.hotsse.datajpa;

import com.hotsse.datajpa.entity.Member;
import com.hotsse.datajpa.entity.Team;
import com.hotsse.datajpa.repository.MemberJpaRepository;
import com.hotsse.datajpa.repository.MemberRepository;
import com.hotsse.datajpa.repository.TeamJpaRepository;
import com.hotsse.datajpa.repository.TeamRepository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class MemberTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TeamRepository teamRepository;
    
    @Autowired
    private MemberJpaRepository memberJpaRepository;
    
    @Autowired
    private TeamJpaRepository teamJpaRepository;

    @Test
    void test() throws Exception {

        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);

        Member member1 = new Member("member1", 13, teamA);
        Member member2 = new Member("member2", 14, teamA);
        Member member3 = new Member("member3", 15, teamB);
        Member member4 = new Member("member4", 16, teamB);
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);
    }
    
    @Test
    void pureJpaTest() throws Exception {
    	
    	Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamJpaRepository.save(teamA);
        teamJpaRepository.save(teamB);
        
        Member member1 = new Member("member1", 13, teamA);
        Member member2 = new Member("member2", 14, teamB);
        
        Member savedMember = memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);
        
        Member findMember = memberJpaRepository.find(savedMember.getId());
        System.out.println(findMember.toString());
        
        List<Member> allMembers = memberJpaRepository.findAll();
        System.out.println(allMembers.toString());
        System.out.println(memberJpaRepository.count());
        
        memberJpaRepository.delete(member1);
        memberJpaRepository.delete(member2);
        
        System.out.println(memberJpaRepository.findAll().toString());
        System.out.println(memberJpaRepository.count());
    }
}
