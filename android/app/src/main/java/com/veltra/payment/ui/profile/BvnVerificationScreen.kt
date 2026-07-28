package com.veltra.payment.ui.profile

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
fun BvnVerificationScreen(onBackClick: () -> Unit) {
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
                    Text("BVN Verification", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
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
                        Text("Verify BVN", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
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
            // BVN Info Card
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Card, RoundedCornerShape(18.dp))
                    .border(1.dp, Border, RoundedCornerShape(18.dp))
                    .padding(20.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Royal.copy(alpha = 0.12f))
                        .border(1.dp, Royal.copy(alpha = 0.2f), RoundedCornerShape(14.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.VerifiedUser, contentDescription = null, tint = Royal, modifier = Modifier.size(26.dp))
                }
                Spacer(modifier = Modifier.height(14.dp))
                Text("Complete verification", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Your BVN helps us confirm your identity without accessing your bank account.",
                    color = Muted,
                    fontSize = 12.sp,
                    lineHeight = 19.sp,
                    fontFamily = Urbanist,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(14.dp))
                HorizontalDivider(color = Border)
                Spacer(modifier = Modifier.height(14.dp))

                Text("This will enable you:", color = Muted, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                Spacer(modifier = Modifier.height(10.dp))
                
                BvnBenefitRow("To comply with financial regulations")
                BvnBenefitRow("To build trust and reduce fraud")
                BvnBenefitRow("To unlock full transaction limits")
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text("Enter your BVN", color = Muted, fontSize = 12.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist, modifier = Modifier.padding(start = 2.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Card, RoundedCornerShape(14.dp))
                    .border(1.dp, Border, RoundedCornerShape(14.dp))
                    .padding(15.dp, 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(Icons.Default.CreditCard, contentDescription = null, tint = Muted, modifier = Modifier.size(16.dp))
                Text("11-digit BVN", color = Muted, fontSize = 13.sp, fontFamily = Urbanist, fontWeight = FontWeight.Medium)
            }

            // Note Card
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Teal.copy(alpha = 0.1f), RoundedCornerShape(12.dp))
                    .border(1.dp, Teal.copy(alpha = 0.2f), RoundedCornerShape(12.dp))
                    .padding(13.dp, 14.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(Icons.Default.Lock, contentDescription = null, tint = Teal, modifier = Modifier.size(15.dp))
                Text(
                    "Your BVN is encrypted and never stored. It is only used once to verify your identity.",
                    color = Teal,
                    fontSize = 11.sp,
                    lineHeight = 17.sp,
                    fontFamily = Urbanist,
                    fontWeight = FontWeight.Medium
                )
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun BvnBenefitRow(text: String) {
    Row(
        modifier = Modifier.padding(vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(22.dp)
                .clip(CircleShape)
                .background(Teal.copy(alpha = 0.15f))
                .border(1.dp, Teal.copy(alpha = 0.25f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text("✓", color = Teal, fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }
        Text(text, color = Muted, fontSize = 12.sp, lineHeight = 18.sp, fontFamily = Urbanist, fontWeight = FontWeight.Medium)
    }
}

@Preview
@Composable
fun BvnPreview() {
    VeltraTheme {
        BvnVerificationScreen({})
    }
}
