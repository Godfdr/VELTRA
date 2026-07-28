package com.veltra.payment.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veltra.payment.ui.theme.*

@Composable
fun LoginScreen(onSignInClick: () -> Unit, onSignUpClick: () -> Unit) {
    var email by remember { mutableStateOf("robert@example.com") }
    var password by remember { mutableStateOf("••••••••••") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Base)
    ) {
        // Glow effects
        Box(
            modifier = Modifier
                .size(300.dp)
                .offset(y = (-60).dp)
                .align(Alignment.TopCenter)
                .background(
                    Brush.radialGradient(
                        colors = listOf(Ultra.copy(alpha = 0.35f), Color.Transparent),
                    )
                )
        )
        Box(
            modifier = Modifier
                .size(200.dp)
                .offset(x = 40.dp, y = (-120).dp)
                .align(Alignment.BottomEnd)
                .background(
                    Brush.radialGradient(
                        colors = listOf(Teal.copy(alpha = 0.2f), Color.Transparent),
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 28.dp, vertical = 60.dp),
            horizontalAlignment = Alignment.Start
        ) {
            // Logo
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Brush.linearGradient(listOf(Ultra, Teal)))
                    .shadow(elevation = 32.dp, shape = RoundedCornerShape(16.dp), ambientColor = Ultra, spotColor = Ultra),
                contentAlignment = Alignment.Center
            ) {
                Text("V", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
            }

            Spacer(modifier = Modifier.height(48.dp))

            Text(
                "Welcome\nback 👋",
                color = Color.White,
                fontSize = 32.sp, // Slightly larger
                fontWeight = FontWeight.ExtraBold,
                lineHeight = 38.sp,
                fontFamily = Urbanist
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                "Sign in to your Veltra account\nto continue.",
                color = Muted,
                fontSize = 15.sp, // Slightly larger
                lineHeight = 22.sp,
                fontFamily = Urbanist
            )

            Spacer(modifier = Modifier.height(44.dp))

            // Email Field
            Text("EMAIL ADDRESS", color = Muted, fontSize = 12.sp, fontWeight = FontWeight.Medium, letterSpacing = 0.5.sp, fontFamily = Urbanist)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Card2, RoundedCornerShape(14.dp)),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedBorderColor = Border,
                    focusedBorderColor = Ultra.copy(alpha = 0.5f),
                    unfocusedTextColor = Color.White,
                    focusedTextColor = Color.White
                ),
                shape = RoundedCornerShape(14.dp),
                placeholder = { Text("robert@example.com", color = Sub, fontFamily = Urbanist) },
                textStyle = LocalTextStyle.current.copy(fontFamily = Urbanist, fontSize = 15.sp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password Field
            Text("PASSWORD", color = Muted, fontSize = 12.sp, fontWeight = FontWeight.Medium, letterSpacing = 0.5.sp, fontFamily = Urbanist)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Card2, RoundedCornerShape(14.dp)),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedBorderColor = Border,
                    focusedBorderColor = Ultra.copy(alpha = 0.5f),
                    unfocusedTextColor = Color.White,
                    focusedTextColor = Color.White
                ),
                shape = RoundedCornerShape(14.dp),
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(
                            if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = null,
                            tint = Muted
                        )
                    }
                },
                placeholder = { Text("••••••••••", color = Sub, fontFamily = Urbanist) },
                textStyle = LocalTextStyle.current.copy(fontFamily = Urbanist, fontSize = 15.sp)
            )

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "Forgot password?",
                color = Royal,
                fontSize = 13.sp,
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable { },
                fontFamily = Urbanist
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Sign In Button
            Button(
                onClick = onSignInClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .shadow(elevation = 32.dp, shape = RoundedCornerShape(50.dp), ambientColor = Ultra.copy(alpha = 0.35f), spotColor = Ultra.copy(alpha = 0.35f)),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues(),
                shape = RoundedCornerShape(50.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Brush.linearGradient(listOf(Ultra, Teal))),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Sign In", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.weight(1f).height(1.dp).background(Border))
                Text(" OR CONTINUE WITH ", color = Muted, fontSize = 12.sp, fontFamily = Urbanist)
                Box(modifier = Modifier.weight(1f).height(1.dp).background(Border))
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                BiometricButton(icon = null, label = "Face ID", modifier = Modifier.weight(1f))
                BiometricButton(icon = null, label = "PIN", modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text("Don't have an account? ", color = Muted, fontSize = 14.sp, fontFamily = Urbanist)
                Text("Sign Up", color = Royal, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.clickable { onSignUpClick() }, fontFamily = Urbanist)
            }
        }
    }
}

@Composable
fun BiometricButton(icon: ImageVector?, label: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .border(1.dp, Border, RoundedCornerShape(14.dp))
            .background(Card2, RoundedCornerShape(14.dp))
            .clickable { }
            .padding(vertical = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            // Icon would go here
            Text(label, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Medium, fontFamily = Urbanist)
        }
    }
}

@Preview
@Composable
fun LoginPreview() {
    VeltraTheme {
        LoginScreen({}, {})
    }
}
