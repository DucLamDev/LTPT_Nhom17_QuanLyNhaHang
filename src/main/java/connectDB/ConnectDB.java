package connectDB;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ConnectDB {
    private static EntityManagerFactory emf;
    private static EntityManager em;

    public static void connect() {
        if (emf == null || !emf.isOpen()) {
            emf = Persistence.createEntityManagerFactory("nhom17"); // "nhom17" là tên persistence-unit trong persistence.xml
        }
        if (em == null || !em.isOpen()) {
            em = emf.createEntityManager();
        }
    }


    public static EntityManager getEntityManager() {
        return em;
    }

    public static void close() {
        if (em != null && em.isOpen()) {
            em.close();
        }
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
