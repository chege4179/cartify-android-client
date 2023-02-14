buildscript {
    val compose_version = "1.4.2"
    val kotlin_version  = "1.8.10"
    dependencies {
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.44.2")
        classpath ("com.google.gms:google-services:4.3.15")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        classpath ("org.jetbrains.kotlin:kotlin-serialization:$kotlin_version")
    }
}// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id ("com.android.application") version "7.1.1" apply false
    id ("com.android.library") version "7.1.1" apply false
    id ("org.jetbrains.kotlin.android") version "1.8.10" apply false
    kotlin("plugin.serialization") version "1.8.10"

}

