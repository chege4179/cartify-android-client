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
package com.peterchege.cartify.core.util

import java.util.Locale
import java.util.Random

fun randomColorCode(): String {
    val random = Random()
    val nextInt = random.nextInt(0xffffff + 1)
    return String.format("#%06x", nextInt).drop(1).capitalize(Locale.ROOT)
}

fun generateAvatarURL(name:String):String{
    val splitname = name.split(" ").joinToString("+")
    val color = randomColorCode()
    return "https://ui-avatars.com/api/?background=${color}&color=fff&name=${splitname}&bold=true&fontsize=0.6&rounded=true"

}
