plugins {
    alias(libs.plugins.hrkdev.android.library)
    alias(libs.plugins.hrkdev.android.library.jacoco)
    alias(libs.plugins.hrkdev.hilt)
    id("kotlinx-serialization")
}

android {
    namespace = "com.hrk.apps.hrkdev.core.data"
}

dependencies {
    api(projects.core.database)
    api(projects.core.common)
    api(projects.core.network)

    implementation(projects.core.analytics)
    implementation(projects.core.notifications)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.kotlinx.serialization.json)
    testImplementation(projects.core.testing)
}
