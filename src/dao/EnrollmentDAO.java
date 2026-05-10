package dao;

import config.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import models.Enrollment;

public class EnrollmentDAO {

    public List<Enrollment> findAll() {
        List<Enrollment> list = new ArrayList<>();
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            String sql = "SELECT * FROM enrollment";
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery(sql);
            while (rs.next()) {
                Integer enrollment_id = rs.getInt("enrollment_id");
                Integer student_id = rs.getInt("student_id");
                Integer course_id = rs.getInt("course_id");
                String enrollment_date = rs.getString("enrollment_date");
                Enrollment e = new Enrollment(enrollment_id, student_id, course_id, enrollment_date);
                list.add(e);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); 
        }
        return list;
    }

    public boolean isDuplicate(Enrollment e) {
        String sql = "SELECT COUNT(*) FROM enrollment WHERE student_id=? AND course_id=?";
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, e.getStudentId());
            ps.setInt(2, e.getCourseId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); 
        }
        return false;
    }

    public boolean insertOne(Enrollment e) {
        if (isDuplicate(e)) {
            return false;
        }
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO enrollment(student_id, course_id, enrollment_date) VALUES(?,?,?)"
            );
            ps.setInt(1, e.getStudentId());
            ps.setInt(2, e.getCourseId());
            ps.setString(3, e.getEnrollmentDate());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace(); 
        }
        return false;
    }

    public boolean updateOne(Enrollment e) {
        String sql = "UPDATE enrollment SET student_id=?, course_id=?, enrollment_date=? WHERE enrollment_id=?";
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, e.getStudentId());
            ps.setInt(2, e.getCourseId());
            ps.setString(3, e.getEnrollmentDate());
            ps.setInt(4, e.getEnrollmentId());
            int noOfRows = ps.executeUpdate();
            return noOfRows > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public List<Enrollment> findByStudentId(int studentId) {
        List<Enrollment> list = new ArrayList<>();
        String sql = "SELECT * FROM enrollment WHERE student_id=?";
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Enrollment e = new Enrollment(
                    rs.getInt("enrollment_id"),
                    rs.getInt("student_id"),
                    rs.getInt("course_id"),
                    rs.getString("enrollment_date")
                );
                list.add(e);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public boolean deleteOne(Enrollment e) {
        String sql = "DELETE FROM enrollment WHERE enrollment_id=?";
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, e.getEnrollmentId());
            int noOfRows = ps.executeUpdate();
            return noOfRows > 0;
        } catch (SQLException ex) {
            ex.printStackTrace(); 
        }
        return false;
    }
}