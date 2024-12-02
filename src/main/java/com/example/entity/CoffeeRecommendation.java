package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "coffee_recommendation")
@Getter
@Setter
public class CoffeeRecommendation {
    @Id
    @Column(name = "coffee_type")
    private String coffeeType;

    @Column(name = "recommended_bean", nullable = false)
    private String recommendedBean;

    @Column(name = "bean_description", nullable = false, length = 1000)
    private String beanDescription;
} 