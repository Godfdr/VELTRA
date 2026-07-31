package com.veltra.payment.ui.transaction

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veltra.payment.ui.SuccessDetailRow
import com.veltra.payment.ui.theme.*
import java.util.Locale

@Composable
fun ConversionSuccessScreen(
    onDoneClick: () -> Unit,
    onViewReceiptClick: () -> Unit = {},
    viewModel: ConversionViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val state by viewModel.state.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Base)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        0f to HeaderStart.copy(alpha = 0.4f),
                        0.5f to Base
                    )
                )
        )

        Scaffold(
            containerColor = Color.Transparent,
            bottomBar = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = onDoneClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .shadow(elevation = 24.dp, shape = RoundedCornerShape(50.dp), ambientColor = Royal.copy(alpha = 0.35f), spotColor = Royal.copy(alpha = 0.35f)),
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
                            Text("Done", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                        }
                    }
                    
                    OutlinedButton(
                        onClick = onViewReceiptClick,
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        border = BorderStroke(1.dp, Border),
                        shape = RoundedCornerShape(50.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                    ) {
                        Text("View Receipt", fontSize = 14.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
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
                Spacer(modifier = Modifier.height(80.dp))

                Box(contentAlignment = Alignment.Center) {
                    Box(
                        modifier = Modifier
                            .size(88.dp)
                            .clip(CircleShape)
                            .background(SuccessGradient)
                            .border(1.dp, Teal.copy(alpha = 0.3f), CircleShape)
                    )
                    Box(
                        modifier = Modifier
                            .size(58.dp)
                            .clip(CircleShape)
                            .background(PrimaryGradient)
                            .shadow(28.dp, CircleShape, spotColor = Teal),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(26.dp))
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))

                Text("Conversion Successful", color = Color.White, fontSize = 19.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Your funds have been converted\nand added to your ${state.toCurrency.code} wallet",
                    color = Muted,
                    fontSize = 12.sp,
                    lineHeight = 18.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = Urbanist,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    AmountBlock("${state.fromCurrency.symbol}${state.fromAmount}", "${state.fromCurrency.flag} ${state.fromCurrency.code}")
                    Spacer(modifier = Modifier.width(20.dp))
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = Teal, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(20.dp))
                    AmountBlock("${state.toCurrency.symbol}${state.toAmount.toPlainString()}", "${state.toCurrency.flag} ${state.toCurrency.code}")
                }

                Spacer(modifier = Modifier.height(40.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Card, RoundedCornerShape(14.dp))
                        .border(1.dp, Border, RoundedCornerShape(14.dp))
                ) {
                    SuccessDetailRow("Date", "Jul 26th 2026, 9:41PM")
                    HorizontalDivider(color = Border, modifier = Modifier.padding(horizontal = 16.dp))
                    SuccessDetailRow("Reference", "VLT-CX-88213")
                    HorizontalDivider(color = Border, modifier = Modifier.padding(horizontal = 16.dp))
                    SuccessDetailRow("Rate applied", "${state.toCurrency.symbol}${state.conversionRate.toPlainString()} / ${state.fromCurrency.symbol}1")
                    HorizontalDivider(color = Border, modifier = Modifier.padding(horizontal = 16.dp))
                    SuccessDetailRow("Fee", "${state.toCurrency.symbol}${state.fee.toPlainString()}")
                }
            }
        }
    }
}

@Composable
fun AmountBlock(amount: String, currency: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(amount, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
        Text(currency, color = Muted, fontSize = 10.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
    }
}
