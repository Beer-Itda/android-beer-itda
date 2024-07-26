// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:${Versions.GRADLE}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${Versions.HILT}")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.NAVIGATION_VERSION}")
        classpath("com.google.gms:google-services:${Versions.GOOGLE_SERVIER}")
        classpath("com.google.firebase:firebase-crashlytics-gradle:${Versions.GOOGLE_CRASHLYTICS}")
        classpath("com.google.android.gms:oss-licenses-plugin:${Versions.OSS_LICENSES_PLUGIN}")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://devrepo.kakao.com/nexus/content/groups/public/") }
        maven { setUrl("https://jitpack.io") }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
