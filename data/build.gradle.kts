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
        create("playStore") {
            dimension = "mode"
        }
        create("dev") {
            dimension = "mode"
        }
    }
    namespace = "com.hjiee.data"
    hilt {
        enableAggregatingTask = ENABLE_AGGREGATING_TASK
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":domain"))


    implementation(Libs.COROUTINES_CORE)
    implementation(Libs.COROUTINES_ANDROID)

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

    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("com.google.android.material:material:1.4.0")
    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}