package com.example.controller;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import jakarta.annotation.PostConstruct;

import com.example.entity.Skill;
import com.example.repository.SkillRepository;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@Named
@ApplicationScoped
public class FightController {
    
    @Inject
    private SkillRepository skillRepository;
    
    @PostConstruct
    public void init() {
        List<Skill> skills = skillRepository.findAll();
        System.out.println("Database initialized with " + skills.size() + " skills:");
        skills.forEach(skill -> {
            System.out.println("Found skill: " + skill.getSkillId() + " - " + skill.getSkillName());
        });
    }
    
    public Skill getSkill(int skillId) {
        try {
            System.out.println("Attempting to find skill with ID: " + skillId);
            return skillRepository.findById(skillId)
                .orElseGet(() -> {
                    System.out.println("No skill found for ID: " + skillId);
                    return null;
                });
        } catch (Exception e) {
            System.err.println("Error finding skill: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }
    
    public List<Skill> getSkillsByType(String type) {
        return skillRepository.findByType(type);
    }
    
    public Map<String, Object> getSkillDetails(int skillId) {
        return skillRepository.findById(skillId)
            .map(skill -> new HashMap<String, Object>() {{
                put("id", skill.getSkillId());
                put("name", skill.getSkillName());
                put("damage", skill.getDamage());
                put("accuracy", skill.getAccuracy());
                put("criticalRate", skill.getCriticalRate());
                put("type", skill.getSkillType());
                put("description", skill.getDescription());
            }})
            .orElse(null);
    }
    
    public Map<String, Double> getAverageDamageByType() {
        return getAllSkills().stream()
            .collect(Collectors.groupingBy(
                Skill::getSkillType,
                Collectors.averagingInt(Skill::getDamage)
            ));
    }
    
    @Transactional
    public void addSkill(Skill skill) {
        skillRepository.save(skill);
    }
    
    @Transactional
    public void updateSkill(Skill skill) {
        skillRepository.update(skill);
    }
    
    @Transactional
    public void deleteSkill(int skillId) {
        skillRepository.deleteById(skillId);
    }
} 