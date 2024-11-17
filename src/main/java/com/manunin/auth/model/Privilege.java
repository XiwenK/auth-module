package com.manunin.auth.model;

import jakarta.persistence.*;

@Entity
@Table(name="privileges")
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    public Privilege(final long id, final String name) {
        this.id = id;
        this.name = name;
    }

    public Privilege() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
