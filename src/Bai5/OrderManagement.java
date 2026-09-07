package Bai5;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Scanner;

public class OrderManagement {

    private static final String URL =
            "jdbc:mysql://localhost:3306/db_order_management";

    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public static void placeOrder(
            int customerId,
            BigDecimal totalAmount,
            int productId,
            int quantity) {

        Connection conn = null;

        try {
            // 1. Kết nối database
            conn = DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );

            // 2. Tắt AutoCommit
            conn.setAutoCommit(false);

            // =========================================
            // BƯỚC 1: Lấy sản phẩm
            // =========================================

            CallableStatement getProduct =
                    conn.prepareCall("{CALL getProductById(?)}");

            getProduct.setInt(1, productId);

            ResultSet rs = getProduct.executeQuery();

            if (!rs.next()) {
                System.out.println("Không tìm thấy sản phẩm!");
                conn.rollback();
                return;
            }

            int currentStock = rs.getInt("stock");

            System.out.println("Tồn kho hiện tại: " + currentStock);

            // =========================================
            // BƯỚC 2: Kiểm tra tồn kho
            // =========================================

            if (currentStock < quantity) {

                System.out.println("Không đủ hàng trong kho!");
                conn.rollback();
                return;
            }

            // =========================================
            // BƯỚC 3: Thêm đơn hàng
            // =========================================

            CallableStatement placeOrder =
                    conn.prepareCall("{CALL place_order(?,?,?,?)}");

            placeOrder.setInt(1, customerId);
            placeOrder.setBigDecimal(2, totalAmount);
            placeOrder.setInt(3, productId);
            placeOrder.setInt(4, quantity);

            placeOrder.executeUpdate();

            // =========================================
            // BƯỚC 4: Cập nhật stock
            // =========================================

            int newStock = currentStock - quantity;

            CallableStatement saveStock =
                    conn.prepareCall("{CALL saveStockProduct(?,?)}");

            saveStock.setInt(1, productId);
            saveStock.setInt(2, newStock);

            saveStock.executeUpdate();

            // =========================================
            // BƯỚC 5: Commit
            // =========================================

            conn.commit();

            System.out.println("Đặt hàng thành công!");
            System.out.println("Số lượng đã đặt: " + quantity);
            System.out.println("Tồn kho còn lại: " + newStock);

        } catch (SQLException e) {

            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            System.out.println("Đặt hàng thất bại!");
            System.out.println("Transaction đã rollback.");

            e.printStackTrace();

        } finally {

            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Nhập customer ID: ");
        int customerId = scanner.nextInt();

        System.out.print("Nhập product ID: ");
        int productId = scanner.nextInt();

        System.out.print("Nhập số lượng: ");
        int quantity = scanner.nextInt();

        System.out.print("Nhập tổng tiền: ");
        BigDecimal totalAmount = scanner.nextBigDecimal();

        placeOrder(
                customerId,
                totalAmount,
                productId,
                quantity
        );

        scanner.close();
    }
}