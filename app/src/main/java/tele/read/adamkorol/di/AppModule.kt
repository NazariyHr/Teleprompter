package tele.read.adamkorol.di

import android.content.Context
import androidx.room.Room
import tele.read.adamkorol.data.ScenarioDatabase
import tele.read.adamkorol.data.ScenariosRepositoryImpl
import tele.read.adamkorol.domain.repository.ScenarioRepository
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