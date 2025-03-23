pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "TuneDetect"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
include(":benchmarks")
include(":core:analytics")
include(":core:common")
include(":core:database")
include(":core:data")
include(":core:designsystem")
include(":core:domain")
include(":core:model")
include(":core:network")
include(":core:notifications")
include(":core:testing")
include(":core:ui")

include(":lint")
include(":sync:work")
include(":sync:sync-test")
include(":ui-test-hilt-manifest")

include(":feature:tuneDetect")
