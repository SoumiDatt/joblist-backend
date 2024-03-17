package com.soumi.joblistbackend.service.implementation;

import com.soumi.joblistbackend.entities.Recruiter;
import com.soumi.joblistbackend.exceptions.ResourceNotFoundException;
import com.soumi.joblistbackend.payloads.RecruiterDto;
import com.soumi.joblistbackend.repositories.RecruiterRepo;
import com.soumi.joblistbackend.service.RecruiterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecruiterServiceImp implements RecruiterService {

    @Autowired
    private RecruiterRepo recruiterRepo;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public RecruiterDto addRecruiter(RecruiterDto recruiterDto) {
        Recruiter recruiter=this.modelMapper.map(recruiterDto, Recruiter.class);
        Recruiter savedRecruiter = this.recruiterRepo.save(recruiter);
        return this.modelMapper.map(savedRecruiter, RecruiterDto.class);
    }

    @Override
    public RecruiterDto updateRecruiter(RecruiterDto recruiterDto, Integer recruiterId) {

        Recruiter recruiter=this.recruiterRepo.findById(recruiterId).orElseThrow(()-> new ResourceNotFoundException("Recruiter", "id", recruiterId));
        recruiter.setUsername(recruiterDto.getUsername());
        recruiter.setName(recruiterDto.getName());
        recruiter.setEmail(recruiterDto.getEmail());
        recruiter.setPassword(recruiterDto.getPassword());

        Recruiter savedRecruiter = this.recruiterRepo.save(recruiter);

        return this.modelMapper.map(savedRecruiter, RecruiterDto.class);

    }

    @Override
    public RecruiterDto getRecruiterById(Integer recruiterId) {
        Recruiter recruiter=this.recruiterRepo.findById(recruiterId).orElseThrow(()-> new ResourceNotFoundException("Recruiter", "id", recruiterId));
        return this.modelMapper.map(recruiter, RecruiterDto.class);
    }

    @Override
    public List<RecruiterDto> getAllRecruiters() {
        List<Recruiter> recruiters=this.recruiterRepo.findAll();

        return recruiters.stream().map(recruiter -> this.modelMapper.map(recruiter, RecruiterDto.class)).collect(Collectors.toList());
    }

    @Override
    public void deleteRecruiter(Integer recruiterId) {
        Recruiter recruiter=this.recruiterRepo.findById(recruiterId).orElseThrow(()-> new ResourceNotFoundException("Recruiter", "id", recruiterId));
        this.recruiterRepo.delete(recruiter);
    }
}
