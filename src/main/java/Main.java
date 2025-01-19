import connectDB.ConnectDB;
import jakarta.persistence.EntityManager;
import model.CategoryEntity;
import utillity.CrudTest;

import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ConnectDB.connect();

//        EntityManager em = ConnectDB.getEntityManager();
//        CategoryEntity category = new CategoryEntity("New Category", "Description of the new category");
//
//        em.getTransaction().begin();
//        em.persist(category);
//        em.getTransaction().commit();
//
//        System.out.println("Category saved: " + category.getCategoryId());
//
//        ConnectDB.close();

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            // Hiển thị menu
            System.out.println("=== MENU CRUD ===");
            System.out.println("1. Thêm mới danh mục");
            System.out.println("2. Cập nhật danh mục");
            System.out.println("3. Lấy danh sách tất cả danh mục");
            System.out.println("4. Xóa danh mục");
            System.out.println("5. Thoát");
            System.out.print("Chọn thao tác (1-5): ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Đọc ký tự Enter sau khi nhập số

            switch (choice) {
                case 1:
                    // Thêm mới danh mục (Create)
                    System.out.print("Nhập tên danh mục: ");
                    String name = scanner.nextLine();
                    System.out.print("Nhập mô tả danh mục: ");
                    String description = scanner.nextLine();
                    CategoryEntity newCategory = new CategoryEntity(name, description);
                    boolean isCreated = CrudTest.create(newCategory);
                    System.out.println("Trạng thái tạo danh mục: " + (isCreated ? "Thành công" : "Thất bại"));
                    break;

                case 2:
                    // Cập nhật danh mục (Update)
                    System.out.print("Nhập ID danh mục cần cập nhật: ");
                    String categoryIdToUpdate = scanner.nextLine();
                    Optional<CategoryEntity> optionalCategory = CrudTest.findById(CategoryEntity.class, categoryIdToUpdate);
                    if (optionalCategory.isPresent()) {
                        CategoryEntity categoryToUpdate = optionalCategory.get();
                        System.out.print("Nhập tên mới cho danh mục: ");
                        String newName = scanner.nextLine();
                        categoryToUpdate.setName(newName);
                        boolean isUpdated = CrudTest.update(categoryToUpdate);
                        System.out.println("Trạng thái cập nhật danh mục: " + (isUpdated ? "Thành công" : "Thất bại"));
                    } else {
                        System.out.println("Không tìm thấy danh mục để cập nhật.");
                    }
                    break;

                case 3:
                    // Lấy danh sách tất cả các danh mục (Read All)
                    System.out.println("Danh sách tất cả các danh mục:");
                    CrudTest.findAll(CategoryEntity.class).forEach(category ->
                            System.out.println("Danh mục: " + category.getCategoryId() + " - " + category.getName())
                    );
                    break;

                case 4:
                    // Xóa danh mục (Delete)
                    System.out.print("Nhập ID danh mục cần xóa: ");
                    String categoryIdToDelete = scanner.nextLine();
                    boolean isDeleted = CrudTest.delete(CategoryEntity.class, categoryIdToDelete);
                    System.out.println("Trạng thái xóa danh mục: " + (isDeleted ? "Thành công" : "Thất bại"));
                    break;

                case 5:
                    // Thoát
                    System.out.println("Thoát chương trình.");
                    break;

                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
                    break;
            }
        } while (choice != 5);

        scanner.close();
    }


}
