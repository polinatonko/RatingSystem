package com.example.ratingsystem.domain.entities;

import com.example.ratingsystem.domain.enums.RequestStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

@Entity
@Table(name = "submit_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SubmitRequest extends BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Enumerated(EnumType.STRING)
    @NotNull
    @ColumnDefault("WAITING")
    private RequestStatus status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CommentDetails commentDetails;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private UserDetails userDetails;
    @ManyToOne
    private User seller;
    @ManyToOne
    private User author;

    public boolean containsComment() { return commentDetails != null; }

    public boolean isRegistration() {
        return userDetails != null;
    }

    public boolean isProcessed() { return status != RequestStatus.WAITING; }
}