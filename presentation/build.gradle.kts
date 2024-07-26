import PropertiesExt.KAKAO
import PropertiesExt.getKakaoKey

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    setCompileSdkVersion(AndroidConfig.COMPILE_SDK_VERSION)

    defaultConfig {
        minSdk = AndroidConfig.MIN_SDK_VERSION
        testOptions.targetSdk = AndroidConfig.TARGET_SDK_VERSION

        manifestPlaceholders[KAKAO] = getKakaoKey()

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

    lint {
        abortOnError = false
    }

    buildFeatures {
        dataBinding = true
    }

    flavorDimensions += listOf("mode")
    productFlavors {
        create("playStore") {
            dimension = "mode"
        }
        create("dev") {
            dimension = "mode"
        }
    }
    compileOptions {
        sourceCompatibility = AndroidConfig.JAVA_VERSION
        targetCompatibility = AndroidConfig.JAVA_VERSION
    }
    kotlinOptions {
        jvmTarget = AndroidConfig.JAVA_VERSION.toString()
    }
    namespace = "com.hjiee.presentation"
}

dependencies {
    implementation(project(":core"))
    implementation(project(":domain"))
    implementation(project(":data"))

    // Kotlin
    implementation(Libs.KOTLIN)
    implementation(Libs.COROUTINES_CORE)
    implementation(Libs.COROUTINES_ANDROID)

    // Android
    implementation(Libs.CORE_KTX)
    implementation(Libs.APP_COMPAT)
    implementation(Libs.CONSTRAINT_LAYOUT)
    implementation(Libs.COORDINATOR_LAYOUT)
    implementation(Libs.RECYCLER_VIEW)
    implementation(Libs.MATERIAL)
    implementation(Libs.ACTIVITY_KTX)
    implementation(Libs.FRAGMENT_KTX)
    implementation(Libs.LIFECYCLE_VIEW_MODEL_KTX)
    implementation(Libs.LIFECYCLE_LIVE_DATA_KTX)
    implementation(Libs.LIFECYCLE_COMMON)
    implementation(Libs.NAVIGATION_FRAGMENT)
    implementation(Libs.NAVIGATION_UI_KTX)
    implementation(Libs.BILLING)

    // Libraries
    implementation(Libs.RETROFIT)
    implementation(Libs.RETROFIT_GSON_CONVERTER)
    implementation(Libs.OK_HTTP)
    implementation(Libs.LOGGING_INTERCEPTOR)
    implementation(Libs.GSON)
    implementation(Libs.GLIDE)
    implementation(Libs.TIMBER)
    implementation(Libs.HILT)
    kapt(Libs.HILT_ANNOTATION)
//    implementation(Libs.HILT_VIEWMODEL)
    kapt(Libs.HILT_COMPILER)
    implementation(Libs.KAKAO)
    implementation(Libs.RANGE_SEEK_BAR)

    implementation(Libs.LICENSES)

//    implementation(Libs.SNAP_RECYCLERVIEW)
    implementation(Libs.LOTTIE)

    testImplementation(TestLibs.JUNIT)
    testImplementation(TestLibs.MOCKITO)
    testImplementation(TestLibs.TEST_CORE)
    testImplementation(TestLibs.COROUTINE_TEST)
    androidTestImplementation(TestLibs.ANDROID_X_JUNIT)
    androidTestImplementation(TestLibs.ESPRESSO_CORE)
}