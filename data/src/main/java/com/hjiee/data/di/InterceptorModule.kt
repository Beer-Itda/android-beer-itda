package com.hjiee.data.di

import com.hjiee.core.manager.VersionManager
import com.hjiee.core.provider.SharedPreferenceProvider
import com.hjiee.data.authentication.AuthenticationInterceptor
import com.kakao.sdk.auth.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InterceptorModule {

    const val EXTRA_HEADER_NAME_PLATFORM = "Platform"
    const val EXTRA_HEADER_NAME_APP_VERSION = "AppVersion"
    const val EXTRA_HEADER_NAME_AUTHORIZATION = "Authorization"
    const val EXTRA_HEADER_NAME_BEARER = "Bearer"
    const val PROVIDE_NAME_HEADERS = "provide_headers"
    const val PROVIDE_NAME_BODY_LOGGING = "provide_body_logging_interceptor"
    const val PROVIDE_NAME_HEADER_LOGGING = "provide_header_logging_interceptor"

    @Provides
    @Singleton
    @Named(PROVIDE_NAME_HEADERS)
    fun provideHeaders(
        preference: SharedPreferenceProvider,
        versionManager: VersionManager
    ): AuthenticationInterceptor {
        return AuthenticationInterceptor(
            preference = preference,
            versionManager = versionManager
        )
    }

    @Provides
    @Singleton
    @Named(PROVIDE_NAME_BODY_LOGGING)
    fun provideBodyHttpLogging(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Provides
    @Singleton
    @Named(PROVIDE_NAME_HEADER_LOGGING)
    fun provideHeaderHttpLogging(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.HEADERS
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }
}