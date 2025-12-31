package com.example.demo.common.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "review")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store storeId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    private String content;

    private boolean isDeleted;
}
