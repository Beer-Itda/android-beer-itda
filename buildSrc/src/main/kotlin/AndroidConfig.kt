import org.gradle.api.JavaVersion

object AndroidConfig {
    const val COMPILE_SDK_VERSION = 35
    const val MIN_SDK_VERSION = 23
    const val TARGET_SDK_VERSION = 35

    const val VERSION_CODE = 11
    const val VERSION_NAME = "2.0.0"

    const val BUILD_TOOLS_VERSION = "35.0.0"
    const val APPLICATION_ID = "com.ddd4.synesthesia.beer"
    const val TEST_INSTRUMENTATION_RUNNER = "androidx.test.runner.AndroidJUnitRunner"
    val JAVA_VERSION = JavaVersion.VERSION_17

}