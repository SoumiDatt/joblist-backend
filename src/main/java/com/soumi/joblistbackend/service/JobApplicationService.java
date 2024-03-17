package com.soumi.joblistbackend.service;

import com.soumi.joblistbackend.entities.JobApplication;
import com.soumi.joblistbackend.entities.Recruiter;
import com.soumi.joblistbackend.payloads.JobApplicationDto;
import com.soumi.joblistbackend.payloads.JobApplicationResponse;
import com.soumi.joblistbackend.payloads.JobDto;
import com.soumi.joblistbackend.payloads.JobResponse;

import java.util.List;

public interface JobApplicationService {

    JobApplicationDto createJobApplication(JobApplicationDto jobApplicationDto, Integer jobId, Integer applicantId);

    //update
    JobApplicationDto updateJobApplication(JobApplicationDto jobApplicationDto, Integer jobApplicationId);

    //delete
    void deleteJobApplication(Integer jobApplicationId);

    JobApplicationResponse getAllJobApplications(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    JobApplicationDto getJobApplicationById(Integer jobApplicationId);

    List<JobApplicationDto> getJobApplicationByJob(Integer jobId);
    List<JobApplicationDto> getJobApplicationByApplicant(Integer applicantId);

    //List<JobApplicationDto> searchJobApplications(String keyword);
}
