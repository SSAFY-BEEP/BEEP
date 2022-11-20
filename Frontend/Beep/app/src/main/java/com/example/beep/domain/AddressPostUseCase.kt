package com.example.beep.domain

import com.example.beep.data.repository.AddressRepository
import com.example.beep.data.dto.mainpage.AddressResponse

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostUserAddressUseCase @Inject constructor(private val addressRepository: AddressRepository) {
    fun execute(phone: String, name: String) = addressRepository.postUserAddress(phone, name)
}

