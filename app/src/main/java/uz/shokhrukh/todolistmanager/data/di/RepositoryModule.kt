package uz.shokhrukh.todolistmanager.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.shokhrukh.todolistmanager.data.local.database.AppDatabase
import uz.shokhrukh.todolistmanager.data.repository.ToDoRepositoryImpl
import uz.shokhrukh.todolistmanager.domain.repository.ToDoRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun toDoRepository(
        appDatabase: AppDatabase
    ): ToDoRepository =
        ToDoRepositoryImpl(
            appDatabase
        )

}
