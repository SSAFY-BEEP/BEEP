package com.example.beep.domain

import com.example.beep.data.repository.PresetRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PresetUseCase @Inject constructor(private val presetRepository: PresetRepository){
    fun getUserPreset(uid: Long) = presetRepository.getUserPreset(uid)
    fun getUserPresetByToken() = presetRepository.getUserPresetByToken()
    fun updatePreset(uid : Long, number : Int, part : Int, content : String) = presetRepository.updatePreset(uid, number, part, content)
    fun deletePreset(pid: Long) = presetRepository.deletePreset(pid)
}