plugins {
    id(Libs.Plugins.javaLibrary)
    kotlin(Libs.Plugins.kotlinJVM)
}

dependencies {
    api(Libs.Kotlin.stdlib)
    implementation(project(AppModules.moduleBase))

    // Testing
    api(Libs.Coroutines.coroutineTest)
    api(Libs.TestDependencies.truth)
    api(Libs.TestDependencies.core)
    api(Libs.OkHttp.mockWebServer)
    api(Libs.TestDependencies.jUnit)
    api(Libs.TestDependencies.Mockk.unitTest)
}
