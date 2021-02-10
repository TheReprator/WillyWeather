package reprator.willyWeather.cityList.datasource.remote.modal

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "cod",
    "message",
    "cnt",
    "list",
    "city"
)
class ForecastLocationEntity(

    @JsonProperty("cod")
    val cod: String,
    @JsonProperty("message")
    val message: Int,
    @JsonProperty("cnt")
    val cnt: Int,
    @JsonProperty("list")
    val list: List<ListRemoteEntity> = emptyList(),
    @JsonProperty("city")
    val city: CityRemoteEntity
)


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "id",
    "name",
    "coord",
    "country",
    "population",
    "timezone"
)
class CityRemoteEntity(

    @JsonProperty("id")
    val id: Int,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("coord")
    val coord: CoordRemoteEntity,
    @JsonProperty("country")
    val country: String,
    @JsonProperty("population")
    val population: Int,
    @JsonProperty("timezone")
    val timezone: Int
)


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "lon",
    "lat"
)
class CoordRemoteEntity(
    @JsonProperty("lon") val lon: Double,
    @JsonProperty("lat") val lat: Double
)


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "day",
    "night",
    "eve",
    "morn"
)
class FeelsLikeRemoteEntity (

    @JsonProperty("day")
    val day: Double,
    @JsonProperty("night")
    val night: Double,
    @JsonProperty("eve")
    val eve: Double,
    @JsonProperty("morn")
    val morn: Double)


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "dt",
    "sunrise",
    "sunset",
    "temp",
    "feels_like",
    "pressure",
    "humidity",
    "weather",
    "speed",
    "deg",
    "clouds",
    "pop",
    "snow"
)
 class ListRemoteEntity (

    @JsonProperty("dt")
    val dt: Long,
    @JsonProperty("sunrise")
    val sunrise: Long,
    @JsonProperty("sunset")
    val  sunset: Long,
    @JsonProperty("temp")
    val temp: TempRemoteEntity,
    @JsonProperty("feels_like")
    val feelsLike: FeelsLikeRemoteEntity,
    @JsonProperty("pressure")
    val pressure: Double,
    @JsonProperty("humidity")
    val  humidity:Double,
    @JsonProperty("weather")
    val  weather: List<WeatherRemoteEntity> = emptyList<WeatherRemoteEntity>(),
    @JsonProperty("speed")
    val speed: Double,
    @JsonProperty("deg")
    val deg: Double,
    @JsonProperty("clouds")
    val clouds: Double,
    @JsonProperty("pop")
    val pop:Double,
    @JsonProperty("snow")
    val snow: Double
    )

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "day",
    "min",
    "max",
    "night",
    "eve",
    "morn"
)
class TempRemoteEntity (
    @JsonProperty("day")
    val day: Double,
    @JsonProperty("min")
    val min: Double,
    @JsonProperty("max")
    val max: Double,
    @JsonProperty("night")
    val night: Double,
    @JsonProperty("eve")
    val eve: Double,
    @JsonProperty("morn")
    val morn: Double)

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "id",
    "main",
    "description",
    "icon"
)
class WeatherRemoteEntity(
    @JsonProperty("id")
    val id: Long,
    @JsonProperty("main")
    val main: String,
    @JsonProperty("description")
    val description: String,
    @JsonProperty("icon")
    val icon: String
)