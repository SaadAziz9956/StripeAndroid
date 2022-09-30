package com.example.stripeintegration.di

import com.example.stripeintegration.domain.network.KtorResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideServiceInterface(): KtorResponse {
        return KtorResponse.create()
    }

}