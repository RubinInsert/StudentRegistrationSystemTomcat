package com.rubinin.DAOs.Semester;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import com.rubinin.Semester;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.w3c.dom.Document;

public class SemesterDAOImpl implements SemesterDAO {
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
    public void addSemester(Semester semester) {
        String sql = "INSERT INTO Semester (semesterID, semester, year, openForEnrolment) VALUES (?, ?, ?, ?)";
        try (Connection conn = datasource.getConnection();
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
        try (Connection conn = datasource.getConnection();
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
        try (Connection conn = datasource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
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
        try (Connection conn = datasource.getConnection();
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
        try (Connection conn = datasource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, semesterID);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}