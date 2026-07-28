package com.veltra.payment.ui.transaction

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veltra.payment.ui.theme.*

@Composable
fun PingMeHubScreen(onBackClick: () -> Unit, onRequestMoneyClick: () -> Unit) {
    var showIncomingPing by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            containerColor = Base,
            topBar = {
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp)
                            .background(HeaderStart)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(HeaderGradient)
                            .padding(horizontal = 20.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = onBackClick,
                            modifier = Modifier
                                .size(34.dp)
                                .background(Color.White.copy(alpha = 0.08f), CircleShape)
                                .border(1.dp, Border, CircleShape)
                        ) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White, modifier = Modifier.size(16.dp))
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Text("Ping Me Hub", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                    }
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Main Action Card
                HubActionCard(
                    title = "Request Money",
                    sub = "Ping a contact and get paid instantly",
                    icon = Icons.Default.NotificationsActive,
                    color = InfoPurple,
                    onClick = onRequestMoneyClick
                )

                // Secondary Action Cards
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    HubSmallCard(title = "Activity", icon = Icons.Default.History, color = Teal, modifier = Modifier.weight(1f))
                    HubSmallCard(title = "Settings", icon = Icons.Default.Settings, color = Royal, modifier = Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(10.dp))
                Text("Active Pings", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)

                // Simulation Trigger for Demo
                Button(
                    onClick = { showIncomingPing = true },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Card)
                ) {
                    Text("Simulate Incoming Ping", color = Muted, fontSize = 12.sp, fontFamily = Urbanist)
                }
            }
        }

        // The high-fidelity overlay
        IncomingPingOverlay(
            isVisible = showIncomingPing,
            onRejectClick = { showIncomingPing = false },
            onPayClick = { showIncomingPing = false }
        )
    }
}

@Composable
fun HubActionCard(title: String, sub: String, icon: ImageVector, color: Color, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(color.copy(alpha = 0.12f))
            .border(1.dp, color.copy(alpha = 0.3f), RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(color, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
        }
        Column {
            Text(title, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
            Text(sub, color = Muted, fontSize = 11.sp, fontFamily = Urbanist)
        }
        Spacer(modifier = Modifier.weight(1f))
        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Muted)
    }
}

@Composable
fun HubSmallCard(title: String, icon: ImageVector, color: Color, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
            .background(Card)
            .border(1.dp, Border, RoundedCornerShape(18.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(color.copy(alpha = 0.15f), RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(18.dp))
        }
        Text(title, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
    }
}

@Preview
@Composable
fun PingHubPreview() {
    VeltraTheme {
        PingMeHubScreen({}, {})
    }
}
