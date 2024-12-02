package com.example.controller;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.Valid;

import java.util.List;

import com.example.entity.User;

import jakarta.ejb.EJBException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.enterprise.context.RequestScoped;


@Transactional
@RequestScoped
public class UserController {
    
    @PersistenceContext
    private EntityManager em;
    
    public User createUser(@Valid String username, @Valid String password) {
        User user = new User(username, password);
        this.em.persist(user);
        this.em.flush();
        this.em.refresh(user);
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

    public User update(Long id, String username, String password) {
        try {
            final User ref = this.em.getReference(User.class, id);
            ref.setUsername(username);
            ref.setPassword(password);
            return this.em.merge(ref);
        } catch (EntityNotFoundException enf) {
            throw new EJBException(enf);
        }
    }

    public List<User> loadAllUsers() {
        System.out.println("loadAllUsers method called in controller");
        List<User> users = em.createNamedQuery(User.FIND_ALLUsers).getResultList();
        return users;
    }
} 