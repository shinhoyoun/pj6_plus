package com.example.demo.domain.store.entity;

import com.example.demo.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "store")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id", nullable = false)
    private Long id;

    private String companyName;
    private boolean review;
    private String mallName;
    private String domainName;
    private String phoneNumber;
    private String email;
    private String salesRegNo;
    private String businessType;
    private LocalDateTime initialReportDate;
    private String address;
    private String status;
    private Long totalRating;
    private Long bizInfoRating;
    private Long withdrawalRating;
    private Long paymentRating;
    private Long termsRating;
    private Long privacyRating;
    private String mainItem;
    private String withdrawalPossibility;
    private String initialScreenInfo;
    private String paymentMethod;
    private String termsCompliance;
    private String privacyPolicy;
    private String requestExtraInfo;
    private String safetyService;
    private String securityServer;
    private String certificationMark;
    private String deliveryDateDisplay;
    private String deliveryFeeBurden;
    private String complaintBoard;
    private String memberWithdrawal;
    private String siteOpenYear;
    private Long monitoringDate;
}
