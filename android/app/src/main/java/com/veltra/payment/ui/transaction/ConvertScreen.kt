package com.veltra.payment.ui.transaction

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.SyncAlt
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veltra.payment.ui.theme.*

@Composable
fun ConvertScreen(onBackClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Base)
    ) {
        // Deep Blue/Purple Header Glow - Matching the Insp exactly
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF131F4F).copy(alpha = 0.8f),
                            Color(0xFF0D1530).copy(alpha = 0.4f),
                            Base
                        )
                    )
                )
        )

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 20.dp)
                        .statusBarsPadding(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.08f))
                            .border(1.dp, Border, CircleShape)
                            .clickable { onBackClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White, modifier = Modifier.size(16.dp))
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Text("Convert", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                    Spacer(modifier = Modifier.weight(1.2f))
                }
            },
            bottomBar = {
                // Bottom Nav from Inspo (Icons only, active highlighted)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Card)
                        .border(1.dp, Border)
                        .padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    NavIcon(icon = Icons.Default.SyncAlt, isActive = true)
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp)
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                Text("Add amount and select currency", color = Muted, fontSize = 13.sp, fontFamily = Urbanist)
                Spacer(modifier = Modifier.height(20.dp))

                // Unified Conversion Card - Exact replica of the Inspo container
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Card, RoundedCornerShape(24.dp))
                        .border(1.dp, Border, RoundedCornerShape(24.dp))
                        .padding(20.dp)
                ) {
                    // From Section
                    Text("From", color = Muted, fontSize = 12.sp, fontFamily = Urbanist)
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("$50,00.00", color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
                        CurrencyChip(flag = "🇺🇸", code = "USD")
                    }

                    // Centered Swap Button overlapping the two sections
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        // Deep glow behind the swap icon
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .background(
                                    Brush.radialGradient(
                                        colors = listOf(Royal.copy(alpha = 0.4f), Color.Transparent)
                                    )
                                )
                        )
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .shadow(8.dp, CircleShape, spotColor = Royal)
                                .clip(CircleShape)
                                .background(Royal)
                                .border(2.dp, Card, CircleShape)
                                .clickable { },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.SyncAlt, contentDescription = "Swap", tint = Color.White, modifier = Modifier.size(20.dp))
                        }
                    }

                    // To Section
                    Text("Amount you will receive", color = Muted, fontSize = 12.sp, fontFamily = Urbanist)
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Value JD78,557.92 in Muted text as per inspo
                        Text("JD78,557.92", color = Muted, fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
                        CurrencyChip(flag = "🇲🇽", code = "MEX")
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Balance Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Current Balance ($)", color = Color.White, fontSize = 14.sp, fontFamily = Urbanist)
                    Text("$50,00.00", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, fontFamily = Urbanist)
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Detail Rows Card - Matches the list style in Inspo
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Card2, RoundedCornerShape(20.dp))
                        .border(1.dp, Border, RoundedCornerShape(20.dp))
                ) {
                    DetailRow(label = "Conversion fee", value = "JD 0.10")
                    Divider(color = Border, modifier = Modifier.padding(horizontal = 16.dp))
                    DetailRow(label = "Amount converted", value = "JD 78,557.82")
                    Divider(color = Border, modifier = Modifier.padding(horizontal = 16.dp))
                    DetailRow(label = "Today's rate", value = "JD = 1 $1.6")
                }

                Spacer(modifier = Modifier.height(48.dp))

                // Exact Glassmorphism Convert Button
                Button(
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .shadow(
                            elevation = 24.dp,
                            shape = RoundedCornerShape(50.dp),
                            ambientColor = Ultra.copy(alpha = 0.5f),
                            spotColor = Ultra.copy(alpha = 0.5f)
                        ),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues(),
                    shape = RoundedCornerShape(50.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(Ultra.copy(alpha = 0.7f), Teal.copy(alpha = 0.7f))
                                )
                            )
                            .border(1.dp, Color.White.copy(alpha = 0.25f), RoundedCornerShape(50.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Convert", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                    }
                }
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@Composable
fun CurrencyChip(flag: String, code: String) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(50.dp))
            .background(Color.White.copy(alpha = 0.08f))
            .border(1.dp, Border, RoundedCornerShape(50.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(flag, fontSize = 18.sp)
        Spacer(modifier = Modifier.width(6.dp))
        Text(code, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
        Spacer(modifier = Modifier.width(4.dp))
        Text("▾", color = Muted, fontSize = 10.sp)
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = Muted, fontSize = 13.sp, fontFamily = Urbanist)
        Text(value, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, fontFamily = Urbanist)
    }
}

@Composable
fun NavIcon(icon: ImageVector, isActive: Boolean = false) {
    Box(
        modifier = Modifier
            .size(44.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(if (isActive) Royal.copy(alpha = 0.18f) else Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        Icon(icon, contentDescription = null, tint = if (isActive) Color.White else Muted, modifier = Modifier.size(22.dp))
    }
}

@Preview
@Composable
fun ConvertPreview() {
    VeltraTheme {
        ConvertScreen({})
    }
}
