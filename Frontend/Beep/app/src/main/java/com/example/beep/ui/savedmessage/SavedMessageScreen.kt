package com.example.beep.ui.savedmessage

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.beep.data.dto.message.MessageResponse
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.beep.ui.base.ErrorScreen
import com.example.beep.ui.base.LoadingScreen
import com.example.beep.ui.mypage.introduce.UiState
import com.example.beep.util.S3_CONSTANT_URI
import com.example.beep.util.VoicePlayer

@Composable
fun SavedMessageScreen(
    viewModel: SavedMessageViewModel = viewModel(),
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    navigateTo: (String) -> Unit
) {
    DisposableEffect(key1 = lifecycleOwner) {
        Log.d("DisposableEvent", "DisposableEvent")
        onDispose {
            Log.d("onDispose", "onDispose")
            if (VoicePlayer.hasInstance()) {
                VoicePlayer.getInstance().release()
            }
            VoicePlayer.nullInstance()
        }
    }
    Log.d("MessageType", viewModel.currentSavedMessageType.name)
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (val currentUiState = viewModel.savedMessageUiState) {
            is UiState.Loading -> {
                LoadingScreen()
            }
            is UiState.Success -> {
                SavedMessageSuccessScreen(messageList = currentUiState.data, onClickMenu = navigateTo)
            }
            is UiState.Error -> {
                ErrorScreen()
            }
        }
    }
}

@Composable
fun SavedMessageSuccessScreen(
    messageList: List<MessageResponse>,
    modifier: Modifier = Modifier,
    viewModel: SavedMessageViewModel = viewModel(),
    onClickMenu: (String) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        SwitchReceivedSent(
            currentMenu = viewModel.currentSavedMessageType,
            selectReceived = { viewModel.changeCurrentSavedMessageType(SavedMessageType.RECEIVED) },
            selectSent = { viewModel.changeCurrentSavedMessageType(SavedMessageType.SEND) },
            selectBlocked = { viewModel.changeCurrentSavedMessageType(SavedMessageType.BLOCKED) },
            onClickMenu = onClickMenu
        )
        if (messageList.isEmpty())
            Box(modifier = modifier.weight(1f), contentAlignment = Alignment.Center) {
                Text(text = "메시지가 없습니다.")
            }
        else
            Column(modifier = Modifier.weight(4f)) {
                MessageList(
                    modifier = Modifier,
                    currentMenu = viewModel.currentSavedMessageType,
                    messageList = messageList,
                    onDelete = { message: MessageResponse ->
                        viewModel.messageToModify = message
                        viewModel.toggleConfirmDeleteAlert()
                    },
                    onChangeTag = { message: MessageResponse ->
                        viewModel.messageToModify = message
                        viewModel.toggleModifyTagAlert()
                    },
                    onBlock = { message: MessageResponse ->
                        viewModel.messageToModify = message
                        viewModel.toggleBlockAlert()
                    })
            }
    }
    DeleteBlockDialog(
        show = viewModel.showDeleteDialog || viewModel.showBlockDialog,
        onConfirmDelete = { viewModel.deleteMessage() },
        onConfirmBlock = { viewModel.blockMessage() })
    TagDialog(
        show = viewModel.showModifyDialog,
        onConfirmModify = { tag: String -> viewModel.changeTag(tag) },
        previousTag = viewModel.messageToModify?.tag
    )
}


@Composable
fun DeleteBlockDialog(
    show: Boolean,
    onConfirmDelete: () -> Unit,
    onConfirmBlock: () -> Unit,
    viewModel: SavedMessageViewModel = viewModel()
) {
    if (show) {
        if (viewModel.showDeleteDialog)
            AlertDialog(
                onDismissRequest = { viewModel.toggleConfirmDeleteAlert() },
                title = {
                    Text(text = "삭제하시겠습니까?")
                },
                text = {
                    Text(text = "메시지가 삭제됩니다.")
                },
                confirmButton = {
                    TextButton(onClick = {
                        onConfirmDelete()
                        viewModel.toggleConfirmDeleteAlert()
                    }) {
                        Text("확인")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { viewModel.toggleConfirmDeleteAlert() }) {
                        Text("취소")
                    }
                }
            )
        else
            if (viewModel.currentSavedMessageType == SavedMessageType.RECEIVED)
                AlertDialog(
                    onDismissRequest = { viewModel.toggleConfirmDeleteAlert() },
                    title = {
                        Text(
                            text = "차단하시겠습니까?"
                        )
                    },
                    text = {
                        Text(
                            text = "차단한 사람으로부터 메시지를 받을 수 없습니다."
                        )
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            onConfirmBlock()
                            viewModel.toggleBlockAlert()
                        }) {
                            Text("확인")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { viewModel.toggleBlockAlert() }) {
                            Text("취소")
                        }
                    }
                )
            else
                AlertDialog(
                    onDismissRequest = { viewModel.toggleConfirmDeleteAlert() },
                    title = {
                        Text(text = "차단 해제하시겠습니까?")
                    },
                    text = {
                        Text(text = "이 유저로부터 다시 메시지를 받을 수 있습니다.")
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            onConfirmBlock()
                            viewModel.toggleBlockAlert()
                        }) {
                            Text("확인")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { viewModel.toggleBlockAlert() }) {
                            Text("취소")
                        }
                    }
                )
    }
}

