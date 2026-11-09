# api-sports-core

Shared code for the api-sports-kotlin clients. It comes with them as a dependency, so you don't need to
add it yourself.

- `ApiSportsClient`: base class that handles base URLs, `x-apisports-key` / RapidAPI headers, HTTP
  caching, envelope decoding and error mapping.
- `ApiResponse<T>`: the `{get, parameters, errors, results, paging, response}` envelope.
- `ApiSportsException`: thrown for API errors, HTTP errors and decoding failures.
- `Host` / `SportApi`: API-SPORTS direct vs RapidAPI, and per-product URLs.

Adding a sport means defining an `Endpoint` enum, a `@Serializable` `QueryParameters` class, the response
models, and a subclass of `ApiSportsClient`. See `api-nfl-kotlin` for an example.
