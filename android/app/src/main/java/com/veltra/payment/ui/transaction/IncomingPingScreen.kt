package com.veltra.payment.ui.transaction

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veltra.payment.ui.theme.*

@Composable
fun IncomingPingOverlay(
    isVisible: Boolean,
    onRejectClick: () -> Unit,
    onPayClick: () -> Unit
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically { it } + fadeIn(),
        exit = slideOutVertically { it } + fadeOut()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f)) // Dim background
                .clickable(enabled = false) { } // Prevent clicks through
        ) {
            // High-fidelity Notification Card - Matched to Lock Screen Style
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 60.dp) // Lifted from bottom like lock screen
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Card.copy(alpha = 0.92f))
                    .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(24.dp))
                    .padding(18.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(PrimaryGradient),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("AV", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                    }
                    Column {
                        Text("Alex Veltra", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                        Text("Veltra · Ping Me", color = Muted, fontSize = 10.sp, fontFamily = Urbanist)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Text("now", color = Muted, fontSize = 10.sp, fontFamily = Urbanist)
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text("Alex is requesting", color = Color.White, fontSize = 13.sp, lineHeight = 20.sp, fontFamily = Urbanist)
                Text("₦5,000.00", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(top = 8.dp), fontFamily = Urbanist)
                Text("\"Dinner at Cactus 🌮\"", color = Muted, fontSize = 11.5.sp, modifier = Modifier.padding(top = 4.dp), fontStyle = androidx.compose.ui.text.font.FontStyle.Italic, fontFamily = Urbanist)

                Spacer(modifier = Modifier.height(16.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Button(
                        onClick = onRejectClick,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.08f)),
                        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.12f)),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text("Reject", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                    }
                    Button(
                        onClick = onPayClick,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        contentPadding = PaddingValues(),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(PrimaryGradient),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Pay ₦5,000", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                        }
                    }
                }
            }
        }
    }
}

// Fallback full-screen for navigation logic if needed
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

@Preview
@Composable
fun IncomingPingPreview() {
    VeltraTheme {
        IncomingPingScreen({}, {})
    }
}
