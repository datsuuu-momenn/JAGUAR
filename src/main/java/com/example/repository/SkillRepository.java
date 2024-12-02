package com.example.repository;

import com.example.entity.Skill;
import java.util.Optional;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@ApplicationScoped
public class SkillRepository {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public Optional<Skill> findById(int id) {
        try {
            System.out.println("Repository: Finding skill with ID: " + id);
            Skill skill = entityManager.find(Skill.class, id);
            return Optional.ofNullable(skill);
        } catch (Exception e) {
            System.err.println("Repository: Error finding skill: " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
    }
    
    public List<Skill> findAll() {
        TypedQuery<Skill> query = entityManager.createQuery(
            "SELECT s FROM Skill s", Skill.class);
        return query.getResultList();
    }
    
    public List<Skill> findByType(String type) {
        TypedQuery<Skill> query = entityManager.createQuery(
            "SELECT s FROM Skill s WHERE s.skillType = :type", Skill.class);
        query.setParameter("type", type);
        return query.getResultList();
    }
    
    public Skill save(Skill skill) {
        entityManager.persist(skill);
        entityManager.flush();
        return skill;
    }
    
    public Skill update(Skill skill) {
        return entityManager.merge(skill);
    }
    
    public void deleteById(int id) {
        Skill skill = entityManager.find(Skill.class, id);
        if (skill != null) {
            entityManager.remove(skill);
        }
    }
} 