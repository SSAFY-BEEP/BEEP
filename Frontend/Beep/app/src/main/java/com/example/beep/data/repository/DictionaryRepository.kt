package com.example.beep.data.repository

import com.example.beep.data.datasource.DictionaryDataSource
import com.example.beep.data.dto.BaseResponse
import com.example.beep.data.dto.dictionary.DictionaryResponse
import com.example.beep.util.ResultType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DictionaryRepository @Inject constructor(private val dictionaryDataSource: DictionaryDataSource){
    fun getDictionary(word: String?): Flow<ResultType<BaseResponse<List<DictionaryResponse>>>> =
        flow {
            emit(ResultType.Loading)
            dictionaryDataSource.getDictionary(word).collect {
                if(it.status == "OK")
                    emit(ResultType.Success(it))
                else emit(ResultType.Fail(it))
            }
        }.catch { e -> emit(ResultType.Error(e)) }
}