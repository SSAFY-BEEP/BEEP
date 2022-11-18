package com.example.beep.domain

import com.example.beep.data.repository.DictionaryRepository
import javax.inject.Inject

class DictionaryUseCase @Inject constructor(private val dictionaryRepository: DictionaryRepository){
    fun getDictionary(word: String?) = dictionaryRepository.getDictionary(word)
}