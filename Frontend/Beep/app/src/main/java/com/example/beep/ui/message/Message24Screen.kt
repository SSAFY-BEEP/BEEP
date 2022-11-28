package com.example.beep.ui.message

import android.media.AudioAttributes
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.beep.data.dto.message.Message24Response
import com.example.beep.ui.base.ErrorScreen
import com.example.beep.ui.base.LoadingScreen
import com.example.beep.ui.home.galmurinineFont
import com.example.beep.ui.savedmessage.AudioBtn
import com.example.beep.ui.savedmessage.MessageOptions
import com.example.beep.ui.savedmessage.SwitchReceivedSent
import com.example.beep.ui.theme.PINK500
import com.example.beep.util.S3_CONSTANT_URI
import com.example.beep.util.S3_REDIS_URI
import com.example.beep.util.VoicePlayer

@Composable
fun MessageScreen(
    viewModel: MessageViewModel = viewModel(),
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onClickMenu: (String) -> Unit
) {

    Log.d("MessageType", viewModel.msg24State.receiveSendState.name)
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (val currentUiState = viewModel.msg24State.resultState) {
            ResultState.Loading -> {
                LoadingScreen()
            }
            ResultState.Success -> {
                MessageSuccessScreen(
                    messageList = viewModel.msg24State.msg24List,
                    viewModel = viewModel,
                    onClickMenu = onClickMenu
                )
            }
            ResultState.Error -> {
                ErrorScreen()
            }
        }
    }
}

@Composable
fun MessageSuccessScreen(
    messageList: List<Message24Response>,
    modifier: Modifier = Modifier,
    viewModel: MessageViewModel,
    onClickMenu: (String) -> Unit
) {

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        SwitchReceivedSent(
            currentMenu = viewModel.msg24State.receiveSendState,
            selectReceived = { viewModel.changeCurrentSavedMessageType(ReceiveSendState.Receive) },
            selectSent = { viewModel.changeCurrentSavedMessageType(ReceiveSendState.Send) },
            onClickMenu = onClickMenu
        )
        Column(modifier = Modifier.weight(4f)) {
            MessageList(
                modifier = Modifier,
                currentMenu = viewModel.msg24State.receiveSendState,
                messageList = messageList,
                onDelete = { message: Message24Response ->
                    viewModel.msg24State = viewModel.msg24State.copy(messageToModify = message)
                    viewModel.toggleConfirmAlert(MessagePopupState.DELETE)
                },
                onSave = { message: Message24Response ->
                    viewModel.msg24State = viewModel.msg24State.copy(messageToModify = message)
                    viewModel.toggleConfirmAlert(MessagePopupState.SAVE)
                },
                onBlock = { message: Message24Response ->
                    viewModel.msg24State = viewModel.msg24State.copy(messageToModify = message)
                    viewModel.toggleConfirmAlert(MessagePopupState.BLOCK)
                }
            )
        }
    }
    ConfirmDialog(
        show = viewModel.msg24State.popupState != MessagePopupState.NORMAL,
        onConfirmDelete = { viewModel.deleteMsg24() },
        onConfirmSave = { viewModel.saveMsg24() },
        onConfirmBlock = { viewModel.blockMsg24() })
}

@Composable
fun ConfirmDialog(
    show: Boolean,
    onConfirmDelete: () -> Unit,
    onConfirmSave: () -> Unit,
    onConfirmBlock: () -> Unit,
    viewModel: MessageViewModel = viewModel()
) {
    if (show) {
        when (viewModel.msg24State.popupState) {
            MessagePopupState.DELETE ->
                AlertDialog(
                    onDismissRequest = { viewModel.toggleConfirmAlert(MessagePopupState.NORMAL) },
                    title = {
                        Text(text = "삭제하시겠습니까?")
                    },
                    text = {
                        Text(text = "메시지가 삭제됩니다.")
                    },
                    confirmButton = {
                        TextButton(onClick = onConfirmDelete) {
                            Text("확인")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { viewModel.toggleConfirmAlert(MessagePopupState.NORMAL) }) {
                            Text("취소")
                        }
                    }
                )
            MessagePopupState.SAVE ->
                AlertDialog(
                    onDismissRequest = { viewModel.toggleConfirmAlert(MessagePopupState.NORMAL) },
                    title = {
                        Text(
                            text = "저장하시겠습니까?"
                        )
                    },
                    text = {
                        Text(
                            text = "메시지가 영구 보관 됩니다."
                        )
                    },
                    confirmButton = {
                        TextButton(onClick = onConfirmSave) {
                            Text("확인")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { viewModel.toggleConfirmAlert(MessagePopupState.NORMAL) }) {
                            Text("취소")
                        }
                    }
                )
            MessagePopupState.BLOCK ->
                AlertDialog(
                    onDismissRequest = { viewModel.toggleConfirmAlert(MessagePopupState.NORMAL) },
                    title = {
                        Text(
                            text = "차단하시겠습니까?"
                        )
                    },
                    text = {
                        Text(
                            text = "상대에게서 메시지를 받을 수 없습니다."
                        )
                    },
                    confirmButton = {
                        TextButton(onClick = onConfirmBlock) {
                            Text("확인")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { viewModel.toggleConfirmAlert(MessagePopupState.NORMAL) }) {
                            Text("취소")
                        }
                    }
                )
            MessagePopupState.DUPLICATE -> {
                Toast.makeText(LocalContext.current, "이미 보관된 메시지입니다", Toast.LENGTH_LONG).show()
            }
        }
    }
}

