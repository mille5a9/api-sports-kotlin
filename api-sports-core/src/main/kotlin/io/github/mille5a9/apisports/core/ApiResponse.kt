package io.github.mille5a9.apisports.core

import kotlinx.serialization.Serializable

/**
 * The envelope every API-SPORTS endpoint wraps its data in.
 *
 * @param T The payload type: usually a `List` of models, or a single object for `status`.
 * @property get The endpoint that was called, as echoed by the API.
 * @property parameters The query parameters the API received.
 * @property errors Errors reported by the API. Always empty here: a non-empty map is thrown as [ApiSportsException].
 * @property results The number of items in [response].
 * @property response The payload.
 * @property paging Present on paginated endpoints only.
 */
@Serializable
public data class ApiResponse<T>(
    public val get: String,
    @Serializable(with = LenientStringMapSerializer::class)
    public val parameters: Map<String, String> = emptyMap(),
    @Serializable(with = LenientStringMapSerializer::class)
    public val errors: Map<String, String> = emptyMap(),
    public val results: Int = 0,
    public val response: T,
    public val paging: Paging? = null,
)

/** Pagination information for endpoints that return results in pages. */
@Serializable
public data class Paging(
    public val current: Int,
    public val total: Int,
)
