package com.mmartinez.data.di


import com.mmartinez.data.BuildConfig
import com.mmartinez.data.HashGenerator
import com.mmartinez.data.mapper.CharacterListMapper
import com.mmartinez.data.mapper.ComicListMapper
import com.mmartinez.data.rest.MarvelService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    @PublicKey
    fun providePublicKey() = BuildConfig.PUBLIC_KEY

    @Provides
    @PrivateKey
    fun providePrivateKey() = BuildConfig.PRIVATE_KEY

    @Provides
    fun providesHashGenerator() = HashGenerator()

    @Provides
    fun providesOkhttpInterceptor(@PublicKey publicKey: String) : Interceptor {
        return  Interceptor { chain ->
            val originalRequest = chain.request()
            val originalUrl = originalRequest.url
            val url = originalUrl.newBuilder()
                .addQueryParameter("apikey", publicKey)
                .build()
            val requestBuilder = originalRequest.newBuilder().url(url)
            val request = requestBuilder.build()

            return@Interceptor chain.proceed(request)
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(interceptor)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL:String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    fun provideApiService(retrofit: Retrofit) = retrofit.create(MarvelService::class.java)

    @Provides
    fun provideCharacterListMapper() = CharacterListMapper()

    @Provides
    fun provideComicListMapper() = ComicListMapper()

}