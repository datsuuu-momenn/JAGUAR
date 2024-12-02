package com.example.repository;

import com.example.entity.SkillType;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class SkillTypeRepository {

    // 获取所有技能类型
    public List<SkillType> findAll() {
        return Arrays.asList(SkillType.values());
    }

    // 根据代码查找技能类型
    public Optional<SkillType> findByCode(String code) {
        return Arrays.stream(SkillType.values())
                .filter(type -> type.name().equals(code))
                .findFirst();
    }


    // 检查代码是否存在
    public boolean existsByCode(String code) {
        return Arrays.stream(SkillType.values())
                .anyMatch(type -> type.getTypeName().equals(code));
    }


} 