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
import com.veltra.payment.ui.SectionHeaderSmall
import com.veltra.payment.ui.theme.*

@Composable
fun TopUpScreen(onBackClick: () -> Unit) {
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
                    Text("Top Up Wallet", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).verticalScroll(rememberScrollState()).padding(horizontal = 16.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text("Select payment method", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
            
            SectionHeaderSmall("Local Methods")
            Column(modifier = Modifier.fillMaxWidth().background(Card, RoundedCornerShape(14.dp)).border(1.dp, Border, RoundedCornerShape(14.dp))) {
                TopUpMethodRow(Icons.Default.AccountBalance, "Bank Transfer", "Instant • No fees", true)
                HorizontalDivider(color = Border, modifier = Modifier.padding(horizontal = 16.dp))
                TopUpMethodRow(Icons.Default.CreditCard, "Debit Card", "Instant • 1% fee", false)
            }

            SectionHeaderSmall("Alternative Methods")
            Column(modifier = Modifier.fillMaxWidth().background(Card, RoundedCornerShape(14.dp)).border(1.dp, Border, RoundedCornerShape(14.dp))) {
                TopUpMethodRow(Icons.Default.Storefront, "Agent Top-up", "Visit Veltra partner", false)
                HorizontalDivider(color = Border, modifier = Modifier.padding(horizontal = 16.dp))
                TopUpMethodRow(Icons.Default.CurrencyBitcoin, "Crypto Deposit", "USDT, BTC, ETH", false)
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun TopUpMethodRow(icon: ImageVector, title: String, sub: String, isRecommended: Boolean) {
    Row(modifier = Modifier.fillMaxWidth().clickable { }.padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        Box(modifier = Modifier.size(40.dp).background(Royal.copy(alpha = 0.12f), CircleShape), contentAlignment = Alignment.Center) {
            Icon(icon, contentDescription = null, tint = Royal, modifier = Modifier.size(20.dp))
        }
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(title, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                if (isRecommended) {
                    Box(modifier = Modifier.background(SuccessGreen.copy(alpha = 0.15f), RoundedCornerShape(4.dp)).padding(horizontal = 6.dp, vertical = 2.dp)) {
                        Text("FAST", color = SuccessGreen, fontSize = 8.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
                    }
                }
            }
            Text(sub, color = Muted, fontSize = 11.sp, fontFamily = Urbanist)
        }
        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Muted, modifier = Modifier.size(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun TopUpPreview() {
    VeltraTheme {
        TopUpScreen({})
    }
}
