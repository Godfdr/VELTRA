package com.veltra.payment.ui.transaction

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veltra.payment.ui.theme.*

@Composable
fun TransactionDetailScreen(onBackClick: () -> Unit) {
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
                        .background(Brush.verticalGradient(listOf(HeaderStart, Base)))
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
                    Text("Details", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                }

                // Hero Details Section
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(Brush.linearGradient(listOf(Royal.copy(alpha = 0.3f), Teal.copy(alpha = 0.2f))))
                            .border(1.dp, Royal.copy(alpha = 0.3f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.FlashOn, contentDescription = null, tint = Teal, modifier = Modifier.size(28.dp))
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    Text("₦20,444.40", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = (-1).sp, fontFamily = Urbanist)
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        color = Teal.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(20.dp),
                        border = BorderStroke(1.dp, Teal.copy(alpha = 0.3f))
                    ) {
                        Text(
                            "Successful", 
                            color = Teal, 
                            fontSize = 11.sp, 
                            fontWeight = FontWeight.Bold, 
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
                            fontFamily = Urbanist
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Base)
                    .padding(16.dp)
            ) {
                Button(
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .border(1.dp, Royal.copy(alpha = 0.3f), RoundedCornerShape(50.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = Royal.copy(alpha = 0.15f)),
                    shape = RoundedCornerShape(50.dp)
                ) {
                    Text("Download Receipt", color = Teal, fontSize = 15.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Card, RoundedCornerShape(14.dp))
                    .border(1.dp, Border, RoundedCornerShape(14.dp))
            ) {
                DetailItemRow("Date", "Jun 20th 2026, 09:41PM")
                HorizontalDivider(color = Border, modifier = Modifier.padding(horizontal = 16.dp))
                DetailItemRow("Bill", "Electricity")
                HorizontalDivider(color = Border, modifier = Modifier.padding(horizontal = 16.dp))
                DetailItemRow("Provider", "EKEDC")
                HorizontalDivider(color = Border, modifier = Modifier.padding(horizontal = 16.dp))
                DetailItemRow("Meter number", "6543154141454")
                HorizontalDivider(color = Border, modifier = Modifier.padding(horizontal = 16.dp))
                DetailItemRow("Type", "Prepaid")
                HorizontalDivider(color = Border, modifier = Modifier.padding(horizontal = 16.dp))
                DetailItemRow("Address", "4 Ablett, Lagos")
                HorizontalDivider(color = Border, modifier = Modifier.padding(horizontal = 16.dp))
                DetailItemRow("Phone number", "08655413421321")
                HorizontalDivider(color = Border, modifier = Modifier.padding(horizontal = 16.dp))
                DetailItemRow("Amount", "₦20,215")
                HorizontalDivider(color = Border, modifier = Modifier.padding(horizontal = 16.dp))
                DetailItemRow("Fee", "₦200")
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun DetailItemRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = Muted, fontSize = 12.sp, fontFamily = Urbanist, fontWeight = FontWeight.Medium)
        Text(value, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, fontFamily = Urbanist)
    }
}

@Preview
@Composable
fun TransactionDetailPreview() {
    VeltraTheme {
        TransactionDetailScreen({})
    }
}
