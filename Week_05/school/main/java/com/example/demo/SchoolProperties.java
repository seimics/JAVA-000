package com.example.demo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "school")
public class SchoolProperties {

    private List<Integer> studentIds;
    private List<String> studentNames;
    private List<Integer> klassIds;
    private List<String> klassNames;
    private List<Map<String, Integer>> studentOfClass;

    public List<Integer> getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(List<Integer> studentIds) {
        this.studentIds = studentIds;
    }

    public List<String> getStudentNames() {
        return studentNames;
    }

    public void setStudentNames(List<String> studentNames) {
        this.studentNames = studentNames;
    }

    public List<Integer> getKlassIds() {
        return klassIds;
    }

    public void setKlassIds(List<Integer> klassIds) {
        this.klassIds = klassIds;
    }

    public List<String> getKlassNames() {
        return klassNames;
    }

    public void setKlassNames(List<String> klassNames) {
        this.klassNames = klassNames;
    }


    public List<Map<String, Integer>> getStudentOfClass() {
        return studentOfClass;
    }

    public void setStudentOfClass(List<Map<String, Integer>> studentOfClass) {
        this.studentOfClass = studentOfClass;
    }
}
