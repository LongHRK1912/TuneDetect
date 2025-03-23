package com.hrk.apps.hrkdev.core.network.manager


object EnvironmentManager {
    var currentEnvironment = BuildEnvironment.DOMAIN_DEV
    var baseUrl = BuildEnvironment.DOMAIN_DEV.url
}

enum class BuildEnvironment(
    val url: String,
    val position: Int,
    val environmentName: String,
    val urlLoadImg: String,
) {
    DOMAIN_DEV(
        "https://api.dev.locstoc.com/",
        0,
        "dev",
        "https://io.dev.locstoc.com"
    ),
    DOMAIN_STAGING(
        "https://api.stg.locstoc.com/",
        1,
        "staging",
        "https://io.stg.locstoc.com"
    ),
    DOMAIN_UAT("", 2, "","https://io.staging.locstoc.com"),
    DOMAIN_LIVE("", 3, "","");

    companion object {
        fun filter(position: Int): BuildEnvironment {
            for (environment in entries) {
                if (position == environment.position) {
                    return environment
                }
            }
            return DOMAIN_DEV
        }
    }
}