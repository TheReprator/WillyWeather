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
    val list: List<ListEntity> = emptyList(),
    @JsonProperty("city")
    val city: CityEntity
)

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "id",
    "name",
    "coord",
    "country",
    "population",
    "timezone",
    "sunrise",
    "sunset"
)
class CityEntity(

    @JsonProperty("id")
    val id: Int,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("coord")
    val coord: CoordEntity,
    @JsonProperty("country")
    val country: String,
    @JsonProperty("population")
    val population: Int,
    @JsonProperty("timezone")
    val timezone: Int,
    @JsonProperty("sunrise")
    val sunrise: Int,
    @JsonProperty("sunset")
    val sunset: Int
)


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "dt",
    "main",
    "weather",
    "clouds",
    "wind",
    "visibility",
    "pop",
    "sys",
    "dt_txt"
)
class ListEntity(
    @JsonProperty("dt")
    val dt: Int,
    @JsonProperty("main")
    val main: MainEntity,
    @JsonProperty("weather")
    val weather: List<WeatherEntity> = emptyList(),
    @JsonProperty("clouds")
    val clouds: CloudEntity,
    @JsonProperty("wind")
    val wind: WindEntity,
    @JsonProperty("visibility")
    val visibility: Int,
    @JsonProperty("pop")
    val pop: Int,
    @JsonProperty("sys")
    val sys: SysEntity,
    @JsonProperty("dt_txt")
    val dtTxt: String
)



@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "temp",
    "feels_like",
    "temp_min",
    "temp_max",
    "pressure",
    "humidity",
    "sea_level",
    "grnd_level"
)
class MainEntity(
    @JsonProperty("temp")
    val temp: Double,
    @JsonProperty("feels_like")
    val feelsLike: Double,
    @JsonProperty("temp_min")
    val tempMin: Double,
    @JsonProperty("temp_max")
    val tempMax: Double,
    @JsonProperty("pressure")
    val pressure: Int,
    @JsonProperty("humidity")
    val humidity: Int,
    @JsonProperty("sea_level")
    val seaLevel: Int,
    @JsonProperty("grnd_level")
    val grndLevel: Int
)


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "id",
    "main",
    "description",
    "icon"
)
class WeatherEntity(
    @JsonProperty("id")
    val id: Int,
    @JsonProperty("main")
    val main: String,
    @JsonProperty("description")
    val description: String,
    @JsonProperty("icon")
    val icon: String
)


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "all"
)
class CloudEntity(@JsonProperty("all") val all: Int)


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "speed",
    "deg"
)
class WindEntity(
    @JsonProperty("speed")
    val speed: Double,
    @JsonProperty("deg")
    val deg: Int
)



@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "country",
    "sunrise",
    "sunset"
)
class SysEntity(

    @JsonProperty("country")
    val country: String = "",
    @JsonProperty("sunrise")
    val sunrise: Int = 0,
    @JsonProperty("sunset")
    val sunset: Int = 0
)


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(
    "lon",
    "lat"
)
class CoordEntity(
    @JsonProperty("lon") val lon: Double,
    @JsonProperty("lat") val lat: Double
)