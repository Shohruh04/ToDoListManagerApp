package uz.shokhrukh.todolistmanager.data.local.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import uz.shokhrukh.todolistmanager.data.local.entity.ToDoEntity

@Dao
interface ToDoListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: ToDoEntity)

    @Query("DELETE FROM todo")
    fun removeAll()


    @Query("DELETE FROM todo WHERE id = :id")
    fun removeToDo(id: Int)

    @Query("SELECT * FROM todo WHERE id = :id")
    fun getToDoDetails(id: Int): Flow<ToDoEntity>

    @Query("SELECT * FROM todo")
    fun getFiles(): Flow<List<ToDoEntity>>

    @Query("UPDATE todo SET title = :title, description = :description WHERE id = :id")
    fun editEntity(id: Int, title: String, description: String)

}