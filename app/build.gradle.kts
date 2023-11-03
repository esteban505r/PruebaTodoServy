plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.esteban.lopez.pruebatodoservy"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.esteban.lopez.pruebatodoservy"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    val koin_android_version = "3.5.0";
    val room_version = "2.6.0"


    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3:1.1.2")
    implementation("androidx.compose.material:material:1.5.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    //Navigation
    implementation("androidx.navigation:navigation-compose:2.7.5")

    //UI
    implementation("androidx.compose.ui:ui-text-google-fonts:1.5.4")

    //Orbit
    implementation("org.orbit-mvi:orbit-core:6.1.0")
    implementation("org.orbit-mvi:orbit-viewmodel:6.1.0")
    implementation("org.orbit-mvi:orbit-compose:6.1.0")
    testImplementation("org.orbit-mvi:orbit-test:6.1.0")

    //Arrow
    implementation("io.arrow-kt:arrow-core:1.2.1")
    implementation("io.arrow-kt:arrow-fx-coroutines:1.2.0")
    implementation("io.arrow-kt:arrow-resilience:1.2.0")

    // Koin

    implementation("io.insert-koin:koin-android:$koin_android_version")
    implementation("io.insert-koin:koin-androidx-workmanager:$koin_android_version")
    implementation("io.insert-koin:koin-androidx-navigation:$koin_android_version")
    implementation("io.insert-koin:koin-androidx-compose:$koin_android_version")


    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    ksp("androidx.room:room-compiler:2.6.0")

    implementation("androidx.room:room-ktx:$room_version")
    testImplementation("androidx.room:room-testing:$room_version")
    implementation("androidx.room:room-paging:$room_version")

}