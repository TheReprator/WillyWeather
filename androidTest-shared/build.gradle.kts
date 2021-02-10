plugins {
    id(Libs.Plugins.androidLibrary)
    kotlin(Libs.Plugins.kotlinAndroid)
    kotlin(Libs.Plugins.kotlinKapt)
}

android {
    compileSdkVersion(AndroidSdk.compile)

    defaultConfig {
        minSdkVersion(AndroidSdk.min)
        resConfigs(AndroidSdk.locales)
    }

    sourceSets {
        map { it.java.srcDirs("src/${it.name}/kotlin") }
    }
}

dependencies {
    implementation(Libs.Kotlin.stdlib)

    // Android Testing
    api(Libs.TestDependencies.truth)
    api(Libs.AndroidX.Room.test)
    api(Libs.TestDependencies.extJUnit)
    api(Libs.TestDependencies.Mockk.instrumentedTest)

    //Hilt
    api(Libs.DaggerHilt.hiltAndroidTest)
    kapt(Libs.DaggerHilt.hiltCompilerAndroid)

    //workmanager
    api(Libs.AndroidX.Work.test)

    // AndroidX test
    api(Libs.TestDependencies.AndroidXTestInstrumented.core)
    api(Libs.TestDependencies.AndroidXTestInstrumented.runner)

    // Architecture components testing
    api(Libs.TestDependencies.core)
    api(Libs.Coroutines.coroutineTest) {
        exclude(group = "org.jetbrains.kotlinx", module = "kotlinx-coroutines-debug")
    }
}
