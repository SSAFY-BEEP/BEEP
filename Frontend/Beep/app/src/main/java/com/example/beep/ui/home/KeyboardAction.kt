package com.example.beep.ui.home

sealed class KeyboardAction {
    data class Number(val number: String): KeyboardAction()
    object Delete: KeyboardAction()
    data class Change(val number: String) : KeyboardAction()
    object Clear: KeyboardAction()
    data class getMessagePreset(val number: String): KeyboardAction()
    data class getAddressPreset(val number: String): KeyboardAction()
}