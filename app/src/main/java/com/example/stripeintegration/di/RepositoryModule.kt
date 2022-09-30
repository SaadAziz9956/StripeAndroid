package com.example.stripeintegration.di

import com.example.stripeintegration.data.repository.MainRepositoryImpl
import com.example.stripeintegration.domain.repository.MainRepository
import com.example.stripeintegration.domain.usecase.Parser
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindBillingScreenRepo(
        billingScreenRepo: MainRepositoryImpl
    ): MainRepository


}