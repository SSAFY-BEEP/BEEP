package com.example.beep.domain

import com.example.beep.data.repository.AddressRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PatchUserAddressUseCase @Inject constructor(private val addressRepository: AddressRepository) {
    fun execute(apiPhone: String, phone: String, name: String) = addressRepository.patchUserAddress(apiPhone, phone, name)
}