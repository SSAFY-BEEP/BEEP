package com.example.beep.ui.home

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beep.data.dto.BaseResponse
import com.example.beep.data.dto.message.Message24Request
import com.example.beep.data.dto.message.Message24Response
import com.example.beep.domain.Message24UseCase
import com.example.beep.domain.S3UseCase
import com.example.beep.ui.message.*
import com.example.beep.ui.mypage.introduce.UiState
import com.example.beep.ui.savedmessage.SavedMessageType
import com.example.beep.util.*
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileInputStream
import java.util.*
import javax.inject.Inject


enum class RecordMessageState {
    Greeting, Before, Recording, Finished, Playing, Error, Loading, NoIntroduce
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val message24UseCase: Message24UseCase,
    private val s3UseCase: S3UseCase
) :
    ViewModel() {

    val gson = Gson()

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()


    //24시간 후에 사라지는 일반 메시지 리스트
    val receiveMsg24: Flow<ResultType<BaseResponse<List<Message24Response>>>> =
        message24UseCase.getReceive24()
    val sendMsg24: Flow<ResultType<BaseResponse<List<Message24Response>>>> =
        message24UseCase.getSend24()
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
                    Log.d("getOne24", "${list.isEmpty()}")
                    currentPage = if (list.isEmpty()) {
                        "PutAddress"
                    } else {
                        Log.d("getOne24", "List Not Empty!!")
                        "ReceivedMsg"
                    }
                    receivedMessageUiState = UiState.Success(list)
                } else if (it is ResultType.Loading) {
                    UiState.Loading
                }
                else {
                    UiState.Error
                }
            }
        }
    }

    fun sendMsg(filepath: String) {
        Log.d("Send REQUEST1", "$filepath $messageToSend")
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

            Log.d("Send REQUEST2", "$filepath $messageToSend")
            message24UseCase.sendMsg(partFile, messageToSend).collectLatest {
                when (it) {
                    is ResultType.Success -> {Log.d("Send Message", it.data.toString())
                        if (file.exists())
                            file.delete()
                        showToast("마음이 성공적으로 전달됐어요!")
                        resetMessageToSend()
                        getOne24()}
                    is ResultType.Loading -> {
                        UiState.Loading
                    }
                    else -> {
                        Log.d("Send Message", "Fail!!")
                        showToast("마음을 전송하지 못했습니다ㅠ")
                    }
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

    @RequiresApi(Build.VERSION_CODES.S)
    fun stopGreeting() {
        try {
            VoicePlayer.getInstance().apply {
                if (this.isPlaying)
                    stop()
                release()
            }
            stopTimer()
            recordMessageState = RecordMessageState.Before
        } catch (e: Exception) {
            Log.e(
                "VoicePlayer",
                "stopGreeting",
                e
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    fun playGreeting() {
        recordMessageState = RecordMessageState.Loading
        /* API에서 상대 인사말 주소를 가져온 뒤 null이 아니라면 MediaPlayer로 재생,
            null이면 상태를 바로 Before로 바꾸기 */
        viewModelScope.launch(Dispatchers.IO) {
            val result = s3UseCase.getIntroduceByPhone(messageToSend.receiverNum)
            opponentGreetingUri = when (result) {
                is ResultType.Success -> {
                    result.data
                }
                else -> {
                    null
                }
            }
            if (opponentGreetingUri != null) {
                recordMessageState = RecordMessageState.Greeting
                VoicePlayer.nullInstance()
                VoicePlayer.getInstance().apply {
                    setDataSource(S3_CONSTANT_URI + opponentGreetingUri)
                    prepare()
                    setOnPreparedListener {
                        fileLength = it.duration / 1000
                        startTimer()
                    }
                    setOnCompletionListener {
                        stopGreeting()
                        recordMessageState = RecordMessageState.Before
                    }
                }.start()
            } else {
                recordMessageState = RecordMessageState.NoIntroduce
                delay(1000)
                recordMessageState = RecordMessageState.Before
            }
        }
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

    fun checkAddress(address: String): Boolean {
        if (address.length != 11) return false
        if (!address.isDigitsOnly()) return false
        if (address.substring(0, 3) != "010") return false
        return true
    }

    fun showToast(message: String) {
        viewModelScope.launch {
            _toastMessage.emit(message)
        }
    }

    init {
        getOne24()
    }
}