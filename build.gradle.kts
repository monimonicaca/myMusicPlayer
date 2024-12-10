// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
}
//1. 添加仓库
allprojects {
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
}
//我们通过发布SNAPSHOT版本和合作方联调，所以建议开发阶段声明以下内容:
allprojects {
    configurations.all {
        resolutionStrategy {
            cacheDynamicVersionsFor(0, TimeUnit.SECONDS)
            cacheChangingModulesFor(0, TimeUnit.SECONDS)
        }
    }
}

