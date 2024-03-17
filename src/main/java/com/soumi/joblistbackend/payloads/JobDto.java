package com.soumi.joblistbackend.payloads;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;


@NoArgsConstructor
@Getter
@Setter
public class JobDto {

    private Integer jobId;

    @NotEmpty(message = "Job title must not be empty")
    private String jobTitle;

    @NotEmpty(message = "Job description must not be empty")
    private String jobDesc;
    private Date date;

    private Integer exp;
    private List<String> skills;
    private String jobType;
    private Boolean active;
    private String companyName;
    private String location;
    private RecruiterDto recruiter;
}