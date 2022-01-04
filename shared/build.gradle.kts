plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    kotlin("android.extensions")
    id("com.android.library")
    id("com.squareup.sqldelight")
    id("com.github.gmazzo.buildconfig") version "2.0.2"
}

buildConfig(Action {
    val prop = com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(rootDir)
    buildConfigField("String", "API_KEY", "\"${prop.getProperty("api_key")}\"")
})

kotlin(Action {
    android()
    ios {
        binaries {
            framework {
                baseName = "shared"
            }
        }
    }
    // Block from https://github.com/cashapp/sqldelight/issues/2044#issuecomment-721299517.
    val onPhone = System.getenv("SDK_NAME")?.startsWith("iphoneos") ?: false
    if (onPhone) {
        iosArm64("ios")
    } else {
        iosX64("ios")
    }

    val ktorVersion = "1.6.1"
    val sqlDelightVersion = "1.5.3"

    sourceSets(Action {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2-native-mt")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.2.2")
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-serialization:$ktorVersion")
                implementation("com.squareup.sqldelight:runtime:$sqlDelightVersion")
                implementation("io.insert-koin:koin-core:3.1.4")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("androidx.core:core-ktx:1.7.0")
                implementation("io.ktor:ktor-client-android:$ktorVersion")
                implementation("com.squareup.sqldelight:android-driver:$sqlDelightVersion")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
            }
        }
        val iosMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-ios:$ktorVersion")
                implementation("com.squareup.sqldelight:native-driver:$sqlDelightVersion")
            }
        }
        val iosTest by getting
    })
})

android(Action {
    compileSdk = 31
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 21
        targetSdk = 31
    }
})

sqldelight(Action {
    database("AppDatabase") {
        packageName = "com.daou.deliagent.shared.cache"
        sourceFolders = listOf("sqldelight")
    }
})