package com.example.fightSystem.entity;

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
} 