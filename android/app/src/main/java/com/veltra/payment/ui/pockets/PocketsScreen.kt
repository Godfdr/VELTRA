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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veltra.payment.ui.CompactPocketCard
import com.veltra.payment.ui.SectionHeader
import com.veltra.payment.ui.VeltraFooter
import com.veltra.payment.ui.theme.*

@Composable
fun PocketsScreen(
    onBackClick: () -> Unit,
    onPocketClick: () -> Unit,
    onAddPocketClick: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    
    PocketsContent(
        selectedTab = selectedTab,
        onTabSelected = { selectedTab = it },
        onBackClick = onBackClick,
        onPocketClick = onPocketClick,
        onAddPocketClick = onAddPocketClick
    )
}

@Composable
fun PocketsContent(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    onBackClick: () -> Unit,
    onPocketClick: () -> Unit,
    onAddPocketClick: () -> Unit
) {
    Scaffold(
        containerColor = Base,
        bottomBar = {
            VeltraFooter(
                activeRoute = "pockets",
                onDashboardClick = onBackClick,
                onPocketsClick = { },
                onProfileClick = { }
            )
        },
        topBar = {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(HeaderGradient)
                        .padding(horizontal = 20.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(
                            onClick = onBackClick,
                            modifier = Modifier.size(34.dp).background(Color.White.copy(alpha = 0.08f), CircleShape).border(1.dp, Border, CircleShape)
                        ) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White, modifier = Modifier.size(16.dp))
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Text("Veltra Pockets", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                    }
                    IconButton(
                        onClick = onAddPocketClick,
                        modifier = Modifier.size(34.dp).background(Royal, CircleShape)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White, modifier = Modifier.size(18.dp))
                    }
                }
                
                // Tabs
                Row(modifier = Modifier.fillMaxWidth().background(HeaderGradient).padding(horizontal = 20.dp, vertical = 10.dp)) {
                    TabItem("My Pockets", selectedTab == 0, Modifier.weight(1f)) { onTabSelected(0) }
                    TabItem("Group Savings", selectedTab == 1, Modifier.weight(1f)) { onTabSelected(1) }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (selectedTab == 0) {
                SectionHeader("Personal Pockets")
                CompactPocketCard("Bali Trip 2026", "Travel & Leisure", "\u20A6320,000", "of \u20A6600,000", 0.53f, WarningOrange, Icons.Default.FlightTakeoff, onPocketClick)
                CompactPocketCard("New Workspace", "Electronics", "\u20A61,200,000", "of \u20A62M", 0.6f, Teal, Icons.Default.Computer, onPocketClick)
                CompactPocketCard("Emergency Fund", "Security", "\u20A6450,000", "of \u20A61M", 0.45f, InfoPurple, Icons.Default.Shield, onPocketClick)
            } else {
                SectionHeader("Active Groups")
                GroupPocketCard("Family Vacation", "5 Members", "\u20A62,400,000", "\u20A65M Target", 0.48f, onPocketClick)
                GroupPocketCard("Startup Fund", "3 Members", "\u20A68,500,000", "\u20A615M Target", 0.56f, onPocketClick)
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun TabItem(label: String, isActive: Boolean, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier.clip(RoundedCornerShape(50.dp)).background(if (isActive) Color.White.copy(alpha = 0.08f) else Color.Transparent).clickable { onClick() }.padding(vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(label, color = if (isActive) Color.White else Muted, fontSize = 12.sp, fontWeight = if (isActive) FontWeight.Bold else FontWeight.Medium, fontFamily = Urbanist)
    }
}

@Composable
fun GroupPocketCard(name: String, members: String, amount: String, target: String, progress: Float, onClick: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Card, RoundedCornerShape(16.dp))
            .border(1.dp, Border, RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text(name, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                Text(members, color = Teal, fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(amount, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
                Text(target, color = Muted, fontSize = 9.5.sp, fontFamily = Urbanist)
            }
        }
        Box(modifier = Modifier.fillMaxWidth().padding(top = 14.dp).height(5.dp).background(Card2, RoundedCornerShape(50.dp))) {
            Box(modifier = Modifier.fillMaxWidth(progress).fillMaxHeight().background(Teal, RoundedCornerShape(50.dp)))
        }
    }
}

@Preview(showBackground = true, name = "My Pockets")
@Composable
fun MyPocketsPreview() {
    VeltraTheme {
        PocketsContent(
            selectedTab = 0,
            onTabSelected = {},
            onBackClick = {},
            onPocketClick = {},
            onAddPocketClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Group Savings")
@Composable
fun GroupSavingsPreview() {
    VeltraTheme {
        PocketsContent(
            selectedTab = 1,
            onTabSelected = {},
            onBackClick = {},
            onPocketClick = {},
            onAddPocketClick = {}
        )
    }
}
