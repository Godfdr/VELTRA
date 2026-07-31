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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veltra.payment.ui.theme.*

@Composable
fun ChangePinScreen(
    onBackClick: () -> Unit,
    viewModel: ChangePinViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val state by viewModel.state.collectAsState()

    ChangePinContent(
        state = state,
        onBackClick = onBackClick,
        onNumberClick = { viewModel.onNumberClick(it) },
        onDeleteClick = { viewModel.onDeleteClick() }
    )
}

@Composable
fun ChangePinContent(
    state: ChangePinState,
    onBackClick: () -> Unit,
    onNumberClick: (String) -> Unit,
    onDeleteClick: () -> Unit
) {
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
                    Text("Change PIN", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                }
            }
        }
    ) { paddingValues ->
        if (state.step == PinStep.SUCCESS) {
            SuccessContent(onBackClick)
        } else {
            Column(
                modifier = Modifier.fillMaxSize().padding(paddingValues).padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(40.dp))
                
                Text(
                    text = when (state.step) {
                        PinStep.ENTER_CURRENT -> "Enter current PIN"
                        PinStep.ENTER_NEW -> "Enter new PIN"
                        PinStep.CONFIRM_NEW -> "Confirm new PIN"
                        else -> ""
                    },
                    color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Text(
                    text = "Ensure your PIN is secure and not easily guessed.",
                    color = Muted, fontSize = 12.sp, textAlign = TextAlign.Center, fontFamily = Urbanist
                )

                if (state.error != null) {
                    Text(state.error!!, color = ErrorRed, fontSize = 12.sp, modifier = Modifier.padding(top = 10.dp), fontFamily = Urbanist, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(40.dp))

                // PIN Dots
                val currentInput = when (state.step) {
                    PinStep.ENTER_CURRENT -> state.currentPin
                    PinStep.ENTER_NEW -> state.newPin
                    PinStep.CONFIRM_NEW -> state.confirmPin
                    else -> ""
                }
                
                Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                    repeat(4) { index ->
                        val isFilled = index < currentInput.length
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .clip(CircleShape)
                                .background(if (isFilled) Teal else Card2)
                                .border(1.dp, if (isFilled) Teal else Border, CircleShape)
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // Custom Numpad
                VeltraNumpad(
                    onNumberClick = onNumberClick,
                    onDeleteClick = onDeleteClick
                )
                
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun ChangePinPreview() {
    VeltraTheme {
        ChangePinContent(
            state = ChangePinState(),
            onBackClick = {},
            onNumberClick = {},
            onDeleteClick = {}
        )
    }
}

@Composable
fun SuccessContent(onBackClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier.size(80.dp).background(SuccessGreen.copy(alpha = 0.1f), CircleShape).border(1.dp, SuccessGreen.copy(alpha = 0.3f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Check, contentDescription = null, tint = SuccessGreen, modifier = Modifier.size(40.dp))
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text("PIN Updated Successfully", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
        Text("Your security settings have been updated.", color = Muted, fontSize = 13.sp, textAlign = TextAlign.Center, modifier = Modifier.padding(top = 8.dp), fontFamily = Urbanist)
        
        Spacer(modifier = Modifier.height(48.dp))
        
        Button(
            onClick = onBackClick,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            contentPadding = PaddingValues(),
            shape = RoundedCornerShape(50.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize().background(PrimaryGradient), contentAlignment = Alignment.Center) {
                Text("Go Back", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
            }
        }
    }
}

@Composable
fun VeltraNumpad(onNumberClick: (String) -> Unit, onDeleteClick: () -> Unit) {
    val numbers = listOf(
        listOf("1", "2", "3"),
        listOf("4", "5", "6"),
        listOf("7", "8", "9"),
        listOf("", "0", "DEL")
    )

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        numbers.forEach { row ->
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                row.forEach { item ->
                    if (item.isEmpty()) {
                        Spacer(modifier = Modifier.size(64.dp))
                    } else {
                        IconButton(
                            onClick = { if (item == "DEL") onDeleteClick() else onNumberClick(item) },
                            modifier = Modifier.size(64.dp).background(Color.White.copy(alpha = 0.05f), CircleShape)
                        ) {
                            if (item == "DEL") {
                                Icon(Icons.Default.Backspace, contentDescription = "Delete", tint = Color.White, modifier = Modifier.size(20.dp))
                            } else {
                                Text(item, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                            }
                        }
                    }
                }
            }
        }
    }
}
