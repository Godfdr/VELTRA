package com.veltra.payment.ui.pockets

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veltra.payment.ui.theme.*

@Composable
fun PocketDetailScreen(onBackClick: () -> Unit, onAddFundsClick: () -> Unit) {
    Scaffold(
        containerColor = Base,
        topBar = {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth().background(HeaderGradient).padding(horizontal = 20.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.size(34.dp).background(Color.White.copy(alpha = 0.08f), CircleShape).border(1.dp, Border, CircleShape)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White, modifier = Modifier.size(16.dp))
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("Bali Trip 2026", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                }
            }
        },
        bottomBar = {
            Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                Button(
                    onClick = onAddFundsClick,
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues(),
                    shape = RoundedCornerShape(50.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize().background(PrimaryGradient), contentAlignment = Alignment.Center) {
                        Text("Add Funds", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).verticalScroll(rememberScrollState()).padding(horizontal = 16.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Stats Card
            Card(
                modifier = Modifier.fillMaxWidth().border(1.dp, Border, RoundedCornerShape(24.dp)),
                colors = CardDefaults.cardColors(containerColor = Card),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(modifier = Modifier.size(64.dp).clip(RoundedCornerShape(18.dp)).background(WarningOrange.copy(alpha = 0.12f)), contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.FlightTakeoff, contentDescription = null, tint = WarningOrange, modifier = Modifier.size(32.dp))
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Current Savings", color = Muted, fontSize = 12.sp, fontFamily = Urbanist)
                    Text("₦320,000.00", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    LinearProgressIndicator(
                        progress = { 0.53f },
                        modifier = Modifier.fillMaxWidth().height(8.dp).clip(CircleShape),
                        color = WarningOrange,
                        trackColor = Color.White.copy(alpha = 0.05f)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("53% complete", color = WarningOrange, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                        Text("Target: ₦600k", color = Muted, fontSize = 11.sp, fontFamily = Urbanist)
                    }
                }
            }

            Text("Recent Contributions", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
            
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                ContributionRow("Weekly Auto-save", "₦5,000", "Today")
                ContributionRow("Top up", "₦50,000", "Yesterday")
                ContributionRow("Initial Deposit", "₦265,000", "July 12")
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun ContributionRow(title: String, amount: String, date: String) {
    Row(modifier = Modifier.fillMaxWidth().background(Card, RoundedCornerShape(16.dp)).border(1.dp, Border, RoundedCornerShape(16.dp)).padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
        Column {
            Text(title, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
            Text(date, color = Muted, fontSize = 10.sp, fontFamily = Urbanist)
        }
        Text(amount, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
    }
}

@Preview(showBackground = true)
@Composable
fun PocketDetailPreview() {
    VeltraTheme {
        PocketDetailScreen({}, {})
    }
}
