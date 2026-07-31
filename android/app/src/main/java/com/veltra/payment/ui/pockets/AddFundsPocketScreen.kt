package com.veltra.payment.ui.pockets

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
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
import java.util.Locale

@Composable
fun AddFundsPocketScreen(
    onBackClick: () -> Unit, 
    onDoneClick: () -> Unit,
    viewModel: AddFundsViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val state by viewModel.state.collectAsState()

    AddFundsPocketContent(
        state = state,
        onBackClick = onBackClick,
        onDoneClick = onDoneClick,
        onAmountChange = { viewModel.updateAmount(it) },
        onAutoSaveToggle = { viewModel.toggleAutoSave(it) }
    )
}

@Composable
fun AddFundsPocketContent(
    state: AddFundsState,
    onBackClick: () -> Unit,
    onDoneClick: () -> Unit,
    onAmountChange: (String) -> Unit,
    onAutoSaveToggle: (Boolean) -> Unit
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
                    Text("Add Funds", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                }

                Column(modifier = Modifier.fillMaxWidth().padding(vertical = 24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("How much would you like to add?", color = Muted, fontSize = 11.sp, fontFamily = Urbanist, fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.horizontalScroll(rememberScrollState())) {
                        Text("₦", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
                        Spacer(modifier = Modifier.width(4.dp))
                        BasicTextField(
                            value = state.amount,
                            onValueChange = onAmountChange,
                            textStyle = LocalTextStyle.current.copy(
                                color = Color.White, 
                                fontSize = 38.sp, 
                                fontWeight = FontWeight.ExtraBold, 
                                fontFamily = Urbanist,
                                letterSpacing = (-1).sp
                            ),
                            cursorBrush = SolidColor(Teal),
                            singleLine = true,
                            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number)
                        )
                    }
                }
            }
        },
        bottomBar = {
            Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                val isButtonEnabled = state.numericAmount > BigDecimal.ZERO
                val buttonBrush = if (isButtonEnabled) PrimaryGradient else SolidColor(Sub)
                Button(
                    onClick = onDoneClick,
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues(),
                    shape = RoundedCornerShape(50.dp),
                    enabled = isButtonEnabled
                ) {
                    Box(modifier = Modifier.fillMaxSize().background(buttonBrush), contentAlignment = Alignment.Center) {
                        Text("Add ₦${String.format(Locale.US, "%,.0f", state.numericAmount.toDouble())}", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).verticalScroll(rememberScrollState()).padding(horizontal = 16.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().background(Card, RoundedCornerShape(16.dp)).border(1.dp, Border, RoundedCornerShape(16.dp)).padding(14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(modifier = Modifier.size(42.dp).clip(RoundedCornerShape(13.dp)).background(WarningOrange.copy(alpha = 0.15f)).border(1.dp, WarningOrange.copy(alpha = 0.25f), RoundedCornerShape(13.dp)), contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.FlightTakeoff, contentDescription = null, tint = WarningOrange, modifier = Modifier.size(20.dp))
                }
                Column {
                    Text("Bali Trip 2026", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                    Text("₦320,000 saved of ₦600,000", color = Muted, fontSize = 10.5.sp, fontFamily = Urbanist)
                }
            }

            Text("Fund from", color = Muted, fontSize = 11.sp, fontFamily = Urbanist, fontWeight = FontWeight.Bold)

            Row(
                modifier = Modifier.fillMaxWidth().background(Card, RoundedCornerShape(14.dp)).border(1.dp, Border, RoundedCornerShape(14.dp)).padding(14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(modifier = Modifier.size(34.dp).clip(RoundedCornerShape(10.dp)).background(Royal.copy(alpha = 0.15f)), contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.AccountBalanceWallet, contentDescription = null, tint = Royal, modifier = Modifier.size(16.dp))
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text("Main Wallet", color = Color.White, fontSize = 12.5.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                    Text("₦100,000.00 available", color = Muted, fontSize = 10.5.sp, fontFamily = Urbanist)
                }
                Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Muted, modifier = Modifier.size(14.dp))
            }

            Row(
                modifier = Modifier.fillMaxWidth().background(Card, RoundedCornerShape(14.dp)).border(1.dp, Border, RoundedCornerShape(14.dp)).padding(14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(modifier = Modifier.size(34.dp).clip(RoundedCornerShape(10.dp)).background(InfoPurple.copy(alpha = 0.15f)), contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.Schedule, contentDescription = null, tint = InfoPurple, modifier = Modifier.size(16.dp))
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text("Auto-save weekly", color = Color.White, fontSize = 12.5.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                    Text("Automatically add this amount every week", color = Muted, fontSize = 10.sp, fontFamily = Urbanist)
                }
                Switch(
                    checked = state.autoSaveEnabled, 
                    onCheckedChange = onAutoSaveToggle,
                    colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = InfoPurple)
                )
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddFundsPreview() {
    VeltraTheme {
        AddFundsPocketContent(
            state = AddFundsState(),
            onBackClick = {},
            onDoneClick = {},
            onAmountChange = {},
            onAutoSaveToggle = {}
        )
    }
}
