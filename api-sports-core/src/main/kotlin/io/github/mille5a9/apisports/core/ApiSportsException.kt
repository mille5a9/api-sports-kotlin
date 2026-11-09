package io.github.mille5a9.apisports.core

/**
 * Thrown when a request fails: the API reported errors (bad key, rate limit, invalid parameter),
 * the server answered with a non-2xx status, or the body could not be decoded into the expected model.
 *
 * @property errors The `errors` object returned by the API, e.g. `{"token": "Error/Missing application key."}`.
 * Empty when the failure did not come from the API's error envelope.
 * @property statusCode The HTTP status code of the response, if one was received.
 */
public class ApiSportsException(
    message: String,
    public val errors: Map<String, String> = emptyMap(),
    public val statusCode: Int? = null,
    cause: Throwable? = null,
) : RuntimeException(message, cause)
