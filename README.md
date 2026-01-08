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
- **인원:** 서울시 인터넷 쇼핑몰 현황(난이도 하) : 이세진 - csv 파일 업로드, openApi를 이용한 데이터베이스 업로드 , 백재현 - 필터기능, Pageable 기반 업체리스트 조회

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

### Step 2: 성능 개선 (Cache)
반복적인 조회 요청에 대한 응답 속도를 개선하기 위해 캐싱 전략을 단계별로 적용했습니다.

- [x] **인기 검색어 기능**
    - 자주 검색되는 키워드를 집계하여 제공.
- [x] **In-memory Cache (Local)**
    - 자주 조회되는 데이터에 로컬 캐시를 적용하여 DB 부하 감소.
- [x] **Remote Cache (Redis)**
    - 다중 서버 환경을 고려하여 **Redis 기반의 글로벌 캐시(v2 API)** 로 고도화.
    - 캐시 적용 전/후 응답 속도 비교 및 문서화 완료.

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

### 1. 대용량 데이터 조회 속도 개선 (Cache)
* **문제:** 검색 요청이 증가함에 따라 DB 조회 Latency 증가.
* **해결:**
    * 1단계: Spring Cache(`@Cacheable`)를 이용한 로컬 캐시 적용.
    * 2단계: 스케일 아웃 상황을 고려하여 Redis로 마이그레이션.
* **결과:** 평균 응답 속도 `XXXms` -> `XXms`로 **약 00% 개선**.

### 2. 동시성 이슈 해결 (Redis Lock)
* **문제:** 테스트 코드를 통해 동시에 100명의 사용자가 요청 시, 갱신 손실(Lost Update) 발생 확인.
* **해결:**
    * Java `synchronized`는 다중 서버에서 동작하지 않음을 확인.
    * Redis의 `Pub/Sub` 기반인 **Redisson** 라이브러리를 도입하여 분산 락 구현.
    * AOP를 적용하여 락 획득/해제 코드를 비즈니스 로직에서 분리하여 가독성 확보.
* **결과:** 1,000건의 동시 요청 테스트 시 **데이터 오차 0건** 달성.

---

## 💻 설치 및 실행 방법

1. **Repository Clone**
   ```bash
   git clone [https://github.com/shinhoyoun/pj6_plus.git](https://github.com/shinhoyoun/pj6_plus.git)
