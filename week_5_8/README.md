
# 5, 6 주차 : Kotlin + Spring Boot 실전 스터디 커리큘럼 (JPA 기반)

## ✅ 개요
- 기간: 5~8주차 (2회차 구성, 2주 단위)
- 목표: Spring Boot + Kotlin 기반의 실무 흐름 학습 (API, DB, 테스트)
- DB 스택: **Spring Data JPA**

---

## 📘 1회차 (5~6주차): Spring + Kotlin 기반 구성 + API 테스트 입문

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

## 📗 2회차 (7~8주차): WebClient + JPA 저장 및 활용 API 구성

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