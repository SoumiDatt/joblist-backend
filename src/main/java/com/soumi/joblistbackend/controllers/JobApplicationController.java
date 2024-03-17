package com.soumi.joblistbackend.controllers;

import com.soumi.joblistbackend.config.AppConstants;
import com.soumi.joblistbackend.payloads.*;
import com.soumi.joblistbackend.service.FileService;
import com.soumi.joblistbackend.service.JobApplicationService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class JobApplicationController {

    @Autowired
    private JobApplicationService jobApplicationService;

    @Autowired
    private FileService fileService;

    @Value("${project.resume}")
    private String path;

    //create job application
    @PostMapping("/jobId/{jobId}/applicant/{applicantId}/jobApplication")
    public ResponseEntity<JobApplicationDto> createJobApplication(@RequestBody JobApplicationDto jobApplicationDto, @PathVariable Integer jobId, @PathVariable Integer applicantId){
        JobApplicationDto jobApplication = this.jobApplicationService.createJobApplication(jobApplicationDto, jobId, applicantId);
        return new ResponseEntity<>(jobApplication, HttpStatus.CREATED);
    }

    @PutMapping("/jobApplications/{jobApplicationId}")
    public ResponseEntity<JobApplicationDto> updateJobApplication(@RequestBody JobApplicationDto jobApplicationDto, @PathVariable Integer jobApplicationId){
        JobApplicationDto updatedJobApp = this.jobApplicationService.updateJobApplication(jobApplicationDto, jobApplicationId);
        return new ResponseEntity<>(updatedJobApp, HttpStatus.OK);
    }

    //get job applications by applicant
    @GetMapping("/applicant/{applicantId}/jobApplications")
    public ResponseEntity<List<JobApplicationDto>> getJobApplicationsByApplicant(@PathVariable Integer applicantId){
        List<JobApplicationDto> jobApplications=this.jobApplicationService.getJobApplicationByApplicant(applicantId);
        return new ResponseEntity<List<JobApplicationDto>>(jobApplications, HttpStatus.OK);
    }

    //get job applications by job
    @GetMapping("/job/{jobId}/jobApplications")
    public ResponseEntity<List<JobApplicationDto>> getJobApplicationsByJob(@PathVariable Integer jobId){
        List<JobApplicationDto> jobApplications=this.jobApplicationService.getJobApplicationByJob(jobId);
        return new ResponseEntity<List<JobApplicationDto>>(jobApplications, HttpStatus.OK);
    }

    //get all job applications

    //for testing use: GET: http://localhost:9090/api/jobApplications?pageNumber=0&pazeSize=2?sortBy=id
    @GetMapping("/jobApplications")
    public ResponseEntity<JobApplicationResponse> getAllJobApplications(
            @RequestParam(value="pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value="pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value="sortBy", defaultValue = AppConstants.SORT_BY_APP, required = false) String sortBy,
            @RequestParam(value="sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
    ){
        JobApplicationResponse jobApplicationResponse=this.jobApplicationService.getAllJobApplications(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<JobApplicationResponse>(jobApplicationResponse, HttpStatus.OK);
    }

    //get job applications by id
    @GetMapping("/jobApplications/{jobApplicationId}")
    public ResponseEntity<JobApplicationDto> getJobApplicationById(@PathVariable Integer jobApplicationId){
        JobApplicationDto jobApplicationDto=this.jobApplicationService.getJobApplicationById(jobApplicationId);
        return new ResponseEntity<JobApplicationDto>(jobApplicationDto, HttpStatus.OK);
    }

    //delete job application
    @DeleteMapping("/jobApplications/{jobApplicationId}")
    public ApiResponse deleteJobApplication(@PathVariable Integer jobApplicationId){
        this.jobApplicationService.deleteJobApplication(jobApplicationId);
        return new ApiResponse("Job Application is successfully deleted", true);
    }

    //resume upload
    @PostMapping("/jobApplications/upload/{jobApplicationId}")
    public ResponseEntity<JobApplicationDto> uploadApplicationResume(
            @RequestParam("resume") MultipartFile resume,
            @PathVariable Integer jobApplicationId
    ) throws IOException {
        JobApplicationDto jobApplicationDto = this.jobApplicationService.getJobApplicationById(jobApplicationId);
        String fileName = this.fileService.uploadResource(path, resume);
        jobApplicationDto.setResumeName(fileName);
        JobApplicationDto updateJobApplication = this.jobApplicationService.updateJobApplication(jobApplicationDto, jobApplicationId);
        return new ResponseEntity<JobApplicationDto>(updateJobApplication, HttpStatus.OK);
    }

    //serve pdfs

    @GetMapping(value = "/jobApplications/resume/{resumeName}", produces = MediaType.APPLICATION_PDF_VALUE)
    public void downloadResume(
            @PathVariable String resumeName,
            HttpServletResponse response
    ) throws IOException {
        InputStream resource = this.fileService.getResource(path, resumeName);
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }

}
