package com.veltra.payment.ui

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veltra.payment.ui.theme.*

@Composable
fun SectionHeaderSmall(text: String) {
    Text(
        text = text.uppercase(),
        color = Muted,
        fontSize = 10.5.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp,
        fontFamily = Urbanist,
        modifier = Modifier.padding(bottom = 10.dp, start = 4.dp)
    )
}

@Composable
fun VeltraTextField(
    value: String,
    onValueChange: (String) -> Unit,
    icon: ImageVector?,
    placeholder: String,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Card2, RoundedCornerShape(12.dp))
            .border(1.dp, Border, RoundedCornerShape(12.dp))
            .padding(horizontal = 14.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        if (icon != null) {
            Icon(icon, contentDescription = null, tint = Muted, modifier = Modifier.size(15.dp))
        }
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.weight(1f).padding(vertical = 12.dp),
            textStyle = LocalTextStyle.current.copy(color = Color.White, fontSize = 12.sp, fontFamily = Urbanist),
            cursorBrush = SolidColor(Teal),
            keyboardOptions = keyboardOptions,
            visualTransformation = visualTransformation,
            decorationBox = { innerTextField ->
                if (value.isEmpty()) {
                    Text(placeholder, color = Muted, fontSize = 12.sp, fontFamily = Urbanist)
                }
                innerTextField()
            }
        )
    }
}

@Composable
fun CurrencyChipInteractive(flag: String, code: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier.clip(RoundedCornerShape(50.dp)).background(Color.White.copy(alpha = 0.08f)).border(1.dp, Border, RoundedCornerShape(50.dp)).clickable { onClick() }.padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(flag, fontSize = 18.sp)
        Spacer(modifier = Modifier.width(6.dp))
        Text(code, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
        Spacer(modifier = Modifier.width(4.dp))
        Text("▾", color = Muted, fontSize = 10.sp)
    }
}

@Composable
fun DetailRowInteractive(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(16.dp, 14.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, color = Muted, fontSize = 13.sp, fontFamily = Urbanist)
        Text(value, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, fontFamily = Urbanist)
    }
}

@Composable
fun AvatarStack() {
    Row {
        val colors = listOf(Royal, Teal, WarningOrange, Color(0xFF9B6DFF))
        val labels = listOf("AV", "VC", "SH", "+2")
        labels.forEachIndexed { index, label ->
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .offset(x = (index * (-12)).dp)
                    .clip(CircleShape)
                    .background(colors[index % colors.size].copy(alpha = 0.6f))
                    .border(2.dp, Card, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(label, color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
            }
        }
    }
}

@Composable
fun SectionHeader(text: String, onSeeAllClick: () -> Unit = {}) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
        Text(
            text = "See all",
            color = Teal,
            fontSize = 11.sp,
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .clickable { onSeeAllClick() }
                .padding(horizontal = 4.dp, vertical = 2.dp),
            fontFamily = Urbanist,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun CompactPocketCard(
    name: String,
    subText: String,
    amount: String,
    goal: String,
    progress: Float,
    color: Color,
    icon: ImageVector,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Card, RoundedCornerShape(16.dp))
            .border(1.dp, Border, RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(color.copy(alpha = 0.15f))
                    .border(1.dp, color.copy(alpha = 0.25f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(18.dp))
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(name, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                Text(subText, color = Muted, fontSize = 10.5.sp, fontFamily = Urbanist, fontWeight = FontWeight.Medium)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(amount, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist, letterSpacing = (-0.2).sp)
                Text(goal, color = Muted, fontSize = 10.sp, fontWeight = FontWeight.SemiBold, fontFamily = Urbanist)
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 14.dp)
                .height(5.dp)
                .background(Card2, RoundedCornerShape(50.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(progress)
                    .fillMaxHeight()
                    .background(color, RoundedCornerShape(50.dp))
            )
        }
    }
}

@Composable
fun SuccessDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 13.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = Muted, fontSize = 12.sp, fontFamily = Urbanist, fontWeight = FontWeight.Medium)
        Text(value, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, fontFamily = Urbanist)
    }
}

@Composable
fun IncomingPingOverlay(
    isVisible: Boolean,
    onRejectClick: () -> Unit,
    onPayClick: () -> Unit
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically { it } + fadeIn(),
        exit = slideOutVertically { it } + fadeOut()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f))
                .clickable(enabled = false) { }
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 60.dp)
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Card.copy(alpha = 0.92f))
                    .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(24.dp))
                    .padding(18.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(PrimaryGradient),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("AV", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                    }
                    Column {
                        Text("Alex Veltra", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                        Text("Veltra · Ping Me", color = Muted, fontSize = 10.sp, fontFamily = Urbanist)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Text("now", color = Muted, fontSize = 10.sp, fontFamily = Urbanist)
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text("Alex is requesting", color = Color.White, fontSize = 13.sp, lineHeight = 20.sp, fontFamily = Urbanist)
                Text("₦5,000.00", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(top = 8.dp), fontFamily = Urbanist)
                Text("\"Dinner at Cactus 🌮\"", color = Muted, fontSize = 11.5.sp, modifier = Modifier.padding(top = 4.dp), fontStyle = androidx.compose.ui.text.font.FontStyle.Italic, fontFamily = Urbanist)

                Spacer(modifier = Modifier.height(16.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Button(
                        onClick = onRejectClick,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.08f)),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.12f)),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text("Reject", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                    }
                    Button(
                        onClick = onPayClick,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        contentPadding = PaddingValues(),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(PrimaryGradient),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Pay ₦5,000", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun VeltraFooter(
    activeRoute: String,
    onDashboardClick: () -> Unit,
    onPocketsClick: () -> Unit,
    onProfileClick: () -> Unit,
    onTapGoClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        // Main Navigation Bar
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            color = Color(0xFF0D1117), // Slightly darker for depth
            border = androidx.compose.foundation.BorderStroke(1.dp, Border)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                FooterItem(Icons.Default.Home, "Home", activeRoute == "dashboard", onDashboardClick, Modifier.weight(1f))
                
                // Space for the big floating button in the center
                Spacer(modifier = Modifier.weight(1f))
                
                FooterItem(Icons.Default.AccountBalanceWallet, "Pockets", activeRoute == "pockets", onPocketsClick, Modifier.weight(1f))
                FooterItem(Icons.Default.Person, "Profile", activeRoute == "profile", onProfileClick, Modifier.weight(1f))
            }
        }

        // Big Tap & Go Button
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-10).dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(PrimaryGradient)
                    .border(4.dp, Base, CircleShape) // "Cutout" effect
                    .clickable { onTapGoClick() }
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.PhonelinkRing,
                    contentDescription = "Tap & Go",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }
            Text(
                "Tap & Go",
                color = if (activeRoute == "tapGo") Royal else Muted,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Urbanist,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
fun FooterItem(
    icon: ImageVector,
    label: String,
    isActive: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            icon,
            contentDescription = label,
            tint = if (isActive) Royal else Muted.copy(alpha = 0.6f),
            modifier = Modifier.size(24.dp)
        )
        Text(
            label,
            color = if (isActive) Royal else Muted.copy(alpha = 0.6f),
            fontSize = 10.sp,
            fontWeight = if (isActive) FontWeight.Bold else FontWeight.Medium,
            fontFamily = Urbanist
        )
    }
}
