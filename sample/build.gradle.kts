@file:Suppress("UnstableApiUsage")

plugins {
    alias(libs.plugins.multiplatform)
}

kotlin {
    jvmToolchain {
        vendor = JvmVendorSpec.JETBRAINS
        languageVersion = JavaLanguageVersion.of(17)
    }


    js {
        nodejs()
        binaries.executable()
    }

    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":debugln"))
            }
        }

        val jsMain by getting {
            dependencies {
                // crashes otherwise for some reason
                implementation("org.jetbrains.kotlin:kotlinx-atomicfu-runtime:1.8.20-RC")
                implementation(kotlinWrappers.node)
            }
        }
    }
}