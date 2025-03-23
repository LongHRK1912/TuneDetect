package com.hrk.tunedetect.util

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

fun String?.orEmpty(): String {
    return this ?: ""
}

fun String.textWithStyle(
    textSpan: String,
    spanStyle: SpanStyle
): AnnotatedString {
    return buildAnnotatedString {
        append(this@textWithStyle)
        withStyle(spanStyle) {
            append(textSpan)
        }
    }
}

fun AnnotatedString.textWithStyle(
    textSpan: String,
    spanStyle: SpanStyle
): AnnotatedString {
    return buildAnnotatedString {
        append(this@textWithStyle)
        withStyle(spanStyle) {
            append(textSpan)
        }
    }
}