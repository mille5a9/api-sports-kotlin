package io.github.mille5a9.apisports.core

import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

/** A path relative to a [SportApi] base URL, e.g. `games` or `teams/statistics`. */
public interface Endpoint {
    public val path: String
}

/** Query parameters for an endpoint. Null values are omitted from the request. */
public interface QueryParameters {
    public fun toQueryMap(): Map<String, String>
}

/**
 * Converts a `@Serializable` parameters class into a query map using its serial names,
 * so a new property can never be silently left out of the request.
 */
public inline fun <reified P : QueryParameters> P.encodeToQueryMap(): Map<String, String> =
    ApiSportsJson.encodeToJsonElement(this).jsonObject.mapValues { (_, value) -> value.jsonPrimitive.content }
