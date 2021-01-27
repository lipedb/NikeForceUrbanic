package com.kforce.urbanic.repository.definition

data class Definition  (
    val definition : String,
    val permalink : String,
    val thumbs_up : Int,
    val sound_urls : List<String>,
    val author : String,
    val word : String,
    val defid : Int,
    val current_vote : String,
    val written_on : String,
    val example : String,
    val thumbs_down : Int
)