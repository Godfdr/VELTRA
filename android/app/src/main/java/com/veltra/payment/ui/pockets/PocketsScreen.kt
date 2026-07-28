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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veltra.payment.ui.theme.*

@Composable
fun PocketsScreen(onBackClick: () -> Unit, onPocketClick: () -> Unit) {
    var selectedTab by remember { mutableStateOf("Group Savings") }

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
                            .size(32.dp)
                            .background(Color.White.copy(alpha = 0.08f), CircleShape)
                            .border(1.dp, Border, CircleShape)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White, modifier = Modifier.size(14.dp))
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("Pockets", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(
                        onClick = { },
                        modifier = Modifier.size(34.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White)
                    }
                }

                // Pocket Tabs
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp)
                        .background(Card, RoundedCornerShape(14.dp))
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    PocketTabItem("My Pockets", selectedTab == "My Pockets", Modifier.weight(1f)) { selectedTab = it }
                    PocketTabItem("Group Savings", selectedTab == "Group Savings", Modifier.weight(1f)) { selectedTab = it }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Featured Group Card - Precision Match
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(Brush.linearGradient(listOf(Color(0xFF9B6DFF).copy(alpha = 0.22f), Royal.copy(alpha = 0.12f))))
                    .border(1.dp, Color(0xFF9B6DFF).copy(alpha = 0.3f), RoundedCornerShape(20.dp))
                    .padding(18.dp)
                    .clickable { onPocketClick() }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column {
                        Text("Lagos Apartment Fund", color = Muted, fontSize = 11.sp, fontFamily = Urbanist)
                        Text("₦850,000.00", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
                    }
                    Surface(
                        color = Color.Black.copy(alpha = 0.25f),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(Icons.Default.Groups, contentDescription = null, tint = Color(0xFF9B6DFF), modifier = Modifier.size(11.dp))
                            Text("Group", color = Color(0xFF9B6DFF), fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                        }
                    }
                }

                Row(
                    modifier = Modifier.padding(top = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AvatarStack()
                    Spacer(modifier = Modifier.width(10.dp))
                    Text("5 contributors", color = Muted, fontSize = 11.sp, fontFamily = Urbanist)
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 14.dp)
                        .height(6.dp)
                        .background(Color.Black.copy(alpha = 0.3f), RoundedCornerShape(50.dp))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.68f)
                            .fillMaxHeight()
                            .background(Brush.horizontalGradient(listOf(Color(0xFF9B6DFF), Royal)), RoundedCornerShape(50.dp))
                    )
                }
                Text("68% of ₦1,250,000 goal · ₦400,000 to go", color = Muted, fontSize = 10.sp, modifier = Modifier.padding(top = 6.dp), fontFamily = Urbanist)
            }

            SectionHeader("Other Group Pockets")

            CompactPocketCard(
                name = "Bali Trip 2026",
                subText = "4 members",
                amount = "₦320,000",
                goal = "of ₦600,000",
                progress = 0.53f,
                color = WarningOrange,
                icon = Icons.Default.FlightTakeoff,
                onClick = onPocketClick
            )

            CompactPocketCard(
                name = "Family School Fund",
                subText = "3 members",
                amount = "₦610,000",
                goal = "of ₦800,000",
                progress = 0.76f,
                color = Teal,
                icon = Icons.Default.School
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF9B6DFF).copy(alpha = 0.1f))
                    .border(1.dp, Color(0xFF9B6DFF).copy(alpha = 0.35f), RoundedCornerShape(16.dp))
                    .clickable { }
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(Icons.Default.Add, contentDescription = null, tint = Color(0xFF9B6DFF), modifier = Modifier.size(16.dp))
                    Text("Create a Group Pocket", color = Color(0xFF9B6DFF), fontSize = 12.5.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                }
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun PocketTabItem(label: String, isActive: Boolean, modifier: Modifier = Modifier, onClick: (String) -> Unit) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(if (isActive) Color(0xFF9B6DFF) else Color.Transparent)
            .clickable { onClick(label) }
            .padding(vertical = 9.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(label, color = if (isActive) Color.White else Muted, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
    }
}

@Composable
fun SectionHeader(text: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
        Text("See all", color = Teal, fontSize = 11.sp, modifier = Modifier.clickable { }, fontFamily = Urbanist)
    }
}

@Composable
fun AvatarStack() {
    Row {
        val colors = listOf(Royal, Teal, WarningOrange, Color(0xFF9B6DFF))
        val labels = listOf("AV", "VC", "SH", "+2")
        labels.forEachIndexed { index, label ->
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .offset(x = (index * (-10)).dp)
                    .clip(CircleShape)
                    .background(colors[index % colors.size].copy(alpha = 0.5f))
                    .border(2.dp, Card, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(label, color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
            }
        }
    }
}

@Composable
fun CompactPocketCard(
    name: String,
    subText: String,
    amount: String,
    goal: String,
    progress: Float,
    color: Color,
    icon: ImageVector,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Card, RoundedCornerShape(16.dp))
            .border(1.dp, Border, RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(color.copy(alpha = 0.15f))
                    .border(1.dp, color.copy(alpha = 0.25f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(18.dp))
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(name, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                Text(subText, color = Muted, fontSize = 10.5.sp, fontFamily = Urbanist)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(amount, color = Color.White, fontSize = 14.5.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist, letterSpacing = (-0.2).sp)
                Text(goal, color = Muted, fontSize = 10.sp, fontWeight = FontWeight.SemiBold, fontFamily = Urbanist)
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
                .height(5.dp)
                .background(Card2, RoundedCornerShape(50.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(progress)
                    .fillMaxHeight()
                    .background(color, RoundedCornerShape(50.dp))
            )
        }
    }
}

@Preview
@Composable
fun PocketsPreview() {
    VeltraTheme {
        PocketsScreen({}, {})
    }
}
