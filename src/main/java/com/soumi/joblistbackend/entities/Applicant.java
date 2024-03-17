package com.soumi.joblistbackend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "applicants")
@NoArgsConstructor
@Getter
@Setter
public class Applicant {

    @Id
    @Column(name = "applicant_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "applicant_username")
    private String userName;

    private String name;

    private String password;

    private String email;

    private List<String> skills;

//    @OneToMany(mappedBy = "applicant", targetEntity = JobApplication.class, cascade = CascadeType.ALL)
//    private List<JobApplication> applicationList;
}
