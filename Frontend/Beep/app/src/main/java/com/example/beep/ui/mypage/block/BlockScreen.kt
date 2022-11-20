package com.example.beep.ui.mypage.block

import android.R
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.beep.data.dto.message.MessageResponse
import com.example.beep.ui.base.ErrorScreen
import com.example.beep.ui.base.LoadingScreen

@Composable
fun BlockScreen(
    navController: NavController,
    blockViewModel: BlockViewModel = viewModel()) {
    val screenState: BlockScreenState = blockViewModel.blockScreenState
    when (screenState.currentState) {
        BlockScreenResult.Success -> {
            BlockSuccessScreen(
                navController,
                blockList = screenState.blockList,
                onAskCancelBlock = { message: MessageResponse ->
                    blockViewModel.onEvent(
                        BlockEvent.AskCancelBlock(message)
                    )
                },
                onChangeTag = { message: MessageResponse ->
                    blockViewModel.onEvent(
                        BlockEvent.AskChangeTag(message)
                    )
                })
        }
        BlockScreenResult.Loading -> {
            LoadingScreen()
        }
        BlockScreenResult.Fail -> {
            ErrorScreen()
        }
    }
    CancelBlockPopup(
        show = screenState.showCancelBlockPopup,
        onConfirm = { blockViewModel.onEvent(BlockEvent.CancelBlock) },
        onDismiss = { blockViewModel.onEvent(BlockEvent.DismissCancelBlock) })

    TagDialog(
        show = screenState.showChangeTagPopup,
        onConfirmModify = { newTag: String -> blockViewModel.onEvent(BlockEvent.ChangeTag(newTag)) },
        onDismiss = {blockViewModel.onEvent(BlockEvent.DismissChangeTag)},
        previousTag = screenState.messageToHandle?.tag
    )
}

@Composable
fun CancelBlockPopup(show: Boolean, onConfirm: () -> Unit, onDismiss: () -> Unit) {
    if (show) {
        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = onConfirm) {
                    Text(text = "차단 해제")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text(text = "취소")
                }
            },
            title = {
                Text(text = "차단을 해제하시겠습니까?")
            },
            text = {
                Text(text = "해당 유저로부터 메시지를 다시 수신할 수 있습니다.")
            })
    }
}

@Composable
fun BlockSuccessScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    blockList: List<MessageResponse>,
    onAskCancelBlock: (MessageResponse) -> Unit,
    onChangeTag: (MessageResponse) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = modifier
                .height(80.dp)
                .fillMaxWidth()
                .padding(10.dp, 0.dp, 0.dp, 0.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
            ) {
                Icon(
                    modifier = Modifier.size(17.dp),
                    painter = painterResource(com.example.beep.R.drawable.backbutton_gray),
                    contentDescription = "뒤로가기"
                )
            }

            Text(
                modifier = modifier
                    .padding(10.dp, 0.dp, 0.dp, 0.dp),
                textAlign = TextAlign.Center,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                text = "차단 목록"
            )
        }
        if (blockList.isEmpty())
            Box(modifier = modifier.weight(1f), contentAlignment = Alignment.Center) {
                Text(text = "메시지가 없습니다.")
            }
        else
            Column(modifier = Modifier.weight(4f)) {
                BlockMessageList(
                    modifier = Modifier,
                    messageList = blockList,
                    onAskCancelBlock = { message: MessageResponse ->
                        onAskCancelBlock(message)
                    },
                    onChangeTag = { message: MessageResponse ->
                        onChangeTag(message)
                    },
                )
            }
    }
}

@Composable
fun BlockMessageList(
    modifier: Modifier = Modifier,
    messageList: List<MessageResponse>,
    onAskCancelBlock: (MessageResponse) -> Unit,
    onChangeTag: (MessageResponse) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(messageList) {
            MessageItem(
                message = it,
                modifier = modifier,
                onChangeTag = onChangeTag,
                onAskCancelBlock = onAskCancelBlock
            )
        }
    }
}

@Composable
fun MessageItem(
    message: MessageResponse,
    modifier: Modifier = Modifier,
    onChangeTag: (MessageResponse) -> Unit,
    onAskCancelBlock: (MessageResponse) -> Unit
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
                    onChangeTag = {
                        onChangeTag(message)
                    },
                    onCancelBlock = { onAskCancelBlock(message) }
                )
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
fun MessageOptions(
    onCancelBlock: () -> Unit,
    onChangeTag: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
        IconButton(onClick = onChangeTag) {
            Icon(
                imageVector = Icons.Filled.Tag,
                tint = MaterialTheme.colors.secondary,
                contentDescription = "modify tag button"
            )
        }
        IconButton(onClick = onCancelBlock) {
            Icon(
                imageVector = Icons.Filled.Delete,
                tint = MaterialTheme.colors.secondary,
                contentDescription = "cancel block button",
            )
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

@Composable
fun TagDialog(
    show: Boolean,
    onConfirmModify: (String) -> Unit,
    onDismiss: () -> Unit,
    previousTag: String?,
) {
    val txtField = remember { mutableStateOf("") }
    txtField.value = previousTag ?: ""
    if (show)
        Dialog(onDismissRequest = onDismiss) {
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
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    BorderStroke(
                                        width = 2.dp,
                                        color = colorResource(id = R.color.holo_purple)
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
                                    tint = colorResource(R.color.holo_purple),
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