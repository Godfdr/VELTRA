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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veltra.payment.ui.SectionHeaderSmall
import com.veltra.payment.ui.theme.*

@Composable
fun PingActivityScreen(onBackClick: () -> Unit) {
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
                    Text("Ping Activity", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).verticalScroll(rememberScrollState()).padding(horizontal = 16.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SectionHeaderSmall("Pending")
            Column(modifier = Modifier.fillMaxWidth().background(Card, RoundedCornerShape(16.dp)).border(1.dp, Border, RoundedCornerShape(16.dp))) {
                ActivityPingRow("Victor", "₦5,000", "Incoming • 2h ago", true, true)
                HorizontalDivider(color = Border, modifier = Modifier.padding(horizontal = 16.dp))
                ActivityPingRow("Sarah", "₦2,500", "Outgoing • Yesterday", false, true)
            }

            SectionHeaderSmall("Completed")
            Column(modifier = Modifier.fillMaxWidth().background(Card, RoundedCornerShape(14.dp)).border(1.dp, Border, RoundedCornerShape(14.dp))) {
                ActivityPingRow("John", "₦1,200", "Received • 2 days ago", true, false)
                HorizontalDivider(color = Border, modifier = Modifier.padding(horizontal = 16.dp))
                ActivityPingRow("Musa", "₦3,000", "Sent • 3 days ago", false, false)
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun ActivityPingRow(name: String, amount: String, sub: String, isIncoming: Boolean, isPending: Boolean) {
    Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Box(modifier = Modifier.size(36.dp).background(if (isIncoming) Teal.copy(alpha = 0.1f) else Royal.copy(alpha = 0.1f), CircleShape), contentAlignment = Alignment.Center) {
                Icon(if (isIncoming) Icons.AutoMirrored.Filled.CallReceived else Icons.AutoMirrored.Filled.CallMade, contentDescription = null, tint = if (isIncoming) Teal else Royal, modifier = Modifier.size(16.dp))
            }
            Column {
                Text(name, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                Text(sub, color = Muted, fontSize = 10.sp, fontFamily = Urbanist)
            }
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(amount, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
            if (isPending) {
                Text("Pending", color = WarningOrange, fontSize = 9.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PingActivityPreview() {
    VeltraTheme {
        PingActivityScreen({})
    }
}
