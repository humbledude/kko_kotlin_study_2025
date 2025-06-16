# kko_kotlin_study_2025

==== 스터디 방법 ====

- 진도표에 따라서, 수단과 방법을 가리지 않고 해당 챕터를 배우고
- 루는 과제를 만들어 주시고
- 3/3/4 로 팀짜서 1명씩 과제 발표 : 당일에 랜덤 돌려서 뽑힌 사람이 과제 코드 리뷰 실시

==== 첫번째 점검날 (4/16 수) 까지 스케쥴 ====

지금부터 1, 2 주차 주제에 대해서 공부하시고
4/11 (금) 첫번쨰 과제 오푼
열심히 과제 풀어서 깃헙 공유
4/16 (수) 에 만나서 팀당 1명씩 과제 발표 및 실전 팁 전수받기

==== 팀 구성 ====
1조: 아니카, 엘란, 제이슨
2조: 그레이, 현, 에단
3조: 프라이드, 조슈, 블레어, 헨리

==== 주차별 주제 ====

## 📌 1단계: 코틀린 문법 익히기 (1~4주차)

✔ 목표: 코틀린의 기본 문법과 특징을 익히고, 자바와의 차이점을 이해하기
✔ 학습 방법: 공식 문서 & 코틀린 공식 온라인 플레이그라운드 활용

📅 주차별 학습 내용

✅ 1주차: 코틀린 기본 문법과 특징
	•	코틀린 소개 및 자바와의 차이
	•	변수, 자료형, 기본적인 함수 문법
	•	null-safety 개념
	•	실습: 간단한 코틀린 콘솔 프로그램 작성

✅ 2주차: 객체지향과 함수형 프로그래밍
	•	클래스와 객체, 상속, 인터페이스
	•	데이터 클래스, 싱글톤, sealed class
	•	확장 함수와 고차 함수 개념
	•	실습: 작은 유틸리티 클래스 구현

✅ 3주차: 컬렉션, 람다, 스트림 처리
	•	List, Set, Map 등 컬렉션 다루기
	•	람다 표현식과 Stream 처리
	•	Scope Functions (let, run, apply, also, with)
	•	실습: 코틀린의 컬렉션 API를 사용한 데이터 처리

✅ 4주차: 비동기 처리 & 코루틴
	•	코루틴 기본 개념 (suspend, async/await, launch)
	•	Flow와 Channel 활용
	•	실습: 간단한 비동기 API 호출 및 데이터 처리

⸻

## 📘 (5~6주차): Spring + Kotlin 기반 구성 + API 테스트 입문

| 세부 주제 | 학습 목표 | 필수 학습 항목 |
|-----------|------------|----------------|
| Kotlin + Spring 프로젝트 셋업 | Kotlin + Spring Boot 프로젝트를 구성하고 실행할 수 있다 | `spring-boot-starter-web`, `kotlin("plugin.spring")`, `application.yml`, `@SpringBootApplication` |
| 간단한 API 작성 | 기본 GET/POST API를 만들고 JSON 응답을 구성할 수 있다 | `@RestController`, `@GetMapping`, `@PostMapping`, `@RequestBody`, `@ResponseBody`, `ResponseEntity` |
| 단위 테스트 | 비즈니스 로직에 대한 단위 테스트를 작성할 수 있다 | `@Test`, `org.junit.jupiter.api`, `Assertions.assertThat` |
| API 테스트 (`@WebMvcTest`) | 컨트롤러 단위로 API 테스트를 작성할 수 있다 | `@WebMvcTest`, `MockMvc`, `mockMvc.perform(...)`, `andExpect(...)`, `status().isOk()` |
| MockK으로 서비스 목 처리 | 서비스 계층을 Mock 처리하여 독립적으로 테스트할 수 있다 | `@MockK`, `@InjectMockKs`, `every { ... } returns ...` |
| 예외 케이스 테스트 작성 | 실패 시 응답 코드와 메시지를 테스트할 수 있다 | `@ExceptionHandler`, `status().isBadRequest()`, 예외 클래스 작성 등 |

🎯 **이 회차 목표**: API와 테스트를 스프링 방식으로 구현하고, MockK으로 의존성을 분리한 테스트를 직접 작성할 수 있다.

---

## 📗 (7~8주차): WebClient + JPA 저장 및 활용 API 구성

| 세부 주제 | 학습 목표 | 필수 학습 항목 |
|-----------|------------|----------------|
| WebClient로 외부 API 호출 | 외부 JSON API를 호출하고 데이터를 수신할 수 있다 | `WebClient.create()`, `.get().uri(...)`, `.retrieve().bodyToMono(...)`, `.awaitBody()` |
| 외부 데이터를 DTO로 매핑 후 가공 | 응답 JSON을 Kotlin 데이터 클래스로 매핑할 수 있다 | `data class`, `@JsonProperty`, `ObjectMapper` |
| Spring Data JPA 기본 사용 | Entity를 정의하고 저장/조회할 수 있다 | `@Entity`, `@Id`, `@GeneratedValue`, `JpaRepository`, `save()`, `findAll()`, `findById()` |
| JPA 전용 테스트 구성 | Repository 단위 테스트를 작성할 수 있다 | `@DataJpaTest`, `TestEntityManager`, `@AutoConfigureTestDatabase`, H2 설정 |
| 저장 데이터를 활용한 API 구성 | DB 데이터를 조건에 따라 가공해 응답할 수 있다 | `@Transactional`, `findByXxx()`, DTO 변환 매핑 |
| 전체 흐름 통합 테스트 구성 | API → Service → DB 전체 흐름 테스트를 작성할 수 있다 | `@SpringBootTest`, `@Transactional`, `TestRestTemplate` 또는 `MockMvc` |

🎯 **이 회차 목표**: 외부 데이터를 JPA로 저장하고, 이를 활용한 API 흐름을 테스트 기반으로 구현할 수 있다.

---

## 📌 주요 실습 흐름 예시

- `POST /festivals/import` → 외부 API 호출 → DB 저장
- `GET /festivals` → 저장된 목록 조회
- `GET /festivals/today` → 날짜 기반 필터링
- 테스트: `@WebMvcTest`, `@DataJpaTest`, `@SpringBootTest`
