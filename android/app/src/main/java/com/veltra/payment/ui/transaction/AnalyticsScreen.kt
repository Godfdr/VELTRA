package com.veltra.payment.ui.transaction

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veltra.payment.ui.theme.*
import java.math.BigDecimal
import java.util.Locale

@Composable
fun AnalyticsScreen(onBackClick: () -> Unit) {
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
                    Text("Analytics", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).verticalScroll(rememberScrollState()).padding(horizontal = 16.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Summary Card
            Card(
                modifier = Modifier.fillMaxWidth().border(1.dp, Border, RoundedCornerShape(24.dp)),
                colors = CardDefaults.cardColors(containerColor = Card),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Spent this month", color = Muted, fontSize = 12.sp, fontFamily = Urbanist)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("₦245,800.00", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                        StatSmall("Income", "₦450,000", SuccessGreen)
                        StatSmall("Savings", "₦80,000", Royal)
                    }
                }
            }

            Text("Category Breakdown", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                AnalyticsCategoryItem("Shopping", "45%", BigDecimal("110610"), Teal)
                AnalyticsCategoryItem("Food & Drinks", "25%", BigDecimal("61450"), Royal)
                AnalyticsCategoryItem("Transportation", "15%", BigDecimal("36870"), WarningOrange)
                AnalyticsCategoryItem("Bills", "10%", BigDecimal("24580"), InfoPurple)
                AnalyticsCategoryItem("Others", "5%", BigDecimal("12290"), Muted)
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun StatSmall(label: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, color = Muted, fontSize = 10.sp, fontFamily = Urbanist)
        Text(value, color = color, fontSize = 13.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
    }
}

@Composable
fun AnalyticsCategoryItem(name: String, percentage: String, amount: BigDecimal, color: Color) {
    Row(
        modifier = Modifier.fillMaxWidth().background(Card, RoundedCornerShape(16.dp)).border(1.dp, Border, RoundedCornerShape(16.dp)).padding(14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(color))
        Column(modifier = Modifier.weight(1f)) {
            Text(name, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
            Text(percentage, color = Muted, fontSize = 10.sp, fontFamily = Urbanist)
        }
        Text("₦${String.format(Locale.US, "%,.2f", amount.toDouble())}", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
    }
}

@Preview(showBackground = true)
@Composable
fun AnalyticsPreview() {
    VeltraTheme {
        AnalyticsScreen({})
    }
}
