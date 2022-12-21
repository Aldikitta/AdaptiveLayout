plugins {
    id("aldikitta.android.library")
    id("aldikitta.android.library.compose")
}

android {
    namespace = "com.aldikitta.mail"
}

dependencies {
    implementation(libs.androidx.activity.activity.compose)
    implementation(libs.androidx.lifecycle.lifecycle.runtime.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.lifecycle.lifecycle.viewmodel.compose)

    implementation(libs.androidx.window.window)
    implementation(libs.com.google.accompanist.accompanist.adaptive)
}