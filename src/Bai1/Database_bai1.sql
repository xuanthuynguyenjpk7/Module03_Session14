CREATE DATABASE student_management;
USE student_management;

CREATE TABLE students (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL
);

drop table if exists students;
DROP PROCEDURE IF EXISTS add_students;

DELIMITER //

CREATE PROCEDURE add_students(
    IN p_name VARCHAR(100),
    IN p_age INT
)
BEGIN
    INSERT INTO students(name, age)
    VALUES (p_name, p_age);
END //

DELIMITER ;

DESC students;
-- tạo procedure update
DROP PROCEDURE IF EXISTS update_student;

DELIMITER //

CREATE PROCEDURE update_student(
    IN p_id INT,
    IN p_name VARCHAR(100),
    IN p_age INT
)
BEGIN
    UPDATE students
    SET
        name = p_name,
        age = p_age
    WHERE id = p_id;
END //

DELIMITER ;

SELECT * FROM students;

CALL update_student(1, 'Nguyen Van An', 25);

DROP PROCEDURE IF EXISTS delete_students_by_age;

DELIMITER //

CREATE PROCEDURE delete_students_by_age(
    IN p_age INT
)
BEGIN
    DELETE FROM students
    WHERE age < p_age;
END //

DELIMITER ;