plugins {
    alias(libs.plugins.hrkdev.android.feature)
    alias(libs.plugins.hrkdev.android.library.compose)
    alias(libs.plugins.hrkdev.android.library.jacoco)
}

android {
    namespace = "com.hrk.tunedetect"
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.google.oss.licenses)
    implementation(projects.core.data)

    testImplementation(projects.core.testing)

    androidTestImplementation(libs.bundles.androidx.compose.ui.test)

    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.ui)
}

