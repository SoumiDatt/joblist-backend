package com.soumi.joblistbackend.payloads;

import com.soumi.joblistbackend.entities.Applicant;
import com.soumi.joblistbackend.entities.Job;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class JobApplicationDto {

    private int id;
    private Date date;
    private String resumeName;
    private JobDto job;
    private ApplicantDto applicant;
}
