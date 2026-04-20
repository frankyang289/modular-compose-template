import groovy.lang.MissingPropertyException
import java.util.Properties
import kotlin.apply

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.heavywater.template.core.data"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    val localPropertiesFile = rootProject.file("local.properties")
    val properties = Properties().apply {
        load(localPropertiesFile.inputStream())
    }

    fun Properties.getPropOrWarn(name: String): String {
        val prop = getProperty(name, "")
        if (prop.isNullOrEmpty()) throw MissingPropertyException("$name missing in local.properties")
        return prop
    }

    val apiKey = properties.getPropOrWarn("API_KEY")

    buildFeatures { buildConfig = true }

    defaultConfig {
        minSdk = 26
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "API_KEY", "\"$apiKey\"")
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

dependencies {
    api(projects.core.model)
    api(projects.core.datastore)
    api(projects.core.network)
    implementation(libs.hilt.android)
    implementation(libs.play.services.location)
    ksp(libs.hilt.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}