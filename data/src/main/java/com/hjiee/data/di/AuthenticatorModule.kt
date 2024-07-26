package com.hjiee.data.di

import com.hjiee.core.provider.SharedPreferenceProvider
import com.hjiee.core.provider.StringProvider
import com.hjiee.data.R
import com.hjiee.data.api.BeerApi
import com.hjiee.data.api.KakaoApi
import com.hjiee.data.authentication.AuthenticationInterceptor
import com.hjiee.data.authentication.TokenAuthenticator
import com.hjiee.data.repository.LoginRepositoryImpl
import com.hjiee.domain.usecase.login.RefreshTokenUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthenticatorModule {

    const val PROVIDE_NAME_AUTHENTICATOR = "provide_authenticator"
    private const val PROVIDE_NAME_AUTHENTICATOR_OKHTTP = "provide_authenticator_okhttp"
    private const val PROVIDE_NAME_BEER_REFRESH_SERVICE = "provide_beer_refresh"


    @Provides
    @Singleton
    @Named(PROVIDE_NAME_AUTHENTICATOR)
    fun provideAuthenticator(
        preference: SharedPreferenceProvider,
        @Named(PROVIDE_NAME_BEER_REFRESH_SERVICE) beerApi: BeerApi,
        kakaoApi: KakaoApi
    ): TokenAuthenticator {
        return TokenAuthenticator(
            useCase = RefreshTokenUseCase(
                repository = LoginRepositoryImpl(
                    beerApi = beerApi,
                    kakaoApi = kakaoApi
                )
            ),
            preference = preference
        )
    }

    @Provides
    @Singleton
    @Named(PROVIDE_NAME_BEER_REFRESH_SERVICE)
    fun provideRetrofit(
        stringProvider: StringProvider,
        @Named(PROVIDE_NAME_AUTHENTICATOR_OKHTTP) okHttp: OkHttpClient
    ): BeerApi {
        return Retrofit.Builder()
            .baseUrl(stringProvider.getStringRes(com.hjiee.domain.R.string.base_url))
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttp)
            .build()
            .create(BeerApi::class.java)
    }

    @Provides
    @Singleton
    @Named(PROVIDE_NAME_AUTHENTICATOR_OKHTTP)
    fun provideOkHttpClient(
        @Named(InterceptorModule.PROVIDE_NAME_HEADERS) headers: AuthenticationInterceptor,
        @Named(InterceptorModule.PROVIDE_NAME_BODY_LOGGING) bodyLoggingInterceptor: HttpLoggingInterceptor,
        @Named(InterceptorModule.PROVIDE_NAME_HEADER_LOGGING) headerLoggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(headers)
            addInterceptor(bodyLoggingInterceptor)
            addInterceptor(headerLoggingInterceptor)
        }.build()
    }
}