package com.esteban.lopez.pruebatodoservy.ui.screens

import IconPickerDialog
import TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.esteban.lopez.pruebatodoservy.R
import com.esteban.lopez.pruebatodoservy.model.extensions.toFormattedString
import com.esteban.lopez.pruebatodoservy.model.extensions.toLocalTime
import com.esteban.lopez.pruebatodoservy.model.model.TaskColor
import com.esteban.lopez.pruebatodoservy.model.model.TaskIcon
import com.esteban.lopez.pruebatodoservy.model.model.db.Task
import com.esteban.lopez.pruebatodoservy.model.model.db.TaskStatus
import com.esteban.lopez.pruebatodoservy.ui.composables.PageTitle
import com.esteban.lopez.pruebatodoservy.viewmodel.CreateTaskViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.time.LocalTime
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTaskScreen(navController: NavController,
                     viewModel: CreateTaskViewModel = koinViewModel()) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedStatus by remember { mutableStateOf(TaskStatus.PENDING) }
    var endTime by remember { mutableStateOf(LocalTime.now().plusHours(1)) }
    var time by remember { mutableStateOf(LocalTime.now()) }
    var taskStyle by remember { mutableStateOf(Pair<TaskColor?,TaskIcon?>(null,null)) }
    var showTimePicker by remember { mutableStateOf(false) }
    var showEndTimePicker by remember { mutableStateOf(false) }
    var showIconPicker by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }


    if(showTimePicker){
        TimePickerDialog(onChange = {
            time = it.toLocalTime()
            showTimePicker = false
        }, onDismiss = {
            showTimePicker = false
        } )
    }

    if(showEndTimePicker){
        TimePickerDialog(onChange = {
            endTime = it.toLocalTime()
            showEndTimePicker = false
        }, onDismiss = {
            showEndTimePicker = false
        } )
    }

    if(showIconPicker){
        IconPickerDialog(selectedColor = taskStyle.first?:TaskColor.DEEP_PURPLE , onIconSelected = {
            taskStyle = taskStyle.copy(first = taskStyle.first?: TaskColor.DEEP_PURPLE,second = it)
        }, onColorSelected = {
            taskStyle = taskStyle.copy(first = it)
        }, onDismissRequest = {
            showIconPicker = false
        })
    }

    Scaffold(snackbarHost = {
        SnackbarHost(hostState = snackbarHostState)
    },){
                Column(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    PageTitle(text = "Create a task", modifier = Modifier.padding(vertical = 20.dp))
                    Spacer(modifier = Modifier.height(16.dp))
                    TextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Name") }
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    TextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Description") },
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    DropdownMenuTaskStatus(selectedStatus) { newStatus ->
                        selectedStatus = newStatus
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    TextField(
                        value = time.toFormattedString(),
                        onValueChange = {  },
                        readOnly = true,
                        interactionSource = remember{MutableInteractionSource()}.also { interactionSource ->
                            LaunchedEffect(interactionSource) {
                                interactionSource.interactions.collect { interaction ->
                                    if (interaction is PressInteraction.Release) {
                                        showTimePicker = true
                                    }
                                }
                            }
                        },
                        label = { Text("Start Time") },
                        suffix = {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_watch_later_24),
                                contentDescription = "ClockIcon",
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(24.dp))

                    TextField(
                        value = endTime.toFormattedString(),
                        onValueChange = {},
                        label = { Text("End Time") },
                        interactionSource = remember{MutableInteractionSource()}.also { interactionSource ->
                            LaunchedEffect(interactionSource) {
                                interactionSource.interactions.collect { interaction ->
                                    if (interaction is PressInteraction.Release) {
                                        showEndTimePicker = true
                                    }
                                }
                            }
                        },
                        suffix = {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_watch_24),
                                contentDescription = "ClockIcon",
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    if(taskStyle.first != null && taskStyle.second != null){
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                            Text("Icon: ", style = MaterialTheme.typography.bodyLarge)
                            Spacer(modifier = Modifier.width(32.dp))
                            Card(shape = CircleShape, elevation = CardDefaults.cardElevation(4.dp), modifier = Modifier
                                .padding(4.dp)
                                .clickable {
                                    showIconPicker = true
                                }){
                                Icon(
                                    painter = painterResource(id = taskStyle.second!!.resource),
                                    contentDescription = "Selected Icon",
                                    tint = taskStyle.first!!.color,
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .size(48.dp)
                                )
                            }
                        }
                    }
                    else{
                        Button(onClick = { showIconPicker = true }) {
                            Text("Select Icon and Color")
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))


                    ElevatedButton(
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        onClick = {
                            coroutineScope.launch {
                                if(time > endTime){
                                    snackbarHostState.showSnackbar("End time must be greater than start time")
                                    return@launch
                                }
                                if(name.isEmpty()){
                                    snackbarHostState.showSnackbar("Name must not be empty")
                                    return@launch
                                }
                                if(description.isEmpty()){
                                    snackbarHostState.showSnackbar("Description must not be empty")
                                    return@launch
                                }
                                if(taskStyle.first==null || taskStyle.second==null){
                                    snackbarHostState.showSnackbar("You must select an icon and a color")
                                    return@launch
                                }

                                viewModel.createTask(
                                    Task(
                                        name = name,
                                        description = description,
                                        status = selectedStatus,
                                        endTime = endTime,
                                        time = time,
                                        color = taskStyle.first!!,
                                        icon = taskStyle.second!!
                                    )
                                )
                                navController.popBackStack()
                            }



                        }
                    ) {
                        Text("OK", modifier = Modifier.padding(horizontal = 20.dp))
                    }
                }
            }
        }


@Composable
fun DropdownMenuTaskStatus(
    selectedStatus: TaskStatus,
    onStatusSelected: (TaskStatus) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
        Text("Initial status: ", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.width(16.dp))
        Box {
            Button(onClick = { expanded = true },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                shape = MaterialTheme.shapes.small) {
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = selectedStatus.text)
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Arrow down"
                    )
                }
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                TaskStatus.values().forEach { status ->
                    DropdownMenuItem(onClick = {
                        onStatusSelected(status)
                        expanded = false
                    }, text = {
                        Text(text = status.text)
                    })
                }
            }
        }
    }
}
