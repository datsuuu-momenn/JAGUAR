package com.example.repository;

import com.example.entity.JsfOperation;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class JsfOperationRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Optional<JsfOperation> findById(Long id) {
        JsfOperation operation = entityManager.find(JsfOperation.class, id);
        return Optional.ofNullable(operation);
    }

    public List<JsfOperation> findAll() {
        return entityManager.createQuery("SELECT o FROM JsfOperation o", JsfOperation.class)
                .getResultList();
    }

    @Transactional
    public JsfOperation save(JsfOperation operation) {
        entityManager.persist(operation);
        return operation;
    }

    @Transactional
    public JsfOperation update(JsfOperation operation) {
        if (operation.getId() == null || !findById(operation.getId()).isPresent()) {
            throw new IllegalArgumentException("操作不存在");
        }
        return entityManager.merge(operation);
    }

    @Transactional
    public void delete(Long id) {
        JsfOperation operation = findById(id)
                .orElseThrow(() -> new IllegalArgumentException("操作不存在"));
        entityManager.remove(operation);
    }



    // 根据类型查找操作
    public List<JsfOperation> findByType(String type) {
        return entityManager
                .createQuery("SELECT o FROM JsfOperation o WHERE o.type = :type", JsfOperation.class)
                .setParameter("type", type)
                .getResultList();
    }
} 