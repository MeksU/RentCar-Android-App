plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-parcelize")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "pl.meksu.rentcar"
    compileSdk = 35

    defaultConfig {
        applicationId = "pl.meksu.rentcar"
        minSdk = 28
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
    implementation(libs.androidx.datastore.preferences.core.jvm)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.appcompat)

    //Remote Data
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.coil.compose)

    //ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    //Bottom Bar
    implementation(libs.androidx.material)
    implementation(libs.material3)

    //Dagger Hilt
    implementation(libs.hilt.android)
    ksp(libs.dagger.hilt.android.compiler)
    ksp(libs.androidx.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    //Navigation
    implementation(libs.androidx.navigation.compose)

    //Font
    implementation(libs.androidx.ui.text.google.fonts)

    //Datastore preferences
    implementation(libs.androidx.datastore.preferences)

    //Splash screen
    implementation(libs.androidx.core.splashscreen)

    //Compose dialogs
    implementation(libs.sheets.compose.dialogs.core)
    implementation(libs.sheets.compose.dialogs.calendar)

    implementation("com.github.amitshekhariitbhu.Fast-Android-Networking:android-networking:1.0.4")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.paypal.android:paypal-web-payments:1.5.0")
}