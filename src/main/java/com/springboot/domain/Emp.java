package com.springboot.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Emp implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private String id;
    private String name;

    protected Emp() {}

    public Emp(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format(
                "Emp[id=%s, name=%s]", id , name);
    }
}
