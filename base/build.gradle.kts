plugins {
    id(Libs.Plugins.javaLibrary)
    id(Libs.Plugins.kotlinLibrary)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8

    sourceSets {
        map { it.java.srcDirs("src/${it.name}/kotlin")}
    }

}

dependencies {
    implementation(Libs.AndroidX.annotation)

    api(Libs.Kotlin.stdlib)
    api(Libs.Coroutines.core)

    api(Libs.timber)

    api(Libs.Retrofit.retrofit)
    api(Libs.Retrofit.jacksonConverter)
    api(Libs.Retrofit.jacksonKotlinModule)
}

