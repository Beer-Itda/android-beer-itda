# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Please add these rules to your existing keep rules in order to suppress warnings.
# This is generated automatically by the Android Gradle plugin.
-dontwarn com.hjiee.data.api.BeerApi
-dontwarn com.hjiee.data.api.KakaoApi
-dontwarn com.hjiee.data.authentication.AuthenticationInterceptor
-dontwarn com.hjiee.data.authentication.TokenAuthenticator
-dontwarn com.hjiee.data.di.AuthenticatorModule_ProvideAuthenticatorFactory
-dontwarn com.hjiee.data.di.AuthenticatorModule_ProvideOkHttpClientFactory
-dontwarn com.hjiee.data.di.AuthenticatorModule_ProvideRetrofitFactory
-dontwarn com.hjiee.data.di.InterceptorModule_ProvideBodyHttpLoggingFactory
-dontwarn com.hjiee.data.di.InterceptorModule_ProvideHeaderHttpLoggingFactory
-dontwarn com.hjiee.data.di.InterceptorModule_ProvideHeadersFactory
-dontwarn com.hjiee.data.di.NetworkModule_ProvideBeerServiceFactory
-dontwarn com.hjiee.data.di.NetworkModule_ProvideKakaoOAuthOkHttpClientFactory
-dontwarn com.hjiee.data.di.NetworkModule_ProvideKakaoRetrofitFactory
-dontwarn com.hjiee.data.di.NetworkModule_ProvideKakaoServiceFactory
-dontwarn com.hjiee.data.di.NetworkModule_ProvideOkHttpClientFactory
-dontwarn com.hjiee.data.di.NetworkModule_ProvideRetrofitFactory
-dontwarn com.hjiee.data.di.RepositoryModule_ProvideBeerRepositoryFactory
-dontwarn com.hjiee.data.di.RepositoryModule_ProvideLoginRepositoryFactory