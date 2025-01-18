/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utillity;

/**
 *
 * @author pc
 */
import jakarta.persistence.EntityManager;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import connectDB.ConnectDB;
import model.*;
import jakarta.persistence.Query;
import java.util.Optional;

public class IDGeneratorUtility {

    // Hàm generate ID
    public static String generateIDWithCreatedDate(String prefix, String entityName, String idFieldName, String dateFieldName) {
        // Lấy thời gian hiện tại
        LocalDateTime currentDate = LocalDateTime.now();
        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("ddMMyy"));
        String newID = null;
        String formattedSequence = null;
        int sequence = 1; // Bắt đầu sequence từ 1

        // Sử dụng JPA EntityManager để truy vấn ID
        ConnectDB.connect();
        EntityManager em = ConnectDB.getEntityManager();
        try {
            String queryStr = String.format(
                    "SELECT e.%s FROM %s e WHERE YEAR(e.%s) = ?1 "
                    + "AND MONTH(e.%s) = ?2 "
                    + "AND DAY(e.%s) = ?3 ORDER BY e.%s DESC",
                    idFieldName, entityName, dateFieldName, dateFieldName, dateFieldName, idFieldName
            );

            // Thực hiện truy vấn để lấy ID gần nhất
            List<String> result = em.createNativeQuery(queryStr)
                    .setParameter(1, currentDate.getYear())
                    .setParameter(2, currentDate.getMonthValue())
                    .setParameter(3, currentDate.getDayOfMonth())
                    .setMaxResults(1)
                    .getResultList();

            if (!result.isEmpty()) {
                String id = result.get(0);
                String orderDate = id.substring(1, 7); // Lấy phần ngày tháng trong ID

                // So sánh ngày tháng trong ID với ngày hiện tại
                if (!orderDate.equals(formattedDate)) {
                    formattedSequence = String.format("%04d", sequence++);
                } else {
                    // Nếu cùng ngày, tăng sequence lên
                    sequence = Integer.parseInt(id.substring(7));
                    formattedSequence = String.format("%04d", ++sequence);
                }
            } else {
                // Nếu không có kết quả nào từ truy vấn, bắt đầu từ sequence 1
                formattedSequence = String.format("%04d", sequence++);
            }

            newID = prefix + formattedDate + formattedSequence;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return newID;
    }

    // Hàm sinh ra ID với prefix và số thứ tự (lớn nhất trong database + 1)
    public static String generateSimpleID(String prefix, String entityName, String idFieldName) {
        ConnectDB.connect();
        EntityManager em = ConnectDB.getEntityManager();
        int sequence = 1;

        try {
            String sql = String.format("select max(cast(substring(e.%s, len(?1) + 1, 4) as int)) from %s e", idFieldName, entityName);

            Query q = em.createNativeQuery(sql);
            q.setParameter(1, prefix);

            Integer maxSequence = (Integer) Optional.ofNullable(q.getSingleResult()).orElse(0);
            sequence = maxSequence + 1;

            return prefix + String.format("%04d", sequence);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
//    public static String generateTableID(FloorEntity f) {
//        ConnectDB.connect();
//        EntityManager em = ConnectDB.getEntityManager();
//        int sequence = 1;
//
//        try {
//            String prefix = f.getFloorId() + "T";
//            String sql = String.format("select max(cast(substring(e.table_id, len(?1) + 1, 4) as int)) from tables e");
//
//            Query q = em.createNativeQuery(sql);
//            q.setParameter(1, prefix);
//
//            Integer maxSequence = (Integer) Optional.ofNullable(q.getSingleResult()).orElse(0);
//            sequence = maxSequence + 1;
//
//            return prefix + String.format("%04d", sequence);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public static void main(String[] args) {
        // Ví dụ sử dụng hàm generateID
        
        //categories
//        System.out.println(new CategoryEntity());
        
        //customers
//        System.out.println(new CustomerEntity());

        //employees
//        System.out.println(new EmployeeEntity());

        //floors
//        System.out.println(new FloorEntity());
        
        //Item
//        System.out.println(new ItemEntity());
        
        //Order
//        System.out.println(new OrderEntity());
        
        //promotions
        System.out.println(new PromotionEntity());
        
        //roles
//        System.out.println(new RoleEntity());
        
        //tables
//        System.out.println((new TableEntity(new FloorEntity("F0001"))));


    }

}