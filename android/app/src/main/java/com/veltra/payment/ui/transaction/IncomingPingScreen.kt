package com.veltra.payment.ui.transaction

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veltra.payment.ui.IncomingPingOverlay
import com.veltra.payment.ui.theme.*

@Composable
fun IncomingPingScreen(onRejectClick: () -> Unit, onPayClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Base)
    ) {
        // Deep blue glow behind everything
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        0f to Royal.copy(alpha = 0.15f),
                        1f to Color.Transparent,
                        center = androidx.compose.ui.geometry.Offset(500f, 400f)
                    )
                )
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(100.dp))

            Text("9:41", color = Color.White, fontSize = 56.sp, fontWeight = FontWeight.ExtraLight, letterSpacing = (-2).sp, fontFamily = Urbanist)
            Text("Friday, July 26", color = Color.White.copy(alpha = 0.5f), fontSize = 14.sp, fontFamily = Urbanist, fontWeight = FontWeight.Medium)

            Spacer(modifier = Modifier.weight(1f))

            IncomingPingOverlay(isVisible = true, onRejectClick = onRejectClick, onPayClick = onPayClick)
        }
    }
}
