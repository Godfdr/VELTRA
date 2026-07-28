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
fun TransactionHistoryScreen(onBackClick: () -> Unit) {
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
                    Text("History", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                }

                // Filter Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    HistoryFilterChip("All", selectedFilter == "All") { selectedFilter = it }
                    HistoryFilterChip("Income", selectedFilter == "Income") { selectedFilter = it }
                    HistoryFilterChip("Expense", selectedFilter == "Expense") { selectedFilter = it }
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
            // Stats Summary
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                HistoryStatCard("Total Income", "₦245,000", Teal, Modifier.weight(1f))
                HistoryStatCard("Total Expense", "₦112,400", ErrorRed, Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(24.dp))

            HistorySection("Today")
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Card, RoundedCornerShape(16.dp))
                    .border(1.dp, Border, RoundedCornerShape(16.dp))
                    .padding(horizontal = 14.dp)
            ) {
                TransactionRow(Icons.Default.FlashOn, "Electricity Bill", "Payment successful", "-₦20,444", Muted, false)
                HorizontalDivider(color = Border)
                TransactionRow(Icons.Default.Add, "Top Up", "Wallet funding", "+₦50,000", Teal, true)
            }

            Spacer(modifier = Modifier.height(24.dp))

            HistorySection("Yesterday")
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Card, RoundedCornerShape(16.dp))
                    .border(1.dp, Border, RoundedCornerShape(16.dp))
                    .padding(horizontal = 14.dp)
            ) {
                TransactionRow(Icons.Default.SyncAlt, "Currency Swap", "USD to NGN", "₦78,557", Teal, true)
                HorizontalDivider(color = Border)
                TransactionRow(Icons.Default.Restaurant, "Cactus 🌮", "Food & Drinks", "-₦12,500", Muted, false)
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun HistoryFilterChip(label: String, isActive: Boolean, onClick: (String) -> Unit) {
    Box(
        modifier = Modifier
            .background(if (isActive) Royal else Card, RoundedCornerShape(50.dp))
            .border(1.dp, if (isActive) Royal else Border, RoundedCornerShape(50.dp))
            .clickable { onClick(label) }
            .padding(horizontal = 16.dp, vertical = 7.dp)
    ) {
        Text(label, color = if (isActive) Color.White else Muted, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
    }
}

@Composable
fun HistoryStatCard(label: String, amount: String, color: Color, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(Card, RoundedCornerShape(16.dp))
            .border(1.dp, Border, RoundedCornerShape(16.dp))
            .padding(14.dp)
    ) {
        Text(label, color = Muted, fontSize = 10.sp, fontFamily = Urbanist)
        Text(amount, color = color, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist, modifier = Modifier.padding(top = 4.dp))
    }
}

@Composable
fun HistorySection(text: String) {
    Text(
        text = text.uppercase(),
        color = Muted,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp,
        modifier = Modifier.padding(bottom = 10.dp, start = 4.dp),
        fontFamily = Urbanist
    )
}

@Composable
fun TransactionRow(icon: ImageVector, title: String, sub: String, amount: String, amountColor: Color, isIncome: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(if (isIncome) Teal.copy(alpha = 0.15f) else Royal.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = if (isIncome) Teal else Royal, modifier = Modifier.size(19.dp))
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
            Text(sub, color = Muted, fontSize = 10.5.sp, fontFamily = Urbanist)
        }
        Text(amount, color = amountColor, fontSize = 13.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
    }
}

@Preview
@Composable
fun HistoryPreview() {
    VeltraTheme {
        TransactionHistoryScreen({})
    }
}
