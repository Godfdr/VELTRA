package com.veltra.payment.ui.auth

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veltra.payment.ui.theme.*

@Composable
fun SignUpScreen(onBackClick: () -> Unit, onSignInClick: () -> Unit) {
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
                            .background(Color.White.copy(alpha = 0.08f), RoundedCornerShape(50.dp))
                            .border(1.dp, Border, RoundedCornerShape(50.dp))
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            // Logo with Exact Gradient
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(Brush.linearGradient(listOf(Royal, Teal))),
                contentAlignment = Alignment.Center
            ) {
                Text("V", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text("Create your account", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
            Text("Join Veltra in under a minute", color = Muted, fontSize = 11.sp, fontFamily = Urbanist)

            Spacer(modifier = Modifier.height(24.dp))

            // Auth Card - Matched padding and radius
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Card, RoundedCornerShape(22.dp))
                    .border(1.5.dp, Border, RoundedCornerShape(22.dp))
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    AuthField(icon = Icons.Default.Person, label = "First name", modifier = Modifier.weight(1f))
                    AuthField(icon = null, label = "Last name", modifier = Modifier.weight(1f))
                }

                AuthField(icon = Icons.Default.Email, label = "Email address")
                AuthField(icon = Icons.Default.Phone, label = "Phone number")
                AuthField(icon = Icons.Default.Lock, label = "Create password")

                Spacer(modifier = Modifier.height(2.dp))

                Row(verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Box(
                        modifier = Modifier
                            .size(18.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .background(Brush.linearGradient(listOf(Royal, Teal)))
                            .padding(2.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("✓", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                    Text(
                        text = buildAnnotatedString {
                            append("I agree to Veltra's ")
                            pushStyle(SpanStyle(color = Teal, fontWeight = FontWeight.Bold))
                            append("Terms of Service")
                            pop()
                            append(" and ")
                            pushStyle(SpanStyle(color = Teal, fontWeight = FontWeight.Bold))
                            append("Privacy Policy")
                        },
                        color = Muted,
                        fontSize = 10.5.sp,
                        lineHeight = 15.sp,
                        fontFamily = Urbanist
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // High-Fidelity Button with Shadow
                Button(
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .shadow(
                            elevation = 24.dp, 
                            shape = RoundedCornerShape(50.dp), 
                            ambientColor = Royal.copy(alpha = 0.5f), 
                            spotColor = Royal.copy(alpha = 0.5f)
                        ),
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
                        Text("Create Account", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(modifier = Modifier.padding(bottom = 32.dp)) {
                Text("Already have an account? ", color = Muted, fontSize = 12.sp, fontFamily = Urbanist)
                Text("Sign in", color = Teal, fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.clickable { onSignInClick() }, fontFamily = Urbanist)
            }
        }
    }
}

@Composable
fun AuthField(icon: ImageVector?, label: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Card2, RoundedCornerShape(12.dp))
            .border(1.dp, Border, RoundedCornerShape(12.dp))
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        if (icon != null) {
            Icon(icon, contentDescription = null, tint = Muted, modifier = Modifier.size(15.dp))
        }
        Text(label, color = Muted, fontSize = 12.sp, fontFamily = Urbanist, fontWeight = FontWeight.Medium)
    }
}

@Preview
@Composable
fun SignUpPreview() {
    VeltraTheme {
        SignUpScreen({}, {})
    }
}
