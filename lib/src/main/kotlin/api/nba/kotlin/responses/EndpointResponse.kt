package api.nba.kotlin.responses

import api.nba.kotlin.serializers.MapSerializer
import kotlinx.serialization.Serializable

/**
 * Represents the response returned from an API endpoint.
 *
 * @param T The type of the response data.
 * @property get The 'get' field of the response.
 * @property parameters The parameters field of the response, represented as a map.
 * @property errors The errors field of the response, containing a list of error messages.
 * @property results The results field of the response, indicating the number of results.
 * @property response The response data, represented as a list of type T.
 * @constructor Creates a new instance of EndpointResponse.
 */
@Serializable
data class EndpointResponse<T : Any>(
    val get: String,
    @Serializable(with = MapSerializer::class) val parameters: Map<String, String>,
    val errors: List<String>,
    val results: Int,
    val response: List<T>,
)
