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
    implementation(project(AppModules.moduleBase))

    api(Libs.AndroidX.Room.common)

    api(Libs.Dagger.javaxInject)
}