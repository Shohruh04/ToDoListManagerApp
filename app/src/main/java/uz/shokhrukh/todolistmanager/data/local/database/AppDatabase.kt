package uz.shokhrukh.todolistmanager.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.shokhrukh.todolistmanager.data.local.dao.ToDoListDao
import uz.shokhrukh.todolistmanager.data.local.entity.ToDoEntity

@Database(
    version = 1,
    entities = [
        ToDoEntity::class
    ],
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun toDoDao(): ToDoListDao

}