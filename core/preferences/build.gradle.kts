plugins {
    alias(libs.plugins.hrkdev.android.library)
    alias(libs.plugins.hrkdev.android.library.jacoco)
    alias(libs.plugins.hrkdev.hilt)
    alias(libs.plugins.ksp)
    id("kotlinx-serialization")
}

android {
    namespace = "com.hrk.preferences"
}

dependencies {
    api(projects.core.common)
    api(projects.core.network)

    implementation(projects.core.analytics)
    implementation(projects.core.notifications)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.kotlinx.serialization.json)
    testImplementation(projects.core.testing)

    ksp(libs.room.compiler)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.androidx.startup.runtime)
}