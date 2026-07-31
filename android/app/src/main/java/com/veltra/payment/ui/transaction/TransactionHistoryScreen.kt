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
fun TransactionHistoryScreen(onBackClick: () -> Unit) {
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
                    Text("Transactions", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).verticalScroll(rememberScrollState()).padding(horizontal = 16.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            SectionHeaderSmall("Recent")
            Column(modifier = Modifier.fillMaxWidth().background(Card, RoundedCornerShape(16.dp)).border(1.dp, Border, RoundedCornerShape(16.dp))) {
                HistoryRow("Salary Payment", "₦450,000", "Incoming • Today", true)
                HorizontalDivider(color = Border, modifier = Modifier.padding(horizontal = 16.dp))
                HistoryRow("Starbucks Coffee", "₦2,400", "Outgoing • Yesterday", false)
                HorizontalDivider(color = Border, modifier = Modifier.padding(horizontal = 16.dp))
                HistoryRow("NFC Transfer sarah", "₦15,000", "Outgoing • Yesterday", false)
            }

            SectionHeaderSmall("Last Week")
            Column(modifier = Modifier.fillMaxWidth().background(Card, RoundedCornerShape(14.dp)).border(1.dp, Border, RoundedCornerShape(14.dp))) {
                HistoryRow("Electricity Bill", "₦5,000", "Outgoing • July 18", false)
                HorizontalDivider(color = Border, modifier = Modifier.padding(horizontal = 16.dp))
                HistoryRow("Top up Wallet", "₦50,000", "Incoming • July 15", true)
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun HistoryRow(title: String, amount: String, sub: String, isIncome: Boolean) {
    Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Box(modifier = Modifier.size(36.dp).background(if (isIncome) Teal.copy(alpha = 0.1f) else Royal.copy(alpha = 0.1f), CircleShape), contentAlignment = Alignment.Center) {
                Icon(if (isIncome) Icons.AutoMirrored.Filled.CallReceived else Icons.AutoMirrored.Filled.CallMade, contentDescription = null, tint = if (isIncome) Teal else Royal, modifier = Modifier.size(16.dp))
            }
            Column {
                Text(title, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                Text(sub, color = Muted, fontSize = 10.sp, fontFamily = Urbanist)
            }
        }
        Text(amount, color = if (isIncome) SuccessGreen else Color.White, fontSize = 13.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionHistoryPreview() {
    VeltraTheme {
        TransactionHistoryScreen({})
    }
}
