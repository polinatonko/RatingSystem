package com.example.ratingsystem.domain.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.*;

import java.time.OffsetDateTime;

@MappedSuperclass
@Data
public class BasicEntity {
    @TimeZoneStorage(TimeZoneStorageType.NATIVE)
    @CreationTimestamp
    @Column(updatable = false)
    protected OffsetDateTime createdAt;
    @UpdateTimestamp
    @TimeZoneStorage(TimeZoneStorageType.NATIVE)
    protected OffsetDateTime updatedAt;
}