package com.revature.miniProjects.LibraryApp.Model;

import java.util.Objects;

public class Author {
    int id;
    String name;

    public Author() {
    }

    public Author(String name) {
        this.name = name;
    }

    public Author(int id, String name) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Author author = (Author) o;
        return id == author.id && Objects.equals(name, author.name);
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
