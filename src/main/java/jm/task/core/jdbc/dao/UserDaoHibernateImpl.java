package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                if (session.createSQLQuery("SHOW TABLES LIKE 'users'").uniqueResult() == null) {
                    session.createSQLQuery("CREATE TABLE IF NOT EXISTS Users(id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                            "name VARCHAR(32), lastName VARCHAR(32), age TINYINT DEFAULT 0)").executeUpdate();
                    transaction.commit();
                }
            } catch (Exception exception) {
                transaction.rollback();
                exception.printStackTrace();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.createSQLQuery("DROP TABLE IF EXISTS Users").executeUpdate();
                transaction.commit();
            } catch (Exception exception) {
                transaction.rollback();
                exception.printStackTrace();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            transaction.commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList;
        try (Session session = Util.getSessionFactory().openSession()) {
            CriteriaQuery<User> criteriaQuery = session.getCriteriaBuilder().createQuery(User.class);
            criteriaQuery.from(User.class);
            userList = session.createQuery(criteriaQuery).getResultList();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("DELETE FROM User");
            query.executeUpdate();
            transaction.commit();
        }
    }
}
