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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veltra.payment.ui.theme.*

@Composable
fun AddFundsPocketScreen(onBackClick: () -> Unit, onDoneClick: () -> Unit) {
    var amount by remember { mutableStateOf("25,000") }
    var autoSaveEnabled by remember { mutableStateOf(true) }

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
                    Text("Add Funds", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                }

                // Amount Hero
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("How much would you like to add?", color = Muted, fontSize = 11.sp, fontFamily = Urbanist, fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("₦", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(amount, color = Color.White, fontSize = 38.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = (-1).sp, fontFamily = Urbanist)
                    }
                    Spacer(modifier = Modifier.height(12.dp))
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
                        Text("Add ₦$amount", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
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
                .padding(horizontal = 16.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Target Card
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Card, RoundedCornerShape(16.dp))
                    .border(1.dp, Border, RoundedCornerShape(16.dp))
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(RoundedCornerShape(13.dp))
                        .background(WarningOrange.copy(alpha = 0.15f))
                        .border(1.dp, WarningOrange.copy(alpha = 0.25f), RoundedCornerShape(13.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.FlightTakeoff, contentDescription = null, tint = WarningOrange, modifier = Modifier.size(20.dp))
                }
                Column {
                    Text("Bali Trip 2026", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                    Text("₦320,000 saved of ₦600,000", color = Muted, fontSize = 10.5.sp, fontFamily = Urbanist)
                }
            }

            Text("Fund from".uppercase(), color = Muted, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp, fontFamily = Urbanist)

            // Source Card
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Card, RoundedCornerShape(14.dp))
                    .border(1.dp, Border, RoundedCornerShape(14.dp))
                    .clickable { }
                    .padding(13.dp, 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Royal.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.AccountBalanceWallet, contentDescription = null, tint = Royal, modifier = Modifier.size(16.dp))
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text("Main Wallet", color = Color.White, fontSize = 12.5.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                    Text("₦100,000.00 available", color = Muted, fontSize = 10.5.sp, fontFamily = Urbanist)
                }
                Text("›", color = Muted, fontSize = 14.sp)
            }

            // Auto-Save Toggle
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Card, RoundedCornerShape(14.dp))
                    .border(1.dp, Border, RoundedCornerShape(14.dp))
                    .padding(13.dp, 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(InfoPurple.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Timer, contentDescription = null, tint = InfoPurple, modifier = Modifier.size(16.dp))
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text("Auto-save weekly", color = Color.White, fontSize = 12.5.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                    Text("Automatically add this amount every week", color = Muted, fontSize = 10.sp, fontFamily = Urbanist)
                }
                Switch(
                    checked = autoSaveEnabled,
                    onCheckedChange = { autoSaveEnabled = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = InfoPurple,
                        uncheckedThumbColor = Muted,
                        uncheckedTrackColor = Card2
                    )
                )
            }

            // Note
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Teal.copy(alpha = 0.05f), RoundedCornerShape(12.dp))
                    .border(1.dp, Teal.copy(alpha = 0.1f), RoundedCornerShape(12.dp))
                    .padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(Icons.Default.Info, contentDescription = null, tint = Teal, modifier = Modifier.size(16.dp))
                Text(
                    "All 4 contributors will see this deposit reflected in the pocket immediately.",
                    color = Teal,
                    fontSize = 10.5.sp,
                    lineHeight = 16.sp,
                    fontFamily = Urbanist,
                    fontWeight = FontWeight.Medium
                )
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Preview
@Composable
fun AddFundsPreview() {
    VeltraTheme {
        AddFundsPocketScreen({}, {})
    }
}
