package com.example.beep.domain

import com.example.beep.data.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserUseCase @Inject constructor(private val userRepository: UserRepository) {
    fun setSound(number : Integer) = userRepository.setSound(number)
    fun setFont(number : Integer) = userRepository.setFont(number)
    fun setTheme(number : Integer) = userRepository.setTheme(number)
    fun setEngrave(engrave : String) = userRepository.setEngrave(engrave)
}