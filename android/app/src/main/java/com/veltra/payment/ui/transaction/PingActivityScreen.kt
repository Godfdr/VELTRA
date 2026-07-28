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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veltra.payment.ui.theme.*

@Composable
fun PingActivityScreen(onBackClick: () -> Unit) {
    var selectedFilter by remember { mutableStateOf("All") }

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
                    Text("Ping Activity", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = { }, modifier = Modifier.size(34.dp)) {
                        Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White)
                    }
                }

                // Filter Chips
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("All", "Sent", "Received", "Pending").forEach { filter ->
                        PingFilterChip(filter, selectedFilter == filter) { selectedFilter = it }
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            HistorySection("Today")
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Card, RoundedCornerShape(14.dp))
                    .border(1.dp, Border, RoundedCornerShape(14.dp))
                    .padding(horizontal = 14.dp)
            ) {
                PingActivityRow(Icons.Default.CallMade, "Ping sent to Victoria", "Dinner at Cactus 🌮", "₦5,000", "Pending", WarningOrange, false)
                HorizontalDivider(color = Border)
                PingActivityRow(Icons.Default.CallReceived, "Ping received from Shawn", "Movie tickets", "₦12,000", "Paid", Teal, true)
            }

            Spacer(modifier = Modifier.height(24.dp))

            HistorySection("Yesterday")
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Card, RoundedCornerShape(14.dp))
                    .border(1.dp, Border, RoundedCornerShape(14.dp))
                    .padding(horizontal = 14.dp)
            ) {
                PingActivityRow(Icons.Default.CallMade, "Ping sent to Kyle", "Rent split", "₦30,000", "Rejected", Color(0xFFF87171), false)
                HorizontalDivider(color = Border)
                PingActivityRow(Icons.Default.CallReceived, "Ping received from Marjorie", "Grocery split", "₦8,500", "Paid", Teal, true)
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun PingFilterChip(label: String, isActive: Boolean, onClick: (String) -> Unit) {
    Box(
        modifier = Modifier
            .background(if (isActive) InfoPurple else Card, RoundedCornerShape(50.dp))
            .border(1.dp, if (isActive) InfoPurple else Border, RoundedCornerShape(50.dp))
            .clickable { onClick(label) }
            .padding(horizontal = 14.dp, vertical = 7.dp)
    ) {
        Text(label, color = if (isActive) Color.White else Muted, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
    }
}

@Composable
fun PingActivityRow(icon: ImageVector, title: String, sub: String, amount: String, status: String, statusColor: Color, isReceived: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(if (isReceived) Teal.copy(alpha = 0.12f) else InfoPurple.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = if (isReceived) Teal else InfoPurple, modifier = Modifier.size(18.dp))
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist, maxLines = 1)
            Text(sub, color = Muted, fontSize = 10.5.sp, fontFamily = Urbanist, maxLines = 1)
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(amount, color = if (status == "Rejected") Color(0xFFF87171) else if (status == "Paid") Teal else Color.White, fontSize = 13.5.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
            Text(status, color = statusColor, fontSize = 9.5.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
        }
    }
}

@Preview
@Composable
fun PingActivityPreview() {
    VeltraTheme {
        PingActivityScreen({})
    }
}
