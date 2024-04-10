
rootProject.name = "gem-lib"
include("gem-lib")

dependencyResolutionManagement {
    versionCatalogs {
        create("tools") {
            version("jvm", "21")
            version("kotlin", "1.9.22")

            version("ktlint", "11.3.1")
            plugin("ktlint-core", "org.jlleitschuh.gradle.ktlint").versionRef("ktlint")
            plugin("ktlint-idea", "org.jlleitschuh.gradle.ktlint-idea").versionRef("ktlint")

            plugin("kover", "org.jetbrains.kotlinx.kover").version("0.6.1")

            plugin("dependency-management", "io.spring.dependency-management").version("1.1.4")
            plugin("spring-boot", "org.springframework.boot").version("3.2.3")

            plugin("kotlin-jvm", "org.jetbrains.kotlin.jvm").versionRef("kotlin")
            plugin("kotlin-spring", "org.jetbrains.kotlin.plugin.spring").versionRef("kotlin")
            library("kotlin-stdlib", "org.jetbrains.kotlin", "kotlin-stdlib").versionRef("kotlin")
            library("kotlin-reflect", "org.jetbrains.kotlin", "kotlin-reflect").versionRef("kotlin")

            bundle("kotlin", listOf("kotlin-stdlib", "kotlin-reflect"))
        }

        create("libs") {
            version("spring-boot", "3.2.3")
            version("dependency-management", "1.1.4")

            library("guava", "com.google.guava:guava:33.1.0-jre")
            library("kotlinlogging", "io.github.microutils:kotlin-logging:3.0.5")
        }

        create("testlibs") {
            version("kotest", "5.8.1")
            library("kotest-runner-junit5", "io.kotest", "kotest-runner-junit5").versionRef("kotest")
            library("kotest-assertions-core", "io.kotest", "kotest-assertions-core").versionRef("kotest")
            library("kotest-assertions-json", "io.kotest", "kotest-assertions-json").versionRef("kotest")
            library("kotest-property", "io.kotest", "kotest-property").versionRef("kotest")
            library("kotest-framework-datatest", "io.kotest", "kotest-framework-datatest").versionRef("kotest")

            library("mockito", "org.mockito.kotlin:mockito-kotlin:5.2.1")
            library("archunit", "com.tngtech.archunit:archunit-junit5:1.2.1")

            library("junit", "org.junit.jupiter:junit-jupiter-engine:5.10.2")

            bundle(
                "kotest-core",
                listOf(
                    "kotest-runner-junit5",
                    "kotest-assertions-core",
                    "kotest-assertions-json",
                    "kotest-property",
                    "kotest-framework-datatest",
                ),
            )

        }
    }
}