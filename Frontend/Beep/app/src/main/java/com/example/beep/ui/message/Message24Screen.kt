package com.example.beep.ui.message

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.beep.data.dto.message.Message24Response
import com.example.beep.ui.base.ErrorScreen
import com.example.beep.ui.base.LoadingScreen

@Composable
fun MessageScreen(
    viewModel: MessageViewModel = viewModel(),
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
//    navigateTo: (String) -> Unit,
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
                MessageSuccessScreen(messageList = viewModel.msg24State.msg24List, viewModel = viewModel)
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
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        SwitchReceivedSent(
            currentMenu = viewModel.msg24State.receiveSendState,
            selectReceived = { viewModel.changeCurrentSavedMessageType(ReceiveSendState.Receive) },
            selectSent = { viewModel.changeCurrentSavedMessageType(ReceiveSendState.Send) },
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
            modifier = Modifier.animateContentSize(
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
                AudioBtn(
                    enabled = message.audioUri != null,
                    onPlay = { viewModel.playSavedMessageAudio(message) },
                    onStop = { viewModel.stopSavedMessageAudio() },
                    isPlaying = viewModel.msg24State.messageAudioState.isPlaying
                            && viewModel.msg24State.messageAudioState.message?.id == message.id
                )
                MessageInfo(
                    modifier = modifier.weight(1f),
                    content = message.content,
                    localDateTime = message.time
                )
                ExpandButton(
                    expanded = expanded,
                    onClick = { expanded = !expanded },
                    modifier = modifier.weight(1f)
                )
            }
            if (expanded) {
                MessageOptions(
                    currentMenu = currentMenu,
                    onDelete = { onDelete(message) },
                    onSave = { onSave(message) },
                    onBlock = { onBlock(message) })
            }
        }
    }
}

@Composable
fun SwitchReceivedSent(
    currentMenu: ReceiveSendState,
    selectReceived: () -> Unit,
    selectSent: () -> Unit,
) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Button(
            onClick = selectReceived,
            colors =
            if (currentMenu == ReceiveSendState.Receive) ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primary
            )
            else ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
        ) {
            Text(text = "수신")
        }
        Button(
            onClick = selectSent, colors =
            if (currentMenu == ReceiveSendState.Send) ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primary
            )
            else ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
        ) {
            Text(text = "송신")
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
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Text(text = content, fontSize = 24.sp)
        Text(text = localDateTime.substring(0,10))
    }
}

@Composable
fun AudioBtn(enabled: Boolean, onPlay: () -> Unit, onStop: () -> Unit, isPlaying: Boolean) {
    IconButton(enabled = enabled, onClick = { if (isPlaying) onPlay() else onStop() }) {
        Icon(
            imageVector = Icons.Filled.Mic,
            tint = if (isPlaying) Color.Green else Color.Black,
            contentDescription = "message audio button",
        )
    }
}

@Composable
fun MessageOptions(
    currentMenu: ReceiveSendState,
    onDelete: () -> Unit,
    onSave: () -> Unit,
    onBlock: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
//        IconButton(onClick = { /*TODO*/ }) {
//            Icon(
//                imageVector = Icons.Filled.Share,
//                tint = MaterialTheme.colors.secondary,
//                contentDescription = "message share button",
//            )
//        }
        IconButton(onClick =  onSave ) {
            Icon(
                imageVector = Icons.Filled.BookmarkAdd,
                tint = MaterialTheme.colors.secondary,
                contentDescription = "message save button",
            )
        }
        IconButton(onClick =  onDelete ) {
            Icon(
                imageVector = Icons.Filled.Delete,
                tint = MaterialTheme.colors.secondary,
                contentDescription = "message delete button",
            )
        }
        if (currentMenu == ReceiveSendState.Receive) {
            IconButton(onClick =  onBlock ) {
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