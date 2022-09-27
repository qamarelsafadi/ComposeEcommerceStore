package com.qamar.composeecommercestore.di

import android.content.Context
import androidx.room.Room
import com.example.android.architecture.blueprints.todoapp.di.IoDispatcher
import com.qamar.composeecommercestore.data.category.source.local.CategoryLocalDataSource
import com.qamar.composeecommercestore.data.category.source.CategoryRepository
import com.qamar.composeecommercestore.data.category.source.CategoryRepositoryImp
import com.qamar.composeecommercestore.data.category.source.local.CategoriesDatabase
import com.qamar.composeecommercestore.data.category.source.local.CategoriesLocalDataSource
import com.qamar.composeecommercestore.data.category.source.remote.CategoriesRemoteDataSource
import com.qamar.composeecommercestore.data.category.source.remote.CategoryApi
import com.qamar.composeecommercestore.data.category.source.remote.CategoryRemoteDataSource
import com.qamar.composeecommercestore.util.RetrofitBuilder
import com.qamar.composeecommercestore.util.HeaderInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RemoteCategoryDataSource

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class LocalCategoryDataSource

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideCategoriesRepository(
        @RemoteCategoryDataSource remoteDataSource: CategoryRemoteDataSource,
        @LocalCategoryDataSource localDataSource: CategoryLocalDataSource,
    ): CategoryRepository {
        return CategoryRepositoryImp(remoteDataSource, localDataSource)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @RemoteCategoryDataSource
    @Provides
    fun provideCategoriesRemoteDataSource(
        apiService: CategoryApi
    ): CategoryRemoteDataSource = CategoriesRemoteDataSource(apiService)

    @Singleton
    @LocalCategoryDataSource
    @Provides
    fun provideCategoriesLocalDataSource(
        database: CategoriesDatabase,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): CategoryLocalDataSource {
        return CategoriesLocalDataSource(database.categoryDao(), ioDispatcher)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): CategoriesDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            CategoriesDatabase::class.java,
            "Categories.db"
        ).build()
    }
}

@Module
@InstallIn(SingletonComponent::class)
object NetworkingModule {

    @Provides
    @Singleton
    fun provideRetrofit(
        retrofitBuilder: RetrofitBuilder,
        headerInterceptor: HeaderInterceptor
    ): Retrofit =
        retrofitBuilder
            .addInterceptors(headerInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideCategoriesApi(retrofit: Retrofit): CategoryApi = retrofit.create(CategoryApi::class.java)

}
