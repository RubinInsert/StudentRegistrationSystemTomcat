package com.rubinin.DAOs.Student;
import java.sql.PreparedStatement;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.w3c.dom.Document;

import com.rubinin.Student;
import com.rubinin.Course;
public class StudentDAOImpl implements StudentDAO {
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
    public void addStudent(Student student) {
        String sql = "INSERT INTO student (stdNO, givenNames, lastName, passwordHash, passwordSalt) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = datasource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, student.getStdNo());
            stmt.setString(2, student.getGivenNames());
            stmt.setString(3, student.getLastName());
            stmt.setString(4, student.getPasswordHash());
            stmt.setDouble(5, student.getSalt());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Student getStudentByStdNo(String stdNo) {
        String sql = "SELECT * FROM student WHERE stdNo = ?";
        try (Connection conn = datasource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, stdNo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Student(rs.getString("stdNo"), rs.getString("givenNames"), 
                    rs.getString("lastName"), rs.getString("passwordHash"), rs.getDouble("passwordSalt"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM student";
        try (Connection conn = datasource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql)) {
             
            while (rs.next()) {
                students.add(new Student(rs.getString("stdNo"), rs.getString("givenNames"), 
                        rs.getString("lastName"), rs.getString("passwordHash"), rs.getDouble("passwordSalt")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }


    @Override
    public void updateStudent(Student student) {
        String sql = "UPDATE student SET givenNames = ?, lastName = ? WHERE stdNo = ?";
        try (Connection conn = datasource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, student.getGivenNames());
            stmt.setString(2, student.getLastName());
            stmt.setString(3, student.getStdNo());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void enrollStudent(Student student, Course course) {
        String sql = "INSERT INTO StudentCourseRegistration (stdNo, courseID, semesterID) VALUES (?, ?, ?)";
        try (Connection conn = datasource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setString(1, student.getStdNo());
            stmt.setString(2, course.getCourseID());
            stmt.setInt(3, course.getSemesterID());
            stmt.executeUpdate();
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void deleteStudent(String stdNo) {
        String sql = "DELETE FROM student WHERE stdNo = ?";
        try (Connection conn = datasource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, stdNo);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<Course> getCoursesByStudent(Student student) {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT co.semesterID, c.courseID, co.maxCapacity, c.cName, c.credits " +
                    "FROM StudentCourseRegistration scr " +
                    "JOIN CourseOfferings co ON scr.courseID = co.courseID AND scr.semesterID = co.semesterID " +
                    "JOIN Course c ON co.courseID = c.courseID " +
                    "WHERE scr.stdNo = ?";
        try (Connection conn = datasource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, student.getStdNo());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int semesterID = rs.getInt("semesterID");
                    String courseID = rs.getString("courseID");
                    int maxCapacity = rs.getInt("maxCapacity");
                    String courseName = rs.getString("cName");
                    int credits = rs.getInt("credits");
                    courses.add(new Course(semesterID, courseID, maxCapacity, courseName, credits));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }
}