package com.example.beep.ui.mypage.block

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beep.data.dto.message.MessageResponse
import com.example.beep.domain.MessageUseCase
import com.example.beep.util.ResultType
import com.example.beep.util.fromJson
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BlockViewModel @Inject constructor(private val messageUseCase: MessageUseCase) : ViewModel() {
    var blockScreenState by mutableStateOf(BlockScreenState())
    val gson = Gson()

    fun onEvent(event: BlockEvent) {
        when (event) {
            is BlockEvent.AskCancelBlock -> {
                blockScreenState = blockScreenState.copy(
                    showCancelBlockPopup = true,
                    messageToHandle = event.message
                )
            }
            is BlockEvent.CancelBlock -> {
                deleteBlockMessage()
            }
            is BlockEvent.DismissCancelBlock -> {
                blockScreenState = blockScreenState.copy(
                    showCancelBlockPopup = false,
                    messageToHandle = null
                )
            }
            is BlockEvent.AskChangeTag -> {
                blockScreenState = blockScreenState.copy(
                    showChangeTagPopup = true,
                    messageToHandle = event.message
                )
            }
            is BlockEvent.ChangeTag -> {
                changeTag(event.newTag)
            }
            is BlockEvent.DismissChangeTag -> {
                blockScreenState = blockScreenState.copy(
                    showChangeTagPopup = false,
                    messageToHandle = null
                )
            }
        }
    }

    private fun changeTag(newTag: String) {
        viewModelScope.launch {
            blockScreenState = blockScreenState.copy(currentState = BlockScreenResult.Loading)
            val result = messageUseCase.changeTag(blockScreenState.messageToHandle!!.id, newTag)
            when (result) {
                is ResultType.Success -> {
                    blockScreenState = blockScreenState.copy(
                        showCancelBlockPopup = false,
                        messageToHandle = null
                    )
                    getBlockMessageList()
                }
                is ResultType.Loading -> {
                    blockScreenState =
                        blockScreenState.copy(currentState = BlockScreenResult.Loading)
                }
                else -> {
                    blockScreenState = BlockScreenState(BlockScreenResult.Fail)
                }
            }
        }
    }

    private fun getBlockMessageList() {
        viewModelScope.launch {
            blockScreenState = blockScreenState.copy(currentState = BlockScreenResult.Loading)
            val result = messageUseCase.getReceive(2)
            blockScreenState = when (result) {
                is ResultType.Success -> {
                    BlockScreenState(
                        currentState = BlockScreenResult.Success,
                        blockList = gson.fromJson<List<MessageResponse>>(gson.toJson(result.data.data)),
                    )
                }
                is ResultType.Loading -> {
                    blockScreenState.copy(
                        currentState = BlockScreenResult.Loading
                    )
                }
                else -> {
                    blockScreenState.copy(
                        currentState = BlockScreenResult.Fail
                    )
                }
            }
        }
    }

    private fun deleteBlockMessage() {
        viewModelScope.launch {
            blockScreenState = blockScreenState.copy(currentState = BlockScreenResult.Loading)
            try {
                var result = messageUseCase.deleteMessage(blockScreenState.messageToHandle!!.id)
                when (result) {
                    is ResultType.Success -> {
                        Log.d("DeleteBlockMessage", "Success")
                        blockScreenState = blockScreenState.copy(
                            showCancelBlockPopup = false,
                            messageToHandle = null
                        )
                        getBlockMessageList()
                    }
                    is ResultType.Loading -> {
                        blockScreenState.copy(
                            currentState = BlockScreenResult.Loading
                        )
                    }
                    else -> {
                        blockScreenState = BlockScreenState(
                            currentState = BlockScreenResult.Fail,
                            showCancelBlockPopup = false
                        )
                    }
                }

            } catch (e: Exception) {
                Log.e("DeleteBlockMessage", "Error", e)
                blockScreenState =
                    BlockScreenState(
                        currentState = BlockScreenResult.Fail,
                        showCancelBlockPopup = false
                    )
            }
        }
    }

    init {
        getBlockMessageList()
    }
}