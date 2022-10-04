package grabarski.mateusz.todo_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import grabarski.mateusz.todo_compose.ui.navigation.SetupNavigation
import grabarski.mateusz.todo_compose.ui.shared.SharedViewModel
import grabarski.mateusz.todo_compose.ui.theme.TodocomposeTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {

    private val sharedViewModel: SharedViewModel by viewModel()

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodocomposeTheme {
                navController = rememberNavController()
                SetupNavigation(
                    navHostController = navController,
                    sharedViewModel = sharedViewModel
                )
            }
        }
    }
}
