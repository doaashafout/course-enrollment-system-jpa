CREATE DATABASE IF NOT EXISTS enrollment_system;
USE enrollment_system;

CREATE TABLE IF NOT EXISTS students (
    student_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS courses (
    course_id INT PRIMARY KEY AUTO_INCREMENT,
    course_name VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS enrollment (
    enrollment_id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    course_id INT NOT NULL,
    enrollment_date VARCHAR(20) NOT NULL,
    UNIQUE (student_id, course_id)
);

-- Sample data
INSERT INTO students (name) VALUES ('Ahmed Ali'), ('Sara Mohamed'), ('Omar Hassan');
INSERT INTO courses (course_name) VALUES ('Mathematics'), ('Physics'), ('Computer Science');
