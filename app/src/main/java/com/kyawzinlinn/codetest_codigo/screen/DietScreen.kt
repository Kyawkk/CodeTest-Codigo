@file:OptIn(ExperimentalMaterial3Api::class)

package com.kyawzinlinn.codetest_codigo.screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltipBox
import androidx.compose.material3.PlainTooltipState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kyawzinlinn.codetest_codigo.domain.model.Diet
import com.kyawzinlinn.codetest_codigo.ui.components.BottomButtonLayout
import kotlinx.coroutines.launch

@Composable
fun DietScreen(
    viewModel: DataViewModel,
    onBackButtonClick: () -> Unit,
    onNextButtonClick: () -> Unit,
    modifier: Modifier = Modifier.fillMaxSize()
        .padding(16.dp)
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.selectedDiets) {
        Log.d("TAG", "DietScreen: ${uiState.selectedDiets}")
    }

    Column(modifier = modifier) {
        Text(
            text = "Select the diets you follow.",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(Modifier.height(16.dp))

        CheckBoxItem(
            diet = Diet(-1, "None", ""),
            showToolTip = false,
            isChecked = uiState.selectedDiets.isEmpty(),
            onChecked = { checked, diet ->
                if (checked) viewModel.resetDiets()
            }
        )

        Column (verticalArrangement = Arrangement.spacedBy(8.dp)) {
            uiState.diets.map {
                CheckBoxItem(
                    diet = it,
                    isChecked = it in uiState.selectedDiets,
                    onChecked = { checked, diet ->
                        if (checked) viewModel.addDiet(diet)
                        else viewModel.removeDiet(diet)
                    }
                )
            }
        }

        Spacer(Modifier.weight(1f))
        BottomButtonLayout(
            onBackButtonClick = onBackButtonClick,
            onNextButtonClick = onNextButtonClick
        )
    }
}

@Composable
fun CheckBoxItem(
    diet: Diet,
    onChecked: (Boolean, Diet) -> Unit,
    showToolTip: Boolean = true,
    isChecked: Boolean = false,
    modifier: Modifier = Modifier
) {
    var checked by rememberSaveable { mutableStateOf(isChecked) }
    val tooltipState = PlainTooltipState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(isChecked) {
        checked = isChecked
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                checked = !checked
                onChecked(checked, diet)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = {
                checked = !checked
                onChecked(checked, diet)
            }
        )
        Spacer(Modifier.width(8.dp))
        Text(text = diet.name)
        Spacer(Modifier.width(4.dp))
        if (showToolTip) {
            PlainTooltipBox(
                tooltip = { Text(diet.tool_tip) },
                tooltipState = tooltipState,
            ) {
                IconButton(onClick = { coroutineScope.launch { tooltipState.show() } }) {
                    Icon(
                        imageVector = Icons.Default.Info, contentDescription = null,
                    )
                }
            }
        }
    }
}