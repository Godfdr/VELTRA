package com.veltra.payment.ui.transaction

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veltra.payment.ui.theme.*

@Composable
fun PingSentScreen(onCancelClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Base)
    ) {
        // Deep Warm Glow mixing
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        0f to HeaderStart.copy(alpha = 0.4f),
                        0.4f to Base
                    )
                )
        )

        Scaffold(
            containerColor = Color.Transparent,
            bottomBar = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Button(
                        onClick = onCancelClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .border(1.dp, Royal.copy(alpha = 0.3f), RoundedCornerShape(50.dp)),
                        colors = ButtonDefaults.buttonColors(containerColor = Royal.copy(alpha = 0.15f)),
                        shape = RoundedCornerShape(50.dp)
                    ) {
                        Text("Cancel Ping", color = Teal, fontSize = 15.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                    }
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(60.dp))

                // Success Ring Visual - Precision mixing
                Box(contentAlignment = Alignment.Center) {
                    Box(
                        modifier = Modifier
                            .size(84.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.radialGradient(
                                    colors = listOf(InfoPurple.copy(alpha = 0.25f), InfoPurple.copy(alpha = 0.05f)),
                                )
                            )
                            .border(1.dp, InfoPurple.copy(alpha = 0.3f), CircleShape)
                    )
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(Brush.linearGradient(listOf(InfoPurple, Royal)))
                            .shadow(14.dp, CircleShape, spotColor = InfoPurple),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.NotificationsActive, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text("Ping Sent to Victoria", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    "She'll get a notification with\nPay & Reject options right away",
                    color = Muted,
                    fontSize = 12.sp,
                    lineHeight = 18.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = Urbanist,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(18.dp))

                Text("₦5,000.00", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)

                Spacer(modifier = Modifier.height(10.dp))

                Surface(
                    color = WarningOrange.copy(alpha = 0.12f),
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.dp, WarningOrange.copy(alpha = 0.25f))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(Icons.Default.Schedule, contentDescription = null, tint = WarningOrange, modifier = Modifier.size(12.dp))
                        Text("Awaiting response", color = WarningOrange, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Details Card
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Card, RoundedCornerShape(14.dp))
                        .border(1.dp, Border, RoundedCornerShape(14.dp))
                ) {
                    SuccessDetailRow("To", "Victoria (@victoria)")
                    HorizontalDivider(color = Border, modifier = Modifier.padding(horizontal = 16.dp))
                    SuccessDetailRow("Note", "Dinner at Cactus 🌮")
                    HorizontalDivider(color = Border, modifier = Modifier.padding(horizontal = 16.dp))
                    SuccessDetailRow("Sent", "Just now")
                    HorizontalDivider(color = Border, modifier = Modifier.padding(horizontal = 16.dp))
                    SuccessDetailRow("Expires", "In 48 hours")
                }
                
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@Preview
@Composable
fun PingSentPreview() {
    VeltraTheme {
        PingSentScreen({})
    }
}
