package com.esteban.lopez.pruebatodoservy.model.model.db

import androidx.compose.ui.graphics.Color
import com.esteban.lopez.pruebatodoservy.R
import com.esteban.lopez.pruebatodoservy.ui.theme.Blue40
import com.esteban.lopez.pruebatodoservy.ui.theme.Blue50
import com.esteban.lopez.pruebatodoservy.ui.theme.Green70
import com.esteban.lopez.pruebatodoservy.ui.theme.Red40
import com.esteban.lopez.pruebatodoservy.ui.theme.Yellow40

enum class TaskStatus(val status: String,val text:String,val color: Color,val icon:Int) {
    PENDING("PENDING","Pending", Yellow40,R.drawable.baseline_watch_later_24),
    IN_PROGRESS("IN_PROGRESS","In progress", Blue50,R.drawable.in_progress_svgrepo_com),
    COMPLETED("COMPLETED","Completed", Green70,R.drawable.baseline_check_circle_24),
    CANCELLED("CANCELLED","Canceled",Red40,R.drawable.baseline_block_24)
}
