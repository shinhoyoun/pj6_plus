package com.example.demo.domain.store.service;

import com.example.demo.domain.review.entity.Review;
import com.example.demo.domain.store.entity.Store;
import com.opencsv.CSVReader;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@NoArgsConstructor
public class StoreUploadService {

    private StoreRepository storeRepository;

    @Autowired
    public StoreUploadService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public void uploadCsvFile(MultipartFile file) throws Exception {
            try (CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
                String[] nextRecord;
                List<Store> stores = new ArrayList<>();
                int batchSize = 1000;

                csvReader.readNext();

                while ((nextRecord = csvReader.readNext()) != null) {
                   // CSV 데이터 추출
                   String companyName = nextRecord[0];
                   String mallName = nextRecord[1];
                   String domainName = nextRecord[2];
                   String phoneNumber = nextRecord[3];
                   String email = nextRecord[4];
                   String salesRegNo = nextRecord[5];
                   String businessType = nextRecord[6];
                   String initialReportDate = nextRecord[7];
                   String address = nextRecord[8];
                   String status = nextRecord[9];
                   String totalRating = nextRecord[10];
                   String bizInfoRating = nextRecord[11];
                   String withdrawalRating = nextRecord[12];
                   String paymentRating = nextRecord[13];
                   String termsRating = nextRecord[14];
                   String privacyRating = nextRecord[15];
                   String mainItem = nextRecord[16];
                   String withdrawalPossibility = nextRecord[17];
                   String initialScreenInfo = nextRecord[18];
                   String paymentMethod = nextRecord[19];
                   String termsCompliance = nextRecord[20];
                   String privacyPolicy = nextRecord[21];
                   String requestExtraInfo = nextRecord[22];
                   String safetyService = nextRecord[23];
                   String securityServer = nextRecord[24];
                   String certificationMark = nextRecord[25];
                   String deliveryDateDisplay = nextRecord[26];
                   String deliveryFeeBurden = nextRecord[27];
                   String complaintBoard = nextRecord[28];
                   String memberWithdrawal = nextRecord[29];
                   String siteOpenYear = nextRecord[30];
                   String monitoringDate = nextRecord[31];

                    // 데이터 변환 및 Store 객체 생성
                    Store store = Store.builder()
                            .companyName(companyName)
                            .mallName(mallName)
                            .domainName(domainName)
                            .phoneNumber(phoneNumber)
                            .email(email)
                            .salesRegNo(salesRegNo)
                            .businessType(businessType)
                            .initialReportDate(LocalDate.parse(initialReportDate))
                            .address(address)
                            .status(status)
                            .totalRating(Integer.valueOf(totalRating))
                            .bizInfoRating(Integer.valueOf(bizInfoRating))
                            .withdrawalRating(Integer.valueOf(withdrawalRating))
                            .paymentRating(Integer.valueOf(paymentRating))
                            .termsRating(Integer.valueOf(termsRating))
                            .privacyRating(Integer.valueOf(privacyRating))
                            .mainItem(mainItem)
                            .withdrawalPossibility(withdrawalPossibility)
                            .initialScreenInfo(initialScreenInfo)
                            .paymentMethod(paymentMethod)
                            .termsCompliance(termsCompliance)
                            .privacyPolicy(privacyPolicy)
                            .requestExtraInfo(requestExtraInfo)
                            .safetyService(safetyService)
                            .securityServer(securityServer)
                            .certificationMark(certificationMark)
                            .deliveryDateDisplay(deliveryDateDisplay)
                            .deliveryFeeBurden(deliveryFeeBurden)
                            .complaintBoard(complaintBoard)
                            .memberWithdrawal(memberWithdrawal)
                            .siteOpenYear(siteOpenYear)
                            .monitoringDate(LocalDate.parse(monitoringDate))
                            .build();

                    stores.add(store);

                    // 메모리 관리를 위해 일정 크기마다 저장 후 리스트 비우기
                    if (stores.size() >= batchSize) {
                        storeRepository.saveAll(stores);
                        stores.clear();
                    }
                }

//                // 남은 데이터 저장
//                if (!stores.isEmpty()) {
//                    storeRepository.saveAll(stores);
//                }
            }
        }
//
//    // 날짜 파싱 헬퍼 메소드 (YYYY-MM-DD 형식 가정)
//    private LocalDate parseDate(String dateStr) {
//        if (dateStr == null || dateStr.trim().isEmpty()) return null;
//        try {
//            return LocalDate.parse(dateStr, DateTimeFormatter.ISO_DATE);
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    // 정수 파싱 헬퍼 메소드
//    private Integer parseInteger(String intStr) {
//        if (intStr == null || intStr.trim().isEmpty()) return null;
//        try {
//            return Integer.parseInt(intStr);
//        } catch (NumberFormatException e) {
//            return 0;
//        }
//    }
}
