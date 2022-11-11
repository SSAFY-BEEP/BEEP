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
import com.example.beep.util.collectAsStateLifecycleAware
import android.util.Log
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.example.beep.ui.base.ErrorScreen
import com.example.beep.ui.base.LoadingScreen
import com.example.beep.ui.message.UiState
import retrofit2.Response

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
            selectSent = { viewModel.changeCurrentSavedMessageType(SavedMessageType.SEND) })
        Column(modifier = Modifier.weight(4f)) {
            MessageList(
                modifier = Modifier,
                currentMenu = viewModel.currentSavedMessageType,
                messageList = messageList,
                onDelete = { id: Long ->
                    viewModel.deleteMessage(id)
                })
        }
    }
}


@Composable
fun DeleteDialog(id: (Long) -> Unit) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text(text = "삭제 하시겠습니까?") },
        text = { Text(text = "메시지가 삭제됩니다.") },
        confirmButton = {
            TextButton(onClick = { /*onDelete(id)*/ }) {
                Text("확인")
            }
        },
        dismissButton = {
            TextButton(onClick = {}) {
                Text("취소")
            }
        }
    )
}

@Composable
fun SwitchReceivedSent(
    currentMenu: SavedMessageType,
    selectReceived: () -> Unit,
    selectSent: () -> Unit
) {
    Row(horizontalArrangement = Arrangement.Center) {
        Button(
            onClick = selectReceived,
            colors =
            if (currentMenu == SavedMessageType.RECEIVED) ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
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
    onDelete: (Long) -> Unit
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
                AudioBtn(message.audioUri)
                MessageInfo(
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
                MessageOptions(currentMenu = currentMenu, onDelete = onDelete(message.id))
            }
        }
    }
}


@Composable
fun MessageInfo(
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
fun AudioBtn(audioUri: String?) {
    IconButton(onClick = { /*TODO*/ }) {
        Icon(
            imageVector = Icons.Filled.Mic,
            tint = MaterialTheme.colors.secondary,
            contentDescription = "message audio button",
        )
    }
}

@Composable
fun MessageOptions(currentMenu: SavedMessageType, onDelete: Unit) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Filled.Share,
                tint = MaterialTheme.colors.secondary,
                contentDescription = "message share button",
            )
        }
        if (currentMenu == SavedMessageType.RECEIVED) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Filled.BookmarkAdd,
                    tint = MaterialTheme.colors.secondary,
                    contentDescription = "message save button",
                )
            }
        }
        IconButton(onClick = { onDelete }) {
            Icon(
                imageVector = Icons.Filled.Delete,
                tint = MaterialTheme.colors.secondary,
                contentDescription = "message delete button",
            )
        }
        if (currentMenu == SavedMessageType.RECEIVED) {
            IconButton(onClick = { /*TODO*/ }) {
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