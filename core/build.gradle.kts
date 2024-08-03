import PropertiesExt.ENABLE_AGGREGATING_TASK

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}

android {
    setCompileSdkVersion(AndroidConfig.COMPILE_SDK_VERSION)
    buildToolsVersion = AndroidConfig.BUILD_TOOLS_VERSION

    defaultConfig {
        minSdk = AndroidConfig.MIN_SDK_VERSION
        targetSdk = AndroidConfig.TARGET_SDK_VERSION

        testInstrumentationRunner = AndroidConfig.TEST_INSTRUMENTATION_RUNNER
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = AndroidConfig.JAVA_VERSION
        targetCompatibility = AndroidConfig.JAVA_VERSION
    }
    kotlinOptions {
        jvmTarget = AndroidConfig.JAVA_VERSION.toString()
    }
    flavorDimensions += listOf("mode")
    productFlavors {
        create("dev") {
            dimension = "mode"
        }
        create("playStore") {
            dimension = "mode"
        }
    }

    lint {
        abortOnError = false
    }
    buildFeatures {
        dataBinding = true
    }
    namespace = "com.hjiee.core"
    hilt {
        enableAggregatingTask = ENABLE_AGGREGATING_TASK
    }
}

dependencies {

    api(Libs.MULTIDEX)
    implementation(Libs.LIFECYCLE_VIEW_MODEL_KTX)
    implementation(Libs.LIFECYCLE_LIVE_DATA_KTX)
    implementation(Libs.LIFECYCLE_COMMON)

    implementation(Libs.TIMBER)
    implementation(Libs.HILT)
    kapt(Libs.HILT_ANNOTATION)
//    implementation(Libs.HILT_VIEWMODEL)
    kapt(Libs.HILT_COMPILER)

    implementation(platform(Libs.FIREBASE))
    implementation(Libs.FIREBASE_ANALYTICS)
    implementation(Libs.FIREBASE_CRASHLYTICS)
    implementation(Libs.FIREBASE_REMOTE_CONFIG)

    testImplementation(TestLibs.JUNIT)

}