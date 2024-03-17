package com.soumi.joblistbackend.service.implementation;

import com.soumi.joblistbackend.entities.Applicant;
import com.soumi.joblistbackend.entities.Job;
import com.soumi.joblistbackend.entities.JobApplication;
import com.soumi.joblistbackend.entities.Recruiter;
import com.soumi.joblistbackend.exceptions.ResourceNotFoundException;
import com.soumi.joblistbackend.payloads.JobApplicationDto;
import com.soumi.joblistbackend.payloads.JobApplicationResponse;
import com.soumi.joblistbackend.payloads.JobDto;
import com.soumi.joblistbackend.payloads.JobResponse;
import com.soumi.joblistbackend.repositories.ApplicantRepo;
import com.soumi.joblistbackend.repositories.JobApplicationRepo;
import com.soumi.joblistbackend.repositories.JobRepo;
import com.soumi.joblistbackend.service.JobApplicationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobApplicationServiceImp implements JobApplicationService {

    @Autowired
    private JobApplicationRepo jobApplicationRepo;

    @Autowired
    private JobRepo jobRepo;

    @Autowired
    private ApplicantRepo applicantRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public JobApplicationDto createJobApplication(JobApplicationDto jobApplicationDto, Integer jobId, Integer applicantId) {
        Applicant applicant=this.applicantRepo.findById(applicantId).orElseThrow(()-> new ResourceNotFoundException("Applicant","applicant id", applicantId));
        Job job=this.jobRepo.findById(jobId).orElseThrow(()-> new ResourceNotFoundException("Job","job id", jobId));
        JobApplication jobApplication=this.modelMapper.map(jobApplicationDto, JobApplication.class);
        jobApplication.setDate(new Date());
        jobApplication.setApplicant(applicant);
        jobApplication.setJob(job);
        jobApplication.setResumeName("default.pdf");
        JobApplication createdJobApplication = this.jobApplicationRepo.save(jobApplication);
        return this.modelMapper.map(createdJobApplication, JobApplicationDto.class);
    }

    @Override
    public JobApplicationDto updateJobApplication(JobApplicationDto jobApplicationDto, Integer jobApplicationId) {
        JobApplication jobApplication=this.jobApplicationRepo.findById(jobApplicationId).orElseThrow(()-> new ResourceNotFoundException("Job application","job application id", jobApplicationId));
        jobApplication.setResumeName(jobApplicationDto.getResumeName());

        JobApplication updatedJobApplication = this.jobApplicationRepo.save(jobApplication);
        return this.modelMapper.map(updatedJobApplication, JobApplicationDto.class);
    }

    @Override
    public void deleteJobApplication(Integer jobApplicationId) {
        JobApplication jobApplication=this.jobApplicationRepo.findById(jobApplicationId).orElseThrow(()-> new ResourceNotFoundException("Job application","job application id", jobApplicationId));
        this.jobApplicationRepo.delete(jobApplication);
    }

    @Override
    public JobApplicationResponse getAllJobApplications(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort=(sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable= PageRequest.of(pageNumber, pageSize, sort);
        Page<JobApplication> pageJobApplication = this.jobApplicationRepo.findAll(pageable);
        List<JobApplication> allJobApplications = pageJobApplication.getContent();
        List<JobApplicationDto> jobApplicationDtoList=allJobApplications.stream().map((jobapp)->this.modelMapper.map(jobapp, JobApplicationDto.class)).collect(Collectors.toList());
        JobApplicationResponse jobApplicationResponse=new JobApplicationResponse();

        jobApplicationResponse.setContent(jobApplicationDtoList);
        jobApplicationResponse.setPageNumber(pageJobApplication.getNumber());
        jobApplicationResponse.setPageSize(pageJobApplication.getSize());
        jobApplicationResponse.setTotalElements(pageJobApplication.getTotalElements());
        jobApplicationResponse.setTotalPages(pageJobApplication.getTotalPages());
        jobApplicationResponse.setLastPage(pageJobApplication.isLast());
        return jobApplicationResponse;
    }

    @Override
    public JobApplicationDto getJobApplicationById(Integer jobApplicationId) {
        JobApplication jobApplication=this.jobApplicationRepo.findById(jobApplicationId).orElseThrow(()-> new ResourceNotFoundException("Job application","job application id", jobApplicationId));
        return this.modelMapper.map(jobApplication, JobApplicationDto.class);
    }

    @Override
    public List<JobApplicationDto> getJobApplicationByJob(Integer jobId) {
        Job job=this.jobRepo.findById(jobId).orElseThrow(()-> new ResourceNotFoundException("Job","job id", jobId));
        List<JobApplication> jobApplicationList=this.jobApplicationRepo.findByJob(job);
        return jobApplicationList.stream().map((jobapp-> this.modelMapper.map(jobapp, JobApplicationDto.class))).collect(Collectors.toList());

    }

    @Override
    public List<JobApplicationDto> getJobApplicationByApplicant(Integer applicantId) {
        Applicant applicant=this.applicantRepo.findById(applicantId).orElseThrow(()-> new ResourceNotFoundException("Applicant","applicant id", applicantId));
        List<JobApplication> jobApplicationList=this.jobApplicationRepo.findByApplicant(applicant);
        return jobApplicationList.stream().map((jobapp-> this.modelMapper.map(jobapp, JobApplicationDto.class))).collect(Collectors.toList());
    }

}
