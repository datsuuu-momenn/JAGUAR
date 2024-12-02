package com.example.repository;

import com.example.entity.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Optional<User> findById(Long id) {
        User user = entityManager.find(User.class, id);
        return Optional.ofNullable(user);
    }

    public List<User> findAll() {
        return entityManager.createQuery("SELECT u FROM User u", User.class)
                .getResultList();
    }

    @Transactional
    public User create(User user) {
        entityManager.persist(user);
        return user;
    }

    @Transactional
    public User update(User user) {
        if (user.getId() == null || !findById(user.getId()).isPresent()) {
            throw new IllegalArgumentException("用户不存在");
        }
        return entityManager.merge(user);
    }

    @Transactional
    public void delete(Long id) {
        User user = findById(id)
                .orElseThrow(() -> new IllegalArgumentException("用户不存在"));
        entityManager.remove(user);
    }

    // 根据用户名查找用户
    public Optional<User> findByUsername(String username) {
        List<User> users = entityManager
                .createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", username)
                .getResultList();
        return users.stream().findFirst();
    }

} 