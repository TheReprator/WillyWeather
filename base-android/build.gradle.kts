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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    sourceSets {
        map { it.java.srcDirs("src/${it.name}/kotlin")}
    }

    testOptions {
        unitTests.apply {
            isReturnDefaultValues = true
            isIncludeAndroidResources = true
        }
    }

    buildFeatures.dataBinding = true

    packagingOptions {
        pickFirst ("META-INF/*")
    }

    kotlinOptions {
        // work-runtime-ktx 2.1.0 and above now requires Java 8
        jvmTarget = JavaVersion.VERSION_1_8.toString()

        // Enable Coroutines and Flow APIs
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlinx.coroutines.FlowPreview"
    }
}

dependencies {
    implementation(Libs.Kotlin.stdlib)

    api(Libs.AndroidX.Fragment.fragment)

    api(Libs.AndroidX.recyclerview)

    api(Libs.Google.materialWidget)
    api(Libs.AndroidX.coreKtx)
    api(Libs.AndroidX.appcompat)
}