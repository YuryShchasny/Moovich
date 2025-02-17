enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Moovich"
include(":app")
include(":presentation")
include(":presentation:home")
include(":core")
include(":presentation:search")
include(":presentation:favourites")
include(":presentation:info")
include(":data")
include(":domain")
include(":presentation:all")
include(":presentation:collection")
include(":presentation:authorization")
include(":presentation:gpt")
include(":presentation:profile")
include(":presentation:onboarding")
