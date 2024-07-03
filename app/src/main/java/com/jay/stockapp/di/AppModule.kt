package com.jay.stockapp.di

import com.jay.stockapp.holdingscreen.repository.HoldingRepository
import com.jay.stockapp.holdingscreen.repository.HoldingRepositoryImpl
import com.jay.stockapp.network.HoldingService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideHoldingRepository(
        service: HoldingService
    ): HoldingRepository {
        return HoldingRepositoryImpl(
            service
        )
    }

}