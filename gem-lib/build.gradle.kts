import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

buildscript {
    repositories {
        mavenCentral()
        mavenLocal()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${tools.versions.kotlin.get()}")
    }
}

repositories {
    mavenCentral()
    mavenLocal()
}

plugins {
    application
    `maven-publish`

    id("application")
    id("java")
    id("maven-publish")

    alias(tools.plugins.dependency.management)
    alias(tools.plugins.spring.boot)
    alias(tools.plugins.kover)
    alias(tools.plugins.ktlint.core)
    alias(tools.plugins.ktlint.idea)
    alias(tools.plugins.kotlin.jvm)
    alias(tools.plugins.kotlin.spring)
}

project.group = "pl.edu.agh.gem"
version = "0.1.0"

apply(plugin = "kotlin")
apply(plugin = "kotlin-spring")
apply(plugin = "java")
apply(plugin = "kover")

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation(tools.bundles.kotlin)
    implementation("com.fasterxml.jackson.module:jackson-module-afterburner")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation(libs.kotlinlogging)

    implementation(testlibs.bundles.kotest.core)
    implementation(testlibs.mockito)
    implementation(testlibs.archunit)
    implementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly(testlibs.junit)
}

ktlint {
    reporters {
        reporter(ReporterType.PLAIN)
    }
}

tasks {
    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = tools.versions.jvm.get()
            freeCompilerArgs = listOf("-Xjvm-default=all", "-Xemit-jvm-type-annotations")
        }
    }

    withType<Test> {
        useJUnitPlatform()
        testLogging {
            exceptionFormat = TestExceptionFormat.FULL
        }
        reports {
            junitXml.required = true
        }
        outputs.upToDateWhen { false }
    }

    withType<org.jlleitschuh.gradle.ktlint.tasks.BaseKtLintCheckTask> {
        workerMaxHeapSize.set("512m")
    }
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/Group-Expense-Manager/gem-lib")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
    publications {
        create<MavenPublication>("github") {
            from(components["kotlin"])
            groupId = project.group.toString()
            artifactId = "lib-gem"
            version = version
        }
    }
}

tasks.register<Wrapper>("wrapper") {
    gradleVersion = "4.1"
}
