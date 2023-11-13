package com.kyawzinlinn.codetest_codigo.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BottomButtonLayout(
    onBackButtonClick: () -> Unit,
    onNextButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
        TextButton(onClick = onBackButtonClick) {
            Text("Back")
        }

        FilledTonalButton(onClick = onNextButtonClick) {
            Text("Next")
        }
    }
}