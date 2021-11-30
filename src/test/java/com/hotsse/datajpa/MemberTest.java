package com.hotsse.datajpa;

import com.hotsse.datajpa.entity.Member;
import com.hotsse.datajpa.entity.Team;
import com.hotsse.datajpa.repository.MemberJpaRepository;
import com.hotsse.datajpa.repository.MemberRepository;
import com.hotsse.datajpa.repository.TeamJpaRepository;
import com.hotsse.datajpa.repository.TeamRepository;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
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
    
    // 각 JPA Repository를 포함한 모든 Bean 에서 호출한 @PersistenceContext EntityManager 는 같은 인스턴스이다
    @PersistenceContext
	private EntityManager em;
    
    private final String 샘플데이터_생성금지 = "샘플데이터_생성금지";
    
    @BeforeEach
    void createTestData(TestInfo testInfo) throws Exception {
    	
    	Set<String> tags = testInfo.getTags();
    	if(tags.contains(샘플데이터_생성금지)) return;
    	
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
    @Tag(샘플데이터_생성금지)
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
    @Tag(샘플데이터_생성금지)
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
    
    @Test
    void FetchTypeLAZY일때_Np1쿼리발생_테스트() throws Exception {
        
    	// @BeforeEach 에서 생성한 데이터의 1차 캐싱 제거
        em.flush();
        em.clear();
        
        List<Member> members = memberRepository.findAll();
        
        for(Member m : members) {
        	System.out.println("member = " + m.toString());
        	System.out.println("member.teamClass = " + m.getTeam().getClass());
        	System.out.println("member.teamName = " + m.getTeam().getName());
        }
    }
    
    @Test
    void FetchTypeLAZY_EntityGraph적용_테스트() throws Exception {
    	
    	// @BeforeEach 에서 생성한 데이터의 1차 캐싱 제거
        em.flush();
        em.clear();
        
        // fetch join 을 통해 모든 데이터를 한번에 조회
        List<Member> members = memberRepository.findFetchAll();
        
        for(Member m : members) {
        	System.out.println("member = " + m.toString());
        	System.out.println("member.teamClass = " + m.getTeam().getClass());
        	System.out.println("member.teamName = " + m.getTeam().getName());
        }
    }
}
