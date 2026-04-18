package com.heavywater.data.di

import com.heavywater.data.repository.OfflineFirstUserDataRepository
import com.heavywater.data.repository.UserDataRepository
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
}
