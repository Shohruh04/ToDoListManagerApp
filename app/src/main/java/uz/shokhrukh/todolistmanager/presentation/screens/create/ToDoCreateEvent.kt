package uz.shokhrukh.todolistmanager.presentation.screens.create

sealed interface ToDoCreateEvent {
    data class AddToDo(
        val title: String,
        val description: String,
        val date: Long,
        val imagePath: String
    ) : ToDoCreateEvent




}