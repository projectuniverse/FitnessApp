package com.codecamp.fitnessapp.di

import android.app.Application
import com.codecamp.fitnessapp.sensor.Gyroscope
import com.codecamp.fitnessapp.sensor.GyroscopeRepositoryImpl
import com.codecamp.fitnessapp.ui.screens.inside.insideutil.PushUpUtil
import com.codecamp.fitnessapp.ui.screens.inside.insideutil.SitUpUtil
import com.codecamp.fitnessapp.ui.screens.inside.insideutil.SquatUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object SensorModule {

    @Provides
    @Singleton
    fun provideGyroscope(app: Application): Gyroscope {
        return Gyroscope(app)
    }

    @Provides
    @Singleton
    fun providePushUpUtil(): PushUpUtil {
        return PushUpUtil()
    }

    @Provides
    @Singleton
    fun provideSquatUtil(): SquatUtil {
        return SquatUtil()
    }

    @Provides
    @Singleton
    fun provideSitUpUtil(): SitUpUtil {
        return SitUpUtil()
    }

    @Provides
    @Singleton
    fun provideGyroscopeRepoImpl(gyroscope: Gyroscope): GyroscopeRepositoryImpl {
        return GyroscopeRepositoryImpl(gyroscope)
    }
}