package uz.shokhrukh.todolistmanager.presentation.screens.details

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import uz.shokhrukh.todolistmanager.presentation.components.ButtonView
import uz.shokhrukh.todolistmanager.presentation.theme.MainColor
import uz.shokhrukh.todolistmanager.presentation.theme.screenBackgroundColor

@Composable
fun ToDoiDetailsScreen(
    toDoId: Int = 0,
    state: ToDoDetailsState = ToDoDetailsState(),
    event: (ToDoDetailsEvent) -> Unit = {},
    onPopBackStack: () -> Unit = {}

) {
    val context = LocalContext.current

    var titleText by remember {
        mutableStateOf(state.toDoItem.title)
    }
    var descriptionText by remember {
        mutableStateOf(state.toDoItem.description)
    }
    LaunchedEffect(Unit) {
        event(ToDoDetailsEvent.GetToDoDetails(toDoId))
    }
    LaunchedEffect(state.isEdited) {
        if (state.isEdited) {
            Toast.makeText(context, "ToDo Edited", Toast.LENGTH_SHORT).show()
            onPopBackStack()
        }
    }

    LaunchedEffect(state.toDoItem) {
        titleText = state.toDoItem.title
        descriptionText = state.toDoItem.description
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(screenBackgroundColor)
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(top = 16.dp)
                .padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            BasicTextField(
                value = titleText,
                onValueChange = {
                    titleText = it

                },
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                ),
                keyboardOptions = KeyboardOptions(
                    autoCorrectEnabled = false,
                    imeAction = ImeAction.Unspecified,
                    keyboardType = KeyboardType.Text
                ),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 10.dp)
                    ) {
                        if (titleText.isEmpty()) {
                            Text(
                                text = "Title...",
                                color = Color.Gray,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                            )
                        }
                        innerTextField()
                    }
                },
                cursorBrush = SolidColor(MainColor)
            )
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(top = 16.dp)
                .padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            BasicTextField(
                value = descriptionText,
                onValueChange = {
                    descriptionText = it

                },
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                ),
                keyboardOptions = KeyboardOptions(
                    autoCorrectEnabled = false,
                    imeAction = ImeAction.Unspecified,
                    keyboardType = KeyboardType.Text
                ),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 10.dp)
                    ) {
                        if (descriptionText.isEmpty()) {
                            Text(
                                text = "Description...",
                                color = Color.Gray,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                            )
                        }
                        innerTextField()
                    }
                },
                cursorBrush = SolidColor(MainColor)
            )
        }
        if (state.toDoItem.imagePath.isNotEmpty()) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(200.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
            ) {
                val painter = rememberAsyncImagePainter(model = state.toDoItem.imagePath)
                Image(
                    painter = painter,
                    contentDescription = "Image",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        ButtonView(
            modifier = Modifier.padding(16.dp),
            enabled = titleText.isNotEmpty() && descriptionText.isNotEmpty(),
            text = "Save",
        ) {

            event(ToDoDetailsEvent.EditToDo(toDoId, titleText, descriptionText))

        }
    }
}

@Preview
@Composable
private fun ToDoiDetailsPreview() {
    ToDoiDetailsScreen()
}