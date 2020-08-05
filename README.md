# Changing weather

## How to run (locally)

There is the option of running the application against the sample OpenWeatherMap API, but that will always returns the same result and will not provide meaningful results.

In order to use the OpenWeatherMap.org API you must register and then obtain an application ID token (referred below as the `app-id`). 

The application can be built and executed locally using `Maven` with:
```shell script
# build
$ mvn clean package
# run (using the sample API)
$ java -jar target/changing-weather-0.0.1-SNAPSHOT.jar
# run (using the openweathermap.org endpoint)
$ java -Dspring.profiles.active=prod -Dopenweathermap.app-id=<app id> -jar target/changing-weather-0.0.1-SNAPSHOT.jar
```

Once the application is up and running the follow options are available:

`GET /weather/current?location={city name},{state code},{country code}`

Retrieves the current temperature in a given city, and additional information if you need to bring an umbrella.

Example:
```shell
curl http://localhost:8080/weather/current?location=Berlin,de
```

```json
{
  "pressure": 1018,
  "umbrella": false,
  "temp": 26.63
}
```

`GET /weather/history?location={city name},{state code},{country code}`

Retrieves historical data. All results from queries made to `/weather/current` for a given city are shown, including average temperature (in degrees celsius) and air pressure (in `hPa`). 

Example:
```shell
curl http://localhost:8080/weather/history?location=Berlin,de
```

```json
{
  "history": [
    {
      "pressure": 1018,
      "umbrella": false,
      "temp": 26.63
    },
    {
      "pressure": 1018,
      "umbrella": false,
      "temp": 26.63
    }
  ],
  "avg_temp": 26.63,
  "avg_pressure": 1018
}
```

Note that `city name`, `state code` and `country code` are the same as specified by [OpenWeatherMap.org API](https://openweathermap.org/current).

## How does it work

The application is designed as follows:

1. When a `/weather/current` is issued, the request is essentially forwarded to OpenWeatherApi.org and the response is show back to the client (in a different JSON format)
2. Before sending back the response to the client, an asynchronous call is made to the "`HistoricalWeatherService`" so that the query history is collected
    * For simplicity's sake, the history is stored in memory and it does not survive application restarts.
    * `Berlin` and `Berlin,de` will be considered different cities
    * The complete query history is stored in a `LinkedList` to take advantage of head insertions (in order to calculate averages of last X queries)
3. When a `/weather/history` is issued, the (in memory) contents of the `HistoricalWeatherService` are shown to the client

## What is missing to be production ready

TODO

## How to deploy application to AWS

TODO
  
# Limitations and future work

* In memory
* Average calculation done at insertion time is not necessary. Since an efficient structure is used, that could be done whenever the client request the historical data