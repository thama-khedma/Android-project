package com.example.khedma.util

import android.app.admin.SystemUpdatePolicy.ValidationFailedException
import com.example.khedma.model.entities.Entreprise


fun validateEntrepriseName(name: String): Validation {
    if (name.isEmpty())
        return Validation.Failed("Nom de l'entreprise ne peut pas être vide.")

    if (name.length < 4)
        return Validation.Failed("Utilisez 4 caractères ou plus pour le nom de l'entreprise.")

    return Validation.Success
}
fun validateEntrepriseId(entreprise: Entreprise): Validation {
    val id = entreprise.id ?: return Validation.Failed("ID de l'entreprise manquant.")
    return Validation.Success
}



fun validateEntrepriseAddress(name: String): Validation {
    if (name.isEmpty())
        return Validation.Failed("Adresse de l'entreprise ne peut pas être vide.")

    if (name.length < 4)
        return Validation.Failed("Utilisez 4 caractères ou plus pour l'adresse de l'entreprise.")

    return Validation.Success
}

fun validateEntrepriseDescription(name: String): Validation {
    if (name.isEmpty())
        return Validation.Failed("Description de l'entreprise ne peut pas être vide.")

    if (name.length < 4)
        return Validation.Failed("Utilisez 4 caractères ou plus pour la description de l'entreprise.")

    return Validation.Success
}
fun validateEntrepriseEmail(name: String): Validation {
    if (name.isEmpty())
        return Validation.Failed("Email de l'entreprise ne peut pas être vide.")

    if (name.length < 4)
        return Validation.Failed("Utilisez 4 caractères ou plus pour la Email de l'entreprise.")

    return Validation.Success
}

fun validateOffreName(title: String): Validation {
    if (title.isEmpty())
        return Validation.Failed("le nom de l'offre ne peut pas être vide.")
    if (title.length < 4)
        return Validation.Failed("Utilisez 4 caractères ou plus pour le nom de l'offre.")

    return Validation.Success
}

fun validateOffreAddress(address: String): Validation {
    if (address.isEmpty())
        return Validation.Failed("L'adresse de l'offre ne peut pas être vide.")
    if (address.length < 4)
        return Validation.Failed("Utilisez 4 caractères ou plus pour l'adresse de l'offre.")

    return Validation.Success
}

fun validateOffreEntreprise(enttreprise: String): Validation {
    if (enttreprise.isEmpty())
        return Validation.Failed("L'entreprise ne peut pas être vide.")
    if (enttreprise.length < 4)
        return Validation.Failed("")

    return Validation.Success
}

fun validateOffreDescription(description: String): Validation {
    if (description.isEmpty())
        return Validation.Failed("La description de l'offre ne peut pas être vide.")
    if (description.length < 4)
        return Validation.Failed("Utilisez 4 caractères ou plus pour la description de l'offre.")

    return Validation.Success
}


