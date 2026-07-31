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
fun TasksScreen(onBackClick: () -> Unit, onBvnClick: () -> Unit = {}) {
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
                    Text("Final Touches", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).verticalScroll(rememberScrollState()).padding(horizontal = 16.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text("Complete your profile", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
            
            Column(modifier = Modifier.fillMaxWidth().background(Card, RoundedCornerShape(16.dp)).border(1.dp, Border, RoundedCornerShape(16.dp)).padding(16.dp)) {
                TaskProgressRow("Profile Completion", 0.7f, "3 of 4 done")
            }

            SectionHeaderSmall("Todo List")
            Column(modifier = Modifier.fillMaxWidth().background(Card, RoundedCornerShape(14.dp)).border(1.dp, Border, RoundedCornerShape(14.dp))) {
                TaskItemRow(Icons.Default.VerifiedUser, "Link BVN", "Unlock basic transactions", false, onBvnClick)
                HorizontalDivider(color = Border, modifier = Modifier.padding(horizontal = 16.dp))
                TaskItemRow(Icons.Default.PhotoCamera, "Profile Photo", "Make your profile unique", true)
                HorizontalDivider(color = Border, modifier = Modifier.padding(horizontal = 16.dp))
                TaskItemRow(Icons.Default.AlternateEmail, "Claim Veltra Tag", "Set your custom handle", true)
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun TaskProgressRow(label: String, progress: Float, sub: String) {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text(label, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
            Text(sub, color = Teal, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
        }
        Spacer(modifier = Modifier.height(12.dp))
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth().height(6.dp).clip(CircleShape),
            color = Teal,
            trackColor = Color.White.copy(alpha = 0.05f)
        )
    }
}

@Composable
fun TaskItemRow(icon: ImageVector, title: String, sub: String, isDone: Boolean, onClick: () -> Unit = {}) {
    Row(modifier = Modifier.fillMaxWidth().clickable(enabled = !isDone) { onClick() }.padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
        Box(modifier = Modifier.size(36.dp).background(if (isDone) SuccessGreen.copy(alpha = 0.1f) else Royal.copy(alpha = 0.1f), RoundedCornerShape(10.dp)), contentAlignment = Alignment.Center) {
            Icon(if (isDone) Icons.Default.Check else icon, contentDescription = null, tint = if (isDone) SuccessGreen else Royal, modifier = Modifier.size(18.dp))
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = if (isDone) Muted else Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
            Text(sub, color = Muted, fontSize = 11.sp, fontFamily = Urbanist)
        }
        if (!isDone) Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Muted, modifier = Modifier.size(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun TasksPreview() {
    VeltraTheme {
        TasksScreen({})
    }
}
