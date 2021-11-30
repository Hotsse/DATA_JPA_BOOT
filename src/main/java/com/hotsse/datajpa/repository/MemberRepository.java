package com.hotsse.datajpa.repository;

import com.hotsse.datajpa.entity.Member;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long> {
	
	@EntityGraph(attributePaths = {"team"})
	@Query("select m from Member m")
	List<Member> findFetchAll();
}
