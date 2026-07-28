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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veltra.payment.ui.theme.*

@Composable
fun TapGoScreen(onBackClick: () -> Unit) {
    var selectedTab by remember { mutableStateOf("Phone") }

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
                    Text("Tap & Go", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                }

                // Tabs - Exact Reference
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp)
                        .background(Card, RoundedCornerShape(14.dp))
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    TapTab("Phone", selectedTab == "Phone") { selectedTab = it }
                    TapTab("Card", selectedTab == "Card") { selectedTab = it }
                    TapTab("Reader", selectedTab == "Reader") { selectedTab = it }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // Pulsing Ring Animation Visual - Polished for warmth
            Box(
                modifier = Modifier.size(180.dp),
                contentAlignment = Alignment.Center
            ) {
                // Outer Ring
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .background(Royal.copy(alpha = 0.06f))
                        .border(1.dp, Royal.copy(alpha = 0.15f), CircleShape)
                )
                // Mid Ring
                Box(
                    modifier = Modifier
                        .size(136.dp)
                        .clip(CircleShape)
                        .background(Royal.copy(alpha = 0.1f))
                        .border(1.dp, Royal.copy(alpha = 0.25f), CircleShape)
                )
                // Core
                Box(
                    modifier = Modifier
                        .size(76.dp)
                        .clip(CircleShape)
                        .background(PrimaryGradient)
                        .border(2.dp, Border, CircleShape)
                        .shadow(32.dp, CircleShape, spotColor = Royal),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.PhonelinkRing, contentDescription = null, tint = Color.White, modifier = Modifier.size(32.dp))
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text("Phone to Phone", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Hold your phone near another\nVeltra user's phone to transfer",
                color = Muted,
                fontSize = 12.sp,
                lineHeight = 18.sp,
                textAlign = TextAlign.Center,
                fontFamily = Urbanist,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.weight(1f))

            // Wallet Pill - Matched to HTML
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50.dp))
                    .background(Card2)
                    .border(1.dp, Border, RoundedCornerShape(50.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clickable { }
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Box(modifier = Modifier.size(32.dp).clip(CircleShape).background(Royal.copy(alpha = 0.15f)), contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.AccountBalanceWallet, contentDescription = null, tint = Royal, modifier = Modifier.size(15.dp))
                    }
                    Column {
                        Text("Main Wallet", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                        Text("$ 100,000.00", color = Muted, fontSize = 10.sp, fontFamily = Urbanist, fontWeight = FontWeight.Medium)
                    }
                    Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, tint = Sub, modifier = Modifier.size(14.dp))
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Primary CTA
            Button(
                onClick = { },
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
                    Text("Start Tap & Go", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                }
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun RowScope.TapTab(label: String, isActive: Boolean, onClick: (String) -> Unit) {
    Box(
        modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(10.dp))
            .background(if (isActive) Royal else Color.Transparent)
            .clickable { onClick(label) }
            .padding(vertical = 9.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(label, color = if (isActive) Color.White else Muted, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
    }
}

@Preview
@Composable
fun TapGoPreview() {
    VeltraTheme {
        TapGoScreen({})
    }
}
