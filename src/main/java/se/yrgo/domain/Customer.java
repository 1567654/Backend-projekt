package se.yrgo.domain;

import javax.persistence.*;
import java.util.Objects;

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

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id == customer.id && Objects.equals(name, customer.name) && Objects.equals(email, customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email);
    }
}
