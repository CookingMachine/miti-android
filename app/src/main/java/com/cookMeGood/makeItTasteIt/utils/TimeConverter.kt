package com.cookMeGood.makeItTasteIt.utils

object TimeConverter {

    fun convertSecondsToTimeFormat(seconds: Int): String {
        val hours = seconds / 3600
        val minutes = seconds / 60 % 60
        return if (hours != 0) {
            if (minutes != 0) {
                "$hours ч $minutes мин"
            } else {
                "$hours ч"
            }
        } else {
            "$minutes мин"
        }
    }

}