package dao;

import config.DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    public List<Integer> getAllStudentsids() {
        List<Integer> ids = new ArrayList<>();
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            String sql = "SELECT student_id FROM students";
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery(sql);
            while (rs.next()) {
                Integer student_id = rs.getInt("student_id");
                ids.add(student_id);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ids;
    }
}
