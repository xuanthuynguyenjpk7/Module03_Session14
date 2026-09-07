package Bai1;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;

public class StudentManagement {

    public void addStudents() {

        Connection conn = null;
        CallableStatement cstmt = null;

        try {
            // 1. Kết nối database
            conn = ConnectionDB.getConnection();

            // 2. Tắt auto commit
            conn.setAutoCommit(false);

            // 3. Gọi stored procedure
            String sql = "{CALL add_students(?, ?)}";
            cstmt = conn.prepareCall(sql);

            // Sinh viên 1
            cstmt.setString(1, "Nguyen Van A");
            cstmt.setInt(2, 20);
            cstmt.executeUpdate();

            // Sinh viên 2
            cstmt.setString(1, "Tran Thi B");
            cstmt.setInt(2, 21);
            cstmt.executeUpdate();

            // Sinh viên 3
            cstmt.setString(1, "Le Van C");
            cstmt.setInt(2, 22);
            cstmt.executeUpdate();

            // 4. Nếu tất cả thành công -> commit
            conn.commit();

            System.out.println("Thêm sinh viên thành công!");

        } catch (SQLException e) {

            // Nếu có lỗi -> rollback
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            System.out.println("Thêm sinh viên thất bại!");
            System.out.println("Lỗi: " + e.getMessage());

        } finally {

            // Đóng tài nguyên
            try {
                if (cstmt != null) {
                    cstmt.close();
                }

                if (conn != null) {
                    conn.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
