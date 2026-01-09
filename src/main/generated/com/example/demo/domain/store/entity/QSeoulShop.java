package com.example.demo.domain.store.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSeoulShop is a Querydsl query type for SeoulShop
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSeoulShop extends EntityPathBase<SeoulShop> {

    private static final long serialVersionUID = 1409971452L;

    public static final QSeoulShop seoulShop = new QSeoulShop("seoulShop");

    public final StringPath address = createString("address");

    public final NumberPath<Integer> businessInfoRating = createNumber("businessInfoRating", Integer.class);

    public final StringPath businessType = createString("businessType");

    public final StringPath certificationMark = createString("certificationMark");

    public final StringPath companyName = createString("companyName");

    public final StringPath complaintBoard = createString("complaintBoard");

    public final StringPath deliveryDateDisplay = createString("deliveryDateDisplay");

    public final StringPath deliveryFeeBurden = createString("deliveryFeeBurden");

    public final StringPath domainName = createString("domainName");

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath initialReportDate = createString("initialReportDate");

    public final StringPath initialScreenInfo = createString("initialScreenInfo");

    public final StringPath mainItem = createString("mainItem");

    public final StringPath memberWithdrawal = createString("memberWithdrawal");

    public final StringPath monitoringDate = createString("monitoringDate");

    public final NumberPath<Integer> paymentMethodRating = createNumber("paymentMethodRating", Integer.class);

    public final StringPath paymentMethods = createString("paymentMethods");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final StringPath privacyPolicy = createString("privacyPolicy");

    public final NumberPath<Integer> privacySecurityRating = createNumber("privacySecurityRating", Integer.class);

    public final StringPath requestExtraInfo = createString("requestExtraInfo");

    public final StringPath safetyService = createString("safetyService");

    public final StringPath salesRegistrationNumber = createString("salesRegistrationNumber");

    public final StringPath securityServer = createString("securityServer");

    public final StringPath shopName = createString("shopName");

    public final StringPath siteOpenYear = createString("siteOpenYear");

    public final StringPath status = createString("status");

    public final StringPath statusCode = createString("statusCode");

    public final StringPath termsCompliance = createString("termsCompliance");

    public final NumberPath<Integer> termsRating = createNumber("termsRating", Integer.class);

    public final NumberPath<Integer> totalRating = createNumber("totalRating", Integer.class);

    public final StringPath withdrawalPossibility = createString("withdrawalPossibility");

    public final NumberPath<Integer> withdrawalRating = createNumber("withdrawalRating", Integer.class);

    public QSeoulShop(String variable) {
        super(SeoulShop.class, forVariable(variable));
    }

    public QSeoulShop(Path<? extends SeoulShop> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSeoulShop(PathMetadata metadata) {
        super(SeoulShop.class, metadata);
    }

}

