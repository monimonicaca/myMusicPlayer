plugins {
    alias(libs.plugins.androidApplication)
   // id("openapi-sdk")
}

android {
    namespace = "com.example.mymusicplayerapplication"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.example.mymusicplayerapplication"
        minSdk = 26
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

}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation (libs.appcompat)
    implementation ("androidx.recyclerview:recyclerview:1.3.0")
    implementation ("com.alibaba.fastjson2:fastjson2:2.0.53")
    implementation ("com.airbnb.android:lottie:6.6.2")
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
}