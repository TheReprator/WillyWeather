package reprator.willyWeather.cityList.datasource.remote.api.setup

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import retrofit2.converter.jackson.JacksonConverterFactory

fun <T> Class<T>.enqueueResponse(
    mockWebServer: MockWebServer,
    fileName: String,
    headers: Map<String, String> = emptyMap()
) {
    val inputStream = this.classLoader!!
        .getResourceAsStream("api-response/$fileName")
    val source = inputStream.source().buffer()
    val mockResponse = MockResponse()
    mockWebServer.enqueue(
        mockResponse
            .setBody(source.readString(Charsets.UTF_8))
    )
}

fun createJacksonMapper() = jacksonObjectMapper().apply {
    setSerializationInclusion(JsonInclude.Include.NON_NULL)
    disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
    disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    registerModule(KotlinModule())
}

fun createJacksonConverterFactory(): JacksonConverterFactory {
    return JacksonConverterFactory.create(createJacksonMapper())
}