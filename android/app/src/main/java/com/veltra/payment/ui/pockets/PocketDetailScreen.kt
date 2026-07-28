package com.veltra.payment.ui.pockets

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.CallMade
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veltra.payment.ui.theme.*

@Composable
fun PocketDetailScreen(onBackClick: () -> Unit, onAddFundsClick: () -> Unit) {
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
                    Text("Bali Trip 2026", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(
                        onClick = { },
                        modifier = Modifier.size(34.dp)
                    ) {
                        Icon(Icons.Default.MoreHoriz, contentDescription = "More", tint = Color.White)
                    }
                }

                // Pocket Detail Hero
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(Brush.linearGradient(listOf(Color(0xFFF59E0B).copy(alpha = 0.25f), Color(0xFFF59E0B).copy(alpha = 0.08f))))
                            .border(1.dp, Color(0xFFF59E0B).copy(alpha = 0.3f), RoundedCornerShape(20.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.FlightTakeoff, contentDescription = null, tint = Color(0xFFF59E0B), modifier = Modifier.size(30.dp))
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    Text("₦320,000.00", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
                    Text("of ₦600,000.00 goal · 4 members", color = Muted, fontSize = 12.sp, fontFamily = Urbanist, fontWeight = FontWeight.Medium)

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                            .height(8.dp)
                            .background(Card2, RoundedCornerShape(50.dp))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.53f)
                                .fillMaxHeight()
                                .background(Brush.horizontalGradient(listOf(Color(0xFFF59E0B), Color(0xFFFBBF24))), RoundedCornerShape(50.dp))
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("53% saved", color = Muted, fontSize = 10.5.sp, fontFamily = Urbanist)
                        Text("₦280,000 to go", color = Muted, fontSize = 10.5.sp, fontFamily = Urbanist)
                    }
                    Spacer(modifier = Modifier.height(14.dp))
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
                    onClick = onAddFundsClick,
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
                            .background(Brush.linearGradient(listOf(Royal, Teal))),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Add Funds", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
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
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                PocketActionButton(Icons.Default.Add, "Add Funds", Teal, Modifier.weight(1f), onAddFundsClick)
                PocketActionButton(Icons.Default.PersonAdd, "Invite", Royal, Modifier.weight(1f))
                PocketActionButton(Icons.Default.Timer, "Set Goal", Color(0xFFF59E0B), Modifier.weight(1f))
                PocketActionButton(Icons.AutoMirrored.Filled.CallMade, "Withdraw", Color(0xFFF87171), Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(10.dp))

            SectionHeaderWithAction("Contributors")

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Card, RoundedCornerShape(14.dp))
                    .border(1.dp, Border, RoundedCornerShape(14.dp))
                    .padding(horizontal = 14.dp)
            ) {
                ContributorRow("AV", "You", "Last added 2 days ago", "₦150,000", Royal)
                HorizontalDivider(color = Border)
                ContributorRow("SH", "Shawn", "Last added 5 days ago", "₦90,000", Color(0xFFF59E0B))
                HorizontalDivider(color = Border)
                ContributorRow("KY", "Kyle", "Last added 1 week ago", "₦50,000", Teal)
                HorizontalDivider(color = Border)
                ContributorRow("MJ", "Marjorie", "Last added 2 weeks ago", "₦30,000", Color(0xFF9B6DFF))
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun PocketActionButton(icon: ImageVector, label: String, color: Color, modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    Box(
        modifier = modifier
            .background(Card, RoundedCornerShape(14.dp))
            .border(1.dp, Border, RoundedCornerShape(14.dp))
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(18.dp))
            Text(label, color = Color.White, fontSize = 10.5.sp, fontWeight = FontWeight.SemiBold, fontFamily = Urbanist)
        }
    }
}

@Composable
fun SectionHeaderWithAction(text: String) {
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
fun ContributorRow(initials: String, name: String, sub: String, amount: String, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 11.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(color.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            Text(initials, color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(name, color = Color.White, fontSize = 12.5.sp, fontWeight = FontWeight.SemiBold, fontFamily = Urbanist)
            Text(sub, color = Muted, fontSize = 10.5.sp, fontFamily = Urbanist)
        }
        Text(amount, color = Teal, fontSize = 12.5.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
    }
}

@Preview
@Composable
fun PocketDetailPreview() {
    VeltraTheme {
        PocketDetailScreen({}, {})
    }
}
