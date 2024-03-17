package com.soumi.joblistbackend.controllers;


import com.soumi.joblistbackend.payloads.ApiResponse;
import com.soumi.joblistbackend.payloads.RecruiterDto;
import com.soumi.joblistbackend.service.RecruiterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recruiters")
public class RecruiterController {

    @Autowired
    private RecruiterService recruiterService;

    //POST-create recruiter
    @PostMapping("/")
    public ResponseEntity<RecruiterDto> createRecruiter(@Valid @RequestBody RecruiterDto recruiterDto){
        RecruiterDto createRecruiterDto=this.recruiterService.addRecruiter(recruiterDto);
        return new ResponseEntity<>(createRecruiterDto, HttpStatus.CREATED);
    }

    //PUT - update user
    @PutMapping("/{recruiterId}")
    public ResponseEntity<RecruiterDto> updateRecruiter(@Valid @RequestBody RecruiterDto recruiterDto, @PathVariable Integer recruiterId){
        RecruiterDto updatedRecruiter=this.recruiterService.updateRecruiter(recruiterDto, recruiterId);
        return new ResponseEntity<>(updatedRecruiter, HttpStatus.OK);
    }

    //DELETE
    @DeleteMapping("/{recruiterId}")
    public ResponseEntity<ApiResponse> deleteRecruiter(@PathVariable Integer recruiterId){
        this.recruiterService.deleteRecruiter(recruiterId);
        return new ResponseEntity(new ApiResponse("Recruiter deleted successfully", true), HttpStatus.OK);
    }

    //GET
    @GetMapping("/")
    public ResponseEntity<List<RecruiterDto>> getAllRecruiters(){
        return ResponseEntity.ok(this.recruiterService.getAllRecruiters());
    }

    @GetMapping("/{recruiterId}")
    public ResponseEntity<RecruiterDto> getRecruiter(@PathVariable Integer recruiterId){
        return ResponseEntity.ok(this.recruiterService.getRecruiterById(recruiterId));
    }

}
