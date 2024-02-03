package org.behemothdi.todolist.dao;

import org.behemothdi.todolist.entity.Task;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskDAO {

    private final SessionFactory sessionFactory;

    public TaskDAO(@Autowired SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public List<Task> getAll(int offset, int limit){
        try (Session session = sessionFactory.openSession()) {
            Query<Task> query = session.createQuery("from Task", Task.class);
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            return query.getResultList();
        }
    }

    public int getAllCount(){
        try(Session session = sessionFactory.openSession()) {
            Query<Long> query = session.createQuery("select count(t) from Task t", Long.class);
            return query.uniqueResult().intValue();
        }
    }

    public Task getById(int id){
        try (Session session = sessionFactory.openSession()) {
            Query<Task> query = session.createQuery("from Task where id=:id", Task.class);
            query.setParameter("id", id);
            return query.uniqueResult();
        }
    }

    public void saveOrUpdate(Task task){
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(task);
            transaction.commit();
        }
    }


    public void delete(Task task) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(task);
            transaction.commit();
        }
    }
}
