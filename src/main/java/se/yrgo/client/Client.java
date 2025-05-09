package se.yrgo.client;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Client {
    public static void main(String[] args) {

        ClassPathXmlApplicationContext container =
                new ClassPathXmlApplicationContext("application.xml");

        try {

        } finally {
            container.close();
        }
    }
}
