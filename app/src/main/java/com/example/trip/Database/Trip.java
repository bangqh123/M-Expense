package com.example.trip.Database;

import java.io.Serializable;
import java.util.Objects;

public class Trip implements Serializable {
    private int id;
    private String name;
    private String date;
    private String require;
    private String desc;

    public Trip(int id, String name, String date, String require, String desc) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.require = require;
        this.desc = desc;
    }

    public Trip(String name, String date, String require, String desc) {
        this.name = name;
        this.date = date;
        this.require = require;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRequire() {
        return require;
    }

    public void setRequire(String require) {
        this.require = require;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", require='" + require + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trip trip = (Trip) o;
        return id == trip.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
