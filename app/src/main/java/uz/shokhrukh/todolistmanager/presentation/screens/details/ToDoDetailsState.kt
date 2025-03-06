package uz.shokhrukh.todolistmanager.presentation.screens.details

import uz.shokhrukh.todolistmanager.domain.model.ToDoModel

data class ToDoDetailsState(
    val toDoItem: ToDoModel = ToDoModel(),
    val isEdited: Boolean = false
)
