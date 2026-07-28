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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veltra.payment.ui.theme.*

enum class UtilityType { DATA, ELECTRICITY }

@Composable
fun UtilityBillsScreen(type: UtilityType, onBackClick: () -> Unit) {
    var selectedProvider by remember { mutableStateOf(if (type == UtilityType.DATA) "MTN" else "EKEDC") }
    var selectedPlan by remember { mutableStateOf("2GB") }
    var selectedBilling by remember { mutableStateOf("Prepaid") }

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
                        .background(HeaderGradient)
                        .padding(horizontal = 20.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier
                            .size(32.dp)
                            .background(Color.White.copy(alpha = 0.08f), CircleShape)
                            .border(1.dp, Border, CircleShape)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White, modifier = Modifier.size(14.dp))
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        if (type == UtilityType.DATA) "Data" else "Electricity", 
                        color = Color.White, 
                        fontSize = 15.sp, 
                        fontWeight = FontWeight.Bold, 
                        fontFamily = Urbanist
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (type == UtilityType.DATA) {
                // --- DATA LAYOUT ---
                Text("Network provider", color = Muted, fontSize = 11.sp, fontFamily = Urbanist, fontWeight = FontWeight.Medium)
                
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ProviderChip("MTN", Color(0xFFFFCC00), selectedProvider == "MTN", Modifier.weight(1f)) { selectedProvider = it }
                    ProviderChip("Airtel", Color(0xFFED1C24), selectedProvider == "Airtel", Modifier.weight(1f)) { selectedProvider = it }
                    ProviderChip("Glo", Color(0xFF00A651), selectedProvider == "Glo", Modifier.weight(1f)) { selectedProvider = it }
                    ProviderChip("9mobile", Color(0xFF00A99D), selectedProvider == "9mobile", Modifier.weight(1f)) { selectedProvider = it }
                }

                Text("Select a plan", color = Muted, fontSize = 11.sp, fontFamily = Urbanist, fontWeight = FontWeight.Medium)

                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        DataPlanCard("1GB", "1 day", "₦300", selectedPlan == "1GB", Modifier.weight(1f)) { selectedPlan = "1GB" }
                        DataPlanCard("2GB", "7 days", "₦700", selectedPlan == "2GB", Modifier.weight(1f)) { selectedPlan = "2GB" }
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        DataPlanCard("5GB", "30 days", "₦1,500", selectedPlan == "5GB", Modifier.weight(1f)) { selectedPlan = "5GB" }
                        DataPlanCard("10GB", "30 days", "₦2,500", selectedPlan == "10GB", Modifier.weight(1f)) { selectedPlan = "10GB" }
                    }
                }
            } else {
                // --- ELECTRICITY LAYOUT ---
                Text("Distribution company", color = Muted, fontSize = 11.sp, fontFamily = Urbanist, fontWeight = FontWeight.Medium)
                
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Card, RoundedCornerShape(16.dp))
                        .border(1.dp, Border, RoundedCornerShape(16.dp))
                ) {
                    DistributionRow("EKEDC", selectedProvider == "EKEDC") { selectedProvider = "EKEDC" }
                    HorizontalDivider(color = Border, modifier = Modifier.padding(horizontal = 16.dp))
                    DistributionRow("IKEDC", selectedProvider == "IKEDC") { selectedProvider = "IKEDC" }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Card, RoundedCornerShape(14.dp))
                        .border(1.dp, Border, RoundedCornerShape(14.dp))
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    UtilityToggleTab("Prepaid", selectedBilling == "Prepaid", Modifier.weight(1f)) { selectedBilling = it }
                    UtilityToggleTab("Postpaid", selectedBilling == "Postpaid", Modifier.weight(1f)) { selectedBilling = it }
                }

                Text("Enter amount", color = Muted, fontSize = 11.sp, fontFamily = Urbanist, fontWeight = FontWeight.Medium)
                
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Card, RoundedCornerShape(14.dp))
                        .border(1.dp, Border, RoundedCornerShape(14.dp))
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("₦", color = Muted, fontSize = 18.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                    Text("20,000.00", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Teal.copy(alpha = 0.1f), RoundedCornerShape(12.dp))
                        .border(1.dp, Teal.copy(alpha = 0.2f), RoundedCornerShape(12.dp))
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(Icons.Default.Info, contentDescription = null, tint = Teal, modifier = Modifier.size(16.dp))
                    Text(
                        "You'll receive your token via SMS and in-app notification after payment.",
                        color = Teal,
                        fontSize = 11.sp,
                        lineHeight = 16.sp,
                        fontFamily = Urbanist,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            
            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(bottom = 10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues(),
                shape = RoundedCornerShape(50.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(PrimaryGradient),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Continue", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                }
            }
        }
    }
}

@Composable
fun ProviderChip(name: String, color: Color, isSelected: Boolean, modifier: Modifier = Modifier, onClick: (String) -> Unit) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(if (isSelected) Royal.copy(alpha = 0.15f) else Card)
            .border(1.dp, if (isSelected) Royal.copy(alpha = 0.4f) else Border, RoundedCornerShape(12.dp))
            .clickable { onClick(name) }
            .padding(vertical = 12.dp, horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.size(22.dp).clip(CircleShape).background(color))
        Spacer(modifier = Modifier.height(6.dp))
        Text(name, color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
    }
}

@Composable
fun DataPlanCard(size: String, validity: String, price: String, isSelected: Boolean, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(if (isSelected) Royal.copy(alpha = 0.12f) else Card)
            .border(1.dp, if (isSelected) Royal.copy(alpha = 0.4f) else Border, RoundedCornerShape(14.dp))
            .clickable { onClick() }
            .padding(12.dp)
    ) {
        Text(size, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
        Text(validity, color = Muted, fontSize = 10.sp, fontFamily = Urbanist, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(6.dp))
        Text(price, color = Teal, fontSize = 12.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
    }
}

@Composable
fun DistributionRow(name: String, isSelected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(name, color = if (isSelected) Color.White else Muted, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, fontFamily = Urbanist)
        Box(
            modifier = Modifier
                .size(16.dp)
                .clip(CircleShape)
                .border(1.5.dp, if (isSelected) Royal else Border, CircleShape)
                .background(if (isSelected) Royal else Color.Transparent)
        )
    }
}

@Composable
fun UtilityToggleTab(label: String, isActive: Boolean, modifier: Modifier = Modifier, onClick: (String) -> Unit) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(if (isActive) Royal else Color.Transparent)
            .clickable { onClick(label) }
            .padding(vertical = 9.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(label, color = if (isActive) Color.White else Muted, fontSize = 12.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
    }
}

@Preview
@Composable
fun DataUtilityPreview() {
    VeltraTheme {
        UtilityBillsScreen(UtilityType.DATA, {})
    }
}

@Preview
@Composable
fun ElectricityUtilityPreview() {
    VeltraTheme {
        UtilityBillsScreen(UtilityType.ELECTRICITY, {})
    }
}
