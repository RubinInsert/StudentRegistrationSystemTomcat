package com.rubinin.DAOs.Course;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.w3c.dom.Document;

import com.rubinin.Course;
public class CourseDAOImpl implements CourseDAO {
    private static DataSource datasource;
    static {
        try {
            File credFile = new File("Sprint2/databaseConfig.xml");
            if (credFile.exists()) {
                System.out.println("Credentials file found. Loading credentials.");
            } else {
                System.out.println("Credentials file not found.");
            }
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            Document doc = dbFactory.newDocumentBuilder().parse(credFile);
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL Driver
            PoolProperties p = new PoolProperties();
            p.setUrl(doc.getElementsByTagName("databaseURL").item(0).getTextContent());
            p.setDriverClassName(doc.getElementsByTagName("jdbcDriver").item(0).getTextContent());
            p.setUsername(doc.getElementsByTagName("user").item(0).getTextContent());
            p.setPassword(doc.getElementsByTagName("password").item(0).getTextContent());
            p.setMaxActive(100);
            datasource = new DataSource();
            datasource.setPoolProperties(p);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Override
    public void insertCourseOffering(int semesterID, String courseID, int maxCapacity) {
        String sql = "INSERT INTO CourseOfferings (semesterID, courseID, maxCapacity) VALUES (?, ?, ?)";
        try (Connection conn = datasource.getConnection();
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
        try (Connection conn = datasource.getConnection();
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
        try (Connection conn = datasource.getConnection();
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
            String sql = "SELECT co.semesterID, co.courseID, co.maxCapacity, c.cName, c.credits " +
                         "FROM CourseOfferings co " +
                         "JOIN Course c ON co.courseID = c.courseID " +
                         "WHERE co.semesterID = ? AND co.courseID = ?";
            try (Connection conn = datasource.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, semesterID);
                pstmt.setString(2, courseID);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        int maxCapacity = rs.getInt("maxCapacity");
                        String courseName = rs.getString("cName");
                        int credits = rs.getInt("credits");
                        // Assuming your Course class has been updated to include courseName and credits
                        return new Course(semesterID, courseID, maxCapacity, courseName, credits);
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
            String sql = "SELECT co.semesterID, co.courseID, co.maxCapacity, c.cName, c.credits " +
                         "FROM CourseOfferings co " +
                         "JOIN Course c ON co.courseID = c.courseID";
            try (Connection conn = datasource.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int semesterID = rs.getInt("semesterID");
                    String courseID = rs.getString("courseID");
                    int maxCapacity = rs.getInt("maxCapacity");
                    String courseName = rs.getString("cName");
                    int credits = rs.getInt("credits");
                    // Assuming your Course class has been updated to include courseName and credits
                    courses.add(new Course(semesterID, courseID, maxCapacity, courseName, credits));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return courses;
        }
        @Override
        public List<Course> getCoursesBySemesterID(int semesterID) {
            List<Course> courses = new ArrayList<>();
            String sql = "SELECT co.semesterID, co.courseID, co.maxCapacity, c.cName, c.credits " +
                         "FROM CourseOfferings co " +
                         "JOIN Course c ON co.courseID = c.courseID " +
                         "WHERE co.semesterID = ?";
            try (Connection conn = datasource.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, semesterID);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        String courseID = rs.getString("courseID");
                        int maxCapacity = rs.getInt("maxCapacity");
                        String courseName = rs.getString("cName");
                        int credits = rs.getInt("credits");
                        // Assuming your Course class has been updated to include courseName and credits
                        courses.add(new Course(semesterID, courseID, maxCapacity, courseName, credits));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return courses;
        }
    }