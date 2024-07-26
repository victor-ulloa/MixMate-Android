package com.example.mixmate.data

class Constants {
    enum class InventoryItemType {
        spirit {
            override fun getImageURL(): String {
                return "https://npcddrdidmrwljkyxolk.supabase.co/storage/v1/object/public/Images/inventory/spirit"
            }
        }, liqueur {
            override fun getImageURL(): String {
                return "https://npcddrdidmrwljkyxolk.supabase.co/storage/v1/object/public/Images/inventory/liqueur"
            }
        }, mixer {
            override fun getImageURL(): String {
                return "https://npcddrdidmrwljkyxolk.supabase.co/storage/v1/object/public/Images/inventory/mixer"
            }
        }, juice {
            override fun getImageURL(): String {
                return "https://npcddrdidmrwljkyxolk.supabase.co/storage/v1/object/public/Images/inventory/juice"
            }
        }, syrup {
            override fun getImageURL(): String {
                return "https://npcddrdidmrwljkyxolk.supabase.co/storage/v1/object/public/Images/inventory/syrup"
            }
        },
        bitters {
            override fun getImageURL(): String {
                return "https://npcddrdidmrwljkyxolk.supabase.co/storage/v1/object/public/Images/inventory/bitters"
            }
        }, herbsAndSpices {
            override fun getImageURL(): String {
                return "https://npcddrdidmrwljkyxolk.supabase.co/storage/v1/object/public/Images/inventory/herbsandspices"
            }

            override fun toString(): String {
                return "Herbs And Spices"
            }
        }, others {
            override fun getImageURL(): String {
                return "https://npcddrdidmrwljkyxolk.supabase.co/storage/v1/object/public/Images/inventory/cocktail_and_shaker.jpg"
            }
        };

        abstract fun getImageURL(): String
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