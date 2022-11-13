package com.example.beep.ui.savedmessage

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.beep.data.dto.message.MessageResponse
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.LifecycleOwner
import com.example.beep.ui.base.ErrorScreen
import com.example.beep.ui.base.LoadingScreen
import com.example.beep.ui.message.*

@Composable
fun SavedMessageScreen(
    viewModel: SavedMessageViewModel = viewModel(),
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    navigateTo: (String) -> Unit
) {
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
                SavedMessageSuccessScreen(messageList = currentUiState.data)
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
    viewModel: SavedMessageViewModel = viewModel()
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        SwitchReceivedSent(
            currentMenu = viewModel.currentSavedMessageType,
            selectReceived = { viewModel.changeCurrentSavedMessageType(SavedMessageType.RECEIVED) },
            selectSent = { viewModel.changeCurrentSavedMessageType(SavedMessageType.SEND) },
            selectBlocked = { viewModel.changeCurrentSavedMessageType(SavedMessageType.BLOCKED) }
        )
        Column(modifier = Modifier.weight(4f)) {
            MessageList(
                modifier = Modifier,
                currentMenu = viewModel.currentSavedMessageType,
                messageList = messageList,
                onDelete = { id: Long ->
                    viewModel.idToDelete = id
                    viewModel.toggleConfirmDeleteAlert()
                })
        }
    }
    DeleteDialog(show = viewModel.showDialog, onConfirmDelete = { viewModel.deleteMessage() })
}


@Composable
fun DeleteDialog(
    show: Boolean,
    onConfirmDelete: () -> Unit,
    viewModel: SavedMessageViewModel = viewModel()
) {
    if (show) {
        AlertDialog(
            onDismissRequest = { viewModel.toggleConfirmDeleteAlert() },
            title = { Text(text = "삭제 하시겠습니까?") },
            text = { Text(text = "메시지가 삭제됩니다.") },
            confirmButton = {
                TextButton(onClick = onConfirmDelete) {
                    Text("확인")
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.toggleConfirmDeleteAlert() }) {
                    Text("취소")
                }
            }
        )
    }
}

@Composable
fun SwitchReceivedSent(
    currentMenu: SavedMessageType,
    selectReceived: () -> Unit,
    selectSent: () -> Unit,
    selectBlocked: () -> Unit
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
            Text(text = "수신")
        }
        Button(
            onClick = selectSent, colors =
            if (currentMenu == SavedMessageType.SEND) ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primary
            )
            else ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
        ) {
            Text(text = "송신")
        }
        Button(
            onClick = selectBlocked,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
        ) {
            Text(text = "차단목록")
        }
    }
}


@Composable
fun MessageList(
    modifier: Modifier = Modifier,
    currentMenu: SavedMessageType,
    messageList: List<MessageResponse>,
    onDelete: (Long) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(messageList) {
            MessageItem(
                message = it,
                modifier = modifier,
                currentMenu = currentMenu,
                onDelete = onDelete
            )
        }
    }
}

@Composable
fun MessageItem(
    message: MessageResponse,
    modifier: Modifier = Modifier,
    currentMenu: SavedMessageType,
    onDelete: (Long) -> Unit,
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
                    .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                AudioBtn(
                    onPlay = { viewModel.playSavedMessageAudio(message) },
                    onStop = { viewModel.stopSavedMessageAudio() },
                    isPlaying = viewModel.savedmessageAudioState.isPlaying
                        && viewModel.savedmessageAudioState.message?.id == message.id
                )
                SavedMessageInfo(
                    modifier = modifier.weight(1f),
                    content = message.content,
                    tag = message.tag,
                    localDateTime = message.localDateTime
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
                    onDelete = { onDelete(message.id) },
                    onShare = {})
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
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Text(text = content, fontSize = 24.sp)
        Text(text = tag ?: "")
        Text(text = localDateTime)
    }
}

@Composable
fun AudioBtn(onPlay: () -> Unit, onStop: () -> Unit, isPlaying: Boolean) {
    IconButton(onClick = { if (isPlaying) onPlay() else onStop() }) {
        Icon(
            imageVector = Icons.Filled.Mic,
            tint = if (isPlaying) Color.Green else Color.Black,
            contentDescription = "message audio button",
        )
    }
}

@Composable
fun MessageOptions(currentMenu: SavedMessageType, onDelete: () -> Unit, onShare: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
        IconButton(onClick = onShare) {
            Icon(
                imageVector = Icons.Filled.Share,
                tint = MaterialTheme.colors.secondary,
                contentDescription = "message share button",
            )
        }
        IconButton(onClick = onDelete) {
            Icon(
                imageVector = Icons.Filled.Delete,
                tint = MaterialTheme.colors.secondary,
                contentDescription = "message delete button",
            )
        }
//        if (currentMenu == SavedMessageType.RECEIVED) {
//            IconButton(onClick = { /*TODO*/ }) {
//                Icon(
//                    imageVector = Icons.Filled.Block,
//                    tint = MaterialTheme.colors.secondary,
//                    contentDescription = "sender block button",
//                )
//            }
//        }
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