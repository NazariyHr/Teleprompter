package com.test.teleprompter.di

import android.content.Context
import androidx.room.Room
import com.test.teleprompter.data.ScenarioDatabase
import com.test.teleprompter.data.ScenariosRepositoryImpl
import com.test.teleprompter.domain.repository.ScenarioRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRatesDatabase(
        @ApplicationContext context: Context
    ): ScenarioDatabase {
        return Room.databaseBuilder(
            context,
            ScenarioDatabase::class.java,
            "scenario.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideScenarioRepository(
        scenarioDatabase: ScenarioDatabase
    ): ScenarioRepository {
        return ScenariosRepositoryImpl(
            scenarioDatabase
        )
    }
}