plugins {
    id("aldikitta.android.library")
}

android {
    namespace = "com.aldikitta.data"
}

dependencies {
    implementation(libs.androidx.core.core.ktx)
    implementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines.core.jvm)
}