package com.example.demo.domain.store.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "seoul_shop_info")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SeoulShop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "shop_name")
    private String shopName;

    @Column(name = "domain_name", columnDefinition = "TEXT")
    private String domainName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "sales_registration_number")
    private String salesRegistrationNumber;

    @Column(name = "business_type")
    private String businessType;

    @Column(name = "initial_report_date")
    private String initialReportDate;

    @Column(name = "address", length = 500)
    private String address;

    @Column(name = "status_code")
    private String statusCode;

    @Column(name = "status")
    private String status;

    @Column(name = "total_rating")
    private Integer totalRating;

    @Column(name = "business_info_rating")
    private Integer businessInfoRating;

    @Column(name = "withdrawal_rating")
    private Integer withdrawalRating;

    @Column(name = "payment_method_rating")
    private Integer paymentMethodRating;

    @Column(name = "terms_rating")
    private Integer termsRating;

    @Column(name = "privacy_security_rating")
    private Integer privacySecurityRating;


    @Column(name = "main_item")
    private String mainItem;

    @Column(name = "withdrawal_possibility")
    private String withdrawalPossibility;

    @Column(name = "initial_screen_info", columnDefinition = "TEXT")
    private String initialScreenInfo;

    @Column(name = "payment_methods", columnDefinition = "TEXT")
    private String paymentMethods;

    @Column(name = "terms_compliance")
    private String termsCompliance;

    @Column(name = "privacy_policy")
    private String privacyPolicy;

    @Column(name = "request_extra_info")
    private String requestExtraInfo;

    @Column(name = "safety_service")
    private String safetyService;

    @Column(name = "security_server")
    private String securityServer;

    @Column(name = "certification_mark")
    private String certificationMark;

    @Column(name = "delivery_date_display")
    private String deliveryDateDisplay;

    @Column(name = "delivery_fee_burden")
    private String deliveryFeeBurden;

    @Column(name = "complaint_board")
    private String complaintBoard;

    @Column(name = "member_withdrawal")
    private String memberWithdrawal;

    @Column(name = "site_open_year")
    private String siteOpenYear;

    @Column(name = "monitoring_date")
    private String monitoringDate;

    @Builder
    public SeoulShop(String companyName, String shopName, String domainName, String phoneNumber, String email, String salesRegistrationNumber, String businessType, String initialReportDate, String address, String statusCode, String status, Integer totalRating, Integer businessInfoRating, Integer withdrawalRating, Integer paymentMethodRating, Integer termsRating, Integer privacySecurityRating, String mainItem, String withdrawalPossibility, String initialScreenInfo, String paymentMethods, String termsCompliance, String privacyPolicy, String requestExtraInfo, String safetyService, String securityServer, String certificationMark, String deliveryDateDisplay, String deliveryFeeBurden, String complaintBoard, String memberWithdrawal, String siteOpenYear, String monitoringDate) {
        this.companyName = companyName;
        this.shopName = shopName;
        this.domainName = domainName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.salesRegistrationNumber = salesRegistrationNumber;
        this.businessType = businessType;
        this.initialReportDate = initialReportDate;
        this.address = address;
        this.statusCode = statusCode;
        this.status = status;
        this.totalRating = totalRating;
        this.businessInfoRating = businessInfoRating;
        this.withdrawalRating = withdrawalRating;
        this.paymentMethodRating = paymentMethodRating;
        this.termsRating = termsRating;
        this.privacySecurityRating = privacySecurityRating;
        this.mainItem = mainItem;
        this.withdrawalPossibility = withdrawalPossibility;
        this.initialScreenInfo = initialScreenInfo;
        this.paymentMethods = paymentMethods;
        this.termsCompliance = termsCompliance;
        this.privacyPolicy = privacyPolicy;
        this.requestExtraInfo = requestExtraInfo;
        this.safetyService = safetyService;
        this.securityServer = securityServer;
        this.certificationMark = certificationMark;
        this.deliveryDateDisplay = deliveryDateDisplay;
        this.deliveryFeeBurden = deliveryFeeBurden;
        this.complaintBoard = complaintBoard;
        this.memberWithdrawal = memberWithdrawal;
        this.siteOpenYear = siteOpenYear;
        this.monitoringDate = monitoringDate;
    }
}
