# Course Enrollment System (JPA)

A **JavaFX** desktop application for managing student course enrollments, built with **JPA (EclipseLink)** and **MySQL**.  
This project was developed as a university assignment following clean MVC architecture and JPA best practices.

---

## Features

- **Add Enrollment** – Enroll a student in a course with a selected date  
- **Update Enrollment** – Modify existing enrollment details  
- **Delete Enrollment** – Remove an enrollment record  
- **View All Enrollments** – Display all records in a TableView  
- **Search by Student** – Filter enrollments by student ID  
- **Duplicate Prevention** – Prevents enrolling the same student in the same course twice  
- **Intuitive UI** – Sidebar layout with ComboBoxes, DatePicker, and action buttons  

---

## Tech Stack

| Layer       | Technology                  |
|-------------|-----------------------------|
| Language    | Java 8+                     |
| UI          | JavaFX (FXML + CSS)         |
| ORM         | JPA 2.2 / EclipseLink 2.5   |
| Database    | MySQL                       |
| Architecture| MVC (Model-View-Controller) |

---

## Project Structure

```
src/
├── app/
│   └── Main.java                          # Application entry point
├── config/
│   └── JPAUtil.java                       # JPA EntityManagerFactory singleton
├── controllers/
│   └── EnrollmentController.java          # UI logic and event handling
├── dao/
│   ├── CourseDAO.java                     # Course data access (JPA)
│   ├── EnrollmentDAO.java                 # Enrollment CRUD (JPA)
│   └── StudentDAO.java                    # Student data access (JPA)
├── models/
│   ├── Course.java                        # Course entity (@Entity)
│   ├── Enrollment.java                    # Enrollment entity (@ManyToOne)
│   └── Student.java                       # Student entity (@Entity)
├── views/
│   └── Enrollment.fxml                    # JavaFX layout
├── styles/
│   └── EnrollmentFormStyle.css            # UI styling
└── META-INF/
    └── persistence.xml                    # JPA configuration
```

---

## Database Schema

| Table        | Columns                                                        |
|--------------|----------------------------------------------------------------|
| `students`   | `student_id` (PK), `student_name`                              |
| `courses`    | `course_id` (PK), `course_name`                                |
| `enrollment` | `enrollment_id` (PK, AUTO_INCREMENT), `student_id` (FK), `course_id` (FK), `enrollment_date` |

> **Note:** Tables are auto-created by JPA when `schema-generation.database.action` is set to `create`.

---

## Getting Started

### Prerequisites

- Java 8 or higher
- MySQL Server
- NetBeans IDE (recommended) or any Java IDE
- EclipseLink JPA libraries
- MySQL Connector/J

### Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/course-enrollment-system-jpa.git
   ```

2. **Create the database in MySQL**
   ```sql
   CREATE DATABASE enrollment_system;
   ```

3. **Configure database credentials**  
   Edit `src/META-INF/persistence.xml` to match your MySQL user/password.

4. **Build and Run**  
   Open the project in NetBeans, **Clean & Build**, then **Run**.

---

## Usage

1. Launch the application  
2. Select a **Student ID** from the ComboBox  
3. Select a **Course ID** from the ComboBox  
4. Pick an **Enrollment Date**  
5. Click **Add** to enroll  
6. Use **Search** → select student → view their enrollments  
7. Select a table row → modify values → click **Update**  
8. Select a row → click **Delete** → confirm removal  

---

## License

This project is for educational purposes.
