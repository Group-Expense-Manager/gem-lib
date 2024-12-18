import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

buildscript {
    repositories {
        mavenCentral()
        mavenLocal()
        maven("https://jitpack.io")
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${tools.versions.kotlin.get()}")
    }
}

tasks.wrapper {
    gradleVersion = "8.11.1"
}

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://jitpack.io")
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
    alias(tools.plugins.detekt)
    alias(tools.plugins.ktlint.core)
    alias(tools.plugins.ktlint.idea)
    alias(tools.plugins.kotlin.jvm)
    alias(tools.plugins.kotlin.spring)
}

project.group = "pl.edu.agh.gem"
version = "0.4.2"

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation(tools.bundles.kotlin)
    implementation("com.fasterxml.jackson.module:jackson-module-afterburner")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    implementation(libs.kotlinlogging)
    implementation(libs.loki.logback.appender)
    implementation(libs.spring.boot.starter.actuator)
    implementation(libs.spring.boot.starter.aop)
    implementation(libs.micrometer.registry.prometheus)
    implementation(libs.springdoc.openapi.starter.webmvc.ui)
    implementation(libs.springdoc.openapi.starter.webmvc.api)

    implementation(testlibs.bundles.kotest.core)
    implementation(testlibs.mockito)
    implementation(testlibs.archunit)
    implementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly(testlibs.junit)

    detektPlugins(detectlibs.detekt.rules.libraries)
    detektPlugins(detectlibs.detekt.rules.ruleauthors)
    detektPlugins(detectlibs.detekt.formatting)
    detektPlugins(detectlibs.detekt.faire)
    detektPlugins(detectlibs.detekt.hbmartin)
    detektPlugins(detectlibs.detekt.compiler.wrapper)
    detektPlugins(detectlibs.kure.potlin)
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
        compilerOptions {
            jvmTarget.set(JvmTarget.fromTarget(tools.versions.jvm.get()))
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

    register<Jar>("sourcesJar") {
        from(sourceSets.main.get().allSource)
        archiveClassifier.set("sources")
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
            artifact(tasks["sourcesJar"])
            groupId = project.group.toString()
            artifactId = "lib-gem"
            version = version
        }
    }
}

detekt {
    buildUponDefaultConfig = false
    autoCorrect = true
    config.setFrom("$projectDir/config/detekt/detekt.yml")
}

tasks.withType<Detekt>().configureEach {
    reports {
        html.required.set(true)
        md.required.set(true)
    }
}

tasks.withType<Detekt>().configureEach {
    jvmTarget = tools.versions.jvm.get()
}
tasks.withType<DetektCreateBaselineTask>().configureEach {
    jvmTarget = tools.versions.jvm.get()
}
