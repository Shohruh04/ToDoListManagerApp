package uz.shokhrukh.todolistmanager.presentation.screens.list

import android.Manifest
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import uz.shokhrukh.todolistmanager.R
import uz.shokhrukh.todolistmanager.domain.model.ToDoModel
import uz.shokhrukh.todolistmanager.presentation.theme.MainColor
import uz.shokhrukh.todolistmanager.presentation.theme.screenBackgroundColor
import uz.shokhrukh.todolistmanager.presentation.utils.checkCameraPermission
import uz.shokhrukh.todolistmanager.presentation.utils.toDateString

@Composable
fun ToDoListScreen(
    state: ToDoListState = ToDoListState(),
    event: (ToDoListEvent) -> Unit = {},
    onNavigateToCreateScreen: () -> Unit = {},
    onItemClick: (Int) -> Unit = {}
) {

    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions.all { it.value }) {
            Unit
        } else {
            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }
    LaunchedEffect(Unit) {
        if (!context.checkCameraPermission()) {
            launcher.launch(arrayOf(Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE))
        }
    }


    if (state.deleteDialog) {
        // show alert dialog

        AlertDialog(
            onDismissRequest = {
                event(ToDoListEvent.HideDeleteDialog)
            },
            title = {
                Text("Delete ToDo")
            },
            text = {
                Text("Are you sure you want to delete ToDo?")
            },
            confirmButton = {
                IconButton(
                    onClick = {
                        event(ToDoListEvent.DeleteToDo(state.deletingId))
                        event(ToDoListEvent.HideDeleteDialog)
                        Toast.makeText(context, "ToDo successfully deleted", Toast.LENGTH_SHORT)
                            .show()
                    }
                ) {
                    Text(
                        "Yes",
                        color = Color.Red
                    )
                }
            },
            dismissButton = {
                IconButton(
                    onClick = {
                        event(ToDoListEvent.HideDeleteDialog)
                    }
                ) {
                    Text(
                        "No",
                        color = Color.Gray
                    )
                }
            }
        )

    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(screenBackgroundColor)
    ) {
        if (state.list.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(10.dp)
            ) {
                items(state.list) {
                    ToDoItem(
                        item = it,
                        onItemClick = { item ->
                            onItemClick.invoke(item.id)
                        },
                        onDeleteClick = { id ->
                            event(ToDoListEvent.ShowDeleteDialog(id))
                        }
                    )
                }
            }


        } else {
            val noData by rememberLottieComposition(
                LottieCompositionSpec.RawRes(
                    R.raw.no_data
                )
            )
            LottieAnimation(
                modifier = Modifier
                    .padding(50.dp),
                composition = noData
            )

        }

        SmallFloatingActionButton(
            onClick = {
                onNavigateToCreateScreen()
            },
            containerColor = MainColor,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Plus",
                tint = Color.White
            )
        }
    }

}

@Composable
private fun ToDoItem(
    item: ToDoModel,
    onItemClick: (ToDoModel) -> Unit = {},
    onDeleteClick: (Int) -> Unit = {}
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            onItemClick.invoke(item)
        },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        ),
    ) {
        Box {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(
                    item.title,
                    fontSize = 16.sp,
                    maxLines = 1,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    item.description,
                    fontSize = 14.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    item.date.toDateString(),
                    fontSize = 10.sp,
                    color = Color.Gray
                )
            }
            IconButton(
                onClick = {
                    onDeleteClick.invoke(item.id)
                },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Plus",
                    tint = Color.Red
                )
            }
        }

    }

}


@Preview
@Composable
private fun ToDoListScreenEmptyPreview() {
    ToDoListScreen()
}

@Preview
@Composable
private fun ToDoItemPreview() {

    ToDoItem(
        item = ToDoModel(
            id = 1,
            title = "To do title",
            description = "This is todo description",
            date = 123123123123
        )
    )
}