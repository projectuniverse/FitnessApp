package com.codecamp.fitnessapp.sensor

import android.app.Application
import com.codecamp.fitnessapp.sensor.pushup.PushUpRepositoryImpl
import com.codecamp.fitnessapp.sensor.pushup.PushUpUtil
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
    fun provideProximitySensor(app: Application): ProximitySensor {
        return ProximitySensor(app)
    }

    @Provides
    @Singleton
    fun provideInsideWorkoutImpl(proximitySensor: ProximitySensor): PushUpRepositoryImpl {
        return PushUpRepositoryImpl(proximitySensor)
    }

    @Provides
    @Singleton
    fun providePushupUtil(): PushUpUtil {
        return PushUpUtil()
    }
}