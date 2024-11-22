package com.example.fightSystem.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.example.fightSystem.entity.Skill;

@ApplicationScoped
public class SkillRepository {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public Skill findById(int id) {
        try {
            System.out.println("Repository: Finding skill with ID: " + id); // デバッグ出力
            Skill skill = entityManager.find(Skill.class, id);
            if (skill == null) {
                System.out.println("Repository: No skill found for ID: " + id); // デバッグ出力
            } else {
                System.out.println("Repository: Found skill: " + skill.getSkillName()); // デバッグ出力
            }
            return skill;
        } catch (Exception e) {
            System.err.println("Repository: Error finding skill: " + e.getMessage()); // エラー出力
            e.printStackTrace();
            return null;
        }
    }
} 