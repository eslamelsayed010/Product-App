plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("plugin.serialization") version "+"
    id("kotlin-kapt")
}

android {
    namespace = "com.example.lab4"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.lab4"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    //nav
    implementation("androidx.navigation:navigation-compose:+")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:+")

    //work manager
    implementation("androidx.work:work-runtime:+")
    implementation("androidx.work:work-runtime-ktx:+")

    //retrofit & glide
    implementation("com.squareup.retrofit2:retrofit:+")
    implementation("com.squareup.retrofit2:converter-gson:+")

    annotationProcessor("com.github.bumptech.glide:compiler:+")
    implementation("com.github.bumptech.glide:glide:+")

    // Glide Integration for Jetpack Compose
    implementation("com.github.skydoves:landscapist-glide:+")

    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:+")

    //Room
    implementation("androidx.room:room-runtime:+")
    implementation("androidx.room:room-ktx:+")
    kapt("androidx.room:room-compiler:+")

    // ViewModel & livedata
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose-android:+")
    implementation("androidx.lifecycle:lifecycle-extensions:+")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:+")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:+")

    //livedata
    implementation("androidx.compose.runtime:runtime-livedata:+")
}