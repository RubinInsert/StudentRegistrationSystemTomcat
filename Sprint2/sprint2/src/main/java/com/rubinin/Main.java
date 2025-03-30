package com.rubinin;
import java.io.File;

import org.apache.catalina.Context;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import com.rubinin.Servlets.AddStudentServlet;
import com.rubinin.Servlets.ConfirmEnrollmentServlet;
import com.rubinin.Servlets.CourseServlet;
import com.rubinin.Servlets.LoginServlet;
import com.rubinin.Servlets.SemesterServlet;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.apache.tomcat.util.net.SSLHostConfig;
import org.apache.tomcat.util.net.SSLHostConfigCertificate;
import org.apache.tomcat.util.net.SSLHostConfigCertificate.Type;
public class Main {
  public static void main(String[] args) {
    // Create Tomcat instance
    Tomcat tomcat = new Tomcat();
    tomcat.setPort(8081); // Set server port
    // Ensure a base directory for Tomcat
    tomcat.setBaseDir("temp");

    // Create and configure context
    Context ctx = tomcat.addWebapp("", new File("Sprint2/sprint2/webapps/ROOT").getAbsolutePath());
    System.out.println("Root path (web apps): " + new File("Sprint2/sprint2/webapps/ROOT").getAbsolutePath());
    Connector httpsConnector = new Connector();
    httpsConnector.setPort(8443);
    httpsConnector.setSecure(true);
    httpsConnector.setScheme("https");

    Http11NioProtocol protocol = (Http11NioProtocol) httpsConnector.getProtocolHandler();
    protocol.setSSLEnabled(true);

    SSLHostConfig sslHostConfig = new SSLHostConfig();
    sslHostConfig.setHostName("_default_");
    sslHostConfig.setProtocols("TLSv1.2,TSLv1.3");
    sslHostConfig.setCertificateVerification("none");
    
    SSLHostConfigCertificate certificate = new SSLHostConfigCertificate(sslHostConfig, Type.RSA);
    certificate.setCertificateKeystoreFile("keystore.jks");
    certificate.setCertificateKeystorePassword("password");
    certificate.setCertificateKeyAlias("tomcat");

    sslHostConfig.addCertificate(certificate);

    protocol.addSslHostConfig(sslHostConfig);

    tomcat.getService().addConnector(httpsConnector);
    File rootDir = new File("Sprint2/sprint2/webapps/ROOT");
    if (rootDir.exists() && rootDir.isDirectory()) {
      for (String fileName : rootDir.list()) {
        System.out.println("Found: " + fileName);
      }
    } else {
      System.out.println("Directory does not exist or is not a directory.");
    }

    if (ctx == null) {
      throw new RuntimeException("Tomcat context initialization failed!");
    }

    // Add a servlet
    tomcat.addServlet("", "LoginServlet", new LoginServlet());
    tomcat.addServlet("", "SemesterServlet", new SemesterServlet());
    tomcat.addServlet("", "CourseServlet", new CourseServlet());
    tomcat.addServlet("", "ConfirmEnrollmentServlet", new ConfirmEnrollmentServlet());
    tomcat.addServlet("", "AddStudentServlet", new AddStudentServlet());
    // Map the servlet
    ctx.addServletMappingDecoded("/login", "LoginServlet");
    ctx.addServletMappingDecoded("/semester", "SemesterServlet");
    ctx.addServletMappingDecoded("/course", "CourseServlet");
    ctx.addServletMappingDecoded("/confirm", "ConfirmEnrollmentServlet");
    ctx.addServletMappingDecoded("/addStudent", "AddStudentServlet");
    // Start Tomcat
    try {
      tomcat.getConnector();
      tomcat.start();
      System.out.println("Tomcat started successfully.");
      tomcat.getServer().await();
    } catch (LifecycleException e) {
      e.printStackTrace();
    }
  }
}