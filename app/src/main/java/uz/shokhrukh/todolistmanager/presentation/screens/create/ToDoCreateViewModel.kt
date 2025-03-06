package uz.shokhrukh.todolistmanager.presentation.screens.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.shokhrukh.todolistmanager.domain.repository.ToDoRepository
import javax.inject.Inject

@HiltViewModel
class ToDoCreateViewModel @Inject constructor(
    private val toDoRepository: ToDoRepository
) : ViewModel() {

    private var _state = MutableStateFlow(ToDoCreateState())
    val state = _state
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = ToDoCreateState()
        )

    fun onEvent(event: ToDoCreateEvent) {
        when (event) {
            is ToDoCreateEvent.AddToDo -> {
                createToDo(event.title, event.description, event.date, event.imagePath)
            }
        }
    }

    private fun createToDo(
        title: String,
        description: String,
        date: Long,
        imagePath: String
    ) = viewModelScope.launch {
        toDoRepository.addToDo(
            title = title,
            description = description,
            date = date,
            imagePath = imagePath
        )
        _state.update { currentState ->
            currentState.copy(
                toDoCreated = true
            )
        }
    }
}