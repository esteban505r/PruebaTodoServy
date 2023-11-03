import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.esteban.lopez.pruebatodoservy.model.extensions.toLocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(onChange:(TimePickerState)->Unit, onDismiss:() -> Unit){
    val timePickerState = rememberTimePickerState()
    Dialog(
        onDismissRequest = {
            onDismiss()
        },
        content = {
            Card(colors = CardDefaults.cardColors(containerColor = Color.White)) {
                TimePicker(
                    state = timePickerState,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                )
                Row(horizontalArrangement = Arrangement.End, modifier = Modifier.padding(16.dp).fillMaxWidth()){
                    Button(
                        onClick = {
                            onDismiss()
                        }
                    ) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(
                        onClick = {
                            onChange(timePickerState)
                            onDismiss()
                        }
                    ) {
                        Text("OK")
                    }
                }
            }
        }
    )
}