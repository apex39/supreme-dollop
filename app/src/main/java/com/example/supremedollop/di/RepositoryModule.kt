package com.example.supremedollop.di

import com.example.supremedollop.repository.FakeSalesmanRepository
import com.example.supremedollop.repository.SalesmanRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideSalesmanRepository(): SalesmanRepository = FakeSalesmanRepository()
}