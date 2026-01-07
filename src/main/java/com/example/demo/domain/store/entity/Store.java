package com.example.demo.domain.store.entity;

import com.example.demo.common.entity.BaseEntity;
import com.example.demo.domain.review.entity.Review;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "stores")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    // 1. 기본 정보
    @Column(name = "company_name")
    private String companyName; // 상호

    @Column(name = "mall_name")
    private String mallName; // 쇼핑몰명

    @Column(name = "domain_name",length = 10000)
    private String domainName; // 도메인명

    @Column(name = "phone_number")
    private String phoneNumber; // 전화번호

    @Column(name = "email")
    private String email; // 운영자이메일

    @Column(name = "sales_reg_no")
    private String salesRegNo; // 통신판매번호

    @Column(name = "business_type")
    private String businessType; // 영업형태

    @Column(name = "initial_report_date")
    private LocalDate initialReportDate; // 최초신고일자 (YYYY-MM-DD)

    @Column(name = "address")
    private String address; // 회사주소

    // 2. 상태 및 평가 (필터링 핵심 컬럼)
    @Column(name = "status")
    private String status; // 업소상태 (영업중, 휴업중 등)

    @Column(name = "total_rating")
    private Integer totalRating; // 전체평가 (0~3)

    // 3. 세부 평가 항목
    @Column(name = "biz_info_rating")
    private Integer bizInfoRating; // 사업자정보표시평가

    @Column(name = "withdrawal_rating")
    private Integer withdrawalRating; // 청약철회평가

    @Column(name = "payment_rating")
    private Integer paymentRating; // 결재방법평가

    @Column(name = "terms_rating")
    private Integer termsRating; // 이용약관평가

    @Column(name = "privacy_rating")
    private Integer privacyRating; // 개인정보보안평가

    // 4. 상세 정보
    @Column(name = "main_item")
    private String mainItem; // 주요취급품목

    @Column(name = "withdrawal_possibility")
    private String withdrawalPossibility; // 청약철회가능여부

    @Column(name = "initial_screen_info", columnDefinition = "TEXT")
    private String initialScreenInfo; // 초기화면필수항목중표시사항 (내용이 길 수 있음)

    @Column(name = "payment_method")
    private String paymentMethod; // 결제방법

    @Column(name = "terms_compliance")
    private String termsCompliance; // 이용약관준수정도

    @Column(name = "privacy_policy")
    private String privacyPolicy; // 개인정보취급방침

    @Column(name = "request_extra_info")
    private String requestExtraInfo; // 표준약관이상개인정보항목요구

    @Column(name = "safety_service")
    private String safetyService; // 구매안전서비스

    @Column(name = "security_server")
    private String securityServer; // 보안서버설치

    @Column(name = "certification_mark")
    private String certificationMark; // 인증마크

    @Column(name = "delivery_date_display")
    private String deliveryDateDisplay; // 배송예정일표시

    @Column(name = "delivery_fee_burden")
    private String deliveryFeeBurden; // 철회시배송비부담여부

    @Column(name = "complaint_board")
    private String complaintBoard; // 고객불만게시판운영

    @Column(name = "member_withdrawal")
    private String memberWithdrawal; // 회원탈퇴방법

    @Column(name = "site_open_year")
    private String siteOpenYear; // 사이트개설년도

    @Column(name = "monitoring_date")
    private LocalDate monitoringDate; // 모니터링날짜

    @Column
    private boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;
}