@Composable
fun TagDialog(
    show: Boolean,
    onConfirmModify: (String) -> Unit,
    previousTag: String?,
    viewModel: SavedMessageViewModel = viewModel()
) {
    val txtField = remember { mutableStateOf("") }
    txtField.value = previousTag ?: ""
    if (show)
        Dialog(onDismissRequest = { viewModel.toggleModifyTagAlert() }) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "태그 설정"
                            )
                            Icon(
                                imageVector = Icons.Filled.Cancel,
                                contentDescription = "",
                                tint = colorResource(android.R.color.darker_gray),
                                modifier = Modifier
                                    .width(30.dp)
                                    .height(30.dp)
                                    .clickable { viewModel.toggleModifyTagAlert() }
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    BorderStroke(
                                        width = 2.dp,
                                        color = colorResource(id = android.R.color.holo_purple)
                                    ),
                                    shape = RoundedCornerShape(50)
                                ),
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Tag,
                                    contentDescription = "",
                                    tint = colorResource(android.R.color.holo_purple),
                                    modifier = Modifier
                                        .width(20.dp)
                                        .height(20.dp)
                                )
                            },
                            placeholder = { Text(text = "태그가 비어있어요!") },
                            value = txtField.value,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            onValueChange = {
                                txtField.value = it.take(10)
                            })

                        Spacer(modifier = Modifier.height(20.dp))

                        Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                            Button(
                                onClick = {
                                    onConfirmModify(txtField.value)
                                    viewModel.toggleModifyTagAlert()
                                },
                                shape = RoundedCornerShape(50.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                            ) {
                                Text(text = "Done")
                            }
                        }
                    }
                }
            }
        }
}

@Composable
fun SwitchReceivedSent(
    currentMenu: SavedMessageType,
    selectReceived: () -> Unit,
    selectSent: () -> Unit,
    selectBlocked: () -> Unit,
    onClickMenu: (String) -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Button(
            onClick = selectReceived,
            colors =
            if (currentMenu == SavedMessageType.RECEIVED) ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primary
            )
            else ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
        ) {
            Text(
                text = "수신",
                color = if (currentMenu == SavedMessageType.RECEIVED) Color.Black else Color.White
            )
        }
        Button(
            onClick = selectSent, colors =
            if (currentMenu == SavedMessageType.SEND) ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primary
            )
            else ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
        ) {
            Text(
                text = "송신",
                color = if (currentMenu == SavedMessageType.SEND) Color.Black else Color.White
            )
        }
        Button(
            onClick = selectBlocked,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
        ) {
            Text(text = "차단목록")
        }
        Button(onClick = { onClickMenu("messageList") }) {
            Text(text = "24 메시지")
        }
    }
}


@Composable
fun MessageList(
    modifier: Modifier = Modifier,
    currentMenu: SavedMessageType,
    messageList: List<MessageResponse>,
    onDelete: (MessageResponse) -> Unit,
    onBlock: (MessageResponse) -> Unit,
    onChangeTag: (MessageResponse) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(messageList) {
            MessageItem(
                message = it,
                modifier = modifier,
                currentMenu = currentMenu,
                onDelete = onDelete,
                onChangeTag = onChangeTag,
                onBlock = onBlock
            )
        }
    }
}

@Composable
fun MessageItem(
    message: MessageResponse,
    modifier: Modifier = Modifier,
    currentMenu: SavedMessageType,
    onDelete: (MessageResponse) -> Unit,
    onBlock: (MessageResponse) -> Unit,
    onChangeTag: (MessageResponse) -> Unit,
    viewModel: SavedMessageViewModel = viewModel()
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
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    AudioBtn(
                        enabled = message.audioUri != null,
                        onPlay = {
                            viewModel.stopSavedMessageAudio()
                            Log.d("DataSource", "$S3_CONSTANT_URI${message.audioUri}")
                        },
                        onStop = {
                            viewModel.playSavedMessageAudio(message)
                        },
                        isPlaying = viewModel.savedMessageAudioState.isPlaying
                                && viewModel.savedMessageAudioState.message?.id == message.id
                    )
                }

                SavedMessageInfo(
                    modifier = modifier.weight(5f),
                    content = message.content,
                    tag = message.tag,
                    localDateTime = message.localDateTime
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
                    onChangeTag = {
                        onChangeTag(message)
                    },
                    onShare = {}, onBlock = { onBlock(message) })
            }
        }
    }
}


@Composable
fun SavedMessageInfo(
    modifier: Modifier = Modifier,
    content: String,
    tag: String?,
    localDateTime: String
) {
    Row(
        modifier = modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = content, fontSize = 18.sp)
        Column() {
            Text(text = tag ?: "", fontSize = 13.sp)
            Text(text = localDateTime.substring(0, 10), fontSize = 11.sp)
        }

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
    currentMenu: SavedMessageType,
    onDelete: () -> Unit,
    onShare: () -> Unit,
    onBlock: () -> Unit,
    onChangeTag: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
        IconButton(onClick = onShare) {
            Icon(
                imageVector = Icons.Filled.Share,
                tint = MaterialTheme.colors.secondary,
                contentDescription = "message share button",
            )
        }
        IconButton(onClick = onChangeTag) {
            Icon(
                imageVector = Icons.Filled.Tag,
                tint = MaterialTheme.colors.secondary,
                contentDescription = "modify tag button"
            )
        }
        IconButton(onClick = onDelete) {
            Icon(
                imageVector = Icons.Filled.Delete,
                tint = MaterialTheme.colors.secondary,
                contentDescription = "message delete button",
            )
        }
        if (currentMenu == SavedMessageType.RECEIVED || currentMenu == SavedMessageType.BLOCKED) {
            IconButton(onClick = onBlock) {
                Icon(
                    imageVector = Icons.Filled.Block,
                    tint = if (currentMenu == SavedMessageType.RECEIVED)
                        MaterialTheme.colors.secondary
                    else
                        Color.Red,
                    contentDescription = "sender block button",
                )
            }
        }
    }
}


@Composable
private fun ExpandButton(
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