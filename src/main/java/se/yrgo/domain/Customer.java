package se.yrgo.domain;

import javax.persistence.*;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String email;

    public Customer() {}
    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
    }
    public int getId() {
        return id;
    }

    public void setName(String name) {this.name = name;}
    public String getName() {return name;}

    public void setEmail(String email) {this.email = email;}
    public String getEmail() {return email;}
}
