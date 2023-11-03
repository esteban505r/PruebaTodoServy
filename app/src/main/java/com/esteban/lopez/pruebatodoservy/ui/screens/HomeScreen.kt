package com.esteban.lopez.pruebatodoservy.ui.screens;


import TaskListItem
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.esteban.lopez.pruebatodoservy.model.model.db.Task
import com.esteban.lopez.pruebatodoservy.model.model.db.TaskStatus
import com.esteban.lopez.pruebatodoservy.ui.composables.PageTitle
import com.esteban.lopez.pruebatodoservy.ui.navigation.BaseScreens
import com.esteban.lopez.pruebatodoservy.ui.theme.Gray50
import com.esteban.lopez.pruebatodoservy.viewmodel.HomeViewModel
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = koinViewModel(),
) {
    val state = homeViewModel.collectAsState().value

    LaunchedEffect(null) {
        homeViewModel.getAll()
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
        }
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 20.dp),
        ) {
            item {
                PageTitle(text = "Tasks", modifier = Modifier.padding(vertical = 20.dp))
            }
            items(state.tasks.size) { index ->
                TaskListItem(
                    task = state.tasks[index],
                    onClick = {

                    }
                )
            }
        }

    }
}




