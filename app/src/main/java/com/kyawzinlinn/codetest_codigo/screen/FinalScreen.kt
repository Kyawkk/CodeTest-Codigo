package com.kyawzinlinn.codetest_codigo.screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kyawzinlinn.codetest_codigo.domain.model.Question

@Composable
fun FinalScreen(
    viewModel: DataViewModel, modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.finalQuestions) {
        Log.d("TAG", "FinalScreen: ${uiState.finalQuestions}")
    }

    Spacer(Modifier.height(32.dp))
    Column {
        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(uiState.finalQuestions) {
                QuestionItem(it, updateQuestion = {
                    viewModel.updateQuestion(it)
                })
            }
        }
        Spacer(Modifier.weight(1f))
        FilledTonalButton(
            modifier = Modifier.fillMaxWidth().padding(vertical = 32.dp, horizontal = 16.dp),
            onClick = {viewModel.printData()}
        ) {
            Text("Get my personalized vitamin")
        }
    }
}

@Composable
fun QuestionItem(
    question: Question,
    updateQuestion: (Question) -> Unit,
    modifier: Modifier = Modifier,
) {

    Column(modifier = modifier) {
        Text(
            text = question.question, style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(8.dp))
        Column {

            var selectedOptionIndex by remember { mutableStateOf(question.answerIndex) }

            question.options.mapIndexed { index, s ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selectedOptionIndex = index
                            updateQuestion(question.copy(answerIndex = index))
                        }, verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(selected = selectedOptionIndex == index, onClick = {
                        selectedOptionIndex = index
                        updateQuestion(question.copy(answerIndex = index))
                    })
                    Spacer(Modifier.width(4.dp))
                    Text(s)
                }
            }
        }
    }
}