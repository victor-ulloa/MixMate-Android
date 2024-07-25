package com.example.mixmate.data

class Constants {
    enum class InventoryItemType {
        spirit, liqueur, mixer, juice, syrup, bitters, herbsAndSpices, others
    }

    companion object {
        val ITEMS = "ITEMS"
        val SAVED_ITEMS = "saved_items"

        val URL = "URL"
        val NAME = "NAME"
        val DESC = "DESC"

        val EMPTY_ARRAY_JSON="[]"
    }
}