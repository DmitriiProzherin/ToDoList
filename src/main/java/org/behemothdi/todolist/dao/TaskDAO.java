package org.behemothdi.todolist.dao;

import org.behemothdi.todolist.entity.Task;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class TaskDAO {

    private final SessionFactory sessionFactory;

    public TaskDAO(@Autowired SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<Task> getAll(int offset, int limit){
        try (Session session = sessionFactory.openSession()) {
            Query<Task> query = session.createQuery("from Task", Task.class);
            query.setFirstResult(offset);
            query.setMaxResults(limit);
            return query.getResultList();
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public int getAllCount(){
        try(Session session = sessionFactory.openSession()) {
            Query<Long> query = session.createQuery("select count(t) from Task t", Long.class);
            return query.uniqueResult().intValue();
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Task getById(int id){
        try (Session session = sessionFactory.openSession()) {
            Query<Task> query = session.createQuery("from Task where id=:id", Task.class);
            query.setParameter("id", id);
            return query.uniqueResult();
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateOrCreate(Task task){
        try (Session session = sessionFactory.openSession()) {
            session.persist(task);
        }
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Task task) {
        try (Session session = sessionFactory.openSession()) {
            session.delete(task);
          //  session.flush();
        }
    }
}
