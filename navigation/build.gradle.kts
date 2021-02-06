plugins {
    id(Libs.Plugins.androidLibrary)
    kotlin(Libs.Plugins.kotlinAndroid)
    id(Libs.Plugins.kotlinNavigation)
}

android {
    compileSdkVersion(AndroidSdk.compile)

    defaultConfig {
        minSdkVersion(AndroidSdk.min)

        testInstrumentationRunner = Libs.TestDependencies.testRunner

        consumerProguardFiles(
            file("proguard-rules.pro")
        )
    }

    buildTypes {

        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                file("proguard-rules.pro")
            )
        }
    }

    sourceSets {
        map { it.java.srcDirs("src/${it.name}/kotlin") }
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
        unitTests.isIncludeAndroidResources = true
    }

     packagingOptions {
         pickFirst ("META-INF/*")
    }
}

dependencies {
    implementation(project(AppModules.moduleBaseAndroid))

    implementation(Libs.AndroidX.appcompat)
    implementation(Libs.AndroidX.coreKtx)

    implementation(Libs.AndroidX.Navigation.fragment)
    implementation(Libs.AndroidX.Navigation.fragmentKtx)
    implementation(Libs.AndroidX.Navigation.uiKtx)
    implementation(Libs.AndroidX.Navigation.ui)
}