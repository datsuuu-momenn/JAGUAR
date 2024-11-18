package com.example.user;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.Valid;

@Stateless
public class UserController {
    
    @PersistenceContext
    private EntityManager em;
    
    public User createUser(@Valid String username, @Valid String password) {
        User user = new User(username, password);
        em.persist(user);
        System.out.println("User created: " + user);
        return user;
    }
    
    public User findUserById(Long id) {
        System.out.println("Finding user by ID: " + id);
        return em.find(User.class, id);
    }
    
    public User findUserByUsername(String username) {
        try {
            User user = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
            System.out.println("User found: " + user);
            return user;
        } catch (Exception e) {
            System.out.println("User not found: " + e.getMessage());
            return null;
        }
    }
} 