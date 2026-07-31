package com.veltra.payment.ui.transaction

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veltra.payment.ui.theme.*
import java.math.BigDecimal

@Composable
fun PingMeScreen(onBackClick: () -> Unit, onSendPingClick: () -> Unit) {
    var amount by remember { mutableStateOf("5,000") }
    var note by remember { mutableStateOf("") }
    
    val numericAmount = remember(amount) {
        amount.replace(",", "").toBigDecimalOrNull() ?: BigDecimal.ZERO
    }

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
                    Text("Ping Me", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                }

                Column(modifier = Modifier.fillMaxWidth().padding(vertical = 32.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Request amount", color = Muted, fontSize = 11.sp, fontFamily = Urbanist, fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.horizontalScroll(rememberScrollState())) {
                        Text("₦", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
                        Spacer(modifier = Modifier.width(4.dp))
                        androidx.compose.foundation.text.BasicTextField(
                            value = amount,
                            onValueChange = { amount = it.filter { c -> c.isDigit() || c == ',' || c == '.' } },
                            textStyle = LocalTextStyle.current.copy(
                                color = Color.White, 
                                fontSize = 38.sp, 
                                fontWeight = FontWeight.ExtraBold, 
                                fontFamily = Urbanist,
                                letterSpacing = (-1).sp
                            ),
                            cursorBrush = SolidColor(Teal),
                            singleLine = true
                        )
                    }
                }
            }
        },
        bottomBar = {
            Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                val isButtonEnabled = numericAmount > BigDecimal.ZERO
                val buttonBrush = if (isButtonEnabled) PrimaryGradient else SolidColor(Sub)
                Button(
                    onClick = onSendPingClick,
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues(),
                    shape = RoundedCornerShape(50.dp),
                    enabled = isButtonEnabled
                ) {
                    Box(modifier = Modifier.fillMaxSize().background(buttonBrush), contentAlignment = Alignment.Center) {
                        Text("Send Ping", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).verticalScroll(rememberScrollState()).padding(horizontal = 16.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Search Veltra user", color = Muted, fontSize = 11.sp, fontFamily = Urbanist, fontWeight = FontWeight.Bold)

            androidx.compose.foundation.text.BasicTextField(
                value = "",
                onValueChange = { },
                modifier = Modifier.fillMaxWidth().background(Card, RoundedCornerShape(14.dp)).border(1.dp, Border, RoundedCornerShape(14.dp)).padding(14.dp),
                textStyle = LocalTextStyle.current.copy(color = Color.White, fontSize = 13.sp, fontFamily = Urbanist),
                cursorBrush = SolidColor(Teal),
                decorationBox = { innerTextField ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Search, contentDescription = null, tint = Muted, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        if (true) Text("Search by name, @username, or phone", color = Muted, fontSize = 13.sp, fontFamily = Urbanist)
                        innerTextField()
                    }
                }
            )

            Text("Select friend", color = Muted, fontSize = 11.sp, fontFamily = Urbanist, fontWeight = FontWeight.Bold)

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                PingFriendAvatar("VC", "Victor", true)
                PingFriendAvatar("SH", "Sarah", false)
                PingFriendAvatar("JD", "John", false)
                PingFriendAvatar("MK", "Musa", false)
            }

            Spacer(modifier = Modifier.height(10.dp))
            Text("Add a note", color = Muted, fontSize = 11.sp, fontFamily = Urbanist, fontWeight = FontWeight.Bold)

            androidx.compose.foundation.text.BasicTextField(
                value = note,
                onValueChange = { note = it },
                modifier = Modifier.fillMaxWidth().background(Card, RoundedCornerShape(14.dp)).border(1.dp, Border, RoundedCornerShape(14.dp)).padding(16.dp),
                textStyle = LocalTextStyle.current.copy(color = Color.White, fontSize = 13.sp, fontFamily = Urbanist),
                cursorBrush = SolidColor(Teal),
                decorationBox = { innerTextField ->
                    if (note.isEmpty()) Text("What's this for? (e.g. Lunch at Cactus 🌮)", color = Muted, fontSize = 13.sp, fontFamily = Urbanist)
                    innerTextField()
                }
            )
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun PingFriendAvatar(initials: String, name: String, isSelected: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Box(
            modifier = Modifier.size(54.dp).clip(CircleShape).background(if (isSelected) Royal else Color.White.copy(alpha = 0.05f)).border(1.dp, if (isSelected) Teal else Border, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(initials, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
        }
        Text(name, color = if (isSelected) Color.White else Muted, fontSize = 10.sp, fontWeight = FontWeight.SemiBold, fontFamily = Urbanist)
    }
}

@Preview(showBackground = true)
@Composable
fun PingMePreview() {
    VeltraTheme {
        PingMeScreen({}, {})
    }
}
