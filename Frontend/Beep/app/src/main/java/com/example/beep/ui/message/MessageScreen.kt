package com.example.beep.ui.message

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
import com.example.beep.data.sample.receivedMessages
import com.example.beep.data.sample.sentMessages

@Composable
fun MessageScreen(viewModel: MessageViewModel = viewModel(), onNextButtonClicked: () -> Unit) {
    var toggleMenu by remember { mutableStateOf(true) }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            SwitchReceivedSent(
                currentMenu = toggleMenu,
                selectReceived = { toggleMenu = true },
                selectSent = { toggleMenu = false })
            Column(modifier = Modifier.weight(4f)) {
                MessageList(modifier = Modifier, currentMenu = toggleMenu)
            }
        }
    }
}

@Composable
fun SwitchReceivedSent(currentMenu: Boolean, selectReceived: () -> Unit, selectSent: () -> Unit) {
    Row(horizontalArrangement = Arrangement.Center) {
        Button(
            onClick = selectReceived,
            colors =
            if (currentMenu) ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
            else ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
        ) {
            Text(text = "수신")
        }
        Button(
            onClick = selectSent, colors =
            if (!currentMenu) ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
            else ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
        ) {
            Text(text = "송신")
        }
    }
}


@Composable
fun MessageList(modifier: Modifier = Modifier, currentMenu: Boolean) {
    LazyColumn(modifier = modifier) {
        if (currentMenu) {
            items(receivedMessages) {
                MessageItem(message = it, modifier = modifier, currentMenu = currentMenu)
            }
        } else {
            items(sentMessages) {
                MessageItem(message = it, modifier = modifier, currentMenu = currentMenu)
            }
        }
    }

}

@Composable
fun MessageItem(message: MessageResponse, modifier: Modifier = Modifier, currentMenu: Boolean) {
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
                MessageOptions(currentMenu = currentMenu)
            }
        }
    }
}


@Composable
fun MessageInfo(
    modifier: Modifier = Modifier,
    content: String,
    tag: String,
    localDateTime: String
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Text(text = content, fontSize = 24.sp)
        Text(text = tag)
        Text(text = localDateTime)
    }
}

@Composable
fun AudioBtn(audioUri: String) {
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
        IconButton(onClick = { /*TODO*/ }) {
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