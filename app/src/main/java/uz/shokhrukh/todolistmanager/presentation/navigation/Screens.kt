package uz.shokhrukh.todolistmanager.presentation.navigation

import kotlinx.serialization.Serializable

interface Screens {

    @Serializable
    data object Splash : Screens


    @Serializable
    data object ToDoList : Screens

    @Serializable
    data object ToDoCrate : Screens

    @Serializable
    data class ToDoDetails(val id: Int) : Screens
}