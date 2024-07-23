package com.example.mixmate.data

class Constants {
    enum class InventoryItemType {
        spirit, liqueur, mixer, juice, syrup, bitters, herbsAndSpices, others
    }

    companion object {
        val SAVED_LIST = "saved_list"
        val URL = "URL"
        val NAME = "NAME"
        val DESC = "DESC"
    }
}