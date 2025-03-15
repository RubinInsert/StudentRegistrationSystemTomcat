package com.rubinin;

import java.io.Serializable;

public class Course implements Serializable{
    private String name;
    private String code;
    private String assumedKnowledge;
    private int units;
    public Course(String name, String code, String assumedKnowledge, int units) {
        this.name = name;
        this.code = code;
        this.assumedKnowledge = assumedKnowledge;
        this.units = units;
    }
    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAssumedKnowledge() {
        return assumedKnowledge;
    }

    public void setAssumedKnowledge(String assumedKnowledge) {
        this.assumedKnowledge = assumedKnowledge;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }
}