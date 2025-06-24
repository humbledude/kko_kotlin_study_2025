# 피드 서비스 구현 계획

## 전체 구현 순서

### Phase 1: 백엔드 기반 구축
- [x] **1.1 프로젝트 초기 설정**
  - [x] Kotlin Spring Boot 프로젝트 생성
  - [x] 기본 의존성 설정 (Spring Web, JPA, H2/MySQL 등)
  - [x] 프로젝트 구조 설정

- [x] **1.2 데이터베이스 설계**
  - [x] 사용자(User) 엔티티 설계
  - [x] 컨텐츠(Content) 엔티티 설계
  - [x] 상호작용(UserInteraction) 엔티티 설계 (읽음/좋아요/싫어요)
  - [x] 데이터베이스 스키마 생성

- [x] **1.3 기본 인증 시스템**
  - [x] 사용자 등록/로그인 API
  - [x] 간단한 세션 또는 JWT 기반 인증
  - [x] 사용자 관리 서비스 구현

### Phase 2: 컨텐츠 시스템 구축
- [x] **2.1 컨텐츠 추상화 레이어**
  - [x] Content 인터페이스 정의 (→ Content 엔티티 body 필드로 통합)
  - [x] ContentProvider 인터페이스 정의
  - [x] 기본 컨텐츠 서비스 구현 (PokemonContent, PokemonContentProvider)

- [x] **2.2 Pokemon API 연동**
  - [x] Pokemon API 클라이언트 구현
  - [x] PokemonContentProvider 구현
  - [x] Pokemon 데이터를 Content 모델로 변환

- [x] **2.3 추천 시스템 기초**
  - [x] RecommendationEngine 인터페이스 정의
  - [x] RandomRecommendationEngine 구현 (초기 버전)
  - [x] 추천 모델 교체 가능한 구조 설정
  - [x] 간단한 추천 API 컨트롤러 작성 (/api/pokemon-feed/recommendations)

### Phase 3: 핵심 API 구현
- [x] **3.1 피드 API**
  - [x] GET /api/feed - 사용자별 피드 조회
  - [x] 중복 컨텐츠 방지 로직 (랜덤 추천에서 중복 없는 방식으로 구현)
  - [ ] 페이징 처리 (피드 특성상 별도 페이징 불필요, 미구현)

- [x] **3.2 상호작용 API**
  - [x] POST /api/content/{id}/read - 읽음 처리
  - [x] POST /api/content/{id}/like - 좋아요
  - [x] POST /api/content/{id}/dislike - 싫어요
  - [x] 상호작용 데이터 저장

### Phase 4: 프론트엔드 구현
- [x] **4.1 기본 구조**
  - [x] HTML 페이지 구조 설계
  - [x] CSS 스타일링 (모던하고 깔끔한 UI)
  - [x] JavaScript 모듈 구조 설정

- [x] **4.2 로그인 페이지**
  - [x] 로그인 폼 UI
  - [x] 로그인 API 연동
  - [x] 세션/토큰 관리

- [x] **4.3 피드 페이지**
  - [x] 피드 목록 표시 UI
  - [x] "더 보기" 버튼 구현
  - [x] 읽음/좋아요/싫어요 버튼
  - [x] API 호출 및 상태 관리

### Phase 5: 개선 및 최적화
- [ ] **5.1 성능 개선**
  - [ ] 데이터베이스 쿼리 최적화
  - [ ] 캐싱 전략 적용
  - [ ] API 응답 시간 개선

- [ ] **5.2 추천 시스템 고도화**
  - [ ] 사용자 선호도 기반 추천 모델 구현
  - [ ] 추천 성능 측정 및 개선
  - [ ] A/B 테스트 기반 모델 비교
  - [ ] **사용자 상호작용 기반 추천 개선**
    - [ ] 사용자별 상호작용 데이터 수집 및 분석
      - [ ] 읽음 처리된 컨텐츠 유형 분석
      - [ ] 좋아요/싫어요 패턴 분석
      - [ ] 포켓몬 타입별 선호도 분석
    - [ ] 협업 필터링 기반 추천
      - [ ] 유사한 사용자 찾기 (Similar Users)
      - [ ] 유사한 아이템 찾기 (Similar Items)
    - [ ] 컨텐츠 기반 필터링
      - [ ] 포켓몬 속성 기반 추천 (타입, 능력치 등)
      - [ ] 사용자 선호 패턴 기반 추천
    - [ ] 하이브리드 추천 시스템
      - [ ] 랜덤 + 선호도 기반 혼합 추천
      - [ ] 신규 사용자 대응 (Cold Start Problem)
      - [ ] 다양성 보장 (Diversity in Recommendations)
    - [ ] 추천 성능 지표 개발
      - [ ] 클릭률(CTR) 측정
      - [ ] 사용자 만족도 지표
      - [ ] 추천 다양성 지표

- [x] **5.3 사용자 경험 개선**
  - [x] 로딩 상태 표시
  - [x] 에러 처리 및 사용자 피드백
  - [ ] 반응형 디자인 적용

## 우선순위별 작업

### 🔥 High Priority (MVP)
1. 기본 인증 시스템
2. Pokemon API 연동 및 기본 피드 제공
3. 상호작용 기록 (읽음/좋아요/싫어요)
4. 기본 프론트엔드 구현

### 🔶 Medium Priority
1. 추천 시스템 고도화
2. UI/UX 개선
3. 성능 최적화

### 🔵 Low Priority (향후 확장)
1. 고급 추천 알고리즘
2. 관리자 페이지
3. 다중 컨텐츠 소스 지원

## 기술적 고려사항

### 백엔드
- Spring Boot 3.x 사용
- JPA/Hibernate로 데이터 접근
- RestTemplate 또는 WebClient로 외부 API 호출
- 설정 기반 추천 모델 교체 (Strategy Pattern)

### 프론트엔드
- ES6+ 모던 JavaScript 활용
- Fetch API로 백엔드 통신
- CSS Grid/Flexbox로 레이아웃
- localStorage로 간단한 상태 관리

### 데이터베이스
- 개발: H2 인메모리 DB
- 운영: MySQL 또는 PostgreSQL 고려

## 예상 개발 기간
- **Phase 1-3**: 2-3주 (핵심 기능)
- **Phase 4**: 1주 (기본 프론트엔드)
- **Phase 5**: 1-2주 (개선 및 최적화)

**총 예상 기간: 4-6주**

## 오늘 작업 내역 (2024-06-13)
- 피드 API(3.1): 중복 없는 랜덤 추천, 페이징 불필요로 결정
- 상호작용 API(3.2): 읽음/좋아요/싫어요 기록 및 DB 저장 구현
- PokemonContentProvider: DB 캐시 우선, 없으면 API에서 받아 저장하는 구조로 개선
- H2 콘솔: WebFlux 환경에서는 404, spring-boot-starter-web 추가 시 사용 가능 (현재 WebFlux만 사용 중)

## 최근 작업 내역 (2025-01-03)
- 프론트엔드 구현: 로그인 페이지, 피드 페이지, 상호작용 기능
- 인증 시스템 통일: email 기준에서 username 기준으로 변경
- UI 개선: 글씨색 가독성 향상, 더보기 버튼 구현
- **아키텍처 개선: 역할 분리**
  - FeedService: 단순히 추천 엔진에서 받은 ID로 컨텐츠 조회하는 역할로 단순화
  - RandomRecommendationEngine: 사용자별 중복 방지 로직 및 추천 알고리즘 담당
  - 추천 시스템 고도화 계획 수립 (사용자 상호작용 기반 개선) 