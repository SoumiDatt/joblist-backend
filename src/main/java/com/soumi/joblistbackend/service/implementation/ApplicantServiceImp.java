package com.soumi.joblistbackend.service.implementation;

import com.soumi.joblistbackend.entities.Applicant;
import com.soumi.joblistbackend.exceptions.ResourceNotFoundException;
import com.soumi.joblistbackend.payloads.ApplicantDto;
import com.soumi.joblistbackend.repositories.ApplicantRepo;
import com.soumi.joblistbackend.service.ApplicantService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicantServiceImp implements ApplicantService {

    @Autowired
    private ApplicantRepo applicantRepo;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public ApplicantDto addApplicant(ApplicantDto applicantDto) {
        Applicant applicant=this.modelMapper.map(applicantDto, Applicant.class);
        Applicant savedApplicant = this.applicantRepo.save(applicant);
        return this.modelMapper.map(savedApplicant, ApplicantDto.class);
    }

    @Override
    public ApplicantDto updateApplicant(ApplicantDto applicantDto, Integer applicantId) {
        Applicant applicant=this.applicantRepo.findById(applicantId).orElseThrow(()-> new ResourceNotFoundException("Applicant", "id", applicantId));
        applicant.setUserName(applicantDto.getUserName());
        applicant.setName(applicantDto.getName());
        applicant.setEmail(applicantDto.getEmail());
        applicant.setPassword(applicantDto.getPassword());
        applicant.setSkills(applicantDto.getSkills());

        Applicant savedApplicant = this.applicantRepo.save(applicant);

        return this.modelMapper.map(savedApplicant, ApplicantDto.class);
    }

    @Override
    public ApplicantDto getApplicantById(Integer applicantId) {
        Applicant applicant=this.applicantRepo.findById(applicantId).orElseThrow(()-> new ResourceNotFoundException("Applicant", "id", applicantId));
        return this.modelMapper.map(applicant, ApplicantDto.class);
    }

    @Override
    public List<ApplicantDto> getAllApplicants() {
        List<Applicant> applicants=this.applicantRepo.findAll();
        return applicants.stream().map(applicant -> this.modelMapper.map(applicant, ApplicantDto.class)).collect(Collectors.toList());
    }

    @Override
    public void deleteApplicant(Integer applicantId) {
        Applicant applicant=this.applicantRepo.findById(applicantId).orElseThrow(()-> new ResourceNotFoundException("Applicant", "id", applicantId));
        this.applicantRepo.delete(applicant);
    }
}
