package com.example.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SkillTest {
    
    private Skill leafBlade;
    private Skill braveBird;
    
    @BeforeEach
    void setUp() {
        leafBlade = new Skill(1, "リーフブレード", 70, 95, 10, SkillType.GRASS, "");
        braveBird = new Skill(2, "ブレイブバード", 120, 85, 15, SkillType.FLYING, "");
    }
    
    @Test
    void testSkillProperties() {
        // 测试叶刃技能
        assertEquals(1, leafBlade.getSkillId());
        assertEquals("リーフブレード", leafBlade.getSkillName());
        assertEquals(70, leafBlade.getDamage());
        assertEquals(95, leafBlade.getAccuracy());
        assertEquals(10, leafBlade.getCriticalRate());
        assertEquals("GRASS", leafBlade.getSkillType());
        
        // 测试勇鸟猛攻技能
        assertEquals(2, braveBird.getSkillId());
        assertEquals("ブレイブバード", braveBird.getSkillName());
        assertEquals(120, braveBird.getDamage());
        assertEquals(85, braveBird.getAccuracy());
        assertEquals(15, braveBird.getCriticalRate());
        assertEquals("FLYING", braveBird.getSkillType());
    }
    
    @Test
    void testInvalidSkillValues() {
        // 测试无效的伤害值
        assertThrows(IllegalArgumentException.class, () -> 
            new Skill(1, "测试技能", -1, 95, 10, SkillType.NORMAL, "")
        );
        
        // 测试无效的命中率
        assertThrows(IllegalArgumentException.class, () -> 
            new Skill(1, "测试技能", 70, 101, 10, SkillType.NORMAL, "")
        );
        
        // 测试无效的暴击率
        assertThrows(IllegalArgumentException.class, () -> 
            new Skill(1, "测试技能", 70, 95, -1, SkillType.NORMAL, "")
        );
    }
    
} 