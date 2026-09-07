package Bai4;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Management {

    // =========================
    // ACCOUNT
    // =========================
    static class Account {
        private int id;
        private double balance;

        public Account() {
        }

        public Account(int id, double balance) {
            this.id = id;
            this.balance = balance;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

        @Override
        public String toString() {
            return "Account{" +
                    "id=" + id +
                    ", balance=" + balance +
                    '}';
        }
    }


    // =========================
    // BANK MANAGEMENT
    // =========================
    static class BankManagement {

        private static final String URL =
                "jdbc:mysql://localhost:3306/bank_management";

        private static final String USER = "root";

        private static final String PASSWORD = "123456";


        // Kết nối database
        public Connection getConnection() throws SQLException {
            return DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );
        }


        // Chuyển tiền
        public boolean transferFunds(
                int fromAccount,
                int toAccount,
                double amount
        ) {

            Connection connection = null;
            CallableStatement callableStatement = null;

            try {

                // Kết nối database
                connection = getConnection();

                // Tắt Auto Commit để sử dụng Transaction
                connection.setAutoCommit(false);

                // Gọi Stored Procedure
                String sql = "{CALL transfer_funds(?, ?, ?)}";

                callableStatement =
                        connection.prepareCall(sql);

                // Truyền tham số
                callableStatement.setInt(1, fromAccount);
                callableStatement.setInt(2, toAccount);
                callableStatement.setDouble(3, amount);

                // Thực hiện procedure
                callableStatement.execute();

                // Thành công → commit
                connection.commit();

                return true;

            } catch (SQLException e) {

                // Có lỗi → rollback
                if (connection != null) {
                    try {
                        connection.rollback();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }

                System.out.println(
                        "Lỗi: " + e.getMessage()
                );

                return false;

            } finally {

                // Đóng CallableStatement
                if (callableStatement != null) {
                    try {
                        callableStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                // Đóng Connection
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    // =========================
    // MAIN
    // =========================
    static class Main {

        public static void main(String[] args) {

            Scanner scanner = new Scanner(System.in);

            BankManagement bankManagement =
                    new BankManagement();

            System.out.println(
                    "===== QUẢN LÝ TÀI KHOẢN NGÂN HÀNG ====="
            );

            System.out.print(
                    "Nhập ID tài khoản chuyển tiền: "
            );
            int fromAccount = scanner.nextInt();

            System.out.print(
                    "Nhập ID tài khoản nhận tiền: "
            );
            int toAccount = scanner.nextInt();

            System.out.print(
                    "Nhập số tiền cần chuyển: "
            );
            double amount = scanner.nextDouble();


            // Kiểm tra số tiền
            if (amount <= 0) {

                System.out.println(
                        "Số tiền phải lớn hơn 0!"
                );

            } else {

                boolean result =
                        bankManagement.transferFunds(
                                fromAccount,
                                toAccount,
                                amount
                        );

                if (result) {

                    System.out.println(
                            "Chuyển tiền thành công!"
                    );

                } else {

                    System.out.println(
                            "Chuyển tiền thất bại!"
                    );
                }
            }

            scanner.close();
        }
    }
}
