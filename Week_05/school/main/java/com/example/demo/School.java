package com.example.demo;

import java.util.List;

public class School {

    private final List<Klass> klasses;

    public School(List<Klass> klasses) {
        this.klasses = klasses;
    }

    @Override
    public String toString() {
        return "School ==" + klasses.toString();
    }
}
