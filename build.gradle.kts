// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath(Libs.androidGradlePlugin)
        classpath(Libs.Kotlin.gradlePlugin)
        classpath(Libs.DaggerHilt.classPath)
        classpath(Libs.AndroidX.Navigation.navigationPlugin)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}


tasks.register("clean",Delete::class){
    delete(rootProject.buildDir)
}