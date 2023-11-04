package com.esteban.lopez.pruebatodoservy.ui.screens;


import com.esteban.lopez.pruebatodoservy.ui.composables.TaskListItem
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.NavigationBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.esteban.lopez.pruebatodoservy.R
import com.esteban.lopez.pruebatodoservy.model.model.db.TaskStatus
import com.esteban.lopez.pruebatodoservy.model.state.HomeSideEffect
import com.esteban.lopez.pruebatodoservy.ui.composables.PageTitle
import com.esteban.lopez.pruebatodoservy.ui.navigation.BaseScreens
import com.esteban.lopez.pruebatodoservy.viewmodel.HomeViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState


@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = koinViewModel(),
) {
    val state = homeViewModel.collectAsState().value

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current


    LaunchedEffect(null) {
        homeViewModel.getAllByStatus(null)
        homeViewModel.startTimer()
        homeViewModel.container.sideEffectFlow.collect {
            when (it) {
                is HomeSideEffect.Toast -> {
                    Toast.makeText(context, it.text, Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(BaseScreens.CreateTaskScreen.name)
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    alwaysShowLabel = false,
                    selected = state.filterBy == null,
                    onClick = {
                        coroutineScope.launch {
                            homeViewModel.getAllByStatus(null)
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.baseline_check_circle_24),
                            contentDescription = "Icon",
                            modifier = Modifier.padding(10.dp),
                            tint = if (state.filterBy == null) MaterialTheme.colorScheme.primary else Color.Gray
                        )
                    },
                    label = {
                        Text(
                            text = "All",
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Center,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                        )
                    }
                )
                for (item in TaskStatus.values()) {
                    NavigationBarItem(
                        alwaysShowLabel = false,
                        selected = state.filterBy == item,
                        onClick = {
                            coroutineScope.launch {
                                homeViewModel.getAllByStatus(item)
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(item.icon),
                                contentDescription = "Icon",
                                tint = if (state.filterBy == item) state.filterBy.color else Color.Gray
                            )
                        },
                        label = {
                            Text(
                                text = item.text,
                                style = MaterialTheme.typography.bodySmall,
                                textAlign = TextAlign.Center,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    )
                }
            }
        }
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it)
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            PageTitle(
                text = "Tasks",
                modifier = Modifier.padding(vertical = 20.dp),
                withLogo = true
            )
            PageTitle(
                text = state.filterBy?.text ?: "All",
                modifier = Modifier.padding(vertical = 20.dp),
                color = state.filterBy?.color ?: MaterialTheme.colorScheme.primary
            )
        }

        if (state.tasks.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.empty),
                    contentDescription = "Empty tasks",
                    modifier = Modifier
                        .size(200.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    text = "No tasks",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(20.dp)
                )
                Text(
                    text = "Add one!",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(20.dp)
                )
            }
            return@Scaffold
        }

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 20.dp).padding(top = 64.dp),
        ) {
            items(state.tasks.size, key = { index -> state.tasks[index].id }) { index ->
                TaskListItem(
                    task = state.tasks[index],
                    onClick = {},
                    onDelete = {
                        coroutineScope.launch {
                            homeViewModel.deleteTask(it.id)
                        }
                    },
                    onCancel = { task ->
                        if (task.status != TaskStatus.CANCELLED) {
                            coroutineScope.launch {
                                homeViewModel.updateTask(
                                    task.copy(
                                        status = TaskStatus.CANCELLED,
                                    )
                                )
                            }
                        }
                    },
                    onDone = { task ->
                        if (task.status != TaskStatus.COMPLETED) {
                            coroutineScope.launch {
                                homeViewModel.updateTask(
                                    task.copy(
                                        status = TaskStatus.COMPLETED,
                                    )
                                )
                            }
                        }
                    },
                    onProgress = { task ->
                        if (task.status != TaskStatus.IN_PROGRESS) {
                            coroutineScope.launch {
                                homeViewModel.updateTask(
                                    task.copy(
                                        status = TaskStatus.IN_PROGRESS,
                                    )
                                )
                            }
                        }
                    },
                    onPending = { task ->
                        if (task.status != TaskStatus.PENDING) {
                            coroutineScope.launch {
                                homeViewModel.updateTask(
                                    task.copy(
                                        status = TaskStatus.PENDING,
                                    )
                                )
                            }
                        }
                    },
                    onEdit = { task ->
                        navController.navigate("${BaseScreens.CreateTaskScreen.name}?userId=${task.id}")
                    },

                    )
            }
        }

    }
}




