package utillity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import connectDB.ConnectDB;
import java.util.Optional;
import java.util.List;
public class CrudTest {

    public static <T> boolean create(T entity) {
        ConnectDB.connect();
        EntityManager em = ConnectDB.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        } finally {
            em.close();
        }
    }


    public static <T> boolean update(T entity) {
        ConnectDB.connect();
        EntityManager em = ConnectDB.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.merge(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        } finally {
            em.close();
        }
    }

    // Xóa theo ID
    public static <T> boolean delete(Class<T> entityClass, Object id) {
        ConnectDB.connect();
        EntityManager em = ConnectDB.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            T entity = em.find(entityClass, id);
            if (entity != null) {
                em.remove(entity);
                transaction.commit();
                return true;
            } else {
                transaction.rollback();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        } finally {
            em.close();
        }
    }

    // Lấy theo ID
    public static <T> Optional<T> findById(Class<T> entityClass, Object id) {
        ConnectDB.connect();
        EntityManager em = ConnectDB.getEntityManager();
        try {
            T entity = em.find(entityClass, id);
            return Optional.ofNullable(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        } finally {
            em.close();
        }
    }

    // Lấy danh sách tất cả
    public static <T> List<T> findAll(Class<T> entityClass) {
        ConnectDB.connect();
        EntityManager em = ConnectDB.getEntityManager();
        try {
            String query = String.format("SELECT e FROM %s e", entityClass.getSimpleName());
            return em.createQuery(query, entityClass).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        } finally {
            em.close();
        }
    }
}
