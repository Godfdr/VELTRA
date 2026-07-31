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
fun NotificationsScreen(onBackClick: () -> Unit) {
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
                    Text("Notifications", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).verticalScroll(rememberScrollState()).padding(horizontal = 16.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            SectionHeaderSmall("Today")
            Column(modifier = Modifier.fillMaxWidth().background(Card, RoundedCornerShape(16.dp)).border(1.dp, Border, RoundedCornerShape(16.dp))) {
                NotificationRow(Icons.Default.SyncAlt, "Money Received", "₦15,000 received from @victor", "2h ago", Teal)
                HorizontalDivider(color = Border, modifier = Modifier.padding(horizontal = 16.dp))
                NotificationRow(Icons.Default.Shield, "Login Alert", "New login from Lagos, Nigeria", "4h ago", WarningOrange)
            }

            SectionHeaderSmall("Yesterday")
            Column(modifier = Modifier.fillMaxWidth().background(Card, RoundedCornerShape(14.dp)).border(1.dp, Border, RoundedCornerShape(14.dp))) {
                NotificationRow(Icons.Default.CheckCircle, "Verification Success", "Standard Tier 2 verification complete", "Yesterday", SuccessGreen)
                HorizontalDivider(color = Border, modifier = Modifier.padding(horizontal = 16.dp))
                NotificationRow(Icons.Default.Favorite, "New Badge!", "You've been with Veltra for 1 month", "Yesterday", InfoPurple)
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun NotificationRow(icon: ImageVector, title: String, msg: String, time: String, color: Color) {
    Row(modifier = Modifier.fillMaxWidth().clickable { }.padding(16.dp), verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
        Box(modifier = Modifier.size(36.dp).background(color.copy(alpha = 0.12f), CircleShape), contentAlignment = Alignment.Center) {
            Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(18.dp))
        }
        Column(modifier = Modifier.weight(1f)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(title, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                Text(time, color = Muted, fontSize = 10.sp, fontFamily = Urbanist)
            }
            Text(msg, color = Muted, fontSize = 11.sp, lineHeight = 16.sp, modifier = Modifier.padding(top = 4.dp), fontFamily = Urbanist)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotificationsPreview() {
    VeltraTheme {
        NotificationsScreen({})
    }
}
