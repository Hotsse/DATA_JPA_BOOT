package com.hotsse.datajpa.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.hotsse.datajpa.entity.Team;

@Repository
public class TeamJpaRepository {

	@PersistenceContext
	private EntityManager em;
	
	public Team save(Team team) {
		em.persist(team);
		return team;
	}
	
	public Team find(long id) {
		return em.find(Team.class, id);
	}
	
	public List<Team> findAll() {
		return em.createQuery("select t from Team t", Team.class)
				.getResultList();
	}
	
	public long count() {
		return em.createQuery("select count(t) from Team t", Long.class)
				.getSingleResult();
	}
	
	public void delete(Team team) {
		em.remove(team);
	}
}
