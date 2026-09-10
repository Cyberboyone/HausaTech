package com.nakudin.techhausa.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.nakudin.techhausa.ui.theme.HausaTechColors

/**
 * Two-tone heading: first [base] words in white (OnBackground), last [accent]
 * word(s) in Accent green — matching the reference's "Hausa Tech", "Koyi
 * Darussa", "Gwada Kanka" style headings.
 */
@Composable
fun TwoToneHeading(
    base: String,
    accent: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = buildAnnotatedString {
            withStyle(SpanStyle(color = HausaTechColors.OnBackground)) {
                append(base)
            }
            append(" ")
            withStyle(SpanStyle(color = HausaTechColors.Accent)) {
                append(accent)
            }
        },
        style = MaterialTheme.typography.headlineLarge,
        modifier = modifier
    )
}
