package com.example.bettergeeks.di

import android.content.Context
import androidx.room.Room
import com.example.bettergeeks.data.dao.local.QuestionDao
import com.example.bettergeeks.data.dao.local.TopicDao
import com.example.bettergeeks.data.dao.remote.AuthInterceptor
import com.example.bettergeeks.data.dao.remote.OpenAiService
import com.example.bettergeeks.data.database.LocalDatabase
import com.example.bettergeeks.utils.Common
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): LocalDatabase {
        return Room.databaseBuilder(
            context,
            LocalDatabase::class.java,
            "database-name"
        ).build()
    }

    @Singleton
    @Provides
    fun provideTopicDao(database: LocalDatabase): TopicDao {
        return database.topicDao()
    }

    @Singleton
    @Provides
    fun provideQuestionDao(database: LocalDatabase): QuestionDao {
        return database.questionDao()
    }

    @Singleton
    @Provides
    fun providesOpenAiService(): OpenAiService {
        val loggingInterceptor =
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(Common.API_KEY))
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openai.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(OpenAiService::class.java)
    }
}
