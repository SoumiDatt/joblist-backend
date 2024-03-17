package com.soumi.joblistbackend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="job")
@NoArgsConstructor
@Getter
@Setter
public class Job {

    @Id
    @Column(name = "job_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer jobId;
    private String jobTitle;
    private String jobDesc;
    private Date date;
    private Integer exp;
    private List<String> skills;
    private String jobType;
    private Boolean active;
    private String companyName;
    private String location;

    @ManyToOne(targetEntity = Recruiter.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "recruiter_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Recruiter recruiter;

//    @OneToMany(mappedBy = "job", targetEntity = JobApplication.class, cascade = CascadeType.ALL)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    private List<JobApplication> allApplications;


}
