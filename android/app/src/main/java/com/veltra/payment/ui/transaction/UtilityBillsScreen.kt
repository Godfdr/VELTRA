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

enum class UtilityType { DATA, ELECTRICITY }

@Composable
fun UtilityBillsScreen(type: UtilityType, onBackClick: () -> Unit) {
    var amount by remember { mutableStateOf("") }
    var selectedProvider by remember { mutableStateOf("") }

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
                    Text(if (type == UtilityType.DATA) "Data Bundle" else "Electricity Bill", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).verticalScroll(rememberScrollState()).padding(horizontal = 16.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Choose provider", color = Muted, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
            
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                if (type == UtilityType.DATA) {
                    ProviderChip("MTN", Color(0xFFFFCC00), selectedProvider == "MTN", Modifier.weight(1f)) { selectedProvider = it }
                    ProviderChip("Airtel", Color(0xFFED1C24), selectedProvider == "Airtel", Modifier.weight(1f)) { selectedProvider = it }
                    ProviderChip("Glo", Color(0xFF00A651), selectedProvider == "Glo", Modifier.weight(1f)) { selectedProvider = it }
                } else {
                    ProviderChip("IKEDC", Color(0xFFE51A1A), selectedProvider == "IKEDC", Modifier.weight(1f)) { selectedProvider = it }
                    ProviderChip("EKEDC", Color(0xFF1A58E5), selectedProvider == "EKEDC", Modifier.weight(1f)) { selectedProvider = it }
                }
            }

            Text(if (type == UtilityType.DATA) "Phone number" else "Meter Number", color = Muted, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
            Row(
                modifier = Modifier.fillMaxWidth().background(Card, RoundedCornerShape(14.dp)).border(1.dp, Border, RoundedCornerShape(14.dp)).padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                androidx.compose.foundation.text.BasicTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = LocalTextStyle.current.copy(color = Color.White, fontSize = 14.sp, fontFamily = Urbanist),
                    cursorBrush = SolidColor(Teal),
                    decorationBox = { innerTextField ->
                        if (amount.isEmpty()) Text(if (type == UtilityType.DATA) "0801 234 5678" else "0123 456 7890", color = Muted, fontSize = 14.sp, fontFamily = Urbanist)
                        innerTextField()
                    }
                )
            }

            Spacer(modifier = Modifier.weight(1f))
            
            Button(
                onClick = { },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues(),
                shape = RoundedCornerShape(50.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize().background(PrimaryGradient), contentAlignment = Alignment.Center) {
                    Text("Continue", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                }
            }
        }
    }
}

@Composable
fun ProviderChip(name: String, color: Color, isSelected: Boolean, modifier: Modifier = Modifier, onClick: (String) -> Unit) {
    Box(
        modifier = modifier.clip(RoundedCornerShape(12.dp)).background(if (isSelected) color.copy(alpha = 0.15f) else Card).border(1.dp, if (isSelected) color else Border, RoundedCornerShape(12.dp)).clickable { onClick(name) }.padding(vertical = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(name, color = if (isSelected) Color.White else Muted, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
    }
}

@Preview(showBackground = true)
@Composable
fun UtilityBillsPreview() {
    VeltraTheme {
        UtilityBillsScreen(UtilityType.DATA, {})
    }
}
