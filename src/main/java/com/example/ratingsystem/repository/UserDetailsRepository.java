package com.example.ratingsystem.repository;

import com.example.ratingsystem.domain.entities.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserInfo, UUID> {
}
