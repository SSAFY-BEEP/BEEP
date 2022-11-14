package com.example.beep.domain

import com.example.beep.data.repository.MessageRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageUseCase @Inject constructor(private val messageRepository: MessageRepository) {
    suspend fun getReceive(type: Int) = messageRepository.getReceiveMessage(type)
    suspend fun getSend() = messageRepository.getSendMessage()
    suspend fun changeTag(id: Long, tag: String) = messageRepository.changeTag(id, tag)
    suspend fun deleteMessage(messageId: Long) = messageRepository.deleteMessage(messageId)
    suspend fun blockMessage(messageId: Long) = messageRepository.blockMessage(messageId)
}