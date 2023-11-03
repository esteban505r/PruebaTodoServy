package com.esteban.lopez.pruebatodoservy.ui.composables

import DismissBackground
import StatusChip
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.esteban.lopez.pruebatodoservy.R
import com.esteban.lopez.pruebatodoservy.model.extensions.timeLeft
import com.esteban.lopez.pruebatodoservy.model.model.db.Task
import com.esteban.lopez.pruebatodoservy.ui.theme.Blue40
import com.esteban.lopez.pruebatodoservy.ui.theme.Blue50
import com.esteban.lopez.pruebatodoservy.ui.theme.Gray20
import com.esteban.lopez.pruebatodoservy.ui.theme.Gray50
import com.esteban.lopez.pruebatodoservy.ui.theme.Green50
import com.esteban.lopez.pruebatodoservy.ui.theme.Green70
import com.esteban.lopez.pruebatodoservy.ui.theme.Red40
import com.esteban.lopez.pruebatodoservy.viewmodel.HomeViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TaskListItem(
    taskViewModel: HomeViewModel = koinViewModel(),
    task: Task,
    onClick: () -> Unit,
    onDone: (Task) -> Unit,
    onProgress: (Task) -> Unit,
    onCancel: (Task) -> Unit,
    onDelete: (Task) -> Unit,
    onEdit: (Task) -> Unit,
) {
    var show by remember { mutableStateOf(true) }
    var showDeleteDialog by remember {
        mutableStateOf(false)
    }
    val coroutineScope = rememberCoroutineScope()
    val dismissState = rememberDismissState(
        confirmStateChange = {
            if (it == DismissValue.DismissedToStart) {
                showDeleteDialog = true
                return@rememberDismissState true
            }
            return@rememberDismissState false
        },
    )

    val state = taskViewModel.collectAsState()

    if (showDeleteDialog) {
        Dialog(onDismissRequest = {
            showDeleteDialog = false
        }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        Text(
                            text = "Delete task",
                            style = MaterialTheme.typography.labelMedium,
                            fontSize = 24.sp,
                            color = Blue50,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                        )
                    }
                    Text(
                        text = "Are you sure?",
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 18.sp,
                        color = Gray50,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        ElevatedButton(
                            onClick = {
                                onDelete(task)
                                showDeleteDialog = false
                                show = false
                            },
                            colors = ButtonDefaults.elevatedButtonColors(
                                containerColor = Green50,
                                contentColor = Color.White
                            )
                        ) {
                            Text("OK")
                        }
                        ElevatedButton(
                            onClick = {
                                showDeleteDialog = false
                                coroutineScope.launch {
                                    dismissState.reset()
                                }
                            },
                            colors = ButtonDefaults.elevatedButtonColors(
                                containerColor = Red40,
                                contentColor = Color.White
                            )
                        ) {
                            Text("Cancel")
                        }
                    }
                }
            }
        }
    }

    AnimatedVisibility(
        show, exit = fadeOut(spring())
    ) {
        SwipeToDismiss(state = dismissState,
            directions = setOf(DismissDirection.EndToStart),
            modifier = Modifier,
            background = {
                DismissBackground()
            },
            dismissContent = {
               Card(modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp),
                   elevation = CardDefaults.cardElevation(8.dp)){
                   Column(
                       modifier = Modifier
                           .fillMaxWidth()
                   ) {
                       Row(modifier = Modifier
                           .background(
                               color = Gray20,
                               shape = RoundedCornerShape(
                                   topStart = 15.dp,
                                   topEnd = 15.dp
                               )
                           )
                           .padding(vertical = 16.dp)
                           .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
                           Icon(
                               painter = painterResource(
                                   id = task.icon.resource
                               ),
                               "Icon",
                               modifier = Modifier
                                   .padding(horizontal = 24.dp)
                                   .size(64.dp),
                               tint = task.color.color
                           )
                           Row {
                               Column(
                               ) {
                                   Text(
                                       task.name,
                                       fontSize = 22.sp,
                                       color = Gray50,
                                       modifier = Modifier.widthIn(0.dp,120.dp)
                                   )
                                   StatusChip(
                                       status = task.status,
                                       modifier = Modifier.padding(top = 8.dp)
                                   )
                               }
                               Column {
                                   Text(if(task.time > state.value.localTime) "Time left to start:" else "Time",
                                       fontSize = 12.sp,
                                       color = Gray50,
                                       modifier = Modifier.padding(start = 24.dp),)
                                   Text(
                                       task.time.timeLeft(state.value.localTime),
                                       fontSize = 18.sp,
                                       color = Gray50,
                                       modifier = Modifier.padding(start = 24.dp)
                                   )
                               }
                           }
                       }
                       Row(
                           modifier = Modifier
                               .background(
                                   color = MaterialTheme.colorScheme.primaryContainer,
                                   shape = RoundedCornerShape(
                                       bottomStart = 15.dp,
                                       bottomEnd = 15.dp
                                   )
                               )
                               .clickable {
                                   onClick()
                               }
                               .fillMaxWidth(),
                           horizontalArrangement = Arrangement.End,
                           verticalAlignment = Alignment.CenterVertically
                       ) {
                           Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                               Row{
                                      IconButton(onClick = {
                                        onEdit(task)
                                      }) {
                                        Icon(
                                             painterResource(id = R.drawable.edit_round_icon),
                                             tint = Blue40,
                                             contentDescription = "Edit",
                                        )
                                      }
                               }
                               Row(
                                   horizontalArrangement = Arrangement.End,
                                   verticalAlignment = Alignment.CenterVertically
                               ) {
                                   IconButton(onClick = {
                                       onDone(task)
                                   }) {
                                       Icon(
                                           painterResource(id = R.drawable.baseline_check_circle_24),
                                           tint = Green70,
                                           contentDescription = "Check",
                                       )
                                   }
                                   IconButton(onClick = {
                                       onProgress(task)
                                   }) {
                                       Icon(
                                           painterResource(id = R.drawable.in_progress_svgrepo_com),
                                           tint = Blue50,
                                           contentDescription = "Edit"
                                       )
                                   }
                                   IconButton(onClick = {
                                       onCancel(task)
                                   }) {
                                       Icon(
                                           painterResource(id = R.drawable.baseline_block_24),
                                           tint = Red40,
                                           contentDescription = "Cancel"
                                       )
                                   }
                               }
                           }
                       }
                   }
               }
            })
    }
}
