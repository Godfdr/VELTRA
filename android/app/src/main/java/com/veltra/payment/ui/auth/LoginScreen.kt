package com.veltra.payment.ui.auth

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dialpad
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veltra.payment.ui.VeltraTextField
import com.veltra.payment.ui.theme.*

@Composable
fun LoginScreen(
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize().background(Base)) {
        // Login Glows from enova-clone.html Screen 1
        Box(
            modifier = Modifier
                .size(300.dp)
                .offset(y = (-60).dp)
                .align(Alignment.TopCenter)
                .background(Brush.radialGradient(listOf(Color(0xFF485AF8).copy(alpha = 0.35f), Color.Transparent)))
        )
        Box(
            modifier = Modifier
                .size(200.dp)
                .offset(x = 40.dp, y = (-120).dp)
                .align(Alignment.BottomEnd)
                .background(Brush.radialGradient(listOf(Color(0xFF2EBCD5).copy(alpha = 0.2f), Color.Transparent)))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            // Logo from enova-clone.html
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(PrimaryGradient),
                contentAlignment = Alignment.Center
            ) {
                Text("V", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
            }

            Spacer(modifier = Modifier.height(48.dp))
            Text("Welcome\nback 👋", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.ExtraBold, lineHeight = 36.sp, fontFamily = Urbanist)
            Spacer(modifier = Modifier.height(10.dp))
            Text("Sign in to your Veltra account\nto continue.", color = Muted, fontSize = 14.sp, lineHeight = 21.sp, fontFamily = Urbanist)

            Spacer(modifier = Modifier.height(44.dp))

            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Column {
                    Text("EMAIL ADDRESS", color = Muted, fontSize = 12.sp, fontWeight = FontWeight.Medium, letterSpacing = 0.5.sp, fontFamily = Urbanist)
                    Spacer(modifier = Modifier.height(8.dp))
                    VeltraTextField(value = email, onValueChange = { email = it }, icon = null, placeholder = "alex@veltra.app")
                }

                Column {
                    Text("PASSWORD", color = Muted, fontSize = 12.sp, fontWeight = FontWeight.Medium, letterSpacing = 0.5.sp, fontFamily = Urbanist)
                    Spacer(modifier = Modifier.height(8.dp))
                    VeltraTextField(value = password, onValueChange = { password = it }, icon = null, placeholder = "••••••••••")
                }
            }

            Text("Forgot password?", color = Royal, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(top = 4.dp).align(Alignment.End).clickable { }, fontFamily = Urbanist)

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onSignInClick,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues(),
                shape = RoundedCornerShape(50.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize().background(PrimaryGradient), contentAlignment = Alignment.Center) {
                    Text("Sign In", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Box(modifier = Modifier.weight(1f).height(1.dp).background(Border))
                Text("OR CONTINUE WITH", color = Muted, fontSize = 12.sp, fontFamily = Urbanist)
                Box(modifier = Modifier.weight(1f).height(1.dp).background(Border))
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                BiometricButton("Face ID", Icons.Default.Face, Modifier.weight(1f))
                BiometricButton("PIN", Icons.Default.Dialpad, Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(24.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text("Don't have an account? ", color = Muted, fontSize = 13.sp, fontFamily = Urbanist)
                Text("Register", color = Royal, fontSize = 13.sp, fontWeight = FontWeight.Bold, modifier = Modifier.clickable { onSignUpClick() }, fontFamily = Urbanist)
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun BiometricButton(label: String, icon: androidx.compose.ui.graphics.vector.ImageVector, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.clip(RoundedCornerShape(14.dp)).background(Card2).border(1.dp, Border, RoundedCornerShape(14.dp)).clickable { }.padding(14.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = Royal, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(label, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Medium, fontFamily = Urbanist)
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    VeltraTheme {
        LoginScreen({}, {})
    }
}
