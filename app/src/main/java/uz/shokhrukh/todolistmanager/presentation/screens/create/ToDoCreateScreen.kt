package uz.shokhrukh.todolistmanager.presentation.screens.create

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import uz.shokhrukh.todolistmanager.presentation.components.ButtonView
import uz.shokhrukh.todolistmanager.presentation.theme.MainColor
import uz.shokhrukh.todolistmanager.presentation.theme.screenBackgroundColor
import uz.shokhrukh.todolistmanager.presentation.utils.cameraIntent

@Composable
fun ToDoCreateScreen(
    state: ToDoCreateState = ToDoCreateState(),
    event: (ToDoCreateEvent) -> Unit = {},
    onPopBackStack: () -> Unit = {}
) {

    val context = LocalContext.current
    val scrollState = rememberScrollState()

    var titleText by remember {
        mutableStateOf("")
    }
    var imagePath by remember {
        mutableStateOf("")
    }
    var descriptionText by remember {
        mutableStateOf("")
    }
    var imageCaptured by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(state.toDoCreated) {
        if (state.toDoCreated) {
            Toast.makeText(context, "ToDo Created", Toast.LENGTH_SHORT).show()
            onPopBackStack()
        }
    }

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
               // imagePath = result.data?.data.toString()
                imageCaptured = true
            } else {
                Toast.makeText(context, "Error occurred", Toast.LENGTH_SHORT).show()
            }
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

        Text(
            "Add Image",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            modifier = Modifier.padding(16.dp)
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(200.dp),
            onClick = {
                context.cameraIntent(
                    { path ->
                        imagePath = path
                        Log.d("imagePath", "ToDoCreateScreen: $path")
                    }, { intent ->
                        cameraLauncher.launch(intent)
                    }
                )
            },
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Date",
                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(MainColor)
                )

                if (imageCaptured) {
                    val painter = rememberAsyncImagePainter(model = imagePath)
                    Image(
                        painter = painter,
                        contentDescription = "Image",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.fillMaxSize()
                    )
                }

            }
        }

        ButtonView(
            modifier = Modifier.padding(16.dp),
            enabled = titleText.isNotEmpty() && descriptionText.isNotEmpty(),
            text = "Create",
        ) {
            event(ToDoCreateEvent.AddToDo(
                title = titleText,
                description = descriptionText,
                date = System.currentTimeMillis(),
                imagePath = imagePath
            ))
        }
    }
}

@Preview
@Composable
private fun ToDoCreateScreenPreview() {

    ToDoCreateScreen()
}