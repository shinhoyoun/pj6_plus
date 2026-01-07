package com.example.demo.domain.store.service;

import com.example.demo.domain.store.entity.SeoulShop;
import com.example.demo.domain.store.entity.Store;
import com.example.demo.domain.store.repository.SeoulShopRepository;
import com.example.demo.domain.store.repository.StoreRepository;
import com.opencsv.CSVReader;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
public class StoreUploadService {

    private final StoreRepository storeRepository;
    private final SeoulShopRepository seoulShopRepository;
    private final ObjectMapper objectMapper;
    private final WebClient webClient;

    public StoreUploadService(StoreRepository storeRepository, SeoulShopRepository seoulShopRepository, ObjectMapper objectMapper, WebClient.Builder webClientBuilder) {
        this.storeRepository = storeRepository;
        this.seoulShopRepository = seoulShopRepository;
        this.objectMapper = objectMapper;
        this.webClient = webClientBuilder.baseUrl("http://openapi.seoul.go.kr:8088").build();
    }

    public void uploadCsvFile(MultipartFile file) throws Exception {
            try (CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
                String[] nextRecord;
                List<Store> stores = new ArrayList<>();
                int batchSize = 1000;

                csvReader.readNext();

                while ((nextRecord = csvReader.readNext()) != null) {
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

                    if (stores.size() >= batchSize) {
                        storeRepository.saveAll(stores);
                        stores.clear();
                    }
                }
            }
        }

    @Transactional
    public void saveAllStore(int start, int end) {

        String key = "686c6f66636c736a3934457a4c4973";
        
        String jsonString;
        try {
            jsonString = webClient.get()
                    .uri("/{key}/json/ServiceInternetShopInfo/{start}/{end}/", key, start, end)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("API 호출 중 오류 발생", e);
        }

        try {
            JsonNode rootNode = objectMapper.readTree(jsonString);

            // "ServiceInternetShopInfo" -> "row" 경로 찾기
            JsonNode rowNode = rootNode.path("ServiceInternetShopInfo").path("row");

            if (rowNode.isMissingNode() || !rowNode.isArray()) {
                return;
            }

            List<SeoulShop> shopList = new ArrayList<>();

            for (JsonNode itemNode : rowNode) {
                SeoulShop seoulShop = SeoulShop.builder()
                        .companyName(itemNode.path("COMPANY").asText(null))
                        .shopName(itemNode.path("SHOP_NAME").asText(null))
                        .domainName(itemNode.path("DOMAIN_NAME").asText(null))
                        .phoneNumber(itemNode.path("TEL").asText(null))
                        .email(itemNode.path("EMAIL").asText(null))
                        .salesRegistrationNumber(itemNode.path("UPJONG_NBR").asText(null))
                        .businessType(itemNode.path("YPFORM").asText(null))
                        .initialReportDate(itemNode.path("FIRST_HEO_DATE").asText(null))
                        .address(itemNode.path("COM_ADDR").asText(null))
                        .statusCode(itemNode.path("STAT_CD").asText(null))
                        .status(itemNode.path("STAT_NM").asText(null))
                        .totalRating(itemNode.path("TOT_RATINGPOINT").asInt(0))
                        .businessInfoRating(itemNode.path("CHOGI_RATINGPOINT").asInt(0))
                        .withdrawalRating(itemNode.path("CHUNG_RATINGPOINT").asInt(0))
                        .paymentMethodRating(itemNode.path("DEAL_RATINGPOINT").asInt(0))
                        .termsRating(itemNode.path("PYOJUN_RATINGPOINT").asInt(0))
                        .privacySecurityRating(itemNode.path("SECURITY_RATINGPOINT").asInt(0))
                        .mainItem(itemNode.path("SERVICE").asText(null))
                        .withdrawalPossibility(itemNode.path("CHUNG").asText(null))
                        .initialScreenInfo(itemNode.path("CHOGI").asText(null))
                        .paymentMethods(itemNode.path("GYULJE").asText(null))
                        .termsCompliance(itemNode.path("PYOJUN").asText(null))
                        .privacyPolicy(itemNode.path("P_INFO_CARE").asText(null))
                        .requestExtraInfo(itemNode.path("PER_INFO").asText(null))
                        .safetyService(itemNode.path("DEAL_CARE").asText(null))
                        .securityServer(itemNode.path("SSL_YN").asText(null))
                        .certificationMark(itemNode.path("INJEUNG").asText(null))
                        .deliveryDateDisplay(itemNode.path("BAESONG_YEJEONG").asText(null))
                        .deliveryFeeBurden(itemNode.path("BAESONG").asText(null))
                        .complaintBoard(itemNode.path("CLIENT_BBS").asText(null))
                        .memberWithdrawal(itemNode.path("LEAVE").asText(null))
                        .siteOpenYear(itemNode.path("KAESOL_YEAR").asText(null))
                        .monitoringDate(itemNode.path("REG_DATE").asText(null))
                        .build();

                shopList.add(seoulShop);
            }

            seoulShopRepository.saveAll(shopList);

        } catch (Exception e) {
            throw new RuntimeException("JSON 파싱 중 오류가 발생했습니다. 데이터: " + jsonString, e);
        }

    }
}
