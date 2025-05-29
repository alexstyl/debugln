@file:Suppress("UnstableApiUsage")
@file:OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)

import org.jetbrains.compose.internal.utils.getLocalProperty
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.multiplatform)
    id("com.android.library")
    id("org.jetbrains.dokka")
    id("maven-publish")
    id("signing")
}

val publishGroupId = "com.alexstyl"
val publishVersion = "1.0.3"
val organization = "alexstyl"
var projectName = "debugln"
val githubUrl = "github.com/$organization/$projectName"

java {
    toolchain {
        vendor = JvmVendorSpec.JETBRAINS
        languageVersion = JavaLanguageVersion.of(17)
    }
}

kotlin {
    jvmToolchain {
        vendor = JvmVendorSpec.JETBRAINS
        languageVersion = JavaLanguageVersion.of(17)
    }

    androidTarget {
        publishLibraryVariants("release", "debug")
        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
        }
    }

    jvm()

    wasmJs {
        browser()
    }

    js {
        nodejs()
        binaries.library()
    }

    js {
        browser()
        binaries.library()
    }

    wasmJs {
        browser()
        binaries.library()
    }

    listOf(iosX64(), iosArm64(), iosSimulatorArm64()).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "DebugLn"
            isStatic = true
        }
    }
}

android {
    namespace = "com.alexstyl.debugln"
    compileSdk = 35
    defaultConfig {
        minSdk = 21
    }
}

val dokkaOutputDir = buildDir.resolve("dokka")
tasks.dokkaHtml { outputDirectory.set(file(dokkaOutputDir)) }
val deleteDokkaOutputDir by tasks.register<Delete>("deleteDokkaOutputDirectory") { delete(dokkaOutputDir) }
val javadocJar = tasks.create<Jar>("javadocJar") {
    archiveClassifier.set("javadoc")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    dependsOn(deleteDokkaOutputDir, tasks.dokkaHtml)
    from(dokkaOutputDir)
}

group = publishGroupId
version = publishVersion

afterEvaluate {
    publishing {
        publications {
            withType<MavenPublication> {
                artifact(javadocJar)

                pom {
                    name.set("debugln")
                    description.set("Dead simple logging for Kotlin apps")
                    url.set("https://${githubUrl}")
                    licenses {
                        license {
                            name.set("MIT License")
                            url.set("https://${githubUrl}/blob/main/LICENSE")
                        }
                    }
                    issueManagement {
                        system.set("GitHub Issues")
                        url.set("https://${githubUrl}/issues")
                    }
                    developers {
                        developer {
                            id.set("alexstyl")
                            name.set("Alex Styl")
                            email.set("alex@alexstyl.com")
                        }
                    }

                    scm {
                        connection.set("scm:git:${githubUrl}.git")
                        developerConnection.set("scm:git:ssh://${githubUrl}.git")
                        url.set("https://${githubUrl}/tree/main")
                    }
                }
            }
        }
        // TODO: remove after https://youtrack.jetbrains.com/issue/KT-46466 is fixed
        project.tasks.withType(AbstractPublishToMaven::class.java).configureEach {
            dependsOn(project.tasks.withType(Sign::class.java))
        }
    }
}

signing {
    useInMemoryPgpKeys(
        getLocalProperty("signing.keyId") ?: System.getenv("SIGNING_KEY_ID"),
        getLocalProperty("signing.key") ?: System.getenv("SIGNING_KEY"),
        getLocalProperty("signing.password") ?: System.getenv("SIGNING_PASSWORD"),
    )
    sign(publishing.publications)
}
