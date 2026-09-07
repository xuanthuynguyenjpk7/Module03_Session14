package Bai2;

import Bai1.ConnectionDB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class StudentDAO {

    public void updateStudent(int id, String name, int age) {

        Connection conn = null;
        CallableStatement cstmt = null;

        try {
            conn = ConnectionDB.getConnection();

            conn.setAutoCommit(false);

            String sql = "{CALL update_student(?, ?, ?)}";
            cstmt = conn.prepareCall(sql);

            cstmt.setInt(1, id);
            cstmt.setString(2, name);
            cstmt.setInt(3, age);

            int result = cstmt.executeUpdate();

            if (result > 0) {
                conn.commit();
                System.out.println("Cập nhật sinh viên thành công!");
            } else {
                conn.rollback();
                System.out.println("Không tìm thấy sinh viên!");
            }

        } catch (SQLException e) {

            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            System.out.println("Cập nhật sinh viên thất bại!");
            System.out.println("Lỗi: " + e.getMessage());

        } finally {

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
