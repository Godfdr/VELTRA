package com.veltra.payment.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veltra.payment.R
import com.veltra.payment.ui.theme.*

@Composable
fun DashboardScreen(
    onConvertClick: () -> Unit,
    onAddMoneyClick: () -> Unit,
    onProfileClick: () -> Unit,
    onTapGoClick: () -> Unit,
    onHistoryClick: () -> Unit,
    onPingMeClick: () -> Unit,
    onTasksClick: () -> Unit,
    onPocketClick: () -> Unit,
    onDataClick: () -> Unit,
    onElectricityClick: () -> Unit,
    onAirtimeClick: () -> Unit = {},
    viewModel: DashboardViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val isBalanceVisible by viewModel.isBalanceVisible.collectAsState()

    DashboardContent(
        isBalanceVisible = isBalanceVisible,
        onToggleBalance = { viewModel.toggleBalanceVisibility() },
        onConvertClick = onConvertClick,
        onAddMoneyClick = onAddMoneyClick,
        onProfileClick = onProfileClick,
        onTapGoClick = onTapGoClick,
        onHistoryClick = onHistoryClick,
        onPingMeClick = onPingMeClick,
        onTasksClick = onTasksClick,
        onPocketClick = onPocketClick,
        onDataClick = onDataClick,
        onElectricityClick = onElectricityClick,
        onAirtimeClick = onAirtimeClick
    )
}

@Composable
fun DashboardContent(
    isBalanceVisible: Boolean,
    onToggleBalance: () -> Unit,
    onConvertClick: () -> Unit,
    onAddMoneyClick: () -> Unit,
    onProfileClick: () -> Unit,
    onTapGoClick: () -> Unit,
    onHistoryClick: () -> Unit,
    onPingMeClick: () -> Unit,
    onTasksClick: () -> Unit,
    onPocketClick: () -> Unit,
    onDataClick: () -> Unit,
    onElectricityClick: () -> Unit,
    onAirtimeClick: () -> Unit,
    showPromo: Boolean = false
) {
    val scrollState = rememberScrollState()

    Scaffold(
        bottomBar = { 
            VeltraFooter(
                activeRoute = "dashboard",
                onDashboardClick = { },
                onPocketsClick = onPocketClick,
                onProfileClick = onProfileClick,
                onTapGoClick = onTapGoClick
            ) 
        },
        containerColor = Base
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
        ) {
            HeaderSection(
                isBalanceVisible = isBalanceVisible,
                onToggleBalance = onToggleBalance,
                onConvertClick = onConvertClick,
                onAddMoneyClick = onAddMoneyClick,
                onProfileClick = onProfileClick,
                onTapGoClick = onTapGoClick,
                onTasksClick = onTasksClick
            )
            
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Surface)
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                OverviewBanner()
                
                if (showPromo) {
                    PromoBanner()
                } else {
                    // Wallet & Pocket Overview
                    WalletPocketOverview(
                        isBalanceVisible = isBalanceVisible,
                        onAddWalletClick = { }, 
                        onPocketClick = onPocketClick
                    )
                }

                QuickAccessSection(
                    onHistoryClick = onHistoryClick, 
                    onDataClick = onDataClick, 
                    onElectricityClick = onElectricityClick,
                    onAirtimeClick = onAirtimeClick
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .background(Brush.linearGradient(listOf(Royal.copy(alpha = 0.05f), Color.Transparent)))
                        .border(1.dp, Border, RoundedCornerShape(18.dp))
                        .padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Veltra Smart Banking • 2026",
                        color = Sub,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        fontFamily = Urbanist
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@Composable
fun HeaderSection(
    isBalanceVisible: Boolean,
    onToggleBalance: () -> Unit,
    onConvertClick: () -> Unit,
    onAddMoneyClick: () -> Unit,
    onProfileClick: () -> Unit,
    onTapGoClick: () -> Unit,
    onTasksClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(HeaderGradient)
            .statusBarsPadding()
            .padding(horizontal = 20.dp, vertical = 20.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(PrimaryGradient)
                        .border(2.dp, Color.White.copy(alpha = 0.15f), CircleShape)
                        .clickable { onProfileClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Text("V", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp, fontFamily = Urbanist)
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.clickable { onProfileClick() }) {
                    Text("Hello 👋", color = Muted, fontSize = 11.sp, fontFamily = Urbanist)
                    Text("Alex Veltra", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                IconButton(
                    onClick = onTasksClick,
                    modifier = Modifier.size(34.dp).background(Color.White.copy(alpha = 0.08f), CircleShape).border(1.dp, Border, CircleShape)
                ) {
                    Icon(Icons.Default.Notifications, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        Card(
            modifier = Modifier.fillMaxWidth().border(1.dp, Border, RoundedCornerShape(20.dp)),
            colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.3f)),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Total balance", color = Muted, fontSize = 11.sp, fontFamily = Urbanist, fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.height(6.dp))
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (isBalanceVisible) "₦100,000.00" else "•••••",
                        color = Color.White,
                        fontSize = if (isBalanceVisible) 24.sp else 29.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = if (isBalanceVisible) (-0.5).sp else 4.sp,
                        fontFamily = Urbanist,
                        modifier = (if (!isBalanceVisible) Modifier.padding(top = 8.dp) else Modifier)
                            .padding(horizontal = 40.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Visible
                    )
                    IconButton(
                        onClick = onToggleBalance, 
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 4.dp)
                            .size(24.dp)
                    ) {
                        Icon(
                            imageVector = if (isBalanceVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = "Toggle Balance",
                            tint = Color.White.copy(alpha = 0.5f),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Surface(
                    onClick = { },
                    color = Color.White.copy(alpha = 0.06f),
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.dp, Border)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(Icons.Default.Info, contentDescription = null, tint = Muted, modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("View account information ›", color = Muted, fontSize = 11.sp, fontFamily = Urbanist, fontWeight = FontWeight.Medium)
                    }
                }
                
                Spacer(modifier = Modifier.height(18.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                    QuickActionItem(icon = Icons.Default.Add, label = "Add Money", isPrimary = true, onClick = onAddMoneyClick)
                    QuickActionItem(icon = Icons.AutoMirrored.Filled.Send, label = "Send")
                    QuickActionItem(icon = Icons.Default.SyncAlt, label = "Convert", onClick = onConvertClick)
                    QuickActionItem(icon = Icons.Default.PhonelinkRing, label = "Tap & Go", onClick = onTapGoClick)
                }
            }
        }
    }
}

@Composable
fun QuickActionItem(icon: ImageVector, label: String, isPrimary: Boolean = false, onClick: () -> Unit = {}) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .background(if (isPrimary) Royal else Color.White.copy(alpha = 0.07f), CircleShape)
                .border(if (isPrimary) 0.dp else 1.dp, Border, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
        }
        Text(label, color = Muted, fontSize = 11.sp, fontWeight = FontWeight.Medium, fontFamily = Urbanist)
    }
}

@Composable
fun OverviewBanner() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Brush.linearGradient(listOf(Royal.copy(alpha = 0.15f), Teal.copy(alpha = 0.2f))), RoundedCornerShape(16.dp))
            .border(1.dp, Royal.copy(alpha = 0.25f), RoundedCornerShape(16.dp))
            .padding(14.dp, 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text("OVERVIEW", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 0.08.sp, fontFamily = Urbanist)
            Text("Receive USD Payments From\nAnywhere, Anytime", color = Muted, fontSize = 11.sp, lineHeight = 15.sp, fontFamily = Urbanist, fontWeight = FontWeight.Medium)
        }
        Surface(
            onClick = { },
            color = Color.White.copy(alpha = 0.1f),
            shape = RoundedCornerShape(20.dp),
            border = BorderStroke(1.dp, Border)
        ) {
            Text("Learn More ›", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), fontFamily = Urbanist)
        }
    }
}

