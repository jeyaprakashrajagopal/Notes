package com.anonymous.notes.widgets

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun RoundIconButton(
    modifier: Modifier = Modifier,
    elevation: Dp = 10.dp,
    backgroundColor: Color = Color.White,
    shape: RoundedCornerShape = CircleShape,
    imageVector: ImageVector,
    imageTint: Color,
    @StringRes contentDescription: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(5.dp)
            .clickable { onClick() }
            .then(modifier),
        backgroundColor = backgroundColor,
        shape = shape,
        elevation = elevation
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = stringResource(id = contentDescription),
            tint = imageTint
        )
    }
}