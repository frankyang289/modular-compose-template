package com.heavywater.data.repository

import com.heavywater.template.core.model.DarkThemeConfig
import com.heavywater.template.core.model.UserData
import com.heavywater.core.datastore.YourApplicationPreferencesDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfflineFirstUserDataRepository @Inject constructor(
    private val dataSource: YourApplicationPreferencesDataSource,
) : UserDataRepository {

    override val userData: Flow<UserData> = dataSource.userData

    override suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) =
        dataSource.setDarkThemeConfig(darkThemeConfig)

    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) =
        dataSource.setDynamicColorPreference(useDynamicColor)
}