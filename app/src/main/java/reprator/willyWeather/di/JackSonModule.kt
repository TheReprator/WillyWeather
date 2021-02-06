package reprator.willyWeather.di

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.type.TypeFactory
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.converter.jackson.JacksonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class JackSonModule {

    @Provides
    fun provideObjectMapper() = jacksonObjectMapper().apply {
        setSerializationInclusion(JsonInclude.Include.NON_NULL)
        disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
        disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        registerModule(KotlinModule())
    }

    @Provides
    fun typeFactory(
        objectMapper: ObjectMapper
    ): TypeFactory {
        return objectMapper.typeFactory
    }

    @Provides
    @Singleton
    fun provideJacksonConverterFactory(objectMapper: ObjectMapper): JacksonConverterFactory =
        JacksonConverterFactory.create(objectMapper)
}
