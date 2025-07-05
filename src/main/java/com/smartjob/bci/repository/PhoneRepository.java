package com.smartjob.bci.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartjob.bci.domain.Phone;

public interface PhoneRepository extends JpaRepository<Phone, Integer> {

}