@Composable
fun MessageList(
    modifier: Modifier = Modifier,
    currentMenu: ReceiveSendState,
    messageList: List<Message24Response>,
    onDelete: (Message24Response) -> Unit,
    onSave: (Message24Response) -> Unit,
    onBlock: (Message24Response) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(messageList) {
            MessageItem(
                message = it,
                modifier = modifier,
                currentMenu = currentMenu,
                onDelete = onDelete,
                onSave = onSave,
                onBlock = onBlock
            )
        }
    }
}

@Composable
fun MessageItem(
    message: Message24Response,
    modifier: Modifier = Modifier,
    currentMenu: ReceiveSendState,
    onDelete: (Message24Response) -> Unit,
    onSave: (Message24Response) -> Unit,
    onBlock: (Message24Response) -> Unit,
    viewModel: MessageViewModel = viewModel()
) {
    var expanded by remember { mutableStateOf(false) }
    Card(elevation = 4.dp, modifier = modifier.padding(8.dp)) {
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
        ) {
            Row(
                modifier = modifier
                    .padding(8.dp)
                    .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    if (message.audioUri != null) {
                        Log.d("DataSource", "$S3_REDIS_URI${message.audioUri}")
                        Message24AudioBtn(
                            enabled = message.audioUri != null,
                            onPlay = {
                                stopMessage24Audio()
                                viewModel.msg24State = viewModel.msg24State.copy(
                                    messageAudioState = MessageAudioState(
                                        isPlaying = false,
                                        message = null
                                    )
                                )
                            },
                            onStop = {
                                playMessage24Audio(
                                    message,
                                    onPrepared = {
                                        viewModel.msg24State = viewModel.msg24State.copy(
                                            messageAudioState = MessageAudioState(
                                                isPlaying = true,
                                                message = message
                                            )
                                        )
                                    },
                                    onComplete = {
                                        viewModel.msg24State = viewModel.msg24State.copy(
                                            messageAudioState = MessageAudioState(
                                                isPlaying = false,
                                                message = null
                                            )
                                        )
                                    })
                            },
                            isPlaying = viewModel.msg24State.messageAudioState.isPlaying && viewModel.msg24State.messageAudioState.message?.id == message.id
                        )
                    }
                }

                MessageInfo(
                    modifier = modifier.weight(10f),
                    content = message.content,
                    localDateTime = message.time
                )
                ExpandButton(
                    expanded = expanded,
                    onClick = { expanded = !expanded },
                    modifier = modifier.weight(2f)
                )
            }
            if (expanded) {
                MessageOptions(
                    currentMenu = currentMenu,
                    onDelete = { onDelete(message) },
                    onSave = { onSave(message) },
                    onBlock = { onBlock(message) },
                    duplicate = message.type == 1
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwitchReceivedSent(
    currentMenu: ReceiveSendState,
    selectReceived: () -> Unit,
    selectSent: () -> Unit,
    onClickMenu: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowRight


    var receivedBtnBgColor = if (currentMenu == ReceiveSendState.Receive) {
        Color(android.graphics.Color.parseColor("#7AA8FF"))
    } else {
        Color(android.graphics.Color.parseColor("#FFFFFF"))
    }
    var receivedBtnTxtColor = if (currentMenu == ReceiveSendState.Receive) {
        Color(android.graphics.Color.parseColor("#FFFFFF"))
    } else {
        Color(android.graphics.Color.parseColor("#7AA8FF"))
    }
    var sentBtnBgColor = if (currentMenu == ReceiveSendState.Send) {
        Color(android.graphics.Color.parseColor("#7AA8FF"))
    } else {
        Color(android.graphics.Color.parseColor("#FFFFFF"))
    }
    var sentBtnTxtColor = if (currentMenu == ReceiveSendState.Send) {
        Color(android.graphics.Color.parseColor("#FFFFFF"))
    } else {
        Color(android.graphics.Color.parseColor("#7AA8FF"))
    }


    Row(
        modifier = Modifier
            .padding(10.dp)
            .height(50.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ExposedDropdownMenuBox(
            modifier = Modifier
                .width(200.dp)
                .height(50.dp),
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberVectorPainter(image = icon),
                    contentDescription = "24시간 메시지"
                )
                Text(
                    text = " 24시간 메시지",
                )
            }
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                Column() {
                    DropdownMenuItem(
                        onClick = { onClickMenu("savedMessage") }
                    ) {
                        Text(text = "내 보관함")
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .offset(0.dp, 0.dp)
                .clip(shape = RoundedCornerShape(15.dp))
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
                .width(108.dp)
                .height(30.dp)
                .background(color = Color.White, shape = RoundedCornerShape(15.dp))
                .border(
                    width = 1.dp, color = Color(android.graphics.Color.parseColor("#7AA8FF")),
                    shape = RoundedCornerShape(15.dp)
                ),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
                    .wrapContentWidth(Alignment.CenterHorizontally)

            ) {
                Button(
                    onClick = selectReceived,
                    modifier = Modifier
                        .width(50.dp)
                        .height(24.dp),
                    elevation = ButtonDefaults.elevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 0.dp,
                        disabledElevation = 0.dp
                    ),
                    colors = ButtonDefaults.buttonColors(backgroundColor = receivedBtnBgColor),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(0.dp),

                    ) {
                    Text(
                        text = "수신",
                        Modifier.padding(0.dp),
                        color = receivedBtnTxtColor,
                        fontFamily = galmurinineFont
                    )
                }
                Button(
                    onClick = selectSent,
                    modifier = Modifier
                        .width(50.dp)
                        .height(24.dp),
                    elevation = ButtonDefaults.elevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 0.dp,
                        disabledElevation = 0.dp
                    ),
                    colors = ButtonDefaults.buttonColors(backgroundColor = sentBtnBgColor),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(0.dp),
                ) {
                    Text(
                        text = "송신",
                        modifier = Modifier
                            .padding(top = 0.dp),
                        color = sentBtnTxtColor,
                        fontFamily = galmurinineFont
                    )
                }
            }
//                Button(
//                    onClick = selectReceived,
//                    colors =
//                    if (currentMenu == ReceiveSendState.Receive) ButtonDefaults.buttonColors(
//                        backgroundColor = MaterialTheme.colors.primary
//                    )
//                    else ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
//                ) {
//                    Text(
//                        text = "수신",
//                        color = if (currentMenu == ReceiveSendState.Receive) Color.Black else Color.White
//                    )
//                }
//                Button(
//                    onClick = selectSent, colors =
//                    if (currentMenu == ReceiveSendState.Send) ButtonDefaults.buttonColors(
//                        backgroundColor = MaterialTheme.colors.primary
//                    )
//                    else ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
//                ) {
//                    Text(
//                        text = "송신",
//                        color = if (currentMenu == ReceiveSendState.Send) Color.Black else Color.White
//                    )
//                }
        }
    }
}


@Composable
fun MessageInfo(
    modifier: Modifier = Modifier,
    content: String,
    localDateTime: String
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = content, fontSize = 17.sp)
        Text(text = localDateTime.substring(0, 10), fontSize = 12.sp)
    }
}

