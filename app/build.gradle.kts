plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

android {
    namespace = "com.kitty.app"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.kitty.app"
        minSdk = 23
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

kapt {
    correctErrorTypes = true
}

val ktlint: Configuration by configurations.creating

val reportsDir = "${project.buildDir}/reports"

val ktlintCheck by tasks.registering(JavaExec::class) {
    description = "Check Kotlin code style."
    mainClass.set("com.pinterest.ktlint.Main")
    classpath = ktlint
    args(
        "src/**/*.kt",
        "--reporter=html,output=${reportsDir}/ktlint.html",
        "--reporter=plain"
    )
}

val ktlintFormat by tasks.registering(JavaExec::class) {
    description = "Fix Kotlin code style deviations."
    mainClass.set("com.pinterest.ktlint.Main")
    classpath = ktlint
    args("-F", "src/**/*.kt")
}

tasks.check {
    dependsOn(ktlintCheck)
}

dependencies {

    // Ktlint
    ktlint("com.pinterest:ktlint:0.50.0")

    // Hilt
    val hiltVersion = "2.47"
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-compiler:$hiltVersion")

    // Coroutines
    val coroutinesAndroidVersion = "1.7.1"
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesAndroidVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesAndroidVersion")

    // Retrofit
    val retrofitVersion = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")

    // OkHttp
    val okHttpVersion = "4.10.0"
    implementation("com.squareup.okhttp3:okhttp:$okHttpVersion")
    implementation("com.squareup.okhttp3:logging-interceptor:$okHttpVersion")

    // Chucker
    val chuckVersion = "3.5.2"
    debugImplementation("com.github.chuckerteam.chucker:library:$chuckVersion")
    releaseImplementation("com.github.chuckerteam.chucker:library-no-op:$chuckVersion")

    // Timber logging
    implementation("com.jakewharton.timber:timber:4.7.1")

    // Core & Compose
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("androidx.navigation:navigation-compose:2.6.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
