package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        session.createSQLQuery("CREATE TABLE users " +
                "(id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY, name TEXT, lastName TEXT, age INTEGER)").executeUpdate();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        try {
            Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
            session.createSQLQuery("DROP TABLE users").executeUpdate();
            session.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {

        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        session.createSQLQuery("DELETE FROM users WHERE id = " + id).executeUpdate();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        List<User> data = (List<User>) session.createCriteria(User.class).list();
        session.close();
        return data;
    }

    @Override
    public void cleanUsersTable() {

        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        session.createSQLQuery("TRUNCATE TABLE users").executeUpdate();
        session.close();
    }
}
