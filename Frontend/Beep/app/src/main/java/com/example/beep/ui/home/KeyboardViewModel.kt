package com.example.beep.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


class KeyboardViewModel: ViewModel() {

    var state by mutableStateOf(KeyboardState())





    fun onAction(action: KeyboardAction) {
        when(action) {
            is KeyboardAction.Number -> enterNumber(action.number)
            is KeyboardAction.Change -> enterChange(action.number)
            is KeyboardAction.Delete -> delete()
        }
    }


    private fun delete() {
        if(state.number1.isNotBlank()) {
            state = state.copy(
                number1 = state.number1.dropLast(1)
            )
        }
    }

    private fun enterChange(number: String) {
        state = state.copy(
            number1 = state.number1.dropLast(1) + number
        )
        return
    }

    private fun enterNumber(number: String) {

    if(state.number1.length >= MAX_NUM_LENGTH) {
        return
    }
    state = state.copy(
        number1 = state.number1 + number
    )
    return
    }
    companion object {
        private const val MAX_NUM_LENGTH = 11
    }
}