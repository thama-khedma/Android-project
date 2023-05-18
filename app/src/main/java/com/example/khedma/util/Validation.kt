package com.example.khedma.util

sealed class Validation() {
    object Success : Validation()
    data class Failed(val message: String) : Validation()
}


data class EntrepriseFieldsState(
    val name: Validation,
    val address: Validation,
    val description: Validation,
    val email: Validation
)

data class OffreFieldsState(
    val name: Validation,
    val address: Validation,
)


