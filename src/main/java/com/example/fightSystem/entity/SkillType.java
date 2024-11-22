package com.example.fightSystem.entity;

public enum SkillType {
    GRASS("草"),    // 草タイプ
    FLYING("飛行"), // 飛行タイプ
    NORMAL("通常"); // ノーマルタイプ
    
    private final String typeName;
    
    SkillType(String typeName) {
        this.typeName = typeName;
    }
    
    public String getTypeName() {
        return typeName;
    }
} 