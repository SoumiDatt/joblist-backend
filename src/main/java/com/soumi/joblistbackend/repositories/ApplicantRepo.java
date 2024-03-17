package com.soumi.joblistbackend.repositories;

import com.soumi.joblistbackend.entities.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicantRepo extends JpaRepository<Applicant, Integer> {
}
