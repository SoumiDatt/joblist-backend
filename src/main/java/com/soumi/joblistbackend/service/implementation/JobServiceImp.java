package com.soumi.joblistbackend.service.implementation;

import com.soumi.joblistbackend.entities.Job;
import com.soumi.joblistbackend.entities.Recruiter;
import com.soumi.joblistbackend.exceptions.ResourceNotFoundException;
import com.soumi.joblistbackend.payloads.JobDto;
import com.soumi.joblistbackend.payloads.JobResponse;
import com.soumi.joblistbackend.payloads.RecruiterDto;
import com.soumi.joblistbackend.repositories.JobRepo;
import com.soumi.joblistbackend.repositories.RecruiterRepo;
import com.soumi.joblistbackend.service.JobService;
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
public class JobServiceImp implements JobService {

    @Autowired
    private JobRepo jobRepo;

    @Autowired
    private RecruiterRepo recruiterRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public JobDto createJob(JobDto jobDto, Integer recruiterId) {
        Recruiter recruiter=this.recruiterRepo.findById(recruiterId).orElseThrow(()-> new ResourceNotFoundException("Recruiter","recruiter id", recruiterId));
        Job job=this.modelMapper.map(jobDto, Job.class);
        job.setDate(new Date());
        job.setRecruiter(recruiter);
        Job createdJob = this.jobRepo.save(job);
        JobDto map = this.modelMapper.map(createdJob, JobDto.class);
        return map;
    }

    @Override
    public JobDto updateJob(JobDto jobDto, Integer jobId) {
        Job job=this.jobRepo.findById(jobId).orElseThrow(()->new ResourceNotFoundException("Job","job id", jobId));
        job.setJobDesc(jobDto.getJobDesc());
        job.setJobTitle(jobDto.getJobTitle());
        job.setJobType(jobDto.getJobType());
        job.setActive(jobDto.getActive());
        job.setLocation(jobDto.getLocation());
        job.setExp(jobDto.getExp());
        job.setSkills(jobDto.getSkills());

        Job updatedJob = this.jobRepo.save(job);
        return this.modelMapper.map(updatedJob, JobDto.class);

    }

    @Override
    public void deleteJob(Integer jobId) {
        Job job=this.jobRepo.findById(jobId).orElseThrow(()->new ResourceNotFoundException("Job","job id", jobId));
        this.jobRepo.delete(job);
    }

    @Override
    public JobResponse getAllJobs(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort=(sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable= PageRequest.of(pageNumber, pageSize, sort);
        Page<Job> pageJob = this.jobRepo.findAll(pageable);
        List<Job> allJobs = pageJob.getContent();
        List<JobDto> jobDtos=allJobs.stream().map((job)->this.modelMapper.map(job, JobDto.class)).collect(Collectors.toList());
        JobResponse jobResponse=new JobResponse();

        jobResponse.setContent(jobDtos);
        jobResponse.setPageNumber(pageJob.getNumber());
        jobResponse.setPageSize(pageJob.getSize());
        jobResponse.setTotalElements(pageJob.getTotalElements());
        jobResponse.setTotalPages(pageJob.getTotalPages());
        jobResponse.setLastPage(pageJob.isLast());
        return jobResponse;
    }

    @Override
    public JobDto getJobById(Integer jobId) {
        Job job=this.jobRepo.findById(jobId).orElseThrow(()->new ResourceNotFoundException("Job","job id", jobId));
        return this.modelMapper.map(job, JobDto.class);
    }

    @Override
    public List<JobDto> getJobByRecruiter(Integer recruiterId) {
        Recruiter recruiter=this.recruiterRepo.findById(recruiterId).orElseThrow(()-> new ResourceNotFoundException("Recruiter","recruiter id", recruiterId));
        List<Job> jobs=this.jobRepo.findByRecruiter(recruiter);

        return jobs.stream().map((job-> this.modelMapper.map(job, JobDto.class))).collect(Collectors.toList());
    }

    @Override
    public List<JobDto> searchJobs(String keyword) {
        List<Job> jobs = this.jobRepo.searchByJobTitle("%"+keyword+"%");
        return jobs.stream().map((job-> this.modelMapper.map(job, JobDto.class))).collect(Collectors.toList());
    }

    @Override
    public List<JobDto> getActiveJobs() {
        List<Job> activeJobs = this.jobRepo.findByActive();
        return activeJobs.stream().map((job-> this.modelMapper.map(job, JobDto.class))).collect(Collectors.toList());
    }

}
