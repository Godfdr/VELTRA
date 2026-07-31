package com.veltra.payment.ui.transaction

import androidx.compose.animation.*
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
fun TapGoScreen(onBackClick: () -> Unit, onStartTapClick: () -> Unit = {}) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Phone", "Card", "Reader")

    Scaffold(
        containerColor = Base,
        topBar = {
            Column(modifier = Modifier.background(HeaderGradient)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 20.dp)
                        .statusBarsPadding(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.size(36.dp).background(Color.White.copy(alpha = 0.08f), CircleShape).border(1.dp, Border, CircleShape)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White, modifier = Modifier.size(16.dp))
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Text("Tap & Go", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                    Spacer(modifier = Modifier.weight(1.2f))
                }
                
                // Tabs from veltra-master.html Screen 7
                Row(
                    modifier = Modifier
                        .padding(horizontal = 24.dp, vertical = 12.dp)
                        .fillMaxWidth()
                        .background(Card, RoundedCornerShape(14.dp))
                        .border(1.dp, Border, RoundedCornerShape(14.dp))
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    tabs.forEachIndexed { index, label ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (selectedTabIndex == index) Royal else Color.Transparent)
                                .clickable { selectedTabIndex = index }
                                .padding(vertical = 9.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(label, color = if (selectedTabIndex == index) Color.White else Muted, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            
            // Concentric Rings
            Box(contentAlignment = Alignment.Center) {
                Box(modifier = Modifier.size(180.dp).background(Royal.copy(alpha = 0.06f), CircleShape).border(1.dp, Royal.copy(alpha = 0.15f), CircleShape))
                Box(modifier = Modifier.size(136.dp).background(Royal.copy(alpha = 0.1f), CircleShape).border(1.dp, Royal.copy(alpha = 0.25f), CircleShape))
                
                Box(
                    modifier = Modifier.size(76.dp).clip(CircleShape).background(PrimaryGradient).border(2.dp, Border, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = when(selectedTabIndex) {
                            0 -> Icons.Default.PhoneAndroid
                            1 -> Icons.Default.CreditCard
                            else -> Icons.Default.Contactless
                        }, 
                        contentDescription = null, 
                        tint = Color.White, 
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = when(selectedTabIndex) {
                    0 -> "Phone to Phone"
                    1 -> "Phone to Card"
                    else -> "Phone to Reader"
                }, 
                color = Color.White, 
                fontSize = 18.sp, 
                fontWeight = FontWeight.Bold, 
                fontFamily = Urbanist
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = when(selectedTabIndex) {
                    0 -> "Hold your phone near another\nVeltra user's phone to transfer"
                    1 -> "Tap your physical Veltra card\nbehind your phone to sync"
                    else -> "Connect your Veltra Reader via\nBluetooth to accept payments"
                },
                color = Muted,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                lineHeight = 18.sp,
                fontFamily = Urbanist
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onStartTapClick,
                modifier = Modifier.fillMaxWidth().height(56.dp).shadow(12.dp, RoundedCornerShape(50.dp), spotColor = Royal),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues(),
                shape = RoundedCornerShape(50.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize().background(PrimaryGradient), contentAlignment = Alignment.Center) {
                    Text(
                        text = when(selectedTabIndex) {
                            0 -> "Start Tap & Go"
                            1 -> "Sync My Card"
                            else -> "Search for Reader"
                        }, 
                        color = Color.White, 
                        fontSize = 15.sp, 
                        fontWeight = FontWeight.Bold, 
                        fontFamily = Urbanist
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(10.dp))
            
            OutlinedButton(
                onClick = { },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                border = BorderStroke(1.dp, Royal.copy(alpha = 0.3f)),
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Teal)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize().background(Brush.linearGradient(listOf(Royal.copy(alpha = 0.15f), Teal.copy(alpha = 0.15f)))),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Show My Tag to Receive", color = Teal, fontSize = 14.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                }
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TapGoPreview() {
    VeltraTheme {
        TapGoScreen({})
    }
}
