import com.diffplug.gradle.spotless.SpotlessExtension

plugins {
    id("com.diffplug.spotless") version "8.1.0"
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "checkstyle")
    apply(plugin = "com.diffplug.spotless")

    group = "org.giglab"
    version = "1.0-SNAPSHOT"

    configure<JavaPluginExtension> {
        toolchain {
            languageVersion = JavaLanguageVersion.of(25)
        }
    }

    repositories {
        mavenCentral()
    }

    dependencies {
        "testImplementation"(platform("org.junit:junit-bom:5.10.0"))
        "testImplementation"("org.junit.jupiter:junit-jupiter")
    }

    tasks.withType<Checkstyle>().configureEach {
        reports {
            xml.required = false
            html.required = true
        }
    }

    tasks.named<Test>("test") {
        useJUnitPlatform()
    }

    configure<CheckstyleExtension> {
        toolVersion = "10.25.0"
        maxWarnings = 0
        configFile = rootProject.file("config/checkstyle/google_checks.xml")
        isIgnoreFailures = false
    }

    configure<SpotlessExtension> {
        java {
            target("src/**/*.java")
            googleJavaFormat("1.25.2")
            removeUnusedImports()
            trimTrailingWhitespace()
            endWithNewline()
        }
    }

    tasks.named("checkstyleMain") {
        mustRunAfter("spotlessCheck")
    }
    tasks.named("checkstyleTest") {
        mustRunAfter("spotlessCheck")
    }
    tasks.named("check") {
        dependsOn("spotlessCheck", "checkstyleMain", "checkstyleTest")
    }
}

spotless {
    format("misc") {
        target("*.md", ".gitignore")
        trimTrailingWhitespace()
        leadingTabsToSpaces(4)
        endWithNewline()
    }

    format("gradle") {
        target("*.gradle.kts", "*/build.gradle.kts")
        trimTrailingWhitespace()
        endWithNewline()
    }
}
