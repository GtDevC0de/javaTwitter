package com.ikungolf.java.javatwitter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TestCommand {

    public static void main(String[] args) {
        System.out.println("Hello, welcome to my first java application.");
        System.out.println("Enter somthing here: ");
        String s = new String();
        while (!"exit".equals(s)) {
            try {
                BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
                s = bufferReader.readLine();
                System.out.println(": " + s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
