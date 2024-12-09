package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "player_efforts")
public class PlayerEffort {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "player_id")
    private String playerId;

    @Column(name = "effort_value")
    private double effortValue;

    @Column(name = "round")
    private int round;
    
    @Column(name = "total_effort")
    private int totalEffort;            
} 