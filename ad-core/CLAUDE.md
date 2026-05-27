# ad-core — Claude Code 가이드

## 모듈 역할

도메인 모델·유스케이스·인프라 어댑터를 모두 포함하는 핵심 라이브러리 모듈. 다른 API 모듈(`ad-admin-api` 등)이 이 모듈을 `implementation(project(":ad-core"))`로 의존한다.

## 기술 스택

- Spring Boot 4.0.6 (Data JPA, Validation)
- Spring Security Crypto (PasswordEncoder)
- Lombok
- JUnit 5

## 패키지 구조

```
org.giglab.ad.core
├── {도메인}/
│   ├── domain/
│   │   ├── entity/          # JPA 엔티티, Enum (AdminRole 등)
│   │   └── exception/       # *DomainException, *ErrorCode enum
│   ├── application/
│   │   ├── usecase/         # 유스케이스 클래스 (@Transactional)
│   │   ├── port/            # 외부 의존 인터페이스 (Store/Load Port)
│   │   ├── dto/command/     # Command(입력) / Result(출력) record
│   │   └── {Domain}Service  # 유스케이스 위임용 서비스 (얇은 파사드)
│   └── infrastructure/
│       ├── adapter/         # Port 구현체 (JpaXxxAdapter)
│       └── persistence/     # Spring Data JPA Repository
└── global/
    └── config/              # 공통 Bean 설정 (PasswordEncoderConfig 등)
```

## 계층 책임

| 계층 | 규칙 |
|------|------|
| domain/entity | 순수 도메인 객체. JPA 어노테이션 허용. 정적 팩터리(`create()`)로 생성 |
| domain/exception | `*ErrorCode` enum에 메시지·HTTP 상태코드 보유. `*DomainException`이 이를 래핑 |
| application/usecase | `@Transactional`. 도메인 규칙 검증 후 엔티티 생성 → Port 저장 |
| application/port | 인터페이스만. 인프라 구현에 의존하지 않음 |
| infrastructure/adapter | Port 인터페이스 구현. JPA Repository 호출만 |

## 도메인 예외 패턴

```java
// ErrorCode enum
public enum AdminErrorCode {
    DUPLICATE_EMAIL("이미 등록된 이메일입니다.", 409),
    INVALID_PASSWORD("비밀번호가 유효하지 않습니다.", 400);
    // ...
}

// 던지는 쪽
throw new AdminDomainException(AdminErrorCode.DUPLICATE_EMAIL);
```

- 새 도메인 규칙 위반 케이스는 `*ErrorCode`에 항목 추가 후 `*DomainException`으로 던진다
- HTTP 상태 변환은 호출 모듈(`ad-admin-api`)의 `@ExceptionHandler`가 담당

## 빌드

```bash
./gradlew :ad-core:build
./gradlew :ad-core:test
```

## 테스트 전략

- `domain/` 테스트: 순수 단위 테스트 (스프링 컨텍스트 불필요)
- `application/usecase` 테스트: Mockito로 Port 목킹
- `infrastructure/` 테스트: `@DataJpaTest` + H2

## 새 도메인 추가 시 체크리스트

1. `domain/entity/` — 엔티티·Enum 작성
2. `domain/exception/` — ErrorCode·DomainException 작성
3. `application/port/` — StorePort / LoadPort 인터페이스 정의
4. `application/usecase/` — 유스케이스 구현 (`@Transactional`)
5. `application/dto/command/` — Command·Result record 정의
6. `application/{Domain}Service` — 유스케이스 위임 서비스
7. `infrastructure/adapter/` — Port 구현체
8. `infrastructure/persistence/` — JPA Repository
