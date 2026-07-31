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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veltra.payment.ui.theme.*
import java.math.BigDecimal
import java.util.Locale

@Composable
fun AirtimeScreen(
    onBackClick: () -> Unit,
    viewModel: AirtimeViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val state by viewModel.state.collectAsState()

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
                    Text("Buy Airtime", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                }
            }
        },
        bottomBar = {
            Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                val buttonBrush = if (state.phoneNumber.length >= 10) PrimaryGradient else SolidColor(Sub)
                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues(),
                    shape = RoundedCornerShape(50.dp),
                    enabled = state.phoneNumber.length >= 10
                ) {
                    Box(modifier = Modifier.fillMaxSize().background(buttonBrush), contentAlignment = Alignment.Center) {
                        Text("Pay ₦${String.format(Locale.US, "%,.0f", state.selectedAmount.toDouble())}", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).verticalScroll(rememberScrollState()).padding(horizontal = 16.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Select provider", color = Muted, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ProviderChip(name = "MTN", color = Color(0xFFFFCC00), isSelected = state.selectedProvider == "MTN", modifier = Modifier.weight(1f)) { viewModel.updateProvider(it) }
                ProviderChip(name = "Airtel", color = Color(0xFFED1C24), isSelected = state.selectedProvider == "Airtel", modifier = Modifier.weight(1f)) { viewModel.updateProvider(it) }
                ProviderChip(name = "Glo", color = Color(0xFF00A651), isSelected = state.selectedProvider == "Glo", modifier = Modifier.weight(1f)) { viewModel.updateProvider(it) }
                ProviderChip(name = "9mobile", color = Color(0xFF00A99D), isSelected = state.selectedProvider == "9mobile", modifier = Modifier.weight(1f)) { viewModel.updateProvider(it) }
            }

            Text("Phone number", color = Muted, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
            
            Row(
                modifier = Modifier.fillMaxWidth().background(Card, RoundedCornerShape(14.dp)).border(1.dp, Border, RoundedCornerShape(14.dp)).padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(Icons.Default.Phone, contentDescription = null, tint = Muted, modifier = Modifier.size(18.dp))
                androidx.compose.foundation.text.BasicTextField(
                    value = state.phoneNumber,
                    onValueChange = { viewModel.updatePhoneNumber(it) },
                    modifier = Modifier.weight(1f),
                    textStyle = LocalTextStyle.current.copy(color = Color.White, fontSize = 14.sp, fontFamily = Urbanist),
                    cursorBrush = SolidColor(Teal),
                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Phone),
                    decorationBox = { innerTextField ->
                        if (state.phoneNumber.isEmpty()) Text("0801 234 5678", color = Muted, fontSize = 14.sp, fontFamily = Urbanist)
                        innerTextField()
                    }
                )
            }

            Text("Select amount", color = Muted, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
            
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    AmountChipItem("₦200", state.selectedAmount.compareTo(BigDecimal("200")) == 0, Modifier.weight(1f)) { viewModel.updateAmount(BigDecimal("200")) }
                    AmountChipItem("₦500", state.selectedAmount.compareTo(BigDecimal("500")) == 0, Modifier.weight(1f)) { viewModel.updateAmount(BigDecimal("500")) }
                    AmountChipItem("₦1,000", state.selectedAmount.compareTo(BigDecimal("1000")) == 0, Modifier.weight(1f)) { viewModel.updateAmount(BigDecimal("1000")) }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    AmountChipItem("₦2,000", state.selectedAmount.compareTo(BigDecimal("2000")) == 0, Modifier.weight(1f)) { viewModel.updateAmount(BigDecimal("2000")) }
                    AmountChipItem("₦5,000", state.selectedAmount.compareTo(BigDecimal("5000")) == 0, Modifier.weight(1f)) { viewModel.updateAmount(BigDecimal("5000")) }
                    AmountChipItem("Custom", false, Modifier.weight(1f)) { }
                }
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun AmountChipItem(label: String, isActive: Boolean, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier.clip(RoundedCornerShape(12.dp)).background(if (isActive) Royal else Card).border(1.dp, if (isActive) Royal else Border, RoundedCornerShape(12.dp)).clickable { onClick() }.padding(vertical = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(label, color = if (isActive) Color.White else Muted, fontSize = 12.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
    }
}
