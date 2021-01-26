package com.kforce.urbanic.repository.settings

/**
 * Data Class for base App Settings
 *
 */
data class BaseSettings (
    val isActive : Boolean,
    val lastVersion : String,
    val enforceUpdate : Boolean,
    val languages : List<AvailableLanguages>,
    val theme : List<ColorTheme>
)
