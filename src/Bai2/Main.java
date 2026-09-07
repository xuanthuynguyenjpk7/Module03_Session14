package Bai2;

public class Main {

    public static void main(String[] args) {

        StudentDAO studentDAO = new StudentDAO();

        studentDAO.updateStudent(
                1,
                "Nguyen Van An",
                25
        );
    }
}