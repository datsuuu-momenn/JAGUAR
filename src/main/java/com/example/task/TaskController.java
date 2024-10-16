package com.example.task;

import java.util.List;

import jakarta.ejb.EJBException;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@RequestScoped
@Transactional
public class TaskController {

    @PersistenceContext
    private EntityManager em;

    public List<Task> loadAll() {

        List<Task> tasks = em.createNamedQuery(Task.FIND_ALL).getResultList();

        return tasks;
    }

    public Task add(String title) {
        final Task newTask = new Task();
        newTask.setTitle(title);

        try{
        
        this.em.persist(newTask);
        this.em.flush(); // 確実にデータベースに書き込み
        this.em.refresh(newTask); // 最新の状態を取得

        }catch(Exception e){
            System.out.println("aaa");
            System.out.println(e.getMessage());
        }
        
        return newTask;
    }

    public Task delete(Long id) {

        // IDが存在するか確認
        Task task = em.find(Task.class, id);
        if (task == null) {
            throw new EJBException("指定されたIDのタスクが見つかりません: " + id);
        }
        
        Task ref = this.em.getReference(Task.class, id);
        this.em.remove(ref);

        return ref;
        
    }

    public Task update(Long id, String title) {
        try {
            final Task ref = this.em.getReference(Task.class, id);
            ref.setTitle(title);
            return this.em.merge(ref);
        } catch (EntityNotFoundException enf) {
            throw new EJBException(enf);
        }
    }
}
