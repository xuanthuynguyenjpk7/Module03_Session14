package Bai3;

import Bai1.ConnectionDB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class StudentManagement {

    public void deleteStudentsByAge(int age) {

        Connection conn = null;
        CallableStatement cstmt = null;

        try {
            // 1. Kết nối database
            conn = ConnectionDB.getConnection();

            // 2. Tắt Auto Commit
            conn.setAutoCommit(false);

            // 3. Gọi Stored Procedure
            String sql = "{CALL delete_students_by_age(?)}";
            cstmt = conn.prepareCall(sql);

            // 4. Truyền tuổi vào Procedure
            cstmt.setInt(1, age);

            // 5. Thực hiện DELETE
            int result = cstmt.executeUpdate();

            // 6. Commit transaction
            conn.commit();

            // 7. Thông báo số lượng đã xóa
            System.out.println(
                    "Đã xóa " + result + " sinh viên có tuổi nhỏ hơn " + age
            );

        } catch (SQLException e) {

            // Có lỗi -> Rollback
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            System.out.println("Xóa sinh viên thất bại!");
            System.out.println("Lỗi: " + e.getMessage());

        } finally {

            // Đóng CallableStatement
            try {
                if (cstmt != null) {
                    cstmt.close();
                }

                // Đóng Connection
                if (conn != null) {
                    conn.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}