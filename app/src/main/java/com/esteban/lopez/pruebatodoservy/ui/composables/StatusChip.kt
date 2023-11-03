import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.esteban.lopez.pruebatodoservy.model.model.db.TaskStatus

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun StatusChip(
    status: TaskStatus,
    modifier: Modifier
) {


    Box(modifier = modifier){
        Chip(
            onClick = {},
            colors = ChipDefaults.chipColors(
                backgroundColor = status.color,
                contentColor = Color.White
            ),
        ) {
            Text(text = status.text, color = Color.White)
        }
    }
}