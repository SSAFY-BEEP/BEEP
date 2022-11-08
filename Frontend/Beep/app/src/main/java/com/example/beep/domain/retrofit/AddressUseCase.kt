package com.example.beep.domain.retrofit

import com.example.beep.data.repository.AddressRepository
import com.example.beep.data.dto.mainpage.AddressResponse

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserAddressUseCase @Inject constructor(private val addressRepository: AddressRepository) {
    fun execute() = addressRepository.getUserAddress()
}