package com.example.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SkillTypeTest {

    @Test
    void testGetTypeName() {
        // 测试每个枚举值的 typeName 是否正确
        assertEquals("草", SkillType.GRASS.getTypeName());
        assertEquals("飛行", SkillType.FLYING.getTypeName());
        assertEquals("通常", SkillType.NORMAL.getTypeName());
    }

    @Test
    void testEnumValues() {
        // 测试枚举值的数量
        assertEquals(3, SkillType.values().length);
        
        // 测试枚举值的存在性
        assertNotNull(SkillType.valueOf("GRASS"));
        assertNotNull(SkillType.valueOf("FLYING"));
        assertNotNull(SkillType.valueOf("NORMAL"));
    }
} 