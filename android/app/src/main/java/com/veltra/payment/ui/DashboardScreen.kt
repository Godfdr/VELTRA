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
    onElectricityClick: () -> Unit
) {
    val scrollState = rememberScrollState()
    var isBalanceVisible by remember { mutableStateOf(true) }

    Scaffold(
        bottomBar = { BottomNavigationBar(onConvertClick, onHistoryClick, onProfileClick) },
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
                onToggleBalance = { isBalanceVisible = !isBalanceVisible },
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
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                OverviewBanner()
                QuickAccessSection(
                    onHistoryClick = onHistoryClick, 
                    onDataClick = onDataClick, 
                    onElectricityClick = onElectricityClick
                )
                
                // Extra Spacing for smooth feel
                Spacer(modifier = Modifier.height(24.dp))
                
                // Subtle feature highlight to match HTML warmth
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
            .padding(horizontal = 20.dp, vertical = 20.dp)
    ) {
        // Status Bar Simulation
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "21:45",
                color = Color.White,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Urbanist
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_onboarding_spot), 
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = Color.White
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_onboarding_nfc),
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = Color.White
                )
                Box(
                    modifier = Modifier
                        .width(22.dp)
                        .height(11.dp)
                        .background(Color.White, RoundedCornerShape(2.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "45",
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.Black,
                        fontFamily = Urbanist
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // User row
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
                    Text(
                        "V",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        fontFamily = Urbanist
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.clickable { onProfileClick() }) {
                    Text(
                        "Hello 👋",
                        color = Muted,
                        fontSize = 11.sp,
                        fontFamily = Urbanist
                    )
                    Text(
                        "Alex Veltra",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Urbanist
                    )
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                IconButton(
                    onClick = onTasksClick,
                    modifier = Modifier
                        .size(34.dp)
                        .background(Color.White.copy(alpha = 0.08f), CircleShape)
                        .border(1.dp, Border, CircleShape)
                ) {
                    Icon(
                        Icons.Default.Notifications,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .size(34.dp)
                        .background(Color.White.copy(alpha = 0.08f), CircleShape)
                        .border(1.dp, Border, CircleShape)
                ) {
                    Icon(
                        Icons.Default.Email,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Balance Card - Exact warmth mixing
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Border, RoundedCornerShape(20.dp)),
            colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.3f)),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Total balance",
                    color = Muted,
                    fontSize = 11.sp,
                    fontFamily = Urbanist,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        if (isBalanceVisible) "$ 100,000.00" else "$ ••••••••",
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = (-0.5).sp,
                        fontFamily = Urbanist
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(
                        painter = painterResource(id = if (isBalanceVisible) android.R.drawable.ic_menu_view else android.R.drawable.ic_menu_close_clear_cancel),
                        contentDescription = null,
                        tint = Color.White.copy(alpha = 0.5f),
                        modifier = Modifier
                            .size(18.dp)
                            .clickable { onToggleBalance() }
                    )
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
                        Icon(
                            Icons.Default.Info,
                            contentDescription = null,
                            tint = Muted,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            "View account information ›",
                            color = Muted,
                            fontSize = 11.sp,
                            fontFamily = Urbanist,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(18.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
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
                .background(
                    if (isPrimary) Royal else Color.White.copy(alpha = 0.07f),
                    CircleShape
                )
                .border(if (isPrimary) 0.dp else 1.dp, Border, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(18.dp)
            )
        }
        Text(
            label,
            color = Muted,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = Urbanist
        )
    }
}

@Composable
fun OverviewBanner() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.linearGradient(
                    listOf(Royal.copy(alpha = 0.15f), Teal.copy(alpha = 0.2f))
                ),
                RoundedCornerShape(16.dp)
            )
            .border(1.dp, Royal.copy(alpha = 0.25f), RoundedCornerShape(16.dp))
            .padding(14.dp, 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                "OVERVIEW",
                color = Color.White,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.08.sp,
                fontFamily = Urbanist
            )
            Text(
                "Receive USD Payments From\nAnywhere, Anytime",
                color = Muted,
                fontSize = 11.sp,
                lineHeight = 15.sp,
                fontFamily = Urbanist,
                fontWeight = FontWeight.Medium
            )
        }
        Surface(
            onClick = { },
            color = Color.White.copy(alpha = 0.1f),
            shape = RoundedCornerShape(20.dp),
            border = BorderStroke(1.dp, Border)
        ) {
            Text(
                "Learn More ›",
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                fontFamily = Urbanist
            )
        }
    }
}

@Composable
fun QuickAccessSection(onHistoryClick: () -> Unit = {}, onDataClick: () -> Unit = {}, onElectricityClick: () -> Unit = {}) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Quick Access",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Urbanist
            )
            Text(
                "See all",
                color = Teal,
                fontSize = 11.sp,
                modifier = Modifier.clickable { onHistoryClick() },
                fontFamily = Urbanist,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        
        val items = listOf(
            AccessItemData("Airtime", "Cash top-up", Icons.Default.PhoneAndroid, {}),
            AccessItemData("Data", "Stay linked", Icons.Default.Wifi, onDataClick),
            AccessItemData("Electricity", "Pay bill", Icons.Default.FlashOn, onElectricityClick),
            AccessItemData("Cable", "", Icons.Default.Tv, {}),
            AccessItemData("Education", "", Icons.Default.School, {}),
            AccessItemData("Support", "", Icons.Default.HeadsetMic, {})
        )
        
        // Custom Grid
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

data class AccessItemData(val name: String, val sub: String, val icon: ImageVector, val onClick: () -> Unit)

@Composable
fun AccessItem(data: AccessItemData, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .border(1.dp, Border, RoundedCornerShape(14.dp)),
        colors = CardDefaults.cardColors(containerColor = Card),
        shape = RoundedCornerShape(14.dp),
        onClick = data.onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp, horizontal = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                data.icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
            Text(
                data.name,
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = Urbanist
            )
            if (data.sub.isNotEmpty()) {
                Text(
                    data.sub,
                    color = Muted,
                    fontSize = 9.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = Urbanist,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(onConvertClick: () -> Unit, onHistoryClick: () -> Unit, onProfileClick: () -> Unit) {
    NavigationBar(
        containerColor = Card,
        tonalElevation = 0.dp,
        modifier = Modifier
            .border(1.dp, Border, RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp))
            .height(80.dp)
    ) {
        NavigationBarItem(
            selected = true,
            onClick = { },
            icon = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Home, contentDescription = null, tint = Royal)
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .size(4.dp)
                            .background(Royal, CircleShape)
                    )
                }
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Royal.copy(alpha = 0.15f)
            )
        )
        NavigationBarItem(
            selected = false,
            onClick = onConvertClick,
            icon = {
                Icon(
                    Icons.Default.SyncAlt,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.4f)
                )
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = onHistoryClick,
            icon = {
                Icon(
                    Icons.Default.GridView,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.4f)
                )
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = onProfileClick,
            icon = {
                Icon(
                    Icons.Default.Person,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.4f)
                )
            }
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF08090F)
@Composable
fun DashboardPreview() {
    VeltraTheme {
        DashboardScreen({}, {}, {}, {}, {}, {}, {}, {}, {}, {})
    }
}
