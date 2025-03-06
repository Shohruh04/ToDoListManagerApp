package uz.shokhrukh.todolistmanager.presentation.screens.details

sealed interface ToDoDetailsEvent {
    data class GetToDoDetails(val id: Int) : ToDoDetailsEvent

    data class EditToDo(
        val itemId: Int,
        val title: String,
        val description: String
    ) : ToDoDetailsEvent
}