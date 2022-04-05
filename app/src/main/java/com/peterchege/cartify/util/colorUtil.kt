package com.peterchege.cartify.util

import androidx.compose.ui.graphics.Color

object colorUtil {
    fun getColor(colorString: String): Color {
        return Color(android.graphics.Color.parseColor("#" + colorString))
    }
}