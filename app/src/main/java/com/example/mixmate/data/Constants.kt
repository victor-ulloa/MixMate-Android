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
        val RECIPE_ID = "RECIPE_ID"

        val NEW_LINE = "\n"
        val EMPTY_ARRAY_JSON="[]"
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