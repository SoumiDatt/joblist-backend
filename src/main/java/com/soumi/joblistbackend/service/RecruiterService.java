package com.soumi.joblistbackend.service;

import com.soumi.joblistbackend.payloads.RecruiterDto;

import java.util.List;

public interface RecruiterService {

    RecruiterDto addRecruiter(RecruiterDto recruiterDto);

    RecruiterDto updateRecruiter(RecruiterDto recruiterDto, Integer recruiterId);

    RecruiterDto getRecruiterById(Integer recruiterId);

    List<RecruiterDto> getAllRecruiters();

    void deleteRecruiter(Integer recruiterId);
}
