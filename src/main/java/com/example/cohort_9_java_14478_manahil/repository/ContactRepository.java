package com.example.cohort_9_java_14478_manahil.repository;

import com.example.cohort_9_java_14478_manahil.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

}