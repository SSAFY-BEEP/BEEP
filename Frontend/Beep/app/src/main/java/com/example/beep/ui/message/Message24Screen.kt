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
                })
        }
    }
}

@Composable
fun MessageList(
    modifier: Modifier = Modifier,
    currentMenu: ReceiveSendState,
    messageList: List<Message24Response>,
    onDelete: (Message24Response) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        items(messageList) {
            MessageItem(
                message = it,
                modifier = modifier,
                currentMenu = currentMenu,
                onDelete = onDelete,
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
//                AudioBtn(
//                    enabled = message.audioUri != null,
//                    onPlay = { viewModel.playSavedMessageAudio(message) },
//                    onStop = { viewModel.stopSavedMessageAudio() },
//                    isPlaying = viewModel.savedMessageAudioState.isPlaying
//                            && viewModel.savedMessageAudioState.message?.id == message.id
//                )
                MessageInfo(
                    modifier = modifier.weight(1f),
                    content = message.content,
                    localDateTime = message.time
                )
//                ExpandButton(
//                    expanded = expanded,
//                    onClick = { expanded = !expanded },
//                    modifier = modifier.weight(1f)
//                )
//            }
//            if (expanded) {
//                MessageOptions(
//                    currentMenu = currentMenu,
//                    onDelete = { onDelete(message) },
//                    onChangeTag = {
//                        viewModel.toggleModifyTagAlert()
//                        onChangeTag(message)
//                    },
//                    onShare = {}, onBlock = { onBlock(message) })
//            }
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
fun MessageOptions(currentMenu: Boolean) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Filled.Share,
                tint = MaterialTheme.colors.secondary,
                contentDescription = "message share button",
            )
        }
        if (currentMenu) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Filled.BookmarkAdd,
                    tint = MaterialTheme.colors.secondary,
                    contentDescription = "message save button",
                )
            }
        }
        IconButton(onClick = {  }) {
            Icon(
                imageVector = Icons.Filled.Delete,
                tint = MaterialTheme.colors.secondary,
                contentDescription = "message delete button",
            )
        }
        if (currentMenu) {
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