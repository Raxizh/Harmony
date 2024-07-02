package com.company.clientserver;

import java.net.*;
import java.util.Scanner;
import java.io.*;
public class ClientTwo{

    public static void main(String[] args) throws IOException, InterruptedException{
        testFunctionShutdown();
        Thread.sleep(10000);



    }
    public static void testFunctionShutdown() throws InterruptedException {
        System.out.println("Start of test function");
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                System.out.println("Shutdown has happened");
            }
        });
    }
}