package ru.job4j.model;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        TestPsqlStore tps = new TestPsqlStore();
        Post p = tps.findPostById(0);
        System.out.println();
    }
}
