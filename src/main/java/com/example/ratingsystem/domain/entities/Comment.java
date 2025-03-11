package com.example.ratingsystem.domain.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

@Entity
@Table(name = "comments")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Comment extends BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(
            optional = false,
            cascade = CascadeType.ALL
    )
    @MapsId
    @JoinColumn(name = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CommentDetails details = new CommentDetails();
    @ManyToOne(optional = false)
    private User seller;
    @ManyToOne
    private User author;

    public Comment(CommentDetails details, User seller, User author) {
        this.details = details;
        this.seller = seller;
        this.author = author;
    }
}