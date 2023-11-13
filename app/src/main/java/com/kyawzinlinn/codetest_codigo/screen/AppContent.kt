package com.kyawzinlinn.codetest_codigo.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.kyawzinlinn.codetest_codigo.ui.navigation.NavigationGraph

@Composable
fun AppContent(
    viewModel: DataViewModel,
    modifier: Modifier = Modifier.padding(bottom = 5.dp)
) {
    val uiState by viewModel.uiState.collectAsState()

    val animatedDp by animateFloatAsState(
        targetValue = uiState.progress,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Box(modifier = modifier.fillMaxSize()) {
        NavigationGraph(
            viewModel = viewModel,
            navController = rememberNavController(),
            modifier = modifier
        )
        AnimatedVisibility(
            uiState.showBottomProgressBar, modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            LinearProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.onBackground,
                progress = animatedDp,
                strokeCap = StrokeCap.Round,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .background(MaterialTheme.colorScheme.onBackground)
            )
        }
    }
}