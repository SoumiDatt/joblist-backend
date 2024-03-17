package com.soumi.joblistbackend.repositories;

import com.soumi.joblistbackend.entities.Applicant;
import com.soumi.joblistbackend.entities.Job;
import com.soumi.joblistbackend.entities.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobApplicationRepo extends JpaRepository<JobApplication, Integer> {

    List<JobApplication> findByApplicant(Applicant applicant);
    List<JobApplication> findByJob(Job job);
}
