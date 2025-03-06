package uz.shokhrukh.todolistmanager.data.mapper

import uz.shokhrukh.todolistmanager.data.local.entity.ToDoEntity
import uz.shokhrukh.todolistmanager.domain.model.ToDoModel

fun ToDoEntity.toModel() = ToDoModel(
    id = id,
    title = title,
    description = description,
    date = date,
    imagePath = imagePath
)

fun ToDoModel.toEntity() = ToDoEntity(
    title = title,
    description = description,
    date = date,
    imagePath = imagePath
)