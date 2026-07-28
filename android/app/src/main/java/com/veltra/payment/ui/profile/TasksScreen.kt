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
fun TasksScreen(onBackClick: () -> Unit, onBvnClick: () -> Unit) {
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
                    Text("Tasks", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
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
            // Progress Card
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(Brush.linearGradient(listOf(Royal.copy(alpha = 0.22f), Teal.copy(alpha = 0.14f))))
                    .border(1.dp, Royal.copy(alpha = 0.3f), RoundedCornerShape(20.dp))
                    .padding(18.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Final Touches ✏️", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                        Text("Just finish these quick tasks!", color = Muted, fontSize = 11.sp, fontFamily = Urbanist)
                    }
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(Card)
                            .border(1.dp, Border, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("1/3", color = Teal, fontSize = 12.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                // Progress Bar
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .background(Card2, RoundedCornerShape(50.dp))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.33f)
                            .fillMaxHeight()
                            .background(Brush.horizontalGradient(listOf(Royal, Teal)), RoundedCornerShape(50.dp))
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            TaskRow(
                icon = Icons.Default.Fingerprint,
                title = "Protect your wallet",
                sub = "Set up biometrics to secure your wallet",
                color = Royal,
                isDone = true
            )
            TaskRow(
                icon = Icons.Default.VerifiedUser,
                title = "Complete your verification",
                sub = "Verify your identity to unlock full access",
                color = Royal
            )
            TaskRow(
                icon = Icons.Default.CreditCard,
                title = "BVN verification",
                sub = "Secure your account and unlock more",
                color = Royal,
                onClick = onBvnClick
            )
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun TaskRow(icon: ImageVector, title: String, sub: String, color: Color, isDone: Boolean = false, onClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Card, RoundedCornerShape(16.dp))
            .border(1.dp, if (isDone) Teal.copy(alpha = 0.25f) else Border, RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(if (isDone) Teal.copy(alpha = 0.12f) else color.copy(alpha = 0.12f))
                .border(1.dp, if (isDone) Teal.copy(alpha = 0.25f) else color.copy(alpha = 0.2f), RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = if (isDone) Teal else color, modifier = Modifier.size(19.dp))
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = if (isDone) Muted else Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
            Text(sub, color = Muted, fontSize = 11.sp, fontFamily = Urbanist, fontWeight = FontWeight.Medium)
        }
        if (isDone) {
            Box(
                modifier = Modifier
                    .size(26.dp)
                    .clip(CircleShape)
                    .background(Teal.copy(alpha = 0.15f))
                    .border(1.dp, Teal.copy(alpha = 0.3f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text("✓", color = Teal, fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }
        } else {
            Text("›", color = Muted, fontSize = 16.sp)
        }
    }
}

@Preview
@Composable
fun TasksPreview() {
    VeltraTheme {
        TasksScreen({}, {})
    }
}
