package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "SKILLS")
@Getter
@Setter
public class Skill {
    @Id
    @Column(name = "skill_id")
    private int skillId;

    @Column(name = "skill_name")
    private String skillName;

    @Column(name = "damage")
    private int damage;

    @Column(name = "accuracy")
    private int accuracy;

    @Column(name = "critical_rate")
    private int criticalRate;

    @Column(name = "skill_type")
    private String skillType;

    @Column(name = "description")
    private String description;

    
    // 默认构造函数（JPA需要）
    public Skill() {
    }

    // 带参数的构造函数
    public Skill(int skillId, String skillName, int damage, int accuracy, int criticalRate, SkillType skillType, String description) {
        validateSkillParameters(damage, accuracy, criticalRate);
        
        this.skillId = skillId;
        this.skillName = skillName;
        this.damage = damage;
        this.accuracy = accuracy;
        this.criticalRate = criticalRate;
        this.skillType = skillType.toString();
        this.description = description;
    }

    private void validateSkillParameters(int damage, int accuracy, int criticalRate) {
        if (damage < 0) {
            throw new IllegalArgumentException("伤害值不能为负数");
        }
        if (accuracy < 0 || accuracy > 100) {
            throw new IllegalArgumentException("命中率必须在0-100之间");
        }
        if (criticalRate < 0) {
            throw new IllegalArgumentException("暴击率不能为负数");
        }
    }
} 