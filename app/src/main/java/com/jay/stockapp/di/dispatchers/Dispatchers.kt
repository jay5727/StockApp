package com.jay.stockapp.di.dispatchers

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
class Dispatchers {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    @Provides
    @Singleton
    @IODispatcher
    fun provideIODispatcher(): CoroutineContext = (Dispatchers.IO + coroutineExceptionHandler)

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class IODispatcher

}
