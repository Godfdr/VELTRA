package com.veltra.payment.ui.auth

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veltra.payment.ui.VeltraTextField
import com.veltra.payment.ui.theme.*

@Composable
fun SignUpScreen(
    onBackClick: () -> Unit,
    onSignInClick: () -> Unit,
    onCreateAccountClick: () -> Unit = {},
    viewModel: AuthViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var agreedToTerms by remember { mutableStateOf(false) }

    val isFormValid = firstName.isNotBlank() && email.isNotBlank() && password.length >= 6 && agreedToTerms

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
                    modifier = Modifier
                        .size(34.dp)
                        .background(Color.White.copy(alpha = 0.08f), CircleShape)
                        .border(1.dp, Border, CircleShape)
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White, modifier = Modifier.size(16.dp))
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(PrimaryGradient),
                contentAlignment = Alignment.Center
            ) {
                Text("V", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            Text("Create your account", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
            Text("Join Veltra in under a minute", color = Muted, fontSize = 11.sp, fontFamily = Urbanist)

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Card, RoundedCornerShape(22.dp))
                    .border(1.dp, Border, RoundedCornerShape(22.dp))
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    VeltraTextField(value = firstName, onValueChange = { firstName = it }, icon = Icons.Default.Person, placeholder = "First name", modifier = Modifier.weight(1f))
                    VeltraTextField(value = lastName, onValueChange = { lastName = it }, icon = null, placeholder = "Last name", modifier = Modifier.weight(1f))
                }
                VeltraTextField(value = email, onValueChange = { email = it }, icon = Icons.Default.Email, placeholder = "Email address", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email))
                VeltraTextField(value = phoneNumber, onValueChange = { phoneNumber = it }, icon = Icons.Default.Phone, placeholder = "Phone number", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone))
                VeltraTextField(value = password, onValueChange = { password = it }, icon = Icons.Default.Lock, placeholder = "Create password", visualTransformation = PasswordVisualTransformation(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password))

                Row(
                    modifier = Modifier.padding(top = 2.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    val checkboxBrush = if (agreedToTerms) PrimaryGradient else SolidColor(Color.Transparent)
                    Box(
                        modifier = Modifier
                            .size(18.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .background(checkboxBrush)
                            .border(1.5.dp, Border, RoundedCornerShape(5.dp))
                            .clickable { agreedToTerms = !agreedToTerms },
                        contentAlignment = Alignment.Center
                    ) {
                        if (agreedToTerms) Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(11.dp))
                    }
                    Text(
                        text = buildAnnotatedString {
                            append("I agree to Veltra's ")
                            withStyle(style = SpanStyle(color = Teal, fontWeight = FontWeight.Bold)) { append("Terms of Service") }
                            append(" and ")
                            withStyle(style = SpanStyle(color = Teal, fontWeight = FontWeight.Bold)) { append("Privacy Policy") }
                        },
                        color = Muted,
                        fontSize = 10.5.sp,
                        lineHeight = 15.sp,
                        fontFamily = Urbanist
                    )
                }

                val buttonBrush = if (isFormValid) PrimaryGradient else SolidColor(Sub)
                Button(
                    onClick = {
                        viewModel.validateUsername("$firstName$lastName".lowercase())
                        viewModel.updateRegistrationData(email, phoneNumber)
                        onCreateAccountClick()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(top = 4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues(),
                    shape = RoundedCornerShape(50.dp),
                    enabled = isFormValid
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(buttonBrush),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Create Account", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth().clickable { onSignInClick() }) {
                Text("Already have an account? ", color = Muted, fontSize = 12.sp, fontFamily = Urbanist)
                Text("Sign in", color = Teal, fontSize = 12.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpPreview() {
    VeltraTheme {
        SignUpScreen({}, {})
    }
}
