# 🛒 서울시 인터넷 쇼핑몰 현황 분석 및 성능 개선 프로젝트

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white)

> **서울시 인터넷 쇼핑몰 인허가 데이터를 활용한 정보 제공 서비스 및 대용량 트래픽/동시성 제어 학습 프로젝트** > 데이터 수집 파이프라인 구축부터 캐싱을 통한 성능 최적화, Redis를 활용한 동시성 이슈 해결까지 단계별로 고도화하였습니다.

---

## 📚 목차
1. [프로젝트 개요](#-프로젝트-개요)
2. [기술 스택](#-기술-스택)
3. [주요 기능 및 구현 내용](#-주요-기능-및-구현-내용)
    - [Step 1: 데이터 파이프라인 (Basic)](#step-1-데이터-파이프라인-basic)
    - [Step 2: 성능 개선 (Cache)](#step-2-성능-개선-cache)
    - [Step 3: 동시성 제어 (Concurrency)](#step-3-동시성-제어-concurrency)
4. [트러블 슈팅 & 성능 최적화](#-트러블-슈팅--성능-최적화)
5. [설치 및 실행 방법](#-설치-및-실행-방법)

---

## 🚀 프로젝트 개요
이 프로젝트는 단순한 CRUD 구현을 넘어, 백엔드 개발에서 마주할 수 있는 **성능 문제(조회 속도)**와 **데이터 무결성 문제(동시성 이슈)**를 기술적으로 해결하는 데 중점을 두었습니다.

- **기간:** 2025.12.31 ~ 2026.01.09
- **인원:** 서울시 인터넷 쇼핑몰 현황(난이도 하) : 이세진 - csv 파일 업로드, openApi를 이용한 데이터베이스 업로드
- 백재현 : 필터기능, ‘전체평가’ 필터 조회 및 ‘업소상태’ 필터 조회기능 구현, Pageable 기반 업체리스트 조회
- 신호윤 : 유저CRUD(회원가입 포함),검색 기능 API 구현, 카페인을 통해 입력 된 인메모리에 캐싱 기능 적용, 레디스 적용
- 이청운 : 로그인(로그인, 토큰 발급 까지만) ,리뷰CRUD 검색 기능 API 구현, 카페인을 통해 입력 된 인메모리에 캐싱 기능 적용, 레디스 적용
- 고아람 :동시성 이슈에 대한 제어하는 로직 구현했다.
---

## 🛠 기술 스택
- **Language:** Java 17
- **Framework:** Spring Boot 3.x
- **Database:** MySQL 8.0, Redis
- **ORM:** Spring Data JPA, QueryDSL(선택 시 기재)
- **Tool:** Gradle, Git, JMeter(테스트용)

---

## 💡 주요 기능 및 구현 내용

### Step 1: 데이터 파이프라인 (Basic)
서울시 공공데이터를 효과적으로 적재하고 조회하는 기본 기능을 구현했습니다.

- [x] **대용량 CSV 데이터 처리**
    - OpenCSV/Batch 처리를 통해 데이터를 100개 단위(Chunk)로 나누어 DB에 적재 (Bulk Insert 최적화).
- [x] **OpenAPI 연동**
    - 서울시 실시간 데이터를 OpenAPI를 통해 수집 및 DB 동기화.
- [x] **검색 및 필터링**
    - `Pageable`을 활용한 페이징 처리 및 조건별 업체 리스트 조회 기능.

### Step 2: 검색 기능 (Cache)
입력된 키워드를 캐시에 담아 저장 하여 많이 조회된 키워드 5개를 상위로 올리는 기능을 구현하였습니다.
카페인을 통한 인메모리를 적용하여 입력 후 인메모리에 저장이 되게 하였고 이후 휘발성으로 인하여 불안한 요소를 레디스를 이용하여 안정성을 더했습니다.
또 한 한 개 이상의 서버를 구성하여 보다 안전하게 구성하였습니다.

- [x] **인기 검색어 기능**
    - 자주 검색되는 키워드를 집계하여 제공.
- [x] **In-memory Cache (Local)** 
    - 자주 조회되는 데이터에 로컬 캐시를 적용하여 DB 부하 감소.
- [x] **Remote Cache (Redis)**
    - 다중 서버 환경을 고려하여 **Redis 기반의 글로벌 캐시(v2 API)** 로 고도화.
    - 검색된 기록에 따라서 **상위 순위**를 볼 수 있는 인기 검색어 적용.

### Step 3: 동시성 제어 (Concurrency)
선착순 이벤트나 재고 차감과 같이 순간적으로 요청이 몰리는 상황을 가정하고 데이터 정합성을 보장했습니다.

- [x] **동시성 이슈 시나리오 설계**
    - 다수의 사용자가 동시에 동일 자원에 접근하는 상황 기획.
- [x] **테스트 코드 작성**
    - `ExecutorService`와 `CountDownLatch`를 활용하여 Race Condition 재현 및 검증.
- [x] **Redis Distributed Lock (Redisson)**
    - 분산 락을 적용하여 동시성 이슈 완벽 제어.
- [x] **AOP 기반 리팩토링**
    - `@DistributedLock` 어노테이션을 생성하고 AOP로 비즈니스 로직과 락 처리 로직 분리 (관심사 분리).
- [x] **MySQL Named Lock/Optimistic Lock** (선택 구현 시)
    - Redis 외의 대안으로 DB 락을 이용한 제어 방식 비교 구현.

---

## 📈 트러블 슈팅 & 성능 최적화

### 1. JPAQueryFactory 등록 부재 이슈 해결
* **문제:** Parameter 0 of constructor in com.example.demo.domain.store.repository.StoreRepositoryImpl required a bean of type 'com.querydsl.jpa.impl.JPAQueryFactory' that could not be found. - 에러발생 : Spring이 생성자 주입을 하려는데, 필요한 Bean이 IoC 컨테이너에 없어서 실패
* **해결:**
    * QueryDSL을 사용할 때 JPAQueryFactory는 Spring이 자동으로 Bean 등록하지 않는다.
    * EntityManager은 Spring Boot가 자동으로 Bean등록✅ BUT, JPAQueryFactory는 QueryDSL 라이브러리 객체라 자동 등록 ❌
    * JPAQueryFactory 타입의 Bean을 직접 설정 클래스에서 등록
 

### 2. 검색어 기록 시 공백 이슈

<img width="218" height="155" alt="스크린샷 2026-01-08 오전 9 55 49" src="https://github.com/user-attachments/assets/780dcd10-a1d1-4156-ada3-813a68805c00" />

**문제**
(튜터님 도움을 받았습니다.)
*  검색시 제대로 값을 저장하지 못하는 상황이 발생함
*  포스트맨에서 키워드 입력 후 조회리스트에 ""의 공백이 발생.
**해결**
    * 검색 키워드를 전체 목록 조회 시 포스트맨에 공백을 확인
    * 로그에 자동으로 한 칸 띄어지는 것을 발견
 
        현 상황 ["", "곱창" "닭발", "삼겹살"] 같은 형태로 출력됨
        올바른 예시 ["곱창" "닭발", "삼겹살"]
      
    * 해당 목록 조회 부분에서 문제 코드 발견 trim으로 인하여 공백이 자동 생성 됨을 확인
    ``` if(keyword == null || keyword.trim().isEmpty()) {return null;}```
    * 코드에서 trim 부분 전부 삭제 후 해결
 
   

### 3. 변경 사항을 문서화 및 설계 방향 이슈

** 문제 ** 
프로젝트 진행 중 요구사항 및 기능 변경이 발생했을 때, 변경 내용을 구두로만 공유한 뒤 바로 개발을 진행한 부분 

그 결과,  API 명세, ERD, 기능 흐름 등 명확한 목적 없이 진행
최종 구현 결과가 초기 목표와 조금은 다른 방향으로 진행되는 문제가 발생

**원인**	
문서 최신화: 변경된 요구사항이 API/ERD에 늦게 반영
소통오류: 같이 맡은 파트 팀원마다 변경 내용을 다르게 이해
검증 불가: 구현 결과가 요구사항과 일치하는지 판단 및 수정 시 많은 시간 소요

앞으로는 변경 사항 발생 시 다음 프로세스를 적용하도록 개선하여야겠다.

**해결**
1. 요구사항 변경 내용 문서화
2. API 명세서, ERD 작성 및 수정 후 소통 
3. 기능 흐름도 또는 시퀀스 정리 -> 단계별 접근하기
4. 이후 개발 착수

**배운 점**
설계 문서와 소통한 부분을 기록하여 문서화 해야되는 것을 알게됨
설계 문서는 개발의 방향성을 확이하는 기준이기에 명확하게 하기

---

## 💻 설치 및 실행 방법 ,csv파일 첨부

1. **Repository Clone**
   ```bash
   git clone [https://github.com/shinhoyoun/pj6_plus.git](https://github.com/shinhoyoun/pj6_plus.git)

https://drive.google.com/file/d/1_nGMA09XmYfG-extIA5bKP1gqT2WoZPv/view?usp=sharing
