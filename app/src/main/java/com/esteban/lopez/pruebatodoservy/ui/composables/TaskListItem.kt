import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
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
import com.esteban.lopez.pruebatodoservy.model.model.db.Task
import com.esteban.lopez.pruebatodoservy.ui.theme.Blue50
import com.esteban.lopez.pruebatodoservy.ui.theme.Gray20
import com.esteban.lopez.pruebatodoservy.ui.theme.Gray50
import com.esteban.lopez.pruebatodoservy.ui.theme.Green50
import com.esteban.lopez.pruebatodoservy.ui.theme.Green70
import com.esteban.lopez.pruebatodoservy.ui.theme.Red40
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TaskListItem(
    task: Task,
    onClick: () -> Unit,
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

    if (showDeleteDialog) {
        Dialog(onDismissRequest = {
            showDeleteDialog = false
        }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(440.dp)
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
                            .background(color = Color(0xFFF9FBFB))
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        Text(
                            text = "Novedad",
                            style = MaterialTheme.typography.labelMedium,
                            fontSize = 30.sp,
                            color = Green50,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                        )
                        Text(
                            text = "Eliminar",
                            style = MaterialTheme.typography.labelMedium,
                            fontSize = 43.sp,
                            color = Blue50,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "¿Está seguro que desea eliminar el registro?",
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 24.sp,
                        color = Gray50,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    ElevatedButton(
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
                        onClick = {
                            showDeleteDialog = false
                            show = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Green50
                        ),
                    ) {
                        Row(modifier = Modifier.padding(vertical = 2.dp, horizontal = 40.dp)) {
                            Icon(Icons.Filled.Check, "", tint = Color.White)
                            Spacer(modifier = Modifier.width(5.dp))
                            Text("Aceptar")
                        }

                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    ElevatedButton(
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
                        onClick = {
                            showDeleteDialog = false
                            coroutineScope.launch {
                                dismissState.reset()
                            }
                        }, colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White
                        )
                    ) {
                        Row(modifier = Modifier.padding(vertical = 2.dp, horizontal = 40.dp)) {
                            Icon(Icons.Filled.Close, "", tint = Color.Gray)
                            Spacer(modifier = Modifier.width(5.dp))
                            Text("Cancelar", color = Color.Gray)
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
                Box(modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp)) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .background(
                                    color = task.color.color,
                                    shape = RoundedCornerShape(
                                        topStart = 15.dp,
                                        topEnd = 15.dp
                                    )
                                )
                                .padding(vertical = 16.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.End,
                        ) {
                            Text(
                                task.name,
                                fontSize = 22.sp,
                                color = Color.White,
                                modifier = Modifier.width(200.dp)
                            )
                            Text(
                                "ID: ${task.id}", fontSize = 12.sp, modifier = Modifier
                                    .width(200.dp)
                                    .padding(vertical = 15.dp)
                            )
                        }
                        Row(
                            modifier = Modifier
                                .background(
                                    color = Gray20, shape = RoundedCornerShape(
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
                            Row(
                                modifier = Modifier
                                    .width(200.dp),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(onClick = {}) {
                                    Icon(
                                        painterResource(id = R.drawable.baseline_check_circle_24),
                                        tint = Green70,
                                        contentDescription = "Check",)
                                }
                                IconButton(onClick = {}) {
                                    Icon(
                                        painterResource(id = R.drawable.edit_round_icon),
                                        tint = Blue50,
                                        contentDescription = "Edit"
                                    )
                                }
                                IconButton(onClick = {}) {
                                    Icon(
                                        painterResource(id = R.drawable.close_round_icon),
                                        tint = Red40,
                                        contentDescription = "Cancel"
                                    )
                                }
                            }
                        }
                    }
                    Card(
                        modifier = Modifier
                            .width(140.dp)
                            .height(160.dp)
                            .padding(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Icon(
                                painter = painterResource(
                                    id = task.icon.resource
                                ),
                                "Icon",
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .size(64.dp),
                                tint = task.color.color
                            )
                        }
                    }
                }
            })
    }
}