package uz.shokhrukh.todolistmanager.presentation.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.shokhrukh.todolistmanager.domain.repository.ToDoRepository
import javax.inject.Inject

@HiltViewModel
class ToDoDetailsViewModel @Inject constructor(
    private val repository: ToDoRepository
) : ViewModel(){
    private var _state = MutableStateFlow(ToDoDetailsState())
    val state = _state
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = ToDoDetailsState()
        )

    fun onEvent(event: ToDoDetailsEvent) {
        when(event){
            is ToDoDetailsEvent.GetToDoDetails -> {
                getToDoDetails(event.id)
            }
            is ToDoDetailsEvent.EditToDo -> {
                editToDo(event.itemId, event.title, event.description)
            }
        }

    }

    private fun editToDo(itemId: Int, title: String, description: String) = viewModelScope.launch {
        repository.updateToDo(itemId, title, description)
        _state.update { currentState ->
            currentState.copy(
                isEdited = true
            )
        }
    }

    private fun getToDoDetails(id: Int) = viewModelScope.launch {
        repository.getToDoDetails(id).collectLatest {
            _state.update { currentState ->
                currentState.copy(
                    toDoItem = it
                )
            }
        }
    }
}