@Composable
fun QuickAccessSection(
    onHistoryClick: () -> Unit = {}, 
    onDataClick: () -> Unit = {}, 
    onElectricityClick: () -> Unit = {},
    onAirtimeClick: () -> Unit = {}
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Quick Access", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
            Text(
                text = "See all",
                color = Teal,
                fontSize = 11.sp,
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .clickable { onHistoryClick() }
                    .padding(horizontal = 4.dp, vertical = 2.dp),
                fontFamily = Urbanist,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        
        val items = listOf(
            AccessItemData("Airtime", "Cash top-up", Icons.Default.Smartphone, onAirtimeClick),
            AccessItemData("Data", "Stay linked", Icons.Default.SignalCellularAlt, onDataClick),
            AccessItemData("Electricity", "Pay bill", Icons.Default.ElectricBolt, onElectricityClick),
            AccessItemData("Cable", "Watch now", Icons.Default.LiveTv, {}),
            AccessItemData("Education", "Learn more", Icons.Default.School, {}),
            AccessItemData("Support", "Get help", Icons.Default.SupportAgent, {})
        )
        
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AccessItem(items[0], Modifier.weight(1f))
                AccessItem(items[1], Modifier.weight(1f))
                AccessItem(items[2], Modifier.weight(1f))
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AccessItem(items[3], Modifier.weight(1f))
                AccessItem(items[4], Modifier.weight(1f))
                AccessItem(items[5], Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun WalletPocketOverview(
    isBalanceVisible: Boolean,
    onAddWalletClick: () -> Unit, 
    onPocketClick: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Wallets & Pockets", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(50.dp))
                    .background(Royal.copy(alpha = 0.1f))
                    .clickable { onAddWalletClick() }
                    .padding(horizontal = 10.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Add, contentDescription = null, tint = Royal, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("New Wallet", color = Royal, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
            }
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            // Main Wallet
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(18.dp))
                    .background(Card)
                    .border(1.dp, Border, RoundedCornerShape(18.dp))
                    .padding(16.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(Icons.Default.AccountBalanceWallet, contentDescription = null, tint = Teal, modifier = Modifier.size(20.dp))
                    Text("Main Wallet", color = Muted, fontSize = 11.sp, fontFamily = Urbanist)
                    Text(
                        text = if (isBalanceVisible) "₦100,000.00" else "•••••",
                        color = Color.White,
                        fontSize = if (isBalanceVisible) 13.sp else 15.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Urbanist,
                        letterSpacing = if (isBalanceVisible) 0.sp else 2.sp,
                        overflow = TextOverflow.Visible,
                        maxLines = 1
                    )
                }
            }

            // Pockets Summary
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(18.dp))
                    .background(Card)
                    .border(1.dp, Border, RoundedCornerShape(18.dp))
                    .clickable { onPocketClick() }
                    .padding(16.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(Icons.Default.Savings, contentDescription = null, tint = Purple, modifier = Modifier.size(20.dp))
                    Text("Smart Pockets", color = Muted, fontSize = 11.sp, fontFamily = Urbanist)
                    Text("3 Active Pockets", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                }
            }
        }
    }
}

