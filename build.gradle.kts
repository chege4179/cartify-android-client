/*
 * Copyright 2023 Cartify By Peter Chege
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import org.jetbrains.kotlin.builtins.StandardNames.FqNames.target
import org.jetbrains.kotlin.js.translate.context.Namer.kotlin

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
    id ("com.android.application") version "7.4.1" apply false
    id ("com.android.library") version "7.4.1" apply false
    id ("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.diffplug.spotless") version "5.3.0"
    kotlin("plugin.serialization") version "1.8.10"

}

apply(plugin = "com.diffplug.spotless")
spotless {
    kotlin {
        target("**/*.kt")
        licenseHeaderFile(
            rootProject.file("${project.rootDir}/spotless/LICENSE.txt"),
            "^(package|object|import|interface)"
        )
    }
    format("kts") {
        target("**/*.kts")
        targetExclude("**/build/**/*.kts")
        // Look for the first line that doesn't have a block comment (assumed to be the license)
        licenseHeaderFile(rootProject.file("spotless/LICENSE.txt"), "(^(?![\\/ ]\\*).*$)")
    }
}
