package uz.shokhrukh.todolistmanager.presentation.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.shokhrukh.todolistmanager.domain.repository.ToDoRepository
import javax.inject.Inject

@HiltViewModel
class ToDoListViewModel @Inject constructor(
    private val toDoRepository: ToDoRepository
) : ViewModel() {

    private var _state = MutableStateFlow(ToDoListState())
    val state = _state
        .onStart {
            getAllToDoList()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = ToDoListState()
        )


    fun onEvent(event: ToDoListEvent) {
        when(event){
            is ToDoListEvent.DeleteToDo -> {
                removeToDoItem(event.id)
            }
            is ToDoListEvent.ShowDeleteDialog -> {
                _state.update { currentState ->
                    currentState.copy(
                        deletingId = event.id,
                        deleteDialog = true
                    )
                }
            }
            is ToDoListEvent.HideDeleteDialog -> {
                _state.update { currentState ->
                    currentState.copy(
                        deleteDialog = false
                    )
                }
            }
        }
    }

    private fun removeToDoItem(id: Int) = viewModelScope.launch {
        toDoRepository.removeToDo(id)
        _state.update { currentState ->
            currentState.copy(
                isDelete = true
            )
        }

    }

    private fun getAllToDoList() = viewModelScope.launch {
           toDoRepository.getToDoList().collectLatest {
               _state.update { currentState ->
                   currentState.copy(
                      list = it
                   )
               }
           }
    }


}