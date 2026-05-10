package dao;

import config.DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    public List<Integer> getAllCourseids() {
        List<Integer> ids = new ArrayList<>();
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            String sql = "SELECT course_id FROM courses";
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery(sql);
            while (rs.next()) {
                Integer course_id = rs.getInt("course_id");
                ids.add(course_id);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ids;
    }
}