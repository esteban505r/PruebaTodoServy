import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.esteban.lopez.pruebatodoservy.model.model.TaskColor
import com.esteban.lopez.pruebatodoservy.model.model.TaskIcon

@Composable
fun IconPickerDialog(
    selectedColor: TaskColor,
    onIconSelected: (TaskIcon) -> Unit,
    onColorSelected: (TaskColor) -> Unit,
    onDismissRequest: () -> Unit
) {

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            shape = MaterialTheme.shapes.medium,
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Select an Icon", style = MaterialTheme.typography.titleMedium)

                LazyRow(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(TaskIcon.values().size) { index ->
                        Card(shape = CircleShape, elevation = CardDefaults.cardElevation(4.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.background
                            ),
                            modifier = Modifier.clickable {
                            onIconSelected(TaskIcon.values()[index])
                            onDismissRequest()
                        }) {
                            Icon(
                                painter = painterResource(id = TaskIcon.values()[index].resource),
                                contentDescription = "Icon",
                                modifier = Modifier
                                    .padding(8.dp)
                                    .size(60.dp),
                                tint = selectedColor.color
                            )
                        }
                    }
                }

                Divider(modifier = Modifier.padding(vertical = 8.dp))

                Text(text = "Select a Color", style = MaterialTheme.typography.titleMedium)

                LazyRow(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(TaskColor.values().size) { index ->
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(TaskColor.values()[index].color, shape = CircleShape)
                                .clickable {
                                    onColorSelected(TaskColor.values()[index])
                                }
                                .border(1.dp, Color.Black, shape = CircleShape)
                        )
                    }
                }
            }
        }
    }
}
