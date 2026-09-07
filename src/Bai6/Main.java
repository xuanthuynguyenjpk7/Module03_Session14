package Bai6;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Nhập ID nhân viên: ");
        int employeeId = sc.nextInt();

        System.out.print("Nhập ID dự án: ");
        int projectId = sc.nextInt();

        EmployeeProjectManagement.assignEmployeeToProject(
                employeeId,
                projectId
        );

        sc.close();
    }
}
