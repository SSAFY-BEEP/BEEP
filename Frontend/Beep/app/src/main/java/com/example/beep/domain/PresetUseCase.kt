package com.example.beep.domain

import com.example.beep.data.dto.message.MessageResponse
import com.example.beep.data.dto.mypage.PresetResponse
import com.example.beep.data.repository.PresetRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserMessagePresetUseCase @Inject constructor(private val presetRepository: PresetRepository){
    fun execute(uid: Int) = presetRepository.getUserPreset(uid)
}