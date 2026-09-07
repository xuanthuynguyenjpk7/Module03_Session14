package Bai4;

import java.util.Scanner;

public class Main {

    // Account
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
    }


    // BankManagement
    static class BankManagement {

        public boolean transferFunds(
                int fromAccount,
                int toAccount,
                double amount
        ) {
            // code transfer ở đây
            return true;
        }
    }


    // Main
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        BankManagement bankManagement = new BankManagement();

        System.out.println("===== QUẢN LÝ TÀI KHOẢN NGÂN HÀNG =====");

        System.out.print("Nhập ID tài khoản chuyển: ");
        int fromAccount = scanner.nextInt();

        System.out.print("Nhập ID tài khoản nhận: ");
        int toAccount = scanner.nextInt();

        System.out.print("Nhập số tiền cần chuyển: ");
        double amount = scanner.nextDouble();

        boolean result = bankManagement.transferFunds(
                fromAccount,
                toAccount,
                amount
        );

        if (result) {
            System.out.println("Chuyển tiền thành công!");
        } else {
            System.out.println("Chuyển tiền thất bại!");
        }

        scanner.close();
    }
}