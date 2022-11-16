package com.example.beep.domain

import com.example.beep.data.repository.AddressRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteUserAddressUseCase @Inject constructor(private val addressRepository: AddressRepository) {
    fun execute(phone: String) = addressRepository.deleteUserAddress(phone)
}