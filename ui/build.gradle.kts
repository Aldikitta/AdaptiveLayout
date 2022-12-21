plugins {
    id("aldikitta.android.library")
    id("aldikitta.android.library.compose")
}

android {
    namespace = "com.aldikitta.ui"
 }

dependencies {
    implementation(project(":core:data"))

    implementation(libs.androidx.window.window)
    implementation(libs.androidx.activity.activity.compose)
    implementation(libs.androidx.lifecycle.lifecycle.runtime.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.material.icons.extended)



//    implementation 'androidx.core:core-ktx:1.9.0'
//    implementation 'androidx.activity:activity-compose:1.6.1'
//    implementation "androidx.compose.ui:ui:$compose_version"
//    implementation "androidx.compose.ui:ui-tooling-preview:$compose_version"
//    implementation 'androidx.compose.material3:material3:1.1.0-alpha03'
//
//    //Icons-Extended
//    implementation "androidx.compose.material:material-icons-extended:1.3.1"
//
//    //Window Adaptive layout
//    implementation "androidx.window:window:1.0.0"
//    implementation 'androidx.compose.material3:material3-window-size-class:1.0.1'
//    implementation "com.google.accompanist:accompanist-adaptive:0.28.0"
}