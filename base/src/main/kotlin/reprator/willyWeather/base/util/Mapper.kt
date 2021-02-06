package reprator.willyWeather.base.util

interface Mapper<in InputModal, out OutputModal> {
    suspend fun map(from: InputModal): OutputModal
}

interface MapperConversion<InputModal, OutputModal> {
    suspend fun mapTo(from: InputModal): OutputModal
    suspend fun mapIn(from: OutputModal): InputModal
}