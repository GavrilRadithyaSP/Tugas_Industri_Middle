plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("org.jetbrains.kotlin.kapt")
    alias(libs.plugins.ksp)
    alias(libs.plugins.daggerHilt)
    alias(libs.plugins.firebaseGms)
    alias(libs.plugins.firebaseCrashlytics)
    alias(libs.plugins.firebasePerf)
}

android {
    namespace = "com.gavril.midapps"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.gavril.midapps"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        kapt{
            arguments{
                arg("room.schemaLocation", "$projectDir/schemas")
            }
        }
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
        dataBinding = true
        viewBinding = true
    }
    packagingOptions {
        exclude ("META-INF/DEPENDENCIES")
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.ssp)
    implementation(libs.sdp)
    implementation(libs.splashscreen)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.activity.ktx)
    implementation(libs.daggerHilt)
    implementation(libs.coreCrocodic)
    ksp(libs.daggerHiltCompiler)
    kapt(libs.room.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

kapt {
    correctErrorTypes = true
    useBuildCache = true
}