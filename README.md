# AsteroidDatabaseFixer

Fixes invalid player profiles in your Asteroid database by removing players that no longer exist.

## Requirements

- Java 21 or higher

## Usage

1. Place the JAR in the same folder as your `asteroid.db` file
2. Run:
```bash
java -jar AsteroidDatabaseFixer-1.0.jar sqlite
```

> **Note:** Only SQLite is currently supported.

## What it does

- Fetches all player profiles from your database
- Removes profiles that no longer exist

## Download

Get the latest release from the [Releases](https://github.com/Asteroid-Enterprise/AsteroidDatabaseFixer/releases) page.