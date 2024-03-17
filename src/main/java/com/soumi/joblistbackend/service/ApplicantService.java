package com.soumi.joblistbackend.service;

import com.soumi.joblistbackend.payloads.ApplicantDto;

import java.util.List;


public interface ApplicantService {

    ApplicantDto addApplicant(ApplicantDto applicantDto);

    ApplicantDto updateApplicant(ApplicantDto applicantDto, Integer applicantId);

    ApplicantDto getApplicantById(Integer applicantId);

    List<ApplicantDto> getAllApplicants();

    void deleteApplicant(Integer applicantId);
}
