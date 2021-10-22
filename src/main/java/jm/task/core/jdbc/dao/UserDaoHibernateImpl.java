package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        try {
            Session session = Util.getSessionFactory().openSession();
            session.createSQLQuery("CREATE TABLE users " +
                    "(id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY, name TEXT, lastName TEXT, age INTEGER)").executeUpdate();
            session.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            Session session = Util.getSessionFactory().openSession();
            session.createSQLQuery("DROP TABLE users").executeUpdate();
            session.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {

        Session session = Util.getSessionFactory().openSession();
        String hql = "delete User where id = :id";
        Query q = session.createQuery(hql).setParameter("id", id);
        q.executeUpdate();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.getSessionFactory().openSession();
        List<User> data = (List<User>) session.createQuery("from User").list();
        session.close();
        return data;
    }

    @Override
    public void cleanUsersTable() {

        Session session = Util.getSessionFactory().openSession();
        session.createSQLQuery("TRUNCATE TABLE users").executeUpdate();
        session.close();
    }
}
