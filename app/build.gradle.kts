plugins {
    id("aldikitta.android.application")
    id("aldikitta.android.application.compose")
    id("aldikitta.android.hilt")
//    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.21"
}

android {
    namespace = "com.aldikitta.adaptivelayout"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.aldikitta.adaptivelayout"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":feature:inbox"))
    implementation(project(":feature:mail"))
    implementation(project(":ui"))

    implementation(libs.androidx.window.window)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.material.icons.extended)

    implementation(libs.androidx.navigation.navigation.compose)
    implementation(libs.androidx.compose.material3.window)
    implementation(libs.com.google.accompanist.accompanist.adaptive)
}