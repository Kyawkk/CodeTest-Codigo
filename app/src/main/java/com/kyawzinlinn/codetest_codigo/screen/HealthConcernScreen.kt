@file:OptIn(ExperimentalLayoutApi::class)

package com.kyawzinlinn.codetest_codigo.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kyawzinlinn.codetest_codigo.R
import com.kyawzinlinn.codetest_codigo.domain.model.HealthConcern
import com.kyawzinlinn.codetest_codigo.ui.components.BottomButtonLayout
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable

@Composable
fun HealthConcernScreen(
    viewModel: DataViewModel,
    onBackButtonClick: () -> Unit,
    onNextButtonClick: () -> Unit,
    modifier: Modifier = Modifier.padding(16.dp)
) {
    val uiState by viewModel.uiState.collectAsState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 5.dp),
    ) {
        Text(
            text = "Select the top health concerns.*\n(upto 5)",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(Modifier.height(32.dp))
        ChipList(healthConcerns = uiState.healthConcerns,
            selectedHealthConcerns = uiState.selectedHealthConcerns,
            onChipItemClick = { isSelected, healthConcern ->
                if (isSelected) viewModel.addHealthConcern(healthConcern)
                else viewModel.removeHealthConcern(healthConcern)
            })

        Spacer(Modifier.height(32.dp))
        Text(
            text = "Prioritize", style = MaterialTheme.typography.headlineSmall
        )
        Spacer(Modifier.height(16.dp))
        PrioritizeList(uiState.selectedHealthConcerns, updateHealthConcerns = {
            viewModel.updateHealthConcerns(it)
        })
        Spacer(Modifier.weight(1f))
        BottomButtonLayout(
            onBackButtonClick = onBackButtonClick, onNextButtonClick = onNextButtonClick
        )
    }

}

@Composable
fun PrioritizeList(
    healthConcerns: List<HealthConcern>,
    updateHealthConcerns: (List<HealthConcern>) -> Unit,
    modifier: Modifier = Modifier
) {
    val data = remember { mutableStateOf(healthConcerns) }
    LaunchedEffect(healthConcerns) {
        data.value = healthConcerns
    }

    val state = rememberReorderableLazyListState(onMove = { from, to ->
        data.value = data.value.toMutableList().apply {
            add(to.index, removeAt(from.index))
        }
        updateHealthConcerns(data.value)
    })
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        state = state.listState,
        modifier = modifier
            .reorderable(state)
            .detectReorderAfterLongPress(state),
    ) {
        items(data.value) {
            ReorderableItem(state, key = it) { isDragging ->
                val elevation by animateDpAsState(if (isDragging) 16.dp else 0.dp)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier
                        .fillMaxWidth()
                        .shadow(elevation)
                        .clip(
                            RoundedCornerShape(10.dp)
                        )
                        .background(MaterialTheme.colorScheme.onSecondary)
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ChipItem(healthConcern = it,
                        clickable = false,
                        selected = true,
                        onChipItemClick = { selected, healthConcern -> })
                    Icon(painter = painterResource(R.drawable.menu), contentDescription = null)
                }
            }
        }
    }
}

@Composable
fun ChipList(
    onChipItemClick: (Boolean, HealthConcern) -> Unit,
    healthConcerns: List<HealthConcern>,
    selectedHealthConcerns: List<HealthConcern>,
    modifier: Modifier = Modifier
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        healthConcerns.forEach {
            ChipItem(
                healthConcern = it,
                selected = it in selectedHealthConcerns,
                onChipItemClick = onChipItemClick
            )
        }
    }
}

@Composable
fun ChipItem(
    onChipItemClick: (Boolean, HealthConcern) -> Unit,
    healthConcern: HealthConcern,
    selected: Boolean = false,
    clickable: Boolean = true,
    modifier: Modifier = Modifier
) {
    var selected by rememberSaveable { mutableStateOf(selected) }

    AnimatedVisibility(true, enter = fadeIn()) {
        Text(text = healthConcern.name,
            textAlign = TextAlign.Center,
            style = TextStyle(color = if (selected) Color.White else MaterialTheme.colorScheme.onBackground),
            modifier = modifier
                .border(
                    width = if (!selected) 1.dp else 0.dp,
                    color = if (!selected) MaterialTheme.colorScheme.primary else Color.Transparent,
                    shape = RoundedCornerShape(20.dp)
                )
                .clip(RoundedCornerShape(20.dp))
                .background(if (selected) MaterialTheme.colorScheme.primary else Color.Transparent)
                .clickable {
                    if (clickable) {
                        selected = !selected
                        onChipItemClick(selected, healthConcern)
                    }
                }
                .padding(horizontal = 24.dp, vertical = 8.dp))
    }
}