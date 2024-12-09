package com.example.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import com.example.entity.PlayerEffort;

@ApplicationScoped
public class EffortRepository {
    @PersistenceContext
    private EntityManager em;

    private static final double BASE_EFFORT = 5.0;
    
    @Transactional
    public void savePlayerEffort(String playerId, double effortValue, int round, int totalEffort) {
        PlayerEffort effort = new PlayerEffort();
        effort.setPlayerId(playerId);
        effort.setEffortValue(effortValue);
        effort.setRound(round);
        effort.setTotalEffort(totalEffort);
        
        em.persist(effort);
    }   
    
    @Transactional
    public double getLatestEffort(String playerId) {
        try {
            return em.createQuery(
                "SELECT e.effortValue FROM PlayerEffort e " +
                "WHERE e.playerId = :playerId " +
                "ORDER BY e.round DESC", Double.class)
                .setParameter("playerId", playerId)
                .setMaxResults(1)
                .getSingleResult();
        } catch (NoResultException e) {
            return BASE_EFFORT;
        }
    }
} 