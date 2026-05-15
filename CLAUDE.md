# Ad Platform Lab — Claude Code 가이드

## 프로젝트 개요

광고 플랫폼 실험 프로젝트. 광고 도메인 설계·구현을 직접 수행하는 포트폴리오 프로젝트.

## 프로젝트 구조

```
ad-platform-lab/
└── src/
    ├── main/java/          # 메인 소스
    └── test/java/          # 테스트
```

## 기술 스택

- **Language**: Java 25
- **Build**: Gradle 9.2 (Kotlin DSL)
- **Test**: JUnit 5
- **Package**: `org.giglab`

## 개발 워크플로우

### 브랜치 전략

```
main                 ← 프로덕션
develop              ← 통합 브랜치
feature/#{이슈번호}  ← 기능 개발
fix/#{이슈번호}      ← 버그 수정
refactor/#{이슈번호} ← 리팩토링
chore/#{이슈번호}    ← 설정·문서·도구
```

### 커밋 컨벤션

```
[#{이슈번호}] type: 설명

type: feat | fix | refactor | docs | chore | perf
```

### PR 흐름

1. `feature/#{N}` 브랜치 생성
2. 개발 후 `pr-create` 스킬로 PR 생성 (base: develop)
3. `pr-review` 스킬로 리뷰 → `pr-apply` 스킬로 반영

## Available Skills

스킬은 `.claude/skills/` 디렉터리에 정의되어 있으며, Claude Code 대화에서 `/스킬명`으로 호출합니다.

| 스킬 | 용도 |
|------|------|
| `plan` | 이슈 분석 → 구현 계획 문서 생성 |
| `issue-create` | 변경사항 분석 → GitHub 이슈 생성 |
| `pr-create` | 현재 브랜치 커밋 분석 → PR 생성 |
| `pr-review` | PR 코드 리뷰 → 코멘트 등록 |
| `pr-apply` | PR 리뷰 코멘트 → 코드 반영 |
| `question` | 질문 답변 → docs/claude/question/ 저장 |

## 빌드 & 실행

```bash
# 전체 빌드
./gradlew build

# 컴파일
./gradlew compileJava

# 테스트
./gradlew test
```

## 코드 품질 규칙

- **패키지**: `org.giglab.*`
- **예외**: 도메인 예외는 `*DomainException`, 에러코드는 `*ErrorCode` enum
- **트랜잭션**: 외부 HTTP 호출은 트랜잭션 밖에서 수행
