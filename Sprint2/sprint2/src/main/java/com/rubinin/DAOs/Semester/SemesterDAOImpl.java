package com.rubinin.DAOs.Semester;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.rubinin.Semester;

public class SemesterDAOImpl implements SemesterDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/unix";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL Driver
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addSemester(Semester semester) {
        String sql = "INSERT INTO Semester (semesterID, semester, year, openForEnrolment) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, semester.getSemesterID());
            stmt.setInt(2, semester.getSemester());
            stmt.setInt(3, semester.getYear());
            stmt.setBoolean(4, semester.isOpenForEnrolment());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Semester getSemesterByID(int semesterID) {
        String sql = "SELECT * FROM Semester WHERE semesterID = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, semesterID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Semester(rs.getInt("semesterID"), rs.getInt("semester"), 
                    rs.getInt("year"), rs.getBoolean("openForEnrolment"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Semester> getAllSemesters() {
        List<Semester> semesters = new ArrayList<>();
        String sql = "SELECT * FROM Semester";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                semesters.add(new Semester(rs.getInt("semesterID"), rs.getInt("semester"), 
                    rs.getInt("year"), rs.getBoolean("openForEnrolment")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return semesters;
    }

    @Override
    public void updateSemester(Semester semester) {
        String sql = "UPDATE Semester SET semester = ?, year = ?, openForEnrolment = ? WHERE semesterID = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, semester.getSemester());
            stmt.setInt(2, semester.getYear());
            stmt.setBoolean(3, semester.isOpenForEnrolment());
            stmt.setInt(4, semester.getSemesterID());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteSemester(int semesterID) {
        String sql = "DELETE FROM Semester WHERE semesterID = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, semesterID);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}