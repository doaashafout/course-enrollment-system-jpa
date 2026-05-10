package controllers;

import dao.CourseDAO;
import dao.EnrollmentDAO;
import dao.StudentDAO;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Enrollment;

public class EnrollmentController implements Initializable {

    @FXML
    private ComboBox<Integer> studentIdCB;
    @FXML
    private ComboBox<Integer> courseIdCB;
    @FXML
    private DatePicker enrollmentDate;
    @FXML
    private TableView<Enrollment> table;
    @FXML
    private TableColumn<Enrollment, Integer> enrollmentIdTC;
    @FXML
    private TableColumn<Enrollment, Integer> studentIdTC;
    @FXML
    private TableColumn<Enrollment, Integer> courseIdTC;
    @FXML
    private TableColumn<Enrollment, String> enrollmentDateTC;

    CourseDAO courseDAO = new CourseDAO();
    StudentDAO studentDAO = new StudentDAO();
    EnrollmentDAO enrollmentDAO = new EnrollmentDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        enrollmentIdTC.setCellValueFactory(new PropertyValueFactory<>("enrollmentId"));
        studentIdTC.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        courseIdTC.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        enrollmentDateTC.setCellValueFactory(new PropertyValueFactory<>("enrollmentDate"));

        List<Integer> studentIds = studentDAO.getAllStudentsids();
        studentIdCB.getItems().addAll(studentIds);

        List<Integer> courseIds = courseDAO.getAllCourseids();
        courseIdCB.getItems().addAll(courseIds);

        table.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue == null) return;
                    studentIdCB.setValue(newValue.getStudentId());
                    courseIdCB.setValue(newValue.getCourseId());
                    enrollmentDate.setValue(LocalDate.parse(newValue.getEnrollmentDate()));
                }
        );
    }

    @FXML
    private void viewHandle(ActionEvent event) {
        List<Enrollment> enrollments = enrollmentDAO.findAll();
        table.getItems().setAll(enrollments);
    }

    @FXML
    private void searchHandle(ActionEvent event) {
        Integer studentId = studentIdCB.getValue();
        if (studentId == null) {
            showWarningAlert("No Selection", "No Student Selected",
                    "Please select a student first");
            return;
        }
        List<Enrollment> enrollments = enrollmentDAO.findByStudentId(studentId);
        table.getItems().setAll(enrollments);
    }

    @FXML
    private void addHandle(ActionEvent event) {
        if (enrollmentValidator()) {
            Integer studentId = studentIdCB.getValue();
            Integer courseId = courseIdCB.getValue();
            LocalDate ed = enrollmentDate.getValue();
            Enrollment e = new Enrollment(studentId, courseId, ed.toString());
            boolean success = enrollmentDAO.insertOne(e);
            if (success) {
                clear();
                viewHandle(event);
                showInfoAlert("Success", "Enrollment Added Successfully");
            } else {
                showWarningAlert("Duplicate", "Duplicate Enrollment",
                        "This student is already enrolled in this course");
            }
        } else {
            showWarningAlert("Invalid Input", "Missing Data",
                    "Please select student id, course id and enrollment date");
        }
    }

    @FXML
    private void updateHandle(ActionEvent event) {
        Enrollment e = table.getSelectionModel().getSelectedItem();
        if (e == null) {
            showWarningAlert("No Selection", "No Record Selected",
                    "Please select an enrollment record from the table");
            return;
        }
        if (studentIdCB.getValue() == null || courseIdCB.getValue() == null
                || enrollmentDate.getValue() == null) {
            showWarningAlert("Invalid Input", "Missing Data",
                    "Please select student id, course id and enrollment date");
            return;
        }

        Enrollment updated = new Enrollment(
                e.getEnrollmentId(),
                studentIdCB.getValue(),
                courseIdCB.getValue(),
                enrollmentDate.getValue().toString()
        );
        boolean success = enrollmentDAO.updateOne(updated);
        if (success) {
            showInfoAlert("Success", "Enrollment Updated Successfully");
            clear();
            viewHandle(event);
        }
    }

    @FXML
    private void deleteHandle(ActionEvent event) {
        Enrollment e = table.getSelectionModel().getSelectedItem();
        if (e == null) {
            showWarningAlert("No Selection", "No Record Selected",
                    "Please select an enrollment record from the table");
            return;
        }
        if (showConfirmationAlert("Delete Confirmation",
                "Are you sure",
                "Do you want to delete this enrollment record")) {
            enrollmentDAO.deleteOne(e);
            viewHandle(event);
            clear();
        }
    }

    private boolean enrollmentValidator() {
        if (studentIdCB.getValue() == null || courseIdCB.getValue() == null
                || enrollmentDate.getValue() == null)
            return false;
        return true;
    }

    private void clear() {
        studentIdCB.setValue(null);
        courseIdCB.setValue(null);
        enrollmentDate.setValue(null);
        table.getSelectionModel().clearSelection();
    }

    private void showWarningAlert(String title, String header, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfoAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean showConfirmationAlert(String title, String header, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            return true;
        }
        return false;
    }
}
