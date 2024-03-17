package com.soumi.joblistbackend.controllers;

import com.soumi.joblistbackend.config.AppConstants;
import com.soumi.joblistbackend.payloads.ApiResponse;
import com.soumi.joblistbackend.payloads.JobDto;
import com.soumi.joblistbackend.payloads.JobResponse;
import com.soumi.joblistbackend.service.JobService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class JobController {

    @Autowired
    private JobService jobService;

    @PostMapping("/recruiterId/{recruiterId}/postJob")
    public ResponseEntity<JobDto> createJob(@Valid @RequestBody JobDto jobDto, @PathVariable Integer recruiterId){
        JobDto job = this.jobService.createJob(jobDto, recruiterId);
        return new ResponseEntity<>(job, HttpStatus.CREATED);
    }

    @PutMapping("/jobs/{jobId}")
    public ResponseEntity<JobDto> updateJob(@Valid @RequestBody JobDto jobDto, @PathVariable Integer jobId){
        JobDto updatedJob = this.jobService.updateJob(jobDto, jobId);
        return new ResponseEntity<>(updatedJob, HttpStatus.OK);
    }

    //get jobs by recruiter
    @GetMapping("/recruiter/{recruiterId}/jobs")
    public ResponseEntity<List<JobDto>> getJobsByRecruiter(@PathVariable Integer recruiterId){
        List<JobDto> jobs=this.jobService.getJobByRecruiter(recruiterId);
        return new ResponseEntity<List<JobDto>>(jobs, HttpStatus.OK);
    }

    //get all jobs

    //for testing use: GET: http://localhost:9090/api/jobs?pageNumber=0&pazeSize=2?sortBy=id
    @GetMapping("/jobs")
    public ResponseEntity<JobResponse> getAllJobs(
            @RequestParam(value="pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value="pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value="sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value="sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
    ){
        JobResponse jobResponse=this.jobService.getAllJobs(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<JobResponse>(jobResponse, HttpStatus.OK);
    }

    //get job by id
    @GetMapping("/jobs/{jobId}")
    public ResponseEntity<JobDto> getJobById(@PathVariable Integer jobId){
        JobDto jobDto=this.jobService.getJobById(jobId);
        return new ResponseEntity<JobDto>(jobDto, HttpStatus.OK);
    }

    //delete job
    @DeleteMapping("/jobs/{jobId}")
    public ApiResponse deleteJob(@PathVariable Integer jobId){
        this.jobService.deleteJob(jobId);
        return new ApiResponse("Job is successfully deleted", true);
    }

    //search
    @GetMapping("/jobs/search/{keywords}")
    public ResponseEntity<List<JobDto>> searchJobs(@PathVariable("keywords") String keywords){
        List<JobDto> result = this.jobService.searchJobs(keywords);
        return new ResponseEntity<List<JobDto>>(result, HttpStatus.OK);
    }

    @GetMapping("/jobs/search/activeJobs")
    public ResponseEntity<List<JobDto>> findActiveJobs(){
        List<JobDto> result = this.jobService.getActiveJobs();
        return new ResponseEntity<List<JobDto>>(result, HttpStatus.OK);
    }


}
