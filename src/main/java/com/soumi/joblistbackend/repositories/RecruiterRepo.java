package com.soumi.joblistbackend.repositories;

import com.soumi.joblistbackend.entities.Recruiter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruiterRepo extends JpaRepository<Recruiter, Integer> {
}
