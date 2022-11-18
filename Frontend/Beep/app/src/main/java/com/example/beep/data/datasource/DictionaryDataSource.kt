package com.example.beep.data.datasource

import com.example.beep.data.dto.BaseResponse
import com.example.beep.data.dto.dictionary.DictionaryResponse
import com.example.beep.network.api.DictionaryApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DictionaryDataSource @Inject constructor(private val dictionaryApi: DictionaryApi){
    fun getDictionary(word: String?) : Flow<BaseResponse<List<DictionaryResponse>>> = flow {
        emit(dictionaryApi.getDictionaryByWord(word))
    }
}