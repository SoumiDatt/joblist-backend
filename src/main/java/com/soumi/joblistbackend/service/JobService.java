package com.soumi.joblistbackend.service;

import com.soumi.joblistbackend.entities.Recruiter;
import com.soumi.joblistbackend.payloads.JobDto;
import com.soumi.joblistbackend.payloads.JobResponse;

import java.util.List;

public interface JobService {

    JobDto createJob(JobDto jobDto, Integer recruiterId);

    //update
    JobDto updateJob(JobDto jobDto, Integer jobId);

    //delete
    void deleteJob(Integer jobId);

    JobResponse getAllJobs(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    JobDto getJobById(Integer jobId);

    List<JobDto> getJobByRecruiter(Integer recruiterId);

    List<JobDto> searchJobs(String keyword);

    List<JobDto> getActiveJobs();
}