@Composable
fun Message24AudioBtn(
    enabled: Boolean,
    onPlay: () -> Unit,
    onStop: () -> Unit,
    isPlaying: Boolean
) {
    IconButton(enabled = enabled, onClick = { if (isPlaying) onPlay() else onStop() }) {
        val color = if (isPlaying) Color.Green else Color.Black
        Icon(
            imageVector = Icons.Filled.Mic,
            tint = color,
            contentDescription = "message audio button",
        )
    }
}

@Composable
fun MessageOptions(
    currentMenu: ReceiveSendState,
    onDelete: () -> Unit,
    onSave: () -> Unit,
    onBlock: () -> Unit,
    duplicate: Boolean
) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
//        IconButton(onClick = { /*TODO*/ }) {
//            Icon(
//                imageVector = Icons.Filled.Share,
//                tint = MaterialTheme.colors.secondary,
//                contentDescription = "message share button",
//            )
//        }
        IconButton(onClick = onSave) {
            Icon(
                imageVector = Icons.Filled.BookmarkAdd,
                tint = if (duplicate) PINK500 else MaterialTheme.colors.secondary,
                contentDescription = "message save button",
            )
        }
        IconButton(onClick = onDelete) {
            Icon(
                imageVector = Icons.Filled.Delete,
                tint = MaterialTheme.colors.secondary,
                contentDescription = "message delete button",
            )
        }
        if (currentMenu == ReceiveSendState.Receive) {
            IconButton(onClick = onBlock) {
                Icon(
                    imageVector = Icons.Filled.Block,
                    tint = MaterialTheme.colors.secondary,
                    contentDescription = "sender block button",
                )
            }
        }
    }
}


@Composable
fun ExpandButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            tint = MaterialTheme.colors.secondary,
            contentDescription = "message options button",
        )
    }
}

fun playMessage24Audio(message: Message24Response, onPrepared: () -> Unit, onComplete: () -> Unit) {
    VoicePlayer.nullInstance()
    VoicePlayer.getInstance().apply {
        setAudioAttributes(
            AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build()
        )
        setDataSource(S3_REDIS_URI + message.audioUri)
        setOnPreparedListener {
            onPrepared()
            it.start()
            Log.d("VoicePlayer", "Duration : ${it.duration}")
        }
        prepareAsync()
        setOnCompletionListener {
            if (it.duration != 0)
                if (this.isPlaying)
                    it.stop()
            it.release()
            onComplete()
        }
    }
}

fun stopMessage24Audio() {
    try {
        VoicePlayer.getInstance().apply {
            if (this.isPlaying)
                stop()
            release()
        }
    } catch (e: Exception) {
        Log.e(
            "VoicePlayer",
            "stopMessage24Audio",
            e
        )
    }

}