package app;

import config.DBConnection;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../views/Enrollment.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Student Course Enrollment System");
        stage.show();
    }

    @Override
    public void stop() {
        try {
            DBConnection.getInstance().closeConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}