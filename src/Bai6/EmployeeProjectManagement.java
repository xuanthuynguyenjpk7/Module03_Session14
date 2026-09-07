package Bai6;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class EmployeeProjectManagement {

    public static void assignEmployeeToProject(
            int employeeId,
            int projectId) {

        Connection conn = null;
        CallableStatement cs = null;

        try {
            conn = ConnectionDB.getConnection();

            // Tắt auto commit
            conn.setAutoCommit(false);

            // Gọi stored procedure
            cs = conn.prepareCall(
                    "{CALL assign_employee_to_project(?, ?)}"
            );

            cs.setInt(1, employeeId);
            cs.setInt(2, projectId);

            cs.executeUpdate();

            // Thành công -> commit
            conn.commit();

            System.out.println(
                    "Gán nhân viên vào dự án thành công!"
            );

        } catch (SQLException e) {

            // Có lỗi -> rollback
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            System.out.println(
                    "Gán nhân viên vào dự án thất bại!"
            );

            System.out.println("Lỗi: " + e.getMessage());

        } finally {

            try {
                if (cs != null) {
                    cs.close();
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