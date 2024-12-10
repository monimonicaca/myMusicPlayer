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
/*dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url =uri("https://ct.y.qq.com/TVapp/nexus/repository/maven-public/")
            credentials {
                username ="tvsdk_client"
                password ="tvsdk-client"
            }
        }
    }
}*/

rootProject.name = "MyMusicPlayerApplication"
include(":app")
