package com.esteban.lopez.pruebatodoservy.model.model

import androidx.compose.ui.graphics.Color
import com.esteban.lopez.pruebatodoservy.ui.theme.BurntSienna
import com.esteban.lopez.pruebatodoservy.ui.theme.Cream
import com.esteban.lopez.pruebatodoservy.ui.theme.DarkSlateGray
import com.esteban.lopez.pruebatodoservy.ui.theme.DeepPurple
import com.esteban.lopez.pruebatodoservy.ui.theme.MistyRose
import com.esteban.lopez.pruebatodoservy.ui.theme.MutedPink
import com.esteban.lopez.pruebatodoservy.ui.theme.OliveGreen
import com.esteban.lopez.pruebatodoservy.ui.theme.RichGold
import com.esteban.lopez.pruebatodoservy.ui.theme.SlateGray


enum class TaskColor(val text: String, val color: Color) {
    DEEP_PURPLE("DEEP_PURPLE", DeepPurple),
    SLATE_GRAY("SLATE_GRAY", SlateGray),
    MUTED_PINK("MUTED_PINK", MutedPink),
    RICH_GOLD("RICH_GOLD", RichGold),
    BURNT_SIENNA("BURNT_SIENNA", BurntSienna),
    OLIVE_GREEN("OLIVE_GREEN", OliveGreen),
    CREAM("CREAM", Cream),
    DARK_SLATE_GRAY("DARK_SLATE_GRAY", DarkSlateGray),
    MISTY_ROSE("MISTY_ROSE", MistyRose)
}