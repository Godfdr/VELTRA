package com.veltra.payment.ui.auth

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
fun CreateUsernameScreen(onBackClick: () -> Unit, onContinueClick: () -> Unit) {
    var username by remember { mutableStateOf("alexveltra") }

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
                        .padding(horizontal = 20.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier
                            .size(34.dp)
                            .background(Color.White.copy(alpha = 0.08f), RoundedCornerShape(50.dp))
                            .border(1.dp, Border, RoundedCornerShape(50.dp))
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White, modifier = Modifier.size(16.dp))
                    }
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
                    onClick = onContinueClick,
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
                        Text("Continue", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            // Hero Icon
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(22.dp))
                    .background(Brush.linearGradient(listOf(Royal.copy(alpha = 0.25f), Teal.copy(alpha = 0.15f))))
                    .border(1.dp, Royal.copy(alpha = 0.3f), RoundedCornerShape(22.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Person, contentDescription = null, tint = Teal, modifier = Modifier.size(32.dp))
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text("Claim your Veltra tag", color = Color.White, fontSize = 19.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "This is how friends will find and pay you.\nChoose something memorable.",
                color = Muted,
                fontSize = 12.sp,
                lineHeight = 19.sp,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                fontFamily = Urbanist,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Input Field
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .background(Card, RoundedCornerShape(14.dp))
                    .border(1.5.dp, Teal.copy(alpha = 0.4f), RoundedCornerShape(14.dp))
                    .padding(15.dp, 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("@", color = Teal, fontSize = 15.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                Spacer(modifier = Modifier.width(4.dp))
                Text(username, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .size(22.dp)
                        .clip(CircleShape)
                        .background(Teal.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("✓", color = Teal, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Icon(Icons.Default.Check, contentDescription = null, tint = Teal, modifier = Modifier.size(12.dp))
                Text("@$username is available", color = Teal, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Suggestions
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Text("Or try one of these", color = Muted, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    SuggestionChip("@alex.veltra")
                    SuggestionChip("@alexv_ng")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    SuggestionChip("@therealalex")
                    SuggestionChip("@alex2026")
                }
            }
        }
    }
}

@Composable
fun SuggestionChip(text: String) {
    Box(
        modifier = Modifier
            .background(Card, RoundedCornerShape(20.dp))
            .border(1.dp, Border, RoundedCornerShape(20.dp))
            .padding(horizontal = 14.dp, vertical = 7.dp)
            .clickable { }
    ) {
        Text(text, color = Muted, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, fontFamily = Urbanist)
    }
}

@Preview
@Composable
fun CreateUsernamePreview() {
    VeltraTheme {
        CreateUsernameScreen({}, {})
    }
}