data class AccessItemData(val name: String, val sub: String, val icon: ImageVector, val onClick: () -> Unit)

@Composable
fun AccessItem(data: AccessItemData, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.border(1.dp, Border, RoundedCornerShape(14.dp)),
        colors = CardDefaults.cardColors(containerColor = Card),
        shape = RoundedCornerShape(14.dp),
        onClick = data.onClick
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(vertical = 14.dp, horizontal = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(data.icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(28.dp))
            Text(data.name, color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, fontFamily = Urbanist)
            if (data.sub.isNotEmpty()) {
                Text(data.sub, color = Muted, fontSize = 9.sp, textAlign = TextAlign.Center, fontFamily = Urbanist, fontWeight = FontWeight.Medium)
            }
        }
    }
}

@Preview(showBackground = true, name = "Dashboard with Promo")
@Composable
fun DashboardPromoPreview() {
    VeltraTheme {
        DashboardContent(
            isBalanceVisible = true,
            onToggleBalance = {},
            onConvertClick = {},
            onAddMoneyClick = {},
            onProfileClick = {},
            onTapGoClick = {},
            onHistoryClick = {},
            onPingMeClick = {},
            onTasksClick = {},
            onPocketClick = {},
            onDataClick = {},
            onElectricityClick = {},
            onAirtimeClick = {},
            showPromo = true
        )
    }
}

@Composable
fun PromoBanner() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Brush.linearGradient(listOf(Purple, Royal)), RoundedCornerShape(18.dp))
            .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(18.dp))
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text("VELTRA PROMO", color = Color.White.copy(alpha = 0.6f), fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp, fontFamily = Urbanist)
            Text("Upgrade to Gold\nGet 2% Cashback", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist, lineHeight = 20.sp)
        }
        Button(
            onClick = { },
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            shape = RoundedCornerShape(50.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp),
            modifier = Modifier.height(34.dp)
        ) {
            Text("Claim Now", color = Royal, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
        }
    }
}

@Preview(showBackground = true, name = "Dashboard Balance Visible")
@Composable
fun DashboardPreview() {
    VeltraTheme {
        DashboardContent(
            isBalanceVisible = true,
            onToggleBalance = {},
            onConvertClick = {},
            onAddMoneyClick = {},
            onProfileClick = {},
            onTapGoClick = {},
            onHistoryClick = {},
            onPingMeClick = {},
            onTasksClick = {},
            onPocketClick = {},
            onDataClick = {},
            onElectricityClick = {},
            onAirtimeClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Dashboard Balance Hidden")
@Composable
fun DashboardHiddenPreview() {
    VeltraTheme {
        DashboardContent(
            isBalanceVisible = false,
            onToggleBalance = {},
            onConvertClick = {},
            onAddMoneyClick = {},
            onProfileClick = {},
            onTapGoClick = {},
            onHistoryClick = {},
            onPingMeClick = {},
            onTasksClick = {},
            onPocketClick = {},
            onDataClick = {},
            onElectricityClick = {},
            onAirtimeClick = {}
        )
    }
}
