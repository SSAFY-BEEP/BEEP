package com.example.beep.ui.mypage

import androidx.lifecycle.ViewModel
import com.example.beep.domain.PresetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContactPresetViewModel @Inject constructor(
    private val presetUseCase: PresetUseCase
) : ViewModel(){

    private fun getPresetByToken(){

    }

}