package com.example.beep.ui.home

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beep.data.dto.BaseResponse
import com.example.beep.data.dto.message.Message24Request
import com.example.beep.data.dto.message.Message24Response
import com.example.beep.data.dto.message.MessageResponse
import com.example.beep.domain.Message24UseCase
import com.example.beep.ui.message.RecordState
import com.example.beep.ui.message.ResultState
import com.example.beep.ui.message.UiState
import com.example.beep.ui.message.stopRecording
import com.example.beep.ui.savedmessage.SavedMessageType
import com.example.beep.util.ResultType
import com.example.beep.util.VoicePlayer
import com.example.beep.util.VoiceRecorder
import com.example.beep.util.fromJson
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileInputStream
import java.util.*
import javax.inject.Inject


enum class ReceivedMessageType {
    SEND, RECEIVED, BLOCKED
}

enum class RecordMessageState {
    Greeting, Before, Recording, Finished, Playing, Error, Loading
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val message24UseCase: Message24UseCase
) :
    ViewModel() {

    val gson = Gson()

    //24시간 후에 사라지는 일반 메시지 리스트
    val receiveMsg24: Flow<ResultType<BaseResponse<List<Message24Response>>>> = message24UseCase.getReceive24()
    val sendMsg24: Flow<ResultType<BaseResponse<List<Message24Response>>>> = message24UseCase.getSend24()
    var receivedMessageUiState: UiState<List<Message24Response>> by mutableStateOf(UiState.Loading)
    var currentReceivedMessageType by mutableStateOf(SavedMessageType.RECEIVED)
    var messageToSend: Message24Request by mutableStateOf(Message24Request())
    var recordMessageState by mutableStateOf(RecordMessageState.Greeting)
    var currentPage by mutableStateOf("ReceivedMsg")
    var opponentGreetingUri: String? by mutableStateOf(null)
    var timerTask: Timer? = null
    var time by mutableStateOf(0)
    var isRunning by mutableStateOf(false)
    var fileLength by mutableStateOf(0)

    fun getOne24() {
        receivedMessageUiState = UiState.Loading
        viewModelScope.launch() {
            message24UseCase.getReceive24().collectLatest {
                if (it is ResultType.Success) {
                    val list = gson.fromJson<List<Message24Response>>(gson.toJson(it.data.data))
                    receivedMessageUiState = UiState.Success(list)
                } else {
                    UiState.Error
                }
            }
        }
    }

    fun sendMsg(filepath: String) {
        Log.d("Send REQUEST", "$filepath $messageToSend")
        viewModelScope.launch(Dispatchers.IO) {
            val file = File(filepath)
            val partFile: MultipartBody.Part? = if (file.exists()) {
                val fis = FileInputStream(file)
                val byteArray = fis.readBytes()
                Log.d("SENDMSG", byteArray.size.toString())
                MultipartBody.Part.createFormData(
                    "file",
                    "${System.currentTimeMillis()}record.mp3",
                    byteArray.toRequestBody(contentType = "multipart/form-data".toMediaTypeOrNull())
                )
            } else null

            if (partFile == null)
                Log.d("SENDMSG", "Partfile is null")

            message24UseCase.sendMsg(partFile, messageToSend).collectLatest {
                if (it is ResultType.Success) {
                    Log.d("Send Message", it.data.toString())
                    if (file.exists())
                        file.delete()
                } else {
                    Log.d("Send Message", "Fail!!")
                }
                currentPage = "ReceivedMsg"
            }
        }
    }

    fun saveMessage(messageId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            message24UseCase.saveMsg(messageId).collectLatest {
                if (it is ResultType.Success) {
                    Log.d("Save Message24", it.data.toString())
                } else {
                    Log.d("Save Message24", "Fail!!")
                }
            }
        }
    }

    fun deleteMsg24(messageId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            message24UseCase.deleteMsg(messageId).collectLatest {
                if (it is ResultType.Success) {
                    Log.d("Delete Message24", it.data.toString())
                } else {
                    Log.d("Delete Message24", "Fail!!")
                }
            }
        }
    }

    fun blockMsg24(messageId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            message24UseCase.blockMsg(messageId).collectLatest {
                if (it is ResultType.Success) {
                    Log.d("Block Message24", it.data.toString())
                } else {
                    Log.d("Block Message24", "Fail!!")
                }
            }
        }
    }

    fun resetMessageToSend() {
        messageToSend = Message24Request()
    }

    fun setMessageContent(content: String) {
        messageToSend = messageToSend.copy(content = content)
    }

    fun setMessageReceiverNum(receiverNum: String) {
        messageToSend = messageToSend.copy(receiverNum = receiverNum)
    }

    fun stopGreeting() {
        try {
            VoicePlayer.getInstance().apply {
                if (this.isPlaying)
                    stop()
                release()
            }
        } catch (e: Exception) {
            Log.e(
                "VoicePlayer",
                "stopGreeting",
                e
            )
        }
    }

    fun playGreeting() {
        /* API에서 상대 인사말 주소를 가져온 뒤 null이 아니라면 MediaPlayer로 재생,
            null이면 상태를 바로 Before로 바꾸기 */
        /* onComplete -> stopGreeting() && 상태 Before로 바꾸기 */
    }

    @RequiresApi(Build.VERSION_CODES.S)
    fun startTimer() {
        isRunning = true
        timerTask = kotlin.concurrent.timer(period = 1000) {
            time++
            if (time >= fileLength) {
                stopTimer()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    fun stopTimer() {
        timerTask?.cancel()

        time = 0
        isRunning = false
        if (recordMessageState == RecordMessageState.Recording) {
            VoiceRecorder.getInstanceWithoutContext()?.run {
                stop()
                release()
            }
            recordMessageState = RecordMessageState.Finished
        } else if (recordMessageState == RecordMessageState.Playing) {
            VoicePlayer.getInstance().run {
                stop()
                release()
            }
            recordMessageState = RecordMessageState.Finished
        }
    }

    init {
        getOne24()
    }
}