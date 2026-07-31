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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veltra.payment.ui.theme.*

@Composable
fun BvnVerificationScreen(onBackClick: () -> Unit) {
    var bvn by remember { mutableStateOf("") }
    var isVerifying by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Base,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 20.dp)
                    .statusBarsPadding(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.size(34.dp).background(Color.White.copy(alpha = 0.08f), CircleShape).border(1.dp, Border, CircleShape)
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White, modifier = Modifier.size(16.dp))
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text("BVN Verification", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier.size(72.dp).clip(RoundedCornerShape(20.dp)).background(Royal.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Shield, contentDescription = null, tint = Royal, modifier = Modifier.size(32.dp))
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            Text("Link your BVN", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
            Text("Secure your account and increase your limits", color = Muted, fontSize = 12.sp, textAlign = TextAlign.Center, modifier = Modifier.padding(top = 8.dp), fontFamily = Urbanist)

            Spacer(modifier = Modifier.height(48.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text("Bank Verification Number", color = Muted, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                Spacer(modifier = Modifier.height(12.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth().background(Card, RoundedCornerShape(14.dp)).border(1.dp, Border, RoundedCornerShape(14.dp)).padding(horizontal = 16.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    androidx.compose.foundation.text.BasicTextField(
                        value = bvn,
                        onValueChange = { if (it.length <= 11) bvn = it.filter { c -> c.isDigit() } },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = LocalTextStyle.current.copy(color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist),
                        cursorBrush = SolidColor(Teal),
                        keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number),
                        decorationBox = { innerTextField ->
                            if (bvn.isEmpty()) Text("221 234 5678", color = Muted, fontSize = 16.sp, fontFamily = Urbanist)
                            innerTextField()
                        }
                    )
                }
                Text("Your BVN is secure and won't be shared", color = Muted, fontSize = 10.sp, modifier = Modifier.padding(top = 8.dp, start = 4.dp), fontFamily = Urbanist)
            }

            Spacer(modifier = Modifier.weight(1f))

            val buttonBrush = if (bvn.length == 11) PrimaryGradient else SolidColor(Sub)
            Button(
                onClick = { isVerifying = true },
                modifier = Modifier.fillMaxWidth().height(56.dp).padding(bottom = 32.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues(),
                shape = RoundedCornerShape(50.dp),
                enabled = bvn.length == 11 && !isVerifying
            ) {
                Box(modifier = Modifier.fillMaxSize().background(buttonBrush), contentAlignment = Alignment.Center) {
                    if (isVerifying) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                    } else {
                        Text("Verify BVN", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BvnVerificationPreview() {
    VeltraTheme {
        BvnVerificationScreen({})
    }
}
