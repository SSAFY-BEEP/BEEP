package com.example.beep.domain

import com.example.beep.data.repository.PresetRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PresetUseCase @Inject constructor(private val presetRepository: PresetRepository) {
    fun getUserPreset(uid: Long, part : Int) = presetRepository.getUserPreset(uid, part)
    fun getUserPresetByToken(part : Int) = presetRepository.getUserPresetByToken(part)
    fun updatePreset(number: Int, part: Int, content: String) =
        presetRepository.updatePreset(number, part, content)

    fun deletePreset(pid: Long) = presetRepository.deletePreset(pid)
}