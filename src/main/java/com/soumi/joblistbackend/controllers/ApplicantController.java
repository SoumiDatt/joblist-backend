package com.soumi.joblistbackend.controllers;

import com.soumi.joblistbackend.payloads.ApiResponse;
import com.soumi.joblistbackend.payloads.ApplicantDto;
import com.soumi.joblistbackend.service.ApplicantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applicants")
public class ApplicantController {

    @Autowired
    private ApplicantService applicantService;

    //POST-create applicant
    @PostMapping("/")
    public ResponseEntity<ApplicantDto> createApplicant(@Valid @RequestBody ApplicantDto applicantDto){
        ApplicantDto createApplicantDto=this.applicantService.addApplicant(applicantDto);
        return new ResponseEntity<>(createApplicantDto, HttpStatus.CREATED);
    }

    //PUT - update applicant
    @PutMapping("/{applicantId}")
    public ResponseEntity<ApplicantDto> updateApplicant(@Valid @RequestBody ApplicantDto applicantDto, @PathVariable Integer applicantId){
        ApplicantDto updatedApplicant=this.applicantService.updateApplicant(applicantDto, applicantId);
        return new ResponseEntity<>(updatedApplicant, HttpStatus.OK);
    }

    //DELETE
    @DeleteMapping("/{applicantId}")
    public ResponseEntity<ApiResponse> deleteApplicant(@PathVariable Integer applicantId){
        this.applicantService.deleteApplicant(applicantId);
        return new ResponseEntity(new ApiResponse("Applicant deleted successfully", true), HttpStatus.OK);
    }

    //GET
    @GetMapping("/")
    public ResponseEntity<List<ApplicantDto>> getAllApplicants(){
        return ResponseEntity.ok(this.applicantService.getAllApplicants());
    }

    @GetMapping("/{applicantId}")
    public ResponseEntity<ApplicantDto> getApplicant(@PathVariable Integer applicantId){
        return ResponseEntity.ok(this.applicantService.getApplicantById(applicantId));
    }

}
