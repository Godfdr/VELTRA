package com.veltra.payment.ui.profile

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
fun AccountVerificationScreen(onBackClick: () -> Unit) {
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
                    Text("Account Verification", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
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
            // Tier Card - Matched for Warmth and Mixing
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(AnalyticsGradient) // Using refined mixing gradient
                    .border(1.dp, Royal.copy(alpha = 0.3f), RoundedCornerShape(20.dp))
                    .padding(vertical = 24.dp, horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    Icons.Default.VerifiedUser, 
                    contentDescription = null, 
                    tint = Teal, 
                    modifier = Modifier.size(32.dp)
                )
                Text("Tier 2", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(top = 8.dp), fontFamily = Urbanist)
                Text("2 of 5 verifications complete", color = Muted, fontSize = 11.sp, fontFamily = Urbanist, fontWeight = FontWeight.Medium)
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Card, RoundedCornerShape(16.dp))
                    .border(1.dp, Border, RoundedCornerShape(16.dp))
            ) {
                VerificationRow(Icons.Default.Person, "Personal Details", "Confirmed", "Verified", Teal)
                HorizontalDivider(color = Border, modifier = Modifier.padding(horizontal = 16.dp))
                VerificationRow(Icons.Default.CreditCard, "BVN Verification", "", "Verified", Teal)
                HorizontalDivider(color = Border, modifier = Modifier.padding(horizontal = 16.dp))
                VerificationRow(Icons.Default.AssignmentInd, "ID Document", "", "Pending", WarningOrange)
                HorizontalDivider(color = Border, modifier = Modifier.padding(horizontal = 16.dp))
                VerificationRow(Icons.Default.CameraAlt, "Face Verification", "", "Start", Muted)
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun VerificationRow(icon: ImageVector, title: String, sub: String, status: String, statusColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(RoundedCornerShape(11.dp))
                .background(Royal.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = Royal, modifier = Modifier.size(17.dp))
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
            if (sub.isNotEmpty()) {
                Text(sub, color = Muted, fontSize = 10.sp, fontFamily = Urbanist, fontWeight = FontWeight.Medium)
            }
        }
        Surface(
            color = statusColor.copy(alpha = 0.15f),
            shape = RoundedCornerShape(20.dp),
            border = BorderStroke(1.dp, statusColor.copy(alpha = 0.25f))
        ) {
            Text(
                status, 
                color = statusColor, 
                fontSize = 10.sp, 
                fontWeight = FontWeight.Bold, 
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                fontFamily = Urbanist
            )
        }
    }
}

@Preview
@Composable
fun AccountVerificationPreview() {
    VeltraTheme {
        AccountVerificationScreen({})
    }
}
