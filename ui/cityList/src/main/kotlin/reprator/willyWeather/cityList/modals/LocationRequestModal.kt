package reprator.willyWeather.cityList.modals

class LocationRequestModal(
    val latitude: String,
    val longitude: String,
    val unit: String = "standard",
    val count: Int = 15

)