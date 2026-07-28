package com.veltra.payment.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush

// Veltra Luxury Palette - Matched for "Warmth and Smoothness"
val Royal = Color(0xFF3576E0)
val Ultra = Color(0xFF485AF8)
val Teal = Color(0xFF2EBCD5)
val Base = Color(0xFF08090F)
val Surface = Color(0xFF0E1017)
val Card = Color(0xFF12151E)
val Card2 = Color(0xFF181C27)
val Border = Color(0xFFFFFFFF).copy(alpha = 0.07f)
val Muted = Color(0xFFFFFFFF).copy(alpha = 0.45f)
val Sub = Color(0xFFFFFFFF).copy(alpha = 0.25f)

// Warm Header Start - Matched from HTML #1a2a5e
val HeaderStart = Color(0xFF1A2A5E)

// Semantic Colors
val SuccessGreen = Color(0xFF34C759)
val ErrorRed = Color(0xFFEF4444)
val WarningOrange = Color(0xFFF59E0B)
val InfoPurple = Color(0xFF9B6DFF)

// Production Gradients - Refined for mixing smoothness
val PrimaryGradient = Brush.horizontalGradient(listOf(Royal, Teal))
val PremiumGradient = Brush.linearGradient(listOf(Ultra, Teal))
val HeaderGradient = Brush.verticalGradient(
    0.0f to HeaderStart,
    0.6f to Surface,
    1.0f to Base
)
val PurpleGradient = Brush.linearGradient(listOf(InfoPurple.copy(alpha = 0.25f), Royal.copy(alpha = 0.15f)))
val SuccessGradient = Brush.radialGradient(colors = listOf(Teal.copy(alpha = 0.25f), Teal.copy(alpha = 0.05f)))

// Special Analytics Gradients
val AnalyticsGradient = Brush.linearGradient(
    0.0f to Royal.copy(alpha = 0.25f),
    1.0f to Teal.copy(alpha = 0.15f)
)
val InsightGradient = Brush.horizontalGradient(
    listOf(Royal.copy(alpha = 0.15f), Teal.copy(alpha = 0.12f))
)
