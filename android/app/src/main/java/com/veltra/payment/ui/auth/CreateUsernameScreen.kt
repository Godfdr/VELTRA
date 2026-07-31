package com.veltra.payment.ui.auth

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veltra.payment.ui.theme.*

@Composable
fun CreateUsernameScreen(
    onBackClick: () -> Unit,
    onContinueClick: () -> Unit,
    viewModel: AuthViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val state by viewModel.state.collectAsState()

    CreateUsernameContent(
        state = state,
        onBackClick = onBackClick,
        onContinueClick = {
            viewModel.finalizeIdentity()
            onContinueClick()
        },
        onUsernameChange = { viewModel.validateUsername(it) }
    )
}

@Composable
fun CreateUsernameContent(
    state: AuthState,
    onBackClick: () -> Unit,
    onContinueClick: () -> Unit,
    onUsernameChange: (String) -> Unit
) {
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
            }
        },
        bottomBar = {
            Box(modifier = Modifier.fillMaxWidth().padding(24.dp)) {
                val isButtonEnabled = state.username.length >= 5 && state.usernameError == null && !state.isProcessing
                val buttonBrush = if (isButtonEnabled) PrimaryGradient else SolidColor(Sub)
                
                Button(
                    onClick = onContinueClick,
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues(),
                    shape = RoundedCornerShape(50.dp),
                    enabled = isButtonEnabled
                ) {
                    Box(modifier = Modifier.fillMaxSize().background(buttonBrush), contentAlignment = Alignment.Center) {
                        if (state.isProcessing) {
                            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                        } else {
                            Text("Claim Veltra Tag", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier.size(72.dp).clip(RoundedCornerShape(22.dp)).background(PrimaryGradient),
                contentAlignment = Alignment.Center
            ) {
                Text("V", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            Text("Create your Veltra Tag", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Your unique ID for receiving payments instantly", color = Muted, fontSize = 12.sp, fontFamily = Urbanist)

            Spacer(modifier = Modifier.height(48.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text("Veltra Tag", color = Muted, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                Spacer(modifier = Modifier.height(12.dp))
                
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Card, RoundedCornerShape(16.dp))
                        .border(1.dp, if (state.usernameError != null) ErrorRed else Border, RoundedCornerShape(16.dp))
                        .padding(horizontal = 20.dp, vertical = 18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("@", color = Teal, fontSize = 18.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                    Spacer(modifier = Modifier.width(4.dp))
                    BasicTextField(
                        value = state.username,
                        onValueChange = onUsernameChange,
                        modifier = Modifier.weight(1f),
                        textStyle = LocalTextStyle.current.copy(color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist),
                        cursorBrush = SolidColor(Teal),
                        singleLine = true,
                        decorationBox = { innerTextField ->
                            if (state.username.isEmpty()) Text("alexveltra", color = Muted, fontSize = 18.sp, fontFamily = Urbanist)
                            innerTextField()
                        }
                    )
                    if (state.username.length >= 5 && state.usernameError == null) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = SuccessGreen, modifier = Modifier.size(20.dp))
                    }
                }
                
                if (state.usernameError != null) {
                    Text(
                        text = state.usernameError!!,
                        color = ErrorRed,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Urbanist,
                        modifier = Modifier.padding(top = 8.dp, start = 4.dp)
                    )
                } else {
                    Text(
                        text = "Username must be at least 5 characters",
                        color = Muted,
                        fontSize = 11.sp,
                        fontFamily = Urbanist,
                        modifier = Modifier.padding(top = 8.dp, start = 4.dp)
                    )
                }
            }
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview
@Composable
fun CreateUsernamePreview() {
    VeltraTheme {
        CreateUsernameContent(
            state = AuthState(username = "alexveltra"),
            onBackClick = {},
            onContinueClick = {},
            onUsernameChange = {}
        )
    }
}
