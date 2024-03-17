package com.soumi.joblistbackend.repositories;


import com.soumi.joblistbackend.entities.Job;
import com.soumi.joblistbackend.entities.Recruiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobRepo extends JpaRepository<Job, Integer> {

    List<Job> findByRecruiter(Recruiter recruiter);

   // @Query("select j from Job j where j.jobTitle like :key or j.location like :key")  //key format %keyword%
   @Query("select j from Job j where concat(j.jobTitle,' ', j.jobDesc, ' ', j.jobType, ' ', j.location, ' ', j.companyName) like :key")
    List<Job> searchByJobTitle(@Param("key") String jobTitle);

   @Query("select j from Job j where j.active=true")
   List<Job> findByActive();

}
