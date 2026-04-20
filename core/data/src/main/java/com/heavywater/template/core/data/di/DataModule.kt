package com.heavywater.data.di

import com.heavywater.template.core.data.repository.OfflineFirstUserDataRepository
import com.heavywater.template.core.data.repository.UserDataRepository
import com.heavywater.template.core.data.repository.WeatherRepository
import com.heavywater.template.core.data.repository.WeatherRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindsUserDataRepository(
        impl: OfflineFirstUserDataRepository,
    ): UserDataRepository

    @Binds
    abstract fun bindsWeatherRepository(
        impl: WeatherRepositoryImpl,
    ): WeatherRepository
}
