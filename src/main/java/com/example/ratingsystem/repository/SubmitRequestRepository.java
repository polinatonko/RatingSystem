package com.example.ratingsystem.repository;

import com.example.ratingsystem.domain.entities.SubmitRequest;
import com.example.ratingsystem.domain.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SubmitRequestRepository extends JpaRepository<SubmitRequest, UUID> {
    List<SubmitRequest> findByStatus(RequestStatus status);
}
