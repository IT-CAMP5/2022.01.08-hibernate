package pl.camp.it.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import java.util.List;

public class Main {
    public static SessionFactory sessionFactory = null;

    public static void main(String[] args) {
        Main.sessionFactory = new Configuration().configure().buildSessionFactory();

        User user = new User();
        user.setId(0);
        user.setLogin("admin3");
        user.setPassword("admin3");

        //saveUser(user);

        //System.out.println(user.getId());

        //user.setLogin("admin4");

        //modifyUser(user);

        User user1 = new User();
        user1.setLogin("admin5");
        user1.setPassword("admin6");

        //modifyUser(user1);

        //persistUser(user);

        System.out.println(getAllUsers());
        System.out.println(getUserById(50));
    }

    public static void saveUser(User user) {
        Session session = Main.sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(user);
            tx.commit();
        } catch (Exception e) {
            if(tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
    }

    public static void modifyUser(User user) {
        Session session = Main.sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(user);
            tx.commit();
        } catch (Exception e) {
            if(tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
    }

    public static void deleteUser(User user) {
        Session session = Main.sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(user);
            tx.commit();
        } catch (Exception e) {
            if(tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
    }

    public static void persistUser(User user) {
        Session session = Main.sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.saveOrUpdate(user);
            tx.commit();
        } catch (Exception e) {
            if(tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
    }

    public static List<User> getAllUsers() {
        Session session = Main.sessionFactory.openSession();
        Query<User> query = session.createQuery("FROM pl.camp.it.hibernate.User");
        List<User> users = query.getResultList();
        session.close();
        return users;
    }

    public static User getUserById(int id) {
        Session session = Main.sessionFactory.openSession();
        Query<User> query = session.createQuery("FROM pl.camp.it.hibernate.User WHERE id = :id");
        query.setParameter("id", id);
        try {
            User user = query.getSingleResult();
            session.close();
            return user;
        } catch (NoResultException e) {
            session.close();
            return null;
        }

    }
}
