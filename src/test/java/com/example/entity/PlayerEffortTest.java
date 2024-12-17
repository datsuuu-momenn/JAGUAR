package com.example.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerEffortTest {
    
    private PlayerEffort playerEffort;
    
    @BeforeEach
    void setUp() {
        playerEffort = new PlayerEffort();
        playerEffort.setId(1L);
        playerEffort.setPlayerId("PLAYER001");
        playerEffort.setEffortValue(85.5);
        playerEffort.setRound(1);
        playerEffort.setTotalEffort(100);
    }
    
    @Test
    void testPlayerEffortProperties() {
        // 测试所有属性的getter方法
        assertEquals(1L, playerEffort.getId());
        assertEquals("PLAYER001", playerEffort.getPlayerId());
        assertEquals(85.5, playerEffort.getEffortValue(), 0.001);
        assertEquals(1, playerEffort.getRound());
        assertEquals(100, playerEffort.getTotalEffort());
    }
    
    @Test
    void testPlayerEffortSetters() {
        PlayerEffort newPlayerEffort = new PlayerEffort();
        
        // 测试setter方法
        newPlayerEffort.setId(2L);
        newPlayerEffort.setPlayerId("PLAYER002");
        newPlayerEffort.setEffortValue(92.3);
        newPlayerEffort.setRound(2);
        newPlayerEffort.setTotalEffort(150);
        
        // 验证设置的值
        assertEquals(2L, newPlayerEffort.getId());
        assertEquals("PLAYER002", newPlayerEffort.getPlayerId());
        assertEquals(92.3, newPlayerEffort.getEffortValue(), 0.001);
        assertEquals(2, newPlayerEffort.getRound());
        assertEquals(150, newPlayerEffort.getTotalEffort());
    }
    
    @Test
    void testDefaultConstructor() {
        PlayerEffort emptyPlayerEffort = new PlayerEffort();
        
        // 验证默认值
        assertNull(emptyPlayerEffort.getId());
        assertNull(emptyPlayerEffort.getPlayerId());
        assertEquals(0.0, emptyPlayerEffort.getEffortValue(), 0.001);
        assertEquals(0, emptyPlayerEffort.getRound());
        assertEquals(0, emptyPlayerEffort.getTotalEffort());
    }
    
}