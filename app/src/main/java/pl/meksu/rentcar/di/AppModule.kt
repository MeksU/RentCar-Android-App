package pl.meksu.rentcar.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pl.meksu.rentcar.common.Constants
import pl.meksu.rentcar.data.datastore.EncryptedDataStoreImpl
import pl.meksu.rentcar.data.remote.RentCarApi
import pl.meksu.rentcar.data.repository.CarRepositoryImpl
import pl.meksu.rentcar.data.repository.MessageRepositoryImpl
import pl.meksu.rentcar.data.repository.OfferRepositoryImpl
import pl.meksu.rentcar.data.repository.ReservationRepositoryImpl
import pl.meksu.rentcar.data.repository.UserRepositoryImpl
import pl.meksu.rentcar.domain.datastore.EncryptedDataStore
import pl.meksu.rentcar.domain.repository.CarRepository
import pl.meksu.rentcar.domain.repository.MessageRepository
import pl.meksu.rentcar.domain.repository.OfferRepository
import pl.meksu.rentcar.domain.repository.ReservationRepository
import pl.meksu.rentcar.domain.repository.UserRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRentCarApi(): RentCarApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RentCarApi::class.java)
    }

    @Provides
    @Singleton
    fun provideEncryptedDataStore(@ApplicationContext context: Context): EncryptedDataStore {
        return EncryptedDataStoreImpl(context)
    }

    @Provides
    @Singleton
    fun provideCarRepository(api: RentCarApi): CarRepository {
        return CarRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideUserRepository(api: RentCarApi): UserRepository {
        return UserRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideOfferRepository(api: RentCarApi): OfferRepository {
        return OfferRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideReservationRepository(api: RentCarApi): ReservationRepository {
        return ReservationRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideMessageRepository(api: RentCarApi): MessageRepository {
        return MessageRepositoryImpl(api)
    }
}