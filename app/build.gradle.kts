plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.prueba1"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.prueba1"
        minSdk = 24
        targetSdk = 36
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
    implementation("androidx.navigation:navigation-compose:2.9.4")
    implementation("androidx.compose.material3:material3:1.3.0")
    dependencies {
        // BOM de Compose para evitar mezclas de versiones
        implementation(platform("androidx.compose:compose-bom:2025.08.00"))

        // Compose / Activity / Material3
        implementation("androidx.compose.ui:ui")
        implementation("androidx.compose.material3:material3")
        implementation("androidx.activity:activity-compose")
        implementation("androidx.compose.ui:ui-tooling-preview")
        debugImplementation("androidx.compose.ui:ui-tooling")

        // Navigation Compose
        implementation("androidx.navigation:navigation-compose:2.9.4")

        // ✅ Lifecycle (soluciona el ClassNotFound de MutableLiveData)
        implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.6")
        implementation("androidx.lifecycle:lifecycle-livedata-core-ktx:2.8.6")
        // (Opcional pero recomendado si luego usas ViewModel)
        implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.6")
        implementation("androidx.compose.material:material-icons-extended")
        implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.6")
        dependencies {
            implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.4")
            implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.4")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")
            // DataStore para guardar el tema
            implementation("androidx.datastore:datastore-preferences:1.1.1")

// ViewModel para Compose (si no lo agregaste antes)
            implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.6")

        }

    }










}