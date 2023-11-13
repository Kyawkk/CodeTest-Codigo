package com.kyawzinlinn.codetest_codigo

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.kyawzinlinn.codetest_codigo.screen.DataViewModel

object ViewModelProvider {
    val Factory = viewModelFactory {
        initializer { DataViewModel((this[APPLICATION_KEY] as App).container.dataRepository) }
    }
}