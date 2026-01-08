package com.example.demo.domain.store.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStore is a Querydsl query type for Store
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStore extends EntityPathBase<Store> {

    private static final long serialVersionUID = -981149165L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStore store = new QStore("store");

    public final com.example.demo.common.entity.QBaseEntity _super = new com.example.demo.common.entity.QBaseEntity(this);

    public final StringPath address = createString("address");

    public final NumberPath<Integer> bizInfoRating = createNumber("bizInfoRating", Integer.class);

    public final StringPath businessType = createString("businessType");

    public final StringPath certificationMark = createString("certificationMark");

    public final StringPath companyName = createString("companyName");

    public final StringPath complaintBoard = createString("complaintBoard");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath deliveryDateDisplay = createString("deliveryDateDisplay");

    public final StringPath deliveryFeeBurden = createString("deliveryFeeBurden");

    public final StringPath domainName = createString("domainName");

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DatePath<java.time.LocalDate> initialReportDate = createDate("initialReportDate", java.time.LocalDate.class);

    public final StringPath initialScreenInfo = createString("initialScreenInfo");

    public final StringPath mainItem = createString("mainItem");

    public final StringPath mallName = createString("mallName");

    public final StringPath memberWithdrawal = createString("memberWithdrawal");

    public final DatePath<java.time.LocalDate> monitoringDate = createDate("monitoringDate", java.time.LocalDate.class);

    public final StringPath paymentMethod = createString("paymentMethod");

    public final NumberPath<Integer> paymentRating = createNumber("paymentRating", Integer.class);

    public final StringPath phoneNumber = createString("phoneNumber");

    public final StringPath privacyPolicy = createString("privacyPolicy");

    public final NumberPath<Integer> privacyRating = createNumber("privacyRating", Integer.class);

    public final StringPath requestExtraInfo = createString("requestExtraInfo");

    public final com.example.demo.domain.review.entity.QReview review;

    public final StringPath safetyService = createString("safetyService");

    public final StringPath salesRegNo = createString("salesRegNo");

    public final StringPath securityServer = createString("securityServer");

    public final StringPath siteOpenYear = createString("siteOpenYear");

    public final StringPath status = createString("status");

    public final StringPath termsCompliance = createString("termsCompliance");

    public final NumberPath<Integer> termsRating = createNumber("termsRating", Integer.class);

    public final NumberPath<Integer> totalRating = createNumber("totalRating", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final StringPath withdrawalPossibility = createString("withdrawalPossibility");

    public final NumberPath<Integer> withdrawalRating = createNumber("withdrawalRating", Integer.class);

    public QStore(String variable) {
        this(Store.class, forVariable(variable), INITS);
    }

    public QStore(Path<? extends Store> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStore(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStore(PathMetadata metadata, PathInits inits) {
        this(Store.class, metadata, inits);
    }

    public QStore(Class<? extends Store> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.review = inits.isInitialized("review") ? new com.example.demo.domain.review.entity.QReview(forProperty("review"), inits.get("review")) : null;
    }

}

