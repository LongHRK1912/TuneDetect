/*
 * Copyright 2022 The Android Open Source Project
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.build.gradle.LibraryExtension
import com.hrk.apps.hrkdev.configureFlavors
import com.hrk.apps.hrkdev.configureGradleManagedDevices
import com.hrk.apps.hrkdev.configureKotlinAndroid
import com.hrk.apps.hrkdev.configurePrintApksTask
import com.hrk.apps.hrkdev.disableUnnecessaryAndroidTests
import com.hrk.apps.hrkdev.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
                apply("hrkdev.android.lint")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 35
                defaultConfig.testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                testOptions.animationsDisabled = true
                configureFlavors(this)
                configureGradleManagedDevices(this)
                // The resource prefix is derived from the module name,
                // so resources inside ":core:module1" must be prefixed with "core_module1_"
                resourcePrefix = path.split("""\W""".toRegex()).drop(1).distinct().joinToString(separator = "_").lowercase() + "_"
            }
            extensions.configure<LibraryAndroidComponentsExtension> {
                configurePrintApksTask(this)
                disableUnnecessaryAndroidTests(target)
            }
            dependencies {
                add("androidTestImplementation", kotlin("test"))
                add("testImplementation", kotlin("test"))
                add("implementation", libs.findLibrary("androidx.navigation.compose").get())
                add("implementation", libs.findLibrary("androidx.tracing.ktx").get())
            }
        }
    }
}
