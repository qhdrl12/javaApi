package com.springboot.repository;

import com.springboot.domain.Emp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpRepository extends JpaRepository<Emp, String> {
}
