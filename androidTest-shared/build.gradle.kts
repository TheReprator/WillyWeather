plugins {
    id(Libs.Plugins.androidLibrary)
    kotlin(Libs.Plugins.kotlinAndroid)
    kotlin(Libs.Plugins.kotlinKapt)
}

android {
    compileSdkVersion(AndroidSdk.compile)

    defaultConfig {
        minSdkVersion(AndroidSdk.min)

        testInstrumentationRunner = Libs.TestDependencies.testRunner

        consumerProguardFiles(
                file("proguard-rules.pro")
        )

        resConfigs(AndroidSdk.locales)
    }
}

dependencies {
    api(Libs.Kotlin.stdlib)

    // Android Testing
    androidTestApi(Libs.TestDependencies.truth)
    androidTestApi(Libs.AndroidX.Room.test)
    androidTestApi(Libs.TestDependencies.extJUnit)
    testApi(Libs.TestDependencies.Mockk.instrumentedTest)

    //Hilt
    androidTestApi(Libs.DaggerHilt.hiltAndroidTest)
    kaptAndroidTest(Libs.DaggerHilt.hiltCompilerAndroid)

    //workmanager
    androidTestApi(Libs.AndroidX.Work.test)

    // AndroidX test
    androidTestApi(Libs.TestDependencies.AndroidXTestInstrumented.core)
    androidTestApi(Libs.TestDependencies.AndroidXTestInstrumented.runner)

    // Architecture components testing
    androidTestApi(Libs.TestDependencies.core)
}
