package uz.shokhrukh.todolistmanager.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import uz.shokhrukh.todolistmanager.data.local.database.AppDatabase
import uz.shokhrukh.todolistmanager.data.mapper.toEntity
import uz.shokhrukh.todolistmanager.data.mapper.toModel
import uz.shokhrukh.todolistmanager.domain.model.ToDoModel
import uz.shokhrukh.todolistmanager.domain.repository.ToDoRepository
import javax.inject.Inject

class ToDoRepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase
) : ToDoRepository {
    override suspend fun getToDoList(): Flow<List<ToDoModel>> {
        return appDatabase.toDoDao().getFiles().map { it.map { entity -> entity.toModel() }.reversed() }
    }

    override suspend fun addToDo(
        title: String,
        description: String,
        date: Long,
        imagePath: String
    ) {
        appDatabase.toDoDao().insert(ToDoModel(title = title, description = description, date = date, imagePath = imagePath).toEntity())
    }

    override suspend fun getToDoDetails(id: Int): Flow<ToDoModel> {
        return appDatabase.toDoDao().getToDoDetails(id).map { it.toModel() }
    }

    override suspend fun removeToDo(id: Int) {
        appDatabase.toDoDao().removeToDo(id)
    }

    override suspend fun updateToDo(id: Int, title: String, description: String) {
        appDatabase.toDoDao().editEntity(id, title, description)
    }
}