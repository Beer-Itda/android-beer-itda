import PropertiesExt.BASE_URL
import PropertiesExt.ENABLE_AGGREGATING_TASK
import PropertiesExt.KAKAO
import PropertiesExt.getBaseUrl
import PropertiesExt.getKakaoKey
import com.android.build.api.dsl.ApkSigningConfig
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.util.Properties

plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.android.gms.oss-licenses-plugin")
}

android {
    setCompileSdkVersion(AndroidConfig.COMPILE_SDK_VERSION)
    buildToolsVersion = AndroidConfig.BUILD_TOOLS_VERSION

    signingConfigs {
        create("dev") {
            val keystorePath = "dev_keystore.properties"
            val keystoreProperties = getFileProperties(keystorePath)
            setSigningConfig(keystoreProperties)
        }
        create("release") {
            val keystorePath = "release_keystore.properties"
            val keystoreProperties = getFileProperties(keystorePath)
            setSigningConfig(keystoreProperties)
        }
    }

    defaultConfig {
        applicationId = AndroidConfig.APPLICATION_ID
        minSdk = AndroidConfig.MIN_SDK_VERSION
        targetSdk = AndroidConfig.TARGET_SDK_VERSION
        versionCode = AndroidConfig.VERSION_CODE
        versionName = AndroidConfig.VERSION_NAME

        testInstrumentationRunner = AndroidConfig.TEST_INSTRUMENTATION_RUNNER

        resValue("string", KAKAO, getKakaoKey())
        resValue("string", BASE_URL, getBaseUrl())
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            isShrinkResources = false
            isDebuggable = false
            signingConfig = signingConfigs.getByName("dev")
            multiDexEnabled = true
        }
        release {
            isMinifyEnabled = false
            isShrinkResources = false
            isDebuggable = false
            proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
            multiDexEnabled = true
            signingConfig = signingConfigs.getByName("release")
        }
    }

    kotlinOptions {
        jvmTarget = AndroidConfig.JAVA_VERSION.toString()
    }

    compileOptions {
        sourceCompatibility = AndroidConfig.JAVA_VERSION
        targetCompatibility = AndroidConfig.JAVA_VERSION
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
            resValue("string", "app_name", "비어있다")
            multiDexEnabled = true
        }
        create("dev") {
            dimension = "mode"
            resValue("string", "app_name", "beer dev")
            multiDexEnabled = true
        }
    }
    namespace = "com.ddd4.synesthesia.beer"

    hilt {
        enableAggregatingTask = ENABLE_AGGREGATING_TASK
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":presentation"))

    implementation(Libs.NAVIGATION_UI_KTX)

    implementation(Libs.TIMBER)
    implementation(Libs.HILT)
    kapt(Libs.HILT_ANNOTATION)
//    implementation(Libs.HILT_VIEWMODEL)
    kapt(Libs.HILT_COMPILER)

    implementation(Libs.KAKAO)

    testImplementation(TestLibs.JUNIT)
    androidTestImplementation(TestLibs.ANDROID_X_JUNIT)
    androidTestImplementation(TestLibs.ESPRESSO_CORE)
}

fun getFileProperties(propertiesPath: String): Properties {
    val fis = try {
        FileInputStream(rootProject.file(propertiesPath))
    } catch (e: FileNotFoundException) {
        FileInputStream(rootProject.file("dev_keystore.properties"))
    }
    val keystoreProperties = Properties()
    keystoreProperties.load(fis)
    return keystoreProperties
}

fun ApkSigningConfig.setSigningConfig(keystoreProperties: Properties) {
    val storeFileKey = "develop_keystore"
    val storePasswordKey = "develop_keystore_password"
    val keyAliasKey = "develop_key_alias"
    val keyPasswordKey = "develop_key_password"

    if (keystoreProperties.getProperty(storeFileKey, "").isNotEmpty()) {
        keyAlias = keystoreProperties.getProperty(keyAliasKey)
        keyPassword = keystoreProperties.getProperty(keyPasswordKey)
        storeFile = file(keystoreProperties.getProperty(storeFileKey))
        storePassword = keystoreProperties.getProperty(storePasswordKey)
    }
}