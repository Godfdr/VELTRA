package com.veltra.payment.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontLoadingStrategy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.veltra.payment.R

// Using local fonts with explicit loading strategy to fix Preview render issues
val Urbanist = FontFamily(
    Font(resId = R.font.urbanist_regular, weight = FontWeight.Normal, loadingStrategy = FontLoadingStrategy.Async),
    Font(resId = R.font.urbanist_medium, weight = FontWeight.Medium, loadingStrategy = FontLoadingStrategy.Async),
    Font(resId = R.font.urbanist_semibold, weight = FontWeight.SemiBold, loadingStrategy = FontLoadingStrategy.Async),
    Font(resId = R.font.urbanist_bold, weight = FontWeight.Bold, loadingStrategy = FontLoadingStrategy.Async),
    Font(resId = R.font.urbanist_extrabold, weight = FontWeight.ExtraBold, loadingStrategy = FontLoadingStrategy.Async)
)

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = Urbanist,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = Urbanist,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Urbanist,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    labelMedium = TextStyle(
        fontFamily = Urbanist,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)
