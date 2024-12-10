plugins {
    alias(libs.plugins.androidApplication)
   // id("openapi-sdk")
}

android {
    namespace = "com.example.mymusicplayerapplication"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.example.mymusicplayerapplication"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    packagingOptions {
        // pickFirst：保留第一个找到的文件，解决重复文件冲突
        pickFirst("lib/armeabi-v7a/libc++_shared.so")
        pickFirst("lib/arm64-v8a/libc++_shared.so")
        pickFirst("lib/x86/libc++_shared.so")
        pickFirst("lib/x86_64/libc++_shared.so")

        pickFirst("lib/armeabi-v7a/libnetbase.so")
        pickFirst("lib/arm64-v8a/libnetbase.so")
        pickFirst("lib/x86/libnetbase.so")
        pickFirst("lib/x86_64/libnetbase.so")

        // exclude：排除不需要的文件
        exclude("META-INF/AL2.0")
        exclude("META-INF/LGPL2.1")
        exclude("META-INF/*")
    }
    //packaging {
    //    resources {
            /*merges.add("META-INF/proguard/androidx-annotations.pro")
            merges.add("META-INF/proguard/coroutines.pro")
            pickFirsts.add("lib/armeabi-v7a/libc++_shared.so")
            pickFirsts.add ("lib/arm64-v8a/libc++_shared.so")
            pickFirsts.add ("lib/x86/libc++_shared.so")
            pickFirsts.add ("lib/x86_64/libc++_shared.so")
            pickFirsts.add ("lib/armeabi-v7a/libnetbase.so")
            pickFirsts.add ("lib/arm64-v8a/libnetbase.so")
            pickFirsts.add ("lib/x86/libnetbase.so")
            pickFirsts.add ("lib/x86_64/libnetbase.so")
            excludes.add("META-INF/AL2.0")
            excludes.add("META-INF/LGPL2.1")
            excludes.add("lib/armeabi-v7a/libc++_shared.so")
            excludes.add("lib/arm64-v8a/libc++_shared.so")
            excludes.add("lib/x86/libc++_shared.so")
            excludes.add("lib/x86_64/libc++_shared.so")
            excludes.add("lib/armeabi-v7a/libnetbase.so")
            excludes.add("lib/arm64-v8a/libnetbase.so")
            excludes.add("lib/x86/libnetbase.so")
            excludes.add("lib/x86_64/libnetbase.so")*/
       // }
      //  resources.excludes.add("META-INF/*")
   // }
    sourceSets {
        getByName("main") {
            // 设置 JNI 库路径（如果你有 .so 文件）
            jniLibs.srcDirs("libs")
        }
    }
    /*externalNativeBuild {
        cmake {
            path =file("src/main/cpp/CMakeLists.txt")
        }
    }*/
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation ("com.tencent.qqmusic.openapi:openapi-sdk:2.7.4")
    implementation (libs.appcompat)
    implementation ("androidx.recyclerview:recyclerview:1.3.0")
}