package uz.shokhrukh.todolistmanager.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.shokhrukh.todolistmanager.domain.model.ToDoModel

interface ToDoRepository {

    suspend fun getToDoList(): Flow<List<ToDoModel>>

    suspend fun addToDo(
        title: String,
        description: String,
        date: Long,
        imagePath: String
    )

    suspend fun getToDoDetails(id: Int): Flow<ToDoModel>

    suspend fun removeToDo(id: Int)

    suspend fun updateToDo(id: Int, title: String, description: String)
}