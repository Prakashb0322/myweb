package com.prakash.myweb.repository;

import com.prakash.myweb.model.Conversion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversionRepository extends JpaRepository<Conversion, Long> {
}