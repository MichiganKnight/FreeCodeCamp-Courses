package com.revature.weekTen.StudentApp.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String room;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "classroomFK")
    private List<Student> students;

    public Classroom(String room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return "Classroom [id=" + id + ", room=" + room + "]";
    }
}
