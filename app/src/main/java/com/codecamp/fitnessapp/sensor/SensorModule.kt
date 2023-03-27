package com.codecamp.fitnessapp.sensor

import android.app.Application
import com.codecamp.fitnessapp.sensor.finall.GyroscopeRepositoryImpl
import com.codecamp.fitnessapp.sensor.pushup.PushUpRepositoryImpl
import com.codecamp.fitnessapp.sensor.pushup.PushUpUtil
import com.codecamp.fitnessapp.sensor.situp.SitUpUtil
import com.codecamp.fitnessapp.sensor.squat.SquatRepositoryImpl
import com.codecamp.fitnessapp.sensor.squat.SquatUtil
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
    fun provideAccelerometer(app: Application): Accelerometer {
        return Accelerometer(app)
    }

    @Provides
    @Singleton
    fun provideGyroscope(app: Application): Gyroscope {
        return Gyroscope(app)
    }

    @Provides
    @Singleton
    fun provideInsideWorkoutImpl(proximitySensor: ProximitySensor): PushUpRepositoryImpl {
        return PushUpRepositoryImpl(proximitySensor)
    }

    @Provides
    @Singleton
    fun provideSquatRepositoryImpl(accelerometer: Accelerometer): SquatRepositoryImpl {
        return SquatRepositoryImpl(accelerometer)
    }

    @Provides
    @Singleton
    fun providePushupUtil(): PushUpUtil {
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