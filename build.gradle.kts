// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.daggerHilt) apply false
    alias(libs.plugins.firebaseGms) apply false
    alias(libs.plugins.firebaseCrashlytics) apply false
    alias(libs.plugins.firebasePerf) apply false
}