package io.github.mille5a9.apisports.core

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

/** The JSON configuration used to decode every API-SPORTS response. */
@PublishedApi
internal val ApiSportsJson: Json = Json {
    // New fields added by API-SPORTS must not break existing clients.
    ignoreUnknownKeys = true
    // Omit null parameters when encoding query maps.
    explicitNulls = false
    isLenient = true
}

/**
 * API-SPORTS sends `parameters` and `errors` as an object when populated but as an empty
 * array (`[]`) when there are none, and occasionally as an array of strings.
 * This accepts all of those shapes and always produces a map.
 */
internal object LenientStringMapSerializer : KSerializer<Map<String, String>> {
    private val delegate = MapSerializer(String.serializer(), String.serializer())

    override val descriptor = delegate.descriptor

    override fun deserialize(decoder: Decoder): Map<String, String> {
        val jsonDecoder = decoder as? JsonDecoder ?: return delegate.deserialize(decoder)
        return when (val element = jsonDecoder.decodeJsonElement()) {
            is JsonObject -> element.mapValues { (_, value) -> value.asText() }
            is JsonArray -> element.mapIndexed { index, value -> index.toString() to value.asText() }.toMap()
            JsonNull -> emptyMap()
            is JsonPrimitive -> mapOf("error" to element.content)
        }
    }

    override fun serialize(
        encoder: Encoder,
        value: Map<String, String>,
    ) = delegate.serialize(encoder, value)

    private fun JsonElement.asText(): String = if (this is JsonPrimitive) content else toString()
}
