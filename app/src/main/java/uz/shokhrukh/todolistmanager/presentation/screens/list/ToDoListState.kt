package uz.shokhrukh.todolistmanager.presentation.screens.list

import uz.shokhrukh.todolistmanager.domain.model.ToDoModel

data class ToDoListState(
    val list: List<ToDoModel> = emptyList(),
    val isDelete: Boolean = false,
    val deleteDialog: Boolean = false,
    val deletingId: Int = 0
)