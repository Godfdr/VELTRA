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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veltra.payment.ui.SectionHeaderSmall
import com.veltra.payment.ui.theme.*

@Composable
fun AccountVerificationScreen(onBackClick: () -> Unit) {
    Scaffold(
        containerColor = Base,
        topBar = {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth().background(HeaderGradient).padding(horizontal = 20.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.size(34.dp).background(Color.White.copy(alpha = 0.08f), CircleShape).border(1.dp, Border, CircleShape)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White, modifier = Modifier.size(16.dp))
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("Account Verification", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).verticalScroll(rememberScrollState()).padding(horizontal = 16.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text("Verification Status", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
            
            Column(modifier = Modifier.fillMaxWidth().background(Card, RoundedCornerShape(16.dp)).border(1.dp, Border, RoundedCornerShape(16.dp)).padding(16.dp)) {
                VerificationLevelRow("Tier 1: Basic", "Daily limit: ₦50,000", true)
                HorizontalDivider(color = Border, modifier = Modifier.padding(vertical = 12.dp))
                VerificationLevelRow("Tier 2: Standard", "Daily limit: ₦500,000", true)
                HorizontalDivider(color = Border, modifier = Modifier.padding(vertical = 12.dp))
                VerificationLevelRow("Tier 3: Premium", "Daily limit: ₦5,000,000", false)
            }

            SectionHeaderSmall("Pending Requirements")
            Column(modifier = Modifier.fillMaxWidth().background(Card, RoundedCornerShape(14.dp)).border(1.dp, Border, RoundedCornerShape(14.dp))) {
                VerificationRequirementRow(Icons.Default.Badge, "Address Verification", "Upload utility bill")
                HorizontalDivider(color = Border, modifier = Modifier.padding(horizontal = 16.dp))
                VerificationRequirementRow(Icons.Default.Face, "Liveness Check", "Quick video selfie")
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun VerificationLevelRow(title: String, limit: String, isVerified: Boolean) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
        Column {
            Text(title, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
            Text(limit, color = Muted, fontSize = 11.sp, fontFamily = Urbanist)
        }
        if (isVerified) {
            Box(modifier = Modifier.background(SuccessGreen.copy(alpha = 0.12f), RoundedCornerShape(50.dp)).padding(horizontal = 10.dp, vertical = 4.dp)) {
                Text("VERIFIED", color = SuccessGreen, fontSize = 9.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
            }
        } else {
            Text("Upgrade ›", color = Teal, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
        }
    }
}

@Composable
fun VerificationRequirementRow(icon: ImageVector, title: String, sub: String) {
    Row(modifier = Modifier.fillMaxWidth().clickable { }.padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        Box(modifier = Modifier.size(32.dp).background(Royal.copy(alpha = 0.12f), RoundedCornerShape(8.dp)), contentAlignment = Alignment.Center) {
            Icon(icon, contentDescription = null, tint = Royal, modifier = Modifier.size(16.dp))
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
            Text(sub, color = Muted, fontSize = 10.sp, fontFamily = Urbanist)
        }
        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Muted, modifier = Modifier.size(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun AccountVerificationPreview() {
    VeltraTheme {
        AccountVerificationScreen({})
    }
}
