package uz.shokhrukh.todolistmanager.domain.model

data class ToDoModel(
    val id: Int = 0,
    val title:String = "",
    val description: String = "",
    val date: Long = 0,
    val imagePath: String = ""
)