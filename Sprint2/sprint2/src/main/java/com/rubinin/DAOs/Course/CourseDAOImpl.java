package com.rubinin.DAOs.Course;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.rubinin.Course;
public class CourseDAOImpl implements CourseDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/unix";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    @Override
    public void insertCourseOffering(int semesterID, String courseID, int maxCapacity) {
        String sql = "INSERT INTO CourseOfferings (semesterID, courseID, maxCapacity) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, semesterID);
            pstmt.setString(2, courseID);
            pstmt.setInt(3, maxCapacity);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateCourseOffering(int semesterID, String courseID, int maxCapacity) {
        String sql = "UPDATE CourseOfferings SET maxCapacity = ? WHERE semesterID = ? AND courseID = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, maxCapacity);
            pstmt.setInt(2, semesterID);
            pstmt.setString(3, courseID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCourseOffering(int semesterID, String courseID) {
        String sql = "DELETE FROM CourseOfferings WHERE semesterID = ? AND courseID = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, semesterID);
            pstmt.setString(2, courseID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

            }
        }
            @Override
            public Course getCourseOffering(int semesterID, String courseID) {
                String sql = "SELECT * FROM CourseOfferings WHERE semesterID = ? AND courseID = ?";
                try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setInt(1, semesterID);
                    pstmt.setString(2, courseID);
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            int maxCapacity = rs.getInt("maxCapacity");
                            return new Course(semesterID, courseID, maxCapacity);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }
            @Override
            public List<Course> getAllCourseOfferings() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM CourseOfferings";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                int semesterID = rs.getInt("semesterID");
                String courseID = rs.getString("courseID");
                int maxCapacity = rs.getInt("maxCapacity");
                courses.add(new Course(semesterID, courseID, maxCapacity));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }
    @Override
    public List<Course> getCoursesBySemesterID(int semesterID) {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM CourseOfferings WHERE semesterID = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, semesterID);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String courseID = rs.getString("courseID");
                    int maxCapacity = rs.getInt("maxCapacity");
                    courses.add(new Course(semesterID, courseID, maxCapacity));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }
}