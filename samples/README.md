# samples

A small command-line app that uses `api-nba-kotlin` and `api-nfl-kotlin`.

```shell
export API_SPORTS_KEY=...            # from https://api-sports.io
export API_SPORTS_HOST=rapidapi      # optional, if your key is from RapidAPI

./gradlew :samples:run --args="status"
./gradlew :samples:run --args="nba standings 2024"
./gradlew :samples:run --args="nfl standings 2024"
./gradlew :samples:run --args="nfl game 13146"
```

`status` checks your key and quota without using a request. Each other command uses one to three
requests of your daily quota.
