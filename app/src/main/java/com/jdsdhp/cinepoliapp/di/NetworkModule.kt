package com.jdsdhp.cinepoliapp.di

import android.content.Context
import com.jdsdhp.cinepoliapp.BuildConfig
import com.jdsdhp.cinepoliapp.data.api.*
import com.jdsdhp.cinepoliapp.data.store.AppDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.internal.http2.Header
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.HostnameVerifier

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    private const val BASE_URL = "http://aws-ami-api.cinepolis.com"
    private const val MAX_REQUEST_COUNT = 5
    private const val TIME_OUT_CONNECT = 25L
    private const val TIME_OUT_WRITE = 25L
    private const val TIME_OUT_READ = 25L

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(RetrofitService::class.java)

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory,
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(converterFactory)
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun provideConverter(): Converter.Factory = GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideOkHttpClient(
        dispatcher: Dispatcher,
        loggingInterceptor: HttpLoggingInterceptor,
        interceptor: Interceptor,
        //authenticator: Authenticator,
    ): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(TIME_OUT_CONNECT, TimeUnit.SECONDS)
        .writeTimeout(TIME_OUT_WRITE, TimeUnit.SECONDS)
        .readTimeout(TIME_OUT_READ, TimeUnit.SECONDS)
        .dispatcher(dispatcher)
        .addInterceptor(interceptor)
        .apply {
            if (BuildConfig.DEBUG) addInterceptor(loggingInterceptor)
            //authenticator(authenticator)
        }
        .build()

    //TODO: Use this for auto refresh token.
    /*@Singleton
    @Provides
    fun provideAuthenticator(
        service: Lazy<ApiService>,
        appDataStore: AppDataStore,
    ): Authenticator = TokenAuthenticator(service = service, dataStore = appDataStore)*/

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply { level = HttpLoggingInterceptor.Level.BODY }

    @Singleton
    @Provides
    fun provideInterceptor(dataStore: AppDataStore, basicHeaders: Array<Header>): Interceptor =
        Interceptor { chain ->
            chain.proceed(
                chain.request().newBuilder()
                    .apply {
                        basicHeaders.forEach { header ->
                            header.toString().split(":").let {
                                addHeader(it[0].trim(), it[1].trim())
                            }
                        }
                        val url = chain.request().url.toString()
                        if (!url.contains(URL_LOGIN)
                        //TODO: Add the other endpoints without token.
                        ) {
                            dataStore.provideAccessToken()?.let {
                                addHeader("Authorization", "Bearer $it")
                            }
                        }
                    }
                    .build()
            )
        }

    @Singleton
    @Provides
    fun provideDispatcher() = Dispatcher().apply { maxRequests = MAX_REQUEST_COUNT }

    @Provides
    fun provideBasicHeaders(): Array<Header> = arrayOf(
        Header(name = "api_key", value = BuildConfig.API_KEY),
    )

    @Provides
    fun provideRequestHandler(@ApplicationContext context: Context): RequestHandler =
        RequestHandlerImpl(context)

}