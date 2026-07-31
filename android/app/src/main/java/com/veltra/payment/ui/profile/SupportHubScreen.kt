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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veltra.payment.ui.SectionHeaderSmall
import com.veltra.payment.ui.theme.*

@Composable
fun SupportHubScreen(onBackClick: () -> Unit) {
    var message by remember { mutableStateOf("") }
    var isSending by remember { mutableStateOf(false) }
    var showSuccess by remember { mutableStateOf(false) }

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
                    Text("Support Hub", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                }
            }
        }
    ) { paddingValues ->
        if (showSuccess) {
            SupportSuccessView(onBackClick)
        } else {
            Column(
                modifier = Modifier.fillMaxSize().padding(paddingValues).verticalScroll(rememberScrollState()).padding(horizontal = 20.dp, vertical = 14.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text("How can we help you today?", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
                
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    SupportCategoryItem("Transaction", Icons.Default.SyncAlt, Modifier.weight(1f))
                    SupportCategoryItem("Security", Icons.Default.Shield, Modifier.weight(1f))
                    SupportCategoryItem("Other", Icons.Default.ChatBubbleOutline, Modifier.weight(1f))
                }

                SectionHeaderSmall("Send us a message")
                
                Column(
                    modifier = Modifier.fillMaxWidth().background(Card, RoundedCornerShape(16.dp)).border(1.dp, Border, RoundedCornerShape(16.dp)).padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    androidx.compose.foundation.text.BasicTextField(
                        value = message,
                        onValueChange = { message = it },
                        modifier = Modifier.fillMaxWidth().heightIn(min = 120.dp),
                        textStyle = LocalTextStyle.current.copy(color = Color.White, fontSize = 14.sp, fontFamily = Urbanist),
                        cursorBrush = SolidColor(Teal),
                        decorationBox = { innerTextField ->
                            if (message.isEmpty()) Text("Describe your issue here...", color = Muted, fontSize = 14.sp, fontFamily = Urbanist)
                            innerTextField()
                        }
                    )
                    
                    val buttonBrush = if (message.length > 10) PrimaryGradient else SolidColor(Sub)
                    Button(
                        onClick = {
                            isSending = true
                            android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                                isSending = false
                                showSuccess = true
                            }, 1500)
                        },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        contentPadding = PaddingValues(),
                        shape = RoundedCornerShape(12.dp),
                        enabled = message.length > 10 && !isSending
                    ) {
                        Box(modifier = Modifier.fillMaxSize().background(buttonBrush), contentAlignment = Alignment.Center) {
                            if (isSending) {
                                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                            } else {
                                Text("Send Ticket", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                            }
                        }
                    }
                }

                SectionHeaderSmall("Quick FAQ")
                Column(modifier = Modifier.fillMaxWidth().background(Card, RoundedCornerShape(14.dp)).border(1.dp, Border, RoundedCornerShape(14.dp))) {
                    FaqRow("How to reset my Veltra PIN?")
                    HorizontalDivider(color = Border, modifier = Modifier.padding(horizontal = 16.dp))
                    FaqRow("What is Veltra Tag?")
                    HorizontalDivider(color = Border, modifier = Modifier.padding(horizontal = 16.dp))
                    FaqRow("Is Veltra available offline?")
                }
                
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SupportHubPreview() {
    VeltraTheme {
        SupportHubScreen({})
    }
}

@Composable
fun SupportCategoryItem(label: String, icon: ImageVector, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.clip(RoundedCornerShape(16.dp)).background(Card).border(1.dp, Border, RoundedCornerShape(16.dp)).clickable { }.padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(icon, contentDescription = null, tint = Teal, modifier = Modifier.size(24.dp))
        Text(label, color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
    }
}

@Composable
fun FaqRow(question: String) {
    Row(modifier = Modifier.fillMaxWidth().clickable { }.padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
        Text(question, color = Color.White, fontSize = 13.sp, fontFamily = Urbanist)
        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Muted, modifier = Modifier.size(16.dp))
    }
}

@Composable
fun SupportSuccessView(onBackClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(modifier = Modifier.size(80.dp).background(SuccessGreen.copy(alpha = 0.1f), CircleShape).border(1.dp, SuccessGreen.copy(alpha = 0.3f), CircleShape), contentAlignment = Alignment.Center) {
            Icon(Icons.Default.Check, contentDescription = null, tint = SuccessGreen, modifier = Modifier.size(40.dp))
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text("Ticket Received", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
        Text("Our team will get back to you within 24 hours.", color = Muted, fontSize = 13.sp, textAlign = TextAlign.Center, modifier = Modifier.padding(top = 8.dp), fontFamily = Urbanist)
        Spacer(modifier = Modifier.height(48.dp))
        Button(onClick = onBackClick, modifier = Modifier.fillMaxWidth().height(56.dp), colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent), contentPadding = PaddingValues(), shape = RoundedCornerShape(50.dp)) {
            Box(modifier = Modifier.fillMaxSize().background(PrimaryGradient), contentAlignment = Alignment.Center) {
                Text("Go Back", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
            }
        }
    }
}
