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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veltra.payment.ui.theme.*

@Composable
fun TopUpScreen(onBackClick: () -> Unit) {
    var selectedAmount by remember { mutableStateOf("25,000") }
    var selectedMethod by remember { mutableStateOf("Bank Transfer") }

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
                    Text("Top Up", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                }

                // Enhanced Amount Section - Production Grade Polish
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Enter amount", color = Muted, fontSize = 11.sp, fontFamily = Urbanist, fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text("₦", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            selectedAmount, 
                            color = Color.White, 
                            fontSize = 46.sp, 
                            fontWeight = FontWeight.ExtraBold, 
                            letterSpacing = (-1.5).sp, 
                            fontFamily = Urbanist
                        )
                        // Blinking cursor simulation
                        Box(
                            modifier = Modifier
                                .padding(start = 4.dp)
                                .width(2.dp)
                                .height(40.dp)
                                .background(Teal)
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(26.dp))
                    
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        AmountChip("₦5,000", selectedAmount == "5,000", modifier = Modifier.weight(1f)) { selectedAmount = "5,000" }
                        AmountChip("₦25,000", selectedAmount == "25,000", modifier = Modifier.weight(1f)) { selectedAmount = "25,000" }
                        AmountChip("₦50,000", selectedAmount == "50,000", modifier = Modifier.weight(1f)) { selectedAmount = "50,000" }
                        AmountChip("₦100,000", selectedAmount == "100,000", modifier = Modifier.weight(1f)) { selectedAmount = "100,000" }
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                }
            }
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Surface)
                    .padding(16.dp)
            ) {
                Button(
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .shadow(
                            elevation = 24.dp, 
                            shape = RoundedCornerShape(50.dp), 
                            ambientColor = Royal.copy(alpha = 0.35f), 
                            spotColor = Royal.copy(alpha = 0.35f)
                        ),
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
                        Text("Continue", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
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
                .padding(horizontal = 16.dp, vertical = 20.dp)
        ) {
            Text(
                "Top up method".uppercase(),
                color = Muted,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                fontFamily = Urbanist,
                modifier = Modifier.padding(bottom = 10.dp, start = 2.dp)
            )

            MethodRow(
                icon = Icons.Default.AccountBalance,
                title = "Bank Transfer",
                sub = "Top up via your linked NGN account",
                color = Royal,
                isSelected = selectedMethod == "Bank Transfer",
                onClick = { selectedMethod = "Bank Transfer" }
            )
            Spacer(modifier = Modifier.height(10.dp))
            MethodRow(
                icon = Icons.Default.SyncAlt,
                title = "Convert Balance",
                sub = "Convert from another currency wallet",
                color = Teal,
                isSelected = selectedMethod == "Convert Balance",
                onClick = { selectedMethod = "Convert Balance" }
            )
            Spacer(modifier = Modifier.height(10.dp))
            MethodRow(
                icon = Icons.Default.Store,
                title = "Agent Top-up",
                sub = "Deposit cash at a Veltra agent",
                color = WarningOrange,
                isSelected = selectedMethod == "Agent Top-up",
                onClick = { selectedMethod = "Agent Top-up" }
            )
            Spacer(modifier = Modifier.height(10.dp))
            MethodRow(
                icon = Icons.Default.NotificationsActive,
                title = "Ping Me",
                sub = "Request money from a contact",
                color = InfoPurple,
                isSelected = selectedMethod == "Ping Me",
                onClick = { selectedMethod = "Ping Me" }
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun AmountChip(label: String, isActive: Boolean, modifier: Modifier = Modifier, onClick: (String) -> Unit) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(if (isActive) Royal else Card)
            .border(1.dp, if (isActive) Royal else Border, RoundedCornerShape(12.dp))
            .clickable { onClick(label) }
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(label, color = if (isActive) Color.White else Muted, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
    }
}

@Composable
fun MethodRow(icon: ImageVector, title: String, sub: String, color: Color, isSelected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(if (isSelected) color.copy(alpha = 0.08f) else Card)
            .border(1.dp, if (isSelected) color.copy(alpha = 0.4f) else Border, RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(color.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(19.dp))
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
            Text(sub, color = Muted, fontSize = 10.5.sp, fontFamily = Urbanist, fontWeight = FontWeight.Medium)
        }
        Box(
            modifier = Modifier
                .size(18.dp)
                .clip(CircleShape)
                .border(1.5.dp, if (isSelected) Royal else Border, CircleShape)
                .background(if (isSelected) Royal else Color.Transparent)
                .padding(4.dp)
        ) {
            if (isSelected) {
                Box(modifier = Modifier.fillMaxSize().background(Color.White, CircleShape))
            }
        }
    }
}

@Preview
@Composable
fun TopUpPreview() {
    VeltraTheme {
        TopUpScreen({})
    }
}
