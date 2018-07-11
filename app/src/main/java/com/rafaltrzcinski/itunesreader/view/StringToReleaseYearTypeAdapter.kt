package com.rafaltrzcinski.itunesreader.view

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter


class StringToReleaseYearTypeAdapter : TypeAdapter<Number>() {

    override fun write(writer: JsonWriter?, value: Number?) {
        if (value == null) {
            writer?.nullValue()
            return
        }
        writer?.value(value)
    }

    override fun read(reader: JsonReader?): Number? {
        if (reader?.peek() == JsonToken.NULL) {
            reader.nextNull()
            return null
        }

        return try {
            val value = reader?.nextString()
            if (value == "") 0
            else value?.toInt()
        } catch (e: NumberFormatException) {
            0
        }
    }
}