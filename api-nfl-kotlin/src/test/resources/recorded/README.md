# Recorded API responses

Responses from API-American-Football v1 (2024 NFL season, recorded in 2026), taken from the
MIT-licensed [fabricatorsltd/api-sports](https://github.com/fabricatorsltd/api-sports)
repository (`api-specs/nfl`). They are unmodified except:

- `games.json` keeps one game for each distinct combination of stage, status and null fields
  (6 of 335) and its `results` count is adjusted to match.
- `games-statistics-players.json` keeps up to three players per stat group, preferring those
  with null values.

`RecordedResponsesTest` decodes each file in full so fields the real API sends as `null` are
caught by the build rather than by users.
