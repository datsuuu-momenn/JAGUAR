package com.example.fightSystem.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.annotation.PostConstruct;

import com.example.fightSystem.entity.Skill;
import com.example.fightSystem.repository.SkillRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Named
@ApplicationScoped
public class FightController {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Inject
    private SkillRepository skillRepository;
    
    @PostConstruct
    public void init() {
        // データベースの初期化状態を確認
        List<Skill> skills = getAllSkills();
        System.out.println("Database initialized with " + skills.size() + " skills:");
        skills.forEach(skill -> {
            System.out.println("Found skill: " + skill.getSkillId() + " - " + skill.getSkillName());
        });
    }
    
    // 特定のスキルを取得
    public Skill getSkill(int skillId) {
        try {
            System.out.println("Attempting to find skill with ID: " + skillId);
            Skill skill = skillRepository.findById(skillId);
            if (skill == null) {
                System.out.println("No skill found for ID: " + skillId);
            } else {
                System.out.println("Found skill: " + skill.getSkillName());
            }
            return skill;
        } catch (Exception e) {
            System.err.println("Error finding skill: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    // 全てのスキルを取得
    public List<Skill> getAllSkills() {
        TypedQuery<Skill> query = entityManager.createQuery(
            "SELECT s FROM Skill s", Skill.class);
        return query.getResultList();
    }
    
    // タイプ別のスキルを取得
    public List<Skill> getSkillsByType(String type) {
        TypedQuery<Skill> query = entityManager.createQuery(
            "SELECT s FROM Skill s WHERE s.skillType = :type", Skill.class);
        query.setParameter("type", type);
        return query.getResultList();
    }
    
    // スキルの詳細情報を取得
    public Map<String, Object> getSkillDetails(int skillId) {
        Skill skill = getSkill(skillId);
        if (skill == null) {
            return null;
        }
        
        return Map.of(
            "id", skill.getSkillId(),
            "name", skill.getSkillName(),
            "damage", skill.getDamage(),
            "accuracy", skill.getAccuracy(),
            "criticalRate", skill.getCriticalRate(),
            "type", skill.getSkillType(),
            "description", skill.getDescription()
        );
    }
    
    // スキルタイプごとの平均ダメージを計算
    public Map<String, Double> getAverageDamageByType() {
        return getAllSkills().stream()
            .collect(Collectors.groupingBy(
                Skill::getSkillType,
                Collectors.averagingInt(Skill::getDamage)
            ));
    }
    
    // 新しいスキルを追加
    @Transactional
    public void addSkill(Skill skill) {
        entityManager.persist(skill);
    }
    
    // スキルを更新
    @Transactional
    public void updateSkill(Skill skill) {
        entityManager.merge(skill);
    }
    
    // スキルを削除
    @Transactional
    public void deleteSkill(int skillId) {
        Skill skill = getSkill(skillId);
        if (skill != null) {
            entityManager.remove(skill);
        }
    }
} 