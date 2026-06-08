package com.read.pdf.check

import android.content.Context
import androidx.annotation.Keep
import kotlin.runCatching

@Keep
object MainContainer {

    init {
        runCatching {
            System.loadLibrary("comfort")
        }
    }

    @Keep
    @JvmStatic
    external fun compact(context: Context, ad: Any, path: String): Long

}