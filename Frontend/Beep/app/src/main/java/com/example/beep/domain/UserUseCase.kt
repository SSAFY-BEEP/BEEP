package com.example.beep.domain

import com.example.beep.data.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserUseCase @Inject constructor(private val userRepository: UserRepository) {
    fun setSound(number : Int) = userRepository.setSound(number)
    fun setFont(number : Int) = userRepository.setFont(number)
    fun setTheme(number : Int) = userRepository.setTheme(number)
    fun setEngrave(engrave : String) = userRepository.setEngrave(engrave)
    fun getUserInfo() = userRepository.getUserInfo()
}