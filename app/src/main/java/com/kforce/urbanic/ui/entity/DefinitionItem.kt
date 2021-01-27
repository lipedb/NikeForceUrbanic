package com.kforce.urbanic.ui.entity

import com.kforce.urbanic.enums.DefinitionItemType
import com.kforce.urbanic.util.EMPTY_STRING

/**
 * class that represents the base class for Definition list items
 */
sealed class DefinitionItem {
    abstract val itemType: Int
    abstract val title: String
}

/**
 * data class that represents the Definition Item header in Definition Item list
 */
data class DefinitionHeaderItem(
    val id: String,
    val text: String = EMPTY_STRING
) : DefinitionItem() {
    override val title: String
        get() = text
    override val itemType: Int
        get() = DefinitionItemType.HEADER.ordinal
}

/**
 * data class that represents the Definition Item expandable in Definition Item list
 */
data class DefinitionExpandableItem(
    val id: String,
    val text: String,
    val exampleText: String,
    val upVote: Int,
    val downVote: Int,
    val expanded: Boolean = false
) : DefinitionItem() {
    override val title: String
        get() = text
    override val itemType: Int
        get() = DefinitionItemType.EXPANDABLE.ordinal
}