package com.kyawzinlinn.codetest_codigo.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kyawzinlinn.codetest_codigo.data.local.DataSource
import com.kyawzinlinn.codetest_codigo.data.repository.DataRepository
import com.kyawzinlinn.codetest_codigo.domain.model.Allergy
import com.kyawzinlinn.codetest_codigo.domain.model.Diet
import com.kyawzinlinn.codetest_codigo.domain.model.HealthConcern
import com.kyawzinlinn.codetest_codigo.domain.model.Question
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DataViewModel (dataRepository: DataRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val healthConcerns = dataRepository.getHealthConcerns()
            val allergies = dataRepository.getAllergies()
            val diets = dataRepository.getDiets()

            CoroutineScope(Dispatchers.Main).launch{
                _uiState.update {
                    it.copy(
                        healthConcerns = healthConcerns,
                        allergies = allergies,
                        finalQuestions = DataSource.questions,
                        diets = diets
                    )
                }
            }
        }
    }

    fun addHealthConcern(healthConcern: HealthConcern) {
        _uiState.update {
            it.copy(
                selectedHealthConcerns = it.selectedHealthConcerns + healthConcern
            )
        }
    }

    fun updateHealthConcerns(healthConcerns: List<HealthConcern>) {
        _uiState.update {
            it.copy(
                selectedHealthConcerns = healthConcerns
            )
        }
    }

    fun reset() {
        _uiState.update {
            it.copy(
                selectedHealthConcerns = emptyList(),
                selectedDiets = emptyList(),
                selectedAllergies = emptyList()
            )
        }
    }

    fun removeHealthConcern(healthConcern: HealthConcern) {
        val selectedHealthConcerns = uiState.value.selectedHealthConcerns.toMutableList()
        selectedHealthConcerns.remove(healthConcern)
        _uiState.update {
            it.copy(selectedHealthConcerns = selectedHealthConcerns)
        }
    }

    fun addDiet (diet: Diet) {
        _uiState.update {
            it.copy(
                selectedDiets = it.selectedDiets + diet
            )
        }
    }

    fun removeDiet(diet: Diet) {
        val selectedDiets = uiState.value.selectedDiets.toMutableList()
        selectedDiets.remove(diet)
        _uiState.update {
            it.copy(selectedDiets = selectedDiets)
        }
    }

    fun addAllergy(allergy: Allergy) {
        val existed = allergy in _uiState.value.selectedAllergies
        _uiState.update {
            it.copy(
                selectedAllergies = if (!existed) it.selectedAllergies + allergy else it.selectedAllergies
            )
        }
    }

    fun removeAllergy(allergy: Allergy) {
        val selectedAllergies = uiState.value.selectedAllergies.toMutableList()
        selectedAllergies.remove(allergy)
        _uiState.update {
            it.copy(selectedAllergies = selectedAllergies)
        }
    }

    fun printData() {
        val data = _uiState.value
        println("***********************************************************")
        println("Health Concerns : ${data.selectedHealthConcerns.map { it.name }}")
        println("-----------------------------------------------------------")
        println("Diets : ${data.selectedDiets.map { it.name }}")
        println("-----------------------------------------------------------")
        println("Allergies : ${data.selectedAllergies.map { it.name }}")
        println("-----------------------------------------------------------")
        data.finalQuestions.map {
            println("Question - ${it.question}")
            println("Answer - ${it.options.get(it.answerIndex)}")
        }
    }

    fun updateBottomProgressBarStatus(showBottomProgressBar: Boolean){
        _uiState.update {
            it.copy(showBottomProgressBar = showBottomProgressBar)
        }
    }

    fun updateProgress(progress: Float) {
        _uiState.update {
            it.copy(progress = progress)
        }
    }

    fun updateQuestion(question: Question) {
        val updatedQuestions = _uiState.value.finalQuestions.map {
            if (it.id == question.id) {
                it.copy(answerIndex = question.answerIndex)
            } else it
        }
        _uiState.update {
            it.copy(
                finalQuestions = updatedQuestions
            )
        }
    }

    fun resetDiets() {
        _uiState.update {
            it.copy(selectedDiets = emptyList())
        }
    }
}

data class UiState(
    val healthConcerns: List<HealthConcern> = listOf(),
    val diets: List<Diet> = listOf(),
    val allergies : List<Allergy> = listOf(),
    val finalQuestions: List<Question> = listOf(),
    val selectedHealthConcerns: List<HealthConcern> = listOf(),
    val selectedDiets: List<Diet> = listOf(),
    val selectedAllergies: List<Allergy> = listOf(),
    val showBottomProgressBar: Boolean = false,
    val progress: Float = 0f
)