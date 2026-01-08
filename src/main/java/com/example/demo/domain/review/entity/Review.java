package com.example.demo.domain.review.entity;

import com.example.demo.common.entity.BaseEntity;
import com.example.demo.domain.store.entity.Store;
import com.example.demo.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reviews")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "name", nullable = false)
    public String name;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    public Review(Store store, User user, String name, String content) {
        this.store = store;
        this.user = user;
        this.content = content;
        this.name = name;
    }

    public void update(String content) {
        this.content = content;
    }

    public void softDelete() {
        this.isDeleted = true;
    }
}
