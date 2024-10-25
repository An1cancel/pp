package jm.task.core.jdbc.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.util.ArrayList;
import java.util.List;



public class UserDaoHibernateImpl implements UserDao {

    private Session session = Util.getSessionFactory().openSession();
//    private Transaction transaction = null;

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        String query = "CREATE TABLE IF NOT EXISTS User (id BIGINT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(50), lastName VARCHAR(50), age TINYINT)";
        Transaction transaction = null;
        try  {
            transaction = session.beginTransaction();
            session.createNativeQuery(query).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }


    @Override
    public void dropUsersTable() {
        String query = "DROP TABLE IF EXISTS User";
        Transaction transaction = null;
        try  {
            transaction = session.beginTransaction();
            session.createNativeQuery(query).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }


    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }


    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }


    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Session session = Util.getSessionFactory().openSession()) {
            users = session.createQuery("FROM User", User.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }


    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
