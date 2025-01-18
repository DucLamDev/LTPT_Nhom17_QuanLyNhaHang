import connectDB.ConnectDB;
import jakarta.persistence.EntityManager;
import model.CategoryEntity;

public class Main {
    public static void main(String[] args) {
        ConnectDB.connect();

        EntityManager em = ConnectDB.getEntityManager();
        CategoryEntity category = new CategoryEntity("New Category", "Description of the new category");

        em.getTransaction().begin();
        em.persist(category);
        em.getTransaction().commit();

        System.out.println("Category saved: " + category.getCategoryId());

        ConnectDB.close();
    }
}
