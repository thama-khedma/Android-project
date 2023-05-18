package com.example.khedma.view.adapters

import com.example.khedma.model.entities.Condidature
import com.example.khedma.model.entities.Entreprise
import com.example.khedma.model.entities.Offre


interface OnListItemClick {
    fun onItemClick(entreprise: Entreprise)
    fun OnListItemClickOffre(offre: Offre)
    fun OnListItemClickCondidature(condidature: Condidature)

}