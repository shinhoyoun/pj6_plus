package com.example.demo.domain.usercoupon.entitiy;

import com.example.demo.domain.coupon.entity.Coupon;
import com.example.demo.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_coupon",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_user_coupon_user_coupon",
                        columnNames = {"user_id", "coupon_id"}
                )
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCoupon {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    // 에러땜에 임시로 컬럼바꿈
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private User user;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;


    public UserCoupon(Coupon coupon, User user) {
        this.coupon = coupon;
        this.user = user;
    }
}
