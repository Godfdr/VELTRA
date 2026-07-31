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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veltra.payment.ui.theme.*

@Composable
fun TransactionDetailScreen(onBackClick: () -> Unit) {
    Scaffold(
        containerColor = Base,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 20.dp)
                    .statusBarsPadding(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.size(34.dp).background(Color.White.copy(alpha = 0.08f), CircleShape).border(1.dp, Border, CircleShape)
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White, modifier = Modifier.size(16.dp))
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text("Transaction Detail", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).verticalScroll(rememberScrollState()).padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            
            Box(
                modifier = Modifier.size(64.dp).clip(CircleShape).background(PrimaryGradient),
                contentAlignment = Alignment.Center
            ) {
                Text("AV", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            Text("Alex Veltra", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
            Text("@alexveltra", color = Teal, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, fontFamily = Urbanist)
            
            Spacer(modifier = Modifier.height(32.dp))
            Text("₦15,000.00", color = Color.White, fontSize = 36.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
            Text("Transaction Successful", color = SuccessGreen, fontSize = 12.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)

            Spacer(modifier = Modifier.height(48.dp))

            Column(modifier = Modifier.fillMaxWidth().background(Card, RoundedCornerShape(16.dp)).border(1.dp, Border, RoundedCornerShape(16.dp)).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                DetailItem("Payment Method", "Veltra Wallet")
                DetailItem("Transaction Type", "NFC P2P Transfer")
                DetailItem("Ref Number", "VLT-TX-99210")
                DetailItem("Date", "July 26, 2026")
                DetailItem("Time", "21:41")
            }
            
            Spacer(modifier = Modifier.height(40.dp))
            
            Button(
                onClick = { },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues(),
                shape = RoundedCornerShape(50.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize().background(PrimaryGradient), contentAlignment = Alignment.Center) {
                    Text("Report an issue", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                }
            }
        }
    }
}

@Composable
fun DetailItem(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, color = Muted, fontSize = 12.sp, fontFamily = Urbanist)
        Text(value, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionDetailPreview() {
    VeltraTheme {
        TransactionDetailScreen({})
    }
}
