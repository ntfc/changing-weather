# Changing weather

## How to run (locally)

There is the option of running the application against the sample OpenWeatherMap API, but that will always returns the same result and will not provide meaningful results.

In order to use the OpenWeatherMap.org API you must [register](https://openweathermap.org/) an account, and then obtain an application ID token (referred below as the `app-id`). 

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

1. When a `/weather/current` is issued, the request is essentially forwarded to OpenWeatherMap.org API and the response is show back to the client (in a different JSON format)
2. Before sending back the response to the client, an asynchronous call is made to the "`HistoricalWeatherService`" so that the query history is collected
    * For simplicity's sake, the history is stored in memory and it does not survive application restarts.
    * `Berlin` and `Berlin,de` will be considered different cities
    * The complete query history is stored in a `LinkedList` to take advantage of head insertions (in order to calculate averages of last X queries)
3. When a `/weather/history` is issued, the (in memory) contents of the `HistoricalWeatherService` are shown to the client

## What is missing to be production ready

- Persist historical data
- Error handling
- Dealing with Secrets
- Testing

### Persist historical data

Persisting historical data would allow the application to survive application restarts.

If the persistent volume is external to the application (filesystem, database, key-value store, etc), and since the application would then become stateless, it would be possible to horizontally scale the application by deploying more instances of it if needed (assuming storage mechanism supports that, and the infrastructure is ready for the load balancing between instances).

This would also enable the application to be gradually deployed (e.g. canary deployments) in order to deploy new versions without downtime.

A NoSQL database sounds like a good candidate for persistence.
In order to add that, a couple of changes have to be made around the `HistoricalWeatherService` so that it can read and write data to such database.

### Error handling

Besides the error handling facilities provided by Spring Boot, not much was added to prevent, or deal with, illegal calls to the OpenWeatherMap API.

For example, when issuing a `GET /weather/current?location=Berlin,pt` (note that country is not `de`) the OpenWeatherMap API responds with a `404 Not Found` error.
There are two ways on how this error could be dealt with:
1. The `OpenWeatherMapClient` throws a runtime `CityNotFoundException`, that is then dealt by either the `Service` class, or at the Spring level using exception handlers, for example.
    * requires more work, but better results for the API client (more detailed error messages)
2. The `OpenWeatherMapClient` would return an `Optional` result, and the Service/Controller classes would return either a generic error message if the `Optional` is empty
    * requires less work, but very artificial error messages to the client. The actual error could be logged to help with further troubleshooting

Ideally, this application would be able to map such errors from OpenWeatherMap and wrap them in a common format (such as the one offered by Spring Boot).

### Dealing with Secrets

The OpenWeatherMap API key (or `app-id`) should be stored in a secret store, and retrieved at runtime only (via environments variable for example).

At the moment, the `app-id` can be supplied via environment variables using Spring Boot's [externalized configuration](https://docs.spring.io/spring-boot/docs/2.3.2.RELEASE/reference/html/spring-boot-features.html#boot-features-external-config), but they must reside on the local machine.

AWS Secrets, or Hashicorp Vault, are examples of commonly used mechanisms for secret management in production systems.

### Increase testing coverage and quality

The provided unit tests give some assurance that some basic features are working:

* parsing of OpenWeatherMap API responses is correct
* getting the current weather in a certain city will _call_ the `HistoricalWeatherService`
* calling the `HistoricalWeatherService` six times (with the same city) will return the average pressure/temperature of the last five queries, and the whole six weather results

However, they don't account for more complicated error scenarios that might occur such as:

* What if `pressure` (from OpenWeatherMap API) is returned as a decimal number?
* The OpenWeatherMap API throws an error, and that is not propagated (as mentioned in the _Error handling_ section)


## How to deploy application to AWS

**NOTE:** a lot of things down here are (probably wrong) assumptions!

A simple EC2 based deployment strategy is used since using container orchestration tools (e.g. kubernetes) might be too complex for such simple application. 

The following AWS infrastructure would be needed (maintained via Terraform):

* One or more EC2 instances where the web application gets deployed
* An ELB to expose the HTTP API to clients
    * since application is deployed to EC2, probably would use the Classic Load Balancer
* A DynamoDB database where the data is persisted
* AWS Secrets Manager to securely store secrets such as API keys

The `jar` artifact could be uploaded to S3 on every push to `master`, and whenever a new EC2 instance is created via Terraform the `jar` artifact is pulled from S3, copied to the fresh EC2 instance, and executed via `java -jar` (and with the proper environment variables set).
