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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veltra.payment.ui.theme.*

@Composable
fun AnalyticsScreen(onBackClick: () -> Unit) {
    var selectedPeriod by remember { mutableStateOf("Month") }

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
                            .size(32.dp) // Matched size
                            .background(Color.White.copy(alpha = 0.08f), CircleShape)
                            .border(1.dp, Border, CircleShape)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White, modifier = Modifier.size(14.dp))
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Text("Smart Analytics", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
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
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Period chips - Exact Match
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                listOf("Week", "Month", "3 Months", "Year").forEach { period ->
                    PeriodChip(period, selectedPeriod == period, Modifier.weight(1f)) { selectedPeriod = it }
                }
            }

            // Spend Hero Card - Matched Spacing and Visuals
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Card, RoundedCornerShape(16.dp))
                    .border(1.dp, Border, RoundedCornerShape(16.dp))
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Total spent", color = Muted, fontSize = 11.sp, fontFamily = Urbanist, fontWeight = FontWeight.Medium)
                Text("₦124,500", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(vertical = 6.dp), fontFamily = Urbanist)
                Text("↓ 12% less than last month", color = Teal, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)

                Spacer(modifier = Modifier.height(20.dp))

                // Donut Chart - Pixel Perfect Replica from HTML SVG
                Box(
                    modifier = Modifier.size(150.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val strokeWidth = 18.dp.toPx()
                        
                        // Remaining/Background (30% gray)
                        drawCircle(color = Color.White.copy(alpha = 0.3f), radius = 60.dp.toPx(), style = Stroke(width = strokeWidth))
                        
                        // Main segments (Mocked dash offsets as per HTML)
                        drawArc(color = Royal, startAngle = -90f, sweepAngle = 115f, useCenter = false, style = Stroke(width = strokeWidth, cap = StrokeCap.Round))
                        drawArc(color = Teal, startAngle = 25f, sweepAngle = 78f, useCenter = false, style = Stroke(width = strokeWidth, cap = StrokeCap.Round))
                        drawArc(color = InfoPurple, startAngle = 103f, sweepAngle = 51f, useCenter = false, style = Stroke(width = strokeWidth, cap = StrokeCap.Round))
                        drawArc(color = WarningOrange, startAngle = 154f, sweepAngle = 58f, useCenter = false, style = Stroke(width = strokeWidth, cap = StrokeCap.Round))
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("5", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
                        Text("categories", color = Muted, fontSize = 9.sp, fontFamily = Urbanist, fontWeight = FontWeight.Medium)
                    }
                }
            }

            // Categories Card
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Card, RoundedCornerShape(16.dp))
                    .border(1.dp, Border, RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                CategoryRow("Bills & Utilities", "₦42,300", Royal, 0.34f)
                HorizontalDivider(color = Border, modifier = Modifier.padding(vertical = 10.dp))
                CategoryRow("Transfers", "₦28,900", Teal, 0.23f)
            }

            // Insight Card - Exact Gradient mixing
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AnalyticsGradient, RoundedCornerShape(16.dp))
                    .border(1.dp, Royal.copy(alpha = 0.25f), RoundedCornerShape(16.dp))
                    .padding(14.dp)
            ) {
                Text("💡 Smart Insight", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    "You spent 34% of your budget on Bills & Utilities — your highest category this month.",
                    color = Muted,
                    fontSize = 11.sp,
                    lineHeight = 15.sp,
                    fontFamily = Urbanist,
                    fontWeight = FontWeight.Medium
                )
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun PeriodChip(label: String, isActive: Boolean, modifier: Modifier = Modifier, onClick: (String) -> Unit) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(if (isActive) Royal else Card)
            .border(1.dp, if (isActive) Royal else Border, RoundedCornerShape(10.dp))
            .clickable { onClick(label) }
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(label, color = if (isActive) Color.White else Muted, fontSize = 9.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
    }
}

@Composable
fun CategoryRow(name: String, amount: String, color: Color, percentage: Float) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(modifier = Modifier.size(9.dp).background(color, CircleShape))
        Column(modifier = Modifier.weight(1f)) {
            Text(name, color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, fontFamily = Urbanist)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
                    .height(4.dp)
                    .background(Card2, RoundedCornerShape(4.dp))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(percentage)
                        .fillMaxHeight()
                        .background(color, RoundedCornerShape(4.dp))
                )
            }
        }
        Text(amount, color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
    }
}

@Preview
@Composable
fun AnalyticsPreview() {
    VeltraTheme {
        AnalyticsScreen({})
    }
}
