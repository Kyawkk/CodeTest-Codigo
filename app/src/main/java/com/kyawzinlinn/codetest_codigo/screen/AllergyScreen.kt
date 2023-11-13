@file:OptIn(ExperimentalLayoutApi::class)

package com.kyawzinlinn.codetest_codigo.screen

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kyawzinlinn.codetest_codigo.domain.model.Allergy
import com.kyawzinlinn.codetest_codigo.ui.components.BottomButtonLayout
import kotlinx.coroutines.delay

@Composable
fun AllergyScreen(
    viewModel: DataViewModel,
    onBackButtonClick: () -> Unit,
    onNextButtonClick: () -> Unit,
    modifier: Modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
) {
    val uiState by viewModel.uiState.collectAsState()
    var text by rememberSaveable { mutableStateOf("") }

    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Write any specific allergies or sensitivity towards specific things. (optional)",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(Modifier.height(24.dp))
        ChipTextField(
            selectedAllergies = uiState.selectedAllergies,
            onSearch = {text = it},
            onAllergyDeleteClick = { viewModel.removeAllergy(it) }
        )

        Column(modifier = Modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
            uiState.allergies.filter { allergy ->
                allergy.name.toLowerCase().contains(text.toLowerCase())
            }.map {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            text = it.name
                            viewModel.addAllergy(it)
                        },
                    text = it.name
                )
            }
        }
        Spacer(Modifier.weight(1f))
        BottomButtonLayout(
            onNextButtonClick = onNextButtonClick,
            onBackButtonClick = onBackButtonClick
        )
    }
}

@Composable
fun ChipTextField(
    modifier: Modifier = Modifier,
    selectedAllergies: List<Allergy>,
    onSearch: (String) -> Unit,
    onAllergyDeleteClick: (Allergy) -> Unit
) {
    var text by rememberSaveable { mutableStateOf("") }
    val focusRequester = remember {
        FocusRequester()
    }
    LaunchedEffect(Unit) {
        delay(100)
        focusRequester.requestFocus()
    }
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        selectedAllergies.forEachIndexed { index, allergy ->
            key(allergy.id) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(
                            RoundedCornerShape(20.dp)
                        )
                        .background(MaterialTheme.colorScheme.onBackground)
                ) {
                    Text(
                        text = allergy.name,
                        color = MaterialTheme.colorScheme.onSecondary,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    IconButton(onClick = { onAllergyDeleteClick(allergy) }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            tint = Color.Black,
                            contentDescription = null
                        )
                    }
                }
            }
        }

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .focusRequester(focusRequester),
            value = text,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
            ),
            textStyle = TextStyle(
                fontSize = 20.sp
            ),
            singleLine = true,
            onValueChange = {
                text = it
                onSearch(it)
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {

                }
            )
        )
    }
}