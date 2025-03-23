plugins {
    alias(libs.plugins.hrkdev.jvm.library)
    alias(libs.plugins.hrkdev.hilt)
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
    implementation(libs.gson.converter)
    implementation(libs.androidx.lifecycle.viewmodel)
}