package uz.shokhrukh.todolistmanager.presentation.screens.list

sealed interface ToDoListEvent {
    data class DeleteToDo(
        val id: Int
    ) : ToDoListEvent

    data class ShowDeleteDialog(val id: Int): ToDoListEvent

    data object HideDeleteDialog : ToDoListEvent

}