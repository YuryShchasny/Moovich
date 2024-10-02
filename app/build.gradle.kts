plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.kspAnnotationPlugin)
    alias(libs.plugins.kapt)
    alias(libs.plugins.androidx.navigation.safeargs.ktx)
    alias(libs.plugins.gradle.secrets)
}

android {
    namespace = "com.sb.moovich"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.sb.moovich"
        minSdk = 26
        targetSdk = 34
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
            buildConfigField("String", "BASE_URL", "\"https://api.kinopoisk.dev/v1.4/\"")
        }
        debug {
            buildConfigField("String", "GPT_BASE_URL", "\"https://lk.neuroapi.host/v1/\"")
            buildConfigField("String", "BASE_URL", "\"https://api.kinopoisk.dev/v1.4/\"")
        }
    }
    secrets {
        propertiesFileName = "secrets.properties"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        dataBinding = true
        buildConfig = true
    }
    kapt {
        correctErrorTypes = true
    }
}


dependencies {
    implementation(projects.domain)
    implementation(projects.data)
    implementation(projects.presentation.home)
    implementation(projects.presentation.info)
    implementation(projects.presentation.favourites)
    implementation(projects.presentation.search)
    implementation(projects.presentation.all)
    implementation(projects.presentation.collection)
    implementation(projects.presentation.authorization)
    implementation(projects.presentation.gpt)
    implementation(projects.core)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.fragment.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    implementation(libs.androidx.core.splashscreen)

    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    implementation(libs.dagger.hilt)
    kapt(libs.dagger.hilt.kapt)
}