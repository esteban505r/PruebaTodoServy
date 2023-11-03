import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.esteban.lopez.pruebatodoservy.ui.navigation.BaseScreens
import com.esteban.lopez.pruebatodoservy.ui.screens.CreateTaskScreen
import com.esteban.lopez.pruebatodoservy.ui.screens.HomeScreen


@Composable
fun BaseNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = BaseScreens.HomeScreen.name) {
        composable(BaseScreens.HomeScreen.name) {
            HomeScreen(navController)
        }
        composable("${BaseScreens.CreateTaskScreen.name}?userId={userId}",
            arguments = listOf(navArgument("taskId") { type = NavType.StringType; defaultValue = null; nullable = true })) {
            CreateTaskScreen(navController,it.arguments?.getString("userId"))
        }
    }
}