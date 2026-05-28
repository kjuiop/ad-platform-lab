# ad-admin-api — Claude Code 가이드

## 모듈 역할

광고 관리자용 REST API 서버. `ad-core`의 도메인/유스케이스를 의존하며, HTTP 진입점 역할만 담당한다.

## 기술 스택

- Spring Boot 4.0.6 (Web, DevTools)
- H2 인메모리 DB (local/test 전용)
- MapStruct (DTO ↔ Command/Result 변환)
- Lombok
- Swagger Annotations 2.2.20

## 패키지 구조

```
org.giglab.ad.admin.api
├── controller/   # REST 컨트롤러 (HTTP 진입점)
├── facade/       # 유스케이스 조합 (Controller → ad-core Service 위임)
├── dto/          # 요청/응답 DTO (Request / Response)
└── mapper/       # MapStruct 인터페이스 (DTO ↔ Core Command/Result)
```

## 계층 책임

| 계층 | 규칙 |
|------|------|
| Controller | HTTP만 다룬다. 비즈니스 로직 없음. `@ExceptionHandler`로 도메인 예외 → HTTP 상태 변환 |
| Facade | 여러 유스케이스/서비스를 조합할 때 사용. 단순 위임이면 생략 가능 |
| DTO | `Request`는 Bean Validation(`@Valid`) 적용. `Response`는 단순 데이터 홀더 |
| Mapper | MapStruct 인터페이스만 선언. 구현은 자동 생성 (`AdminMapperImpl`) |

## 실행

```bash
# 로컬 프로파일로 실행 (application-local.yml 적용)
./gradlew :ad-admin-api:bootRun --args='--spring.profiles.active=local'

# 기본 포트: 8081 (SERVER_PORT 환경변수로 오버라이드 가능)
```

## 테스트

```bash
./gradlew :ad-admin-api:test
```

- `@SpringBootTest` + `MockMvc` 통합 테스트 사용 (H2 인메모리 DB)
- 컨트롤러 테스트는 `src/test/java/.../auth/` 하위에 작성

## 도메인 예외 처리 패턴

```java
@ExceptionHandler(AdminDomainException.class)
public ResponseEntity<Map<String, String>> handle(AdminDomainException ex) {
    return ResponseEntity.status(ex.getErrorCode().getHttpStatusCode())
        .body(Map.of("error", ex.getMessage()));
}
```

- `ad-core`에서 던지는 `AdminDomainException`을 컨트롤러 단에서 잡아 HTTP 응답으로 변환
- 새 도메인 예외가 추가되면 해당 컨트롤러에 `@ExceptionHandler` 추가 또는 글로벌 핸들러로 이동

## 주의사항

- 이 모듈은 HTTP 어댑터 역할. 비즈니스 로직은 `ad-core`에 작성
- MapStruct `@Mapper` 인터페이스 수정 후 반드시 재빌드 (`./gradlew :ad-admin-api:compileJava`)
