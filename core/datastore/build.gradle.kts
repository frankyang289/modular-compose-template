plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.wire)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.heavywater.core.datastore"
    compileSdk = 36

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

wire {
    kotlin {
        android = true
    }
}

dependencies {
    api(projects.core.model)
    implementation(projects.core.common)
    implementation(libs.datastore)
    implementation(libs.wire.runtime)
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}