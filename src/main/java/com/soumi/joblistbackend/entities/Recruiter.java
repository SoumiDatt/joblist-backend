package com.soumi.joblistbackend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
public class Recruiter {

    @Id
    @Column(name = "recruiter_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "recruiter_username", nullable = false, length = 100, unique = true)
    private String username;

    private String name;

    private String email;

    private String password;

    @OneToMany(mappedBy = "recruiter", targetEntity = Job.class, cascade = CascadeType.ALL)
    private List<Job> jobsPosted;

}
