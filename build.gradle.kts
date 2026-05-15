plugins {
    id("java")
    id("checkstyle")
    id("com.diffplug.spotless") version "8.1.0"
}

group = "org.giglab"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

checkstyle {
    toolVersion = "10.25.0"
    maxWarnings = 0
    configFile = file("config/checkstyle/google_checks.xml")
    isIgnoreFailures = false
}

tasks.withType<Checkstyle>().configureEach {
    reports {
        xml.required = false
        html.required = true
    }
}

spotless {
    java {
        target("src/**/*.java")
        googleJavaFormat("1.25.2")
        removeUnusedImports()
        trimTrailingWhitespace()
        endWithNewline()
    }

    format("misc") {
        target("*.md", ".gitignore")
        trimTrailingWhitespace()
        leadingTabsToSpaces(4)
        endWithNewline()
    }

    format("gradle") {
        target("*.gradle.kts")
        trimTrailingWhitespace()
        endWithNewline()
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.named("check") {
    dependsOn("spotlessCheck", "checkstyleMain", "checkstyleTest")
}

tasks.named("build") {
    dependsOn("spotlessCheck", "checkstyleMain")
}
