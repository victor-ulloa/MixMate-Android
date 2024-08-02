package com.example.mixmate.data

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.UUID

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
                return "https://npcddrdidmrwljkyxolk.supabase.co/storage/v1/object/public/Images/inventory/others"
            }
        };

        abstract fun getImageURL(): String
    }

    enum class Tags {
        nonAlcoholic {
            override fun toString(): String {
                return "non-alcoholic"
            }
        }, sweet, sour, bitter, cold, refreshing, carbonated
        , dairyFree {
            override fun toString(): String {
                return "dairy-free"
            }
        }, fruits, spices, summer, coffee, classic, elegant, smooth, sophisticated, spicy, herbal
        , juicy, vegan, tropical, frozen
    }

    companion object {
        const val ITEMS = "ITEMS"
        const val SAVED_ITEMS = "saved_items"

        const val URL = "URL"
        const val NAME = "NAME"
        const val RECIPE_ID = "RECIPE_ID"

        const val NEW_LINE = "\n"
        const val EMPTY_ARRAY_JSON="[]"
    }

    object UUIDSerializer : KSerializer<UUID> {
        override val descriptor = PrimitiveSerialDescriptor("UUID", PrimitiveKind.STRING)

        override fun deserialize(decoder: Decoder): UUID {
            return UUID.fromString(decoder.decodeString())
        }

        override fun serialize(encoder: Encoder, value: UUID) {
            encoder.encodeString(value.toString())
        }
    }
}