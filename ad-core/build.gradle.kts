plugins {
    `java-library`
    id("io.spring.dependency-management") version "1.1.7"
}

// CVE-2024-49203 (SQL/HQL Injection, CVSS 9.8) 패치 반영
// 원본 com.querydsl은 5.1.0이 최신이므로, 패치된 OpenFeign 포크 사용
val querydslVersion = "5.6.1"

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:4.0.6")
    }
}

dependencies {

    // spring boot
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    api("org.springframework.boot:spring-boot-starter-validation")

    // security
    implementation("org.springframework.security:spring-security-crypto")

    // querydsl (CVE-2024-49203 패치 버전: OpenFeign 포크 5.6.1)
    implementation("io.github.openfeign.querydsl:querydsl-jpa:${querydslVersion}:jakarta")
    annotationProcessor("io.github.openfeign.querydsl:querydsl-apt:${querydslVersion}:jakarta")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api")
    annotationProcessor("jakarta.annotation:jakarta.annotation-api")

    // lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testCompileOnly("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")
}
