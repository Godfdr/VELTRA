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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veltra.payment.ui.theme.*
import java.util.*

@Composable
fun ReceiptScreen(
    transactionId: String = "VLT-${UUID.randomUUID().toString().take(8).uppercase()}",
    amount: String,
    recipient: String,
    type: String = "NFC Transfer",
    onBackClick: () -> Unit,
    onDownloadClick: () -> Unit = {}
) {
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
                Text("Transaction Receipt", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
            }
        },
        bottomBar = {
            Row(
                modifier = Modifier.fillMaxWidth().padding(24.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = onDownloadClick,
                    modifier = Modifier.weight(1f).height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues(),
                    shape = RoundedCornerShape(50.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize().background(PrimaryGradient), contentAlignment = Alignment.Center) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(Icons.Default.Download, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                            Text("Download PDF", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                        }
                    }
                }
                IconButton(
                    onClick = { },
                    modifier = Modifier.size(56.dp).background(Card, RoundedCornerShape(50.dp)).border(1.dp, Border, RoundedCornerShape(50.dp))
                ) {
                    Icon(Icons.Default.Share, contentDescription = "Share", tint = Color.White, modifier = Modifier.size(20.dp))
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).verticalScroll(rememberScrollState()).padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Card, RoundedCornerShape(24.dp))
                    .border(1.dp, Border, RoundedCornerShape(24.dp))
                    .padding(vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier.size(54.dp).clip(RoundedCornerShape(16.dp)).background(PrimaryGradient),
                    contentAlignment = Alignment.Center
                ) {
                    Text("V", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                Text("VELTRA SMART BANKING", color = Muted, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.5.sp, fontFamily = Urbanist)
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Surface(
                    color = SuccessGreen.copy(alpha = 0.12f),
                    shape = RoundedCornerShape(50.dp),
                    border = BorderStroke(1.dp, SuccessGreen.copy(alpha = 0.3f))
                ) {
                    Text("Transaction Successful", color = SuccessGreen, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp), fontFamily = Urbanist)
                }

                Spacer(modifier = Modifier.height(24.dp))
                Text("₦$amount", color = Color.White, fontSize = 38.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
                Text(type, color = Muted, fontSize = 12.sp, fontFamily = Urbanist, fontWeight = FontWeight.Medium)

                Spacer(modifier = Modifier.height(40.dp))
                
                Canvas(modifier = Modifier.fillMaxWidth().height(1.dp).padding(horizontal = 24.dp)) {
                    drawLine(
                        color = Border,
                        start = androidx.compose.ui.geometry.Offset(0f, 0f),
                        end = androidx.compose.ui.geometry.Offset(size.width, 0f),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp), verticalArrangement = Arrangement.spacedBy(18.dp)) {
                    ReceiptRow("Recipient", recipient)
                    ReceiptRow("Ref Number", transactionId)
                    ReceiptRow("Date & Time", "July 26, 2026 • 21:41")
                    ReceiptRow("Status", "Completed")
                    ReceiptRow("Payment Method", "Veltra NFC P2P")
                }
                
                Spacer(modifier = Modifier.height(40.dp))
                
                Text(
                    "This is a digitally generated receipt and does not require a physical signature.",
                    color = Muted.copy(alpha = 0.5f),
                    fontSize = 9.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 40.dp),
                    fontFamily = Urbanist,
                    lineHeight = 14.sp
                )
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReceiptPreview() {
    VeltraTheme {
        ReceiptScreen(amount = "50,000", recipient = "@victor", onBackClick = {})
    }
}

@Composable
fun ReceiptRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, color = Muted, fontSize = 13.sp, fontFamily = Urbanist)
        Text(value, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist, textAlign = TextAlign.End)
    }
}
