package com.veltra.payment.ui.transaction

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.CallMade
import androidx.compose.material.icons.automirrored.filled.CallReceived
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
import com.veltra.payment.ui.SectionHeaderSmall
import com.veltra.payment.ui.theme.*

@Composable
fun PingMeHubScreen(
    onBackClick: () -> Unit,
    onRequestMoneyClick: () -> Unit,
    onActivityClick: () -> Unit
) {
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
                    Text("Ping Me Hub", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).verticalScroll(rememberScrollState()).padding(horizontal = 16.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text("Social Payments", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
            
            // Actions
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                PingActionCard("Request Money", Icons.Default.Add, Teal, Modifier.weight(1f), onRequestMoneyClick)
                PingActionCard("Ping Activity", Icons.Default.History, Royal, Modifier.weight(1f), onActivityClick)
            }

            SectionHeaderSmall("Suggested Friends")
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                PingFriendItem("VC", "Victor", true)
                PingFriendItem("SH", "Sarah", false)
                PingFriendItem("JD", "John", false)
                PingFriendItem("MK", "Musa", false)
            }

            SectionHeaderSmall("Recent Requests")
            Column(modifier = Modifier.fillMaxWidth().background(Card, RoundedCornerShape(16.dp)).border(1.dp, Border, RoundedCornerShape(16.dp))) {
                RecentPingRow("Request from @victor", "₦5,000", "2h ago", true)
                HorizontalDivider(color = Border, modifier = Modifier.padding(horizontal = 16.dp))
                RecentPingRow("Sent to @sarah", "₦2,500", "Yesterday", false)
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun PingActionCard(label: String, icon: ImageVector, color: Color, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Column(
        modifier = modifier.clip(RoundedCornerShape(16.dp)).background(Card).border(1.dp, Border, RoundedCornerShape(16.dp)).clickable { onClick() }.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(modifier = Modifier.size(40.dp).background(color.copy(alpha = 0.12f), CircleShape), contentAlignment = Alignment.Center) {
            Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(20.dp))
        }
        Text(label, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
    }
}

@Composable
fun PingFriendItem(initials: String, name: String, isOnline: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Box(contentAlignment = Alignment.BottomEnd) {
            Box(modifier = Modifier.size(52.dp).clip(CircleShape).background(Color.White.copy(alpha = 0.05f)).border(1.dp, Border, CircleShape), contentAlignment = Alignment.Center) {
                Text(initials, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
            }
            if (isOnline) {
                Box(modifier = Modifier.size(12.dp).clip(CircleShape).background(SuccessGreen).border(2.dp, Base, CircleShape))
            }
        }
        Text(name, color = Muted, fontSize = 10.sp, fontWeight = FontWeight.SemiBold, fontFamily = Urbanist)
    }
}

@Composable
fun RecentPingRow(title: String, amount: String, time: String, isIncoming: Boolean) {
    Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Box(modifier = Modifier.size(32.dp).background(if (isIncoming) Teal.copy(alpha = 0.12f) else Royal.copy(alpha = 0.12f), CircleShape), contentAlignment = Alignment.Center) {
                Icon(if (isIncoming) Icons.AutoMirrored.Filled.CallReceived else Icons.AutoMirrored.Filled.CallMade, contentDescription = null, tint = if (isIncoming) Teal else Royal, modifier = Modifier.size(16.dp))
            }
            Column {
                Text(title, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                Text(time, color = Muted, fontSize = 10.sp, fontFamily = Urbanist)
            }
        }
        Text(amount, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
    }
}

@Preview(showBackground = true)
@Composable
fun PingMeHubPreview() {
    VeltraTheme {
        PingMeHubScreen({}, {}, {})
    }
}
