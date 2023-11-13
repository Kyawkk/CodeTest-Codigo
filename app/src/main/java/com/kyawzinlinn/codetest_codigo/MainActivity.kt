package com.kyawzinlinn.codetest_codigo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kyawzinlinn.codetest_codigo.data.local.DIETS_JSON
import com.kyawzinlinn.codetest_codigo.domain.model.Diet
import com.kyawzinlinn.codetest_codigo.ui.theme.CodeTestCodigoTheme
import com.kyawzinlinn.codetest_codigo.data.local.JsonDataSource
import com.kyawzinlinn.codetest_codigo.screen.AppContent
import com.kyawzinlinn.codetest_codigo.screen.DataViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CodeTestCodigoTheme {
                val viewModel : DataViewModel = viewModel(factory = ViewModelProvider.Factory)
                val uiState by viewModel.uiState.collectAsState()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column (modifier = Modifier.fillMaxSize()) {
                        AppContent(viewModel)
                    }
                }
            }
        }
    }
}
