package com.example.ratingsystem.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Generated;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.TimeZoneStorage;
import org.hibernate.annotations.TimeZoneStorageType;

import java.time.OffsetDateTime;
import java.util.UUID;

@MappedSuperclass
@Data
public class BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @TimeZoneStorage(TimeZoneStorageType.NATIVE)
    @Generated
    @ColumnDefault("NOW()")
    @Column(updatable = false)
    @NotNull
    private OffsetDateTime createdAt;
    @TimeZoneStorage(TimeZoneStorageType.NATIVE)
    @Generated
    @ColumnDefault("NOW()")
    @Column(updatable = false)
    @NotNull
    private OffsetDateTime updatedAt;
}