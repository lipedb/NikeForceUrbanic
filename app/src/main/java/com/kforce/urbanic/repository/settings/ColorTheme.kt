package com.kforce.urbanic.repository.settings

data class ColorTheme(
    val name : String,
    val active : Boolean,
    val pallet : Pallet
)