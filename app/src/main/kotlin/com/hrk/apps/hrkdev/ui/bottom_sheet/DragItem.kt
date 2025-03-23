package com.hrk.apps.hrkdev.ui.bottom_sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun DragItem() {
    HorizontalDivider(
        modifier = Modifier
            .padding(vertical = 12.dp)
            .clip(RoundedCornerShape(12.dp))
            .width(32.dp)
            .height(5.dp)
            .background(Color.LightGray)
    )
}
