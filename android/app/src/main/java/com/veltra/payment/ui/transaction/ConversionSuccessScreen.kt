package com.veltra.payment.ui.transaction

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veltra.payment.ui.theme.*

@Composable
fun ConversionSuccessScreen(onDoneClick: () -> Unit) {
    Scaffold(
        containerColor = Base,
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Base)
                    .padding(16.dp)
            ) {
                Button(
                    onClick = onDoneClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .shadow(elevation = 24.dp, shape = RoundedCornerShape(50.dp), ambientColor = Royal.copy(alpha = 0.35f), spotColor = Royal.copy(alpha = 0.35f)),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues(),
                    shape = RoundedCornerShape(50.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(PrimaryGradient),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Done", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
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
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            // Success Hero - Pulsing Ring Style
            Box(contentAlignment = Alignment.Center) {
                Box(
                    modifier = Modifier
                        .size(88.dp)
                        .clip(CircleShape)
                        .background(SuccessGradient)
                        .border(1.dp, Teal.copy(alpha = 0.3f), CircleShape)
                )
                Box(
                    modifier = Modifier
                        .size(58.dp)
                        .clip(CircleShape)
                        .background(PrimaryGradient)
                        .shadow(14.dp, CircleShape, spotColor = Teal),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(26.dp))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Conversion Successful", color = Color.White, fontSize = 19.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                "Your funds have been converted\nand added to your NGN wallet",
                color = Muted,
                fontSize = 12.sp,
                lineHeight = 18.sp,
                textAlign = TextAlign.Center,
                fontFamily = Urbanist,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Conversion Flow Card
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                AmountBlock("$50,000", "🇺🇸 USD")
                Spacer(modifier = Modifier.width(14.dp))
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = Teal, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(14.dp))
                AmountBlock("₦78,557.92", "🇳🇬 NGN")
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Details Card
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Card, RoundedCornerShape(14.dp))
                    .border(1.dp, Border, RoundedCornerShape(14.dp))
            ) {
                SuccessDetailRow("Date", "Jul 26th 2026, 9:41PM")
                HorizontalDivider(color = Border, modifier = Modifier.padding(horizontal = 16.dp))
                SuccessDetailRow("Reference", "VLT-CX-88213")
                HorizontalDivider(color = Border, modifier = Modifier.padding(horizontal = 16.dp))
                SuccessDetailRow("Rate applied", "₦1,571.16 / $1")
                HorizontalDivider(color = Border, modifier = Modifier.padding(horizontal = 16.dp))
                SuccessDetailRow("Fee", "₦0.10")
            }
        }
    }
}

@Composable
fun AmountBlock(amount: String, currency: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(amount, color = Color.White, fontSize = 17.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
        Text(currency, color = Muted, fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
    }
}

@Composable
fun SuccessDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 13.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = Muted, fontSize = 12.sp, fontFamily = Urbanist, fontWeight = FontWeight.Medium)
        Text(value, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, fontFamily = Urbanist)
    }
}

@Preview
@Composable
fun SuccessPreview() {
    VeltraTheme {
        ConversionSuccessScreen({})
    }
}
