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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veltra.payment.ui.theme.*

@Composable
fun PingMeScreen(onBackClick: () -> Unit) {
    var amount by remember { mutableStateOf("5,000.00") }

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
                    Text("Ping Me", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 10.dp)
                ) {
                    Text("Who do you want to", color = Color.White, fontSize = 19.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
                    Text("ping for money?", color = Teal, fontSize = 19.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
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
                            .background(Brush.horizontalGradient(listOf(Royal, Teal))),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Send Ping →", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
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
            SectionHeaderSmall("Select a contact")
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ContactAvatar("VC", "Victoria", true)
                ContactAvatar("SH", "Shawn")
                ContactAvatar("KY", "Kyle")
                ContactAvatar("MJ", "Marjorie")
            }

            Spacer(modifier = Modifier.height(24.dp))

            SectionHeaderSmall("Amount")
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Card, RoundedCornerShape(14.dp))
                    .border(1.dp, Border, RoundedCornerShape(14.dp))
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("₦", color = Muted, fontSize = 18.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                Text(amount, color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Card, RoundedCornerShape(14.dp))
                    .border(1.dp, Border, RoundedCornerShape(14.dp))
                    .padding(horizontal = 14.dp, vertical = 13.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(Icons.Default.EditNote, contentDescription = null, tint = Muted, modifier = Modifier.size(15.dp))
                Text("Add a note (e.g. Dinner at Cactus 🌮)", color = Muted, fontSize = 12.sp, fontFamily = Urbanist, fontWeight = FontWeight.Medium)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Notification Banner
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Teal.copy(alpha = 0.1f), RoundedCornerShape(12.dp))
                    .border(1.dp, Teal.copy(alpha = 0.2f), RoundedCornerShape(12.dp))
                    .padding(13.dp, 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(Icons.Default.NotificationsActive, contentDescription = null, tint = Teal, modifier = Modifier.size(15.dp))
                Text(
                    "Victoria will get a Ping notification with Pay & Reject buttons",
                    color = Teal,
                    fontSize = 11.sp,
                    lineHeight = 16.sp,
                    fontFamily = Urbanist,
                    fontWeight = FontWeight.Medium
                )
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun SectionHeaderSmall(text: String) {
    Text(
        text = text.uppercase(),
        color = Muted,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp,
        modifier = Modifier.padding(bottom = 8.dp, start = 2.dp),
        fontFamily = Urbanist
    )
}

@Composable
fun ContactAvatar(initials: String, name: String, isSelected: Boolean = false) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(5.dp)) {
        Box(
            modifier = Modifier
                .size(46.dp)
                .clip(CircleShape)
                .background(if (isSelected) Royal else Royal.copy(alpha = 0.12f))
                .border(1.5.dp, if (isSelected) Teal else Border, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(initials, color = if (isSelected) Color.White else Royal, fontSize = 13.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
        }
        Text(name, color = Muted, fontSize = 9.5.sp, fontFamily = Urbanist, fontWeight = FontWeight.Medium)
    }
}

@Preview
@Composable
fun PingMePreview() {
    VeltraTheme {
        PingMeScreen({})
    }
}
