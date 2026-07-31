package com.veltra.payment.ui.transaction

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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veltra.payment.ui.*
import com.veltra.payment.ui.theme.*
import java.util.Locale

@Composable
fun ConvertScreen(
    onBackClick: () -> Unit,
    onSelectCurrency: (Boolean) -> Unit = {},
    onConvertSuccess: () -> Unit = {},
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
                .fillMaxWidth()
                .height(350.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF131F4F).copy(alpha = 0.8f),
                            Color(0xFF0D1530).copy(alpha = 0.4f),
                            Base
                        )
                    )
                )
        )

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 20.dp)
                        .statusBarsPadding(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.08f))
                            .border(1.dp, Border, CircleShape)
                            .clickable { onBackClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White, modifier = Modifier.size(16.dp))
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Text("Convert", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                    Spacer(modifier = Modifier.weight(1.2f))
                }
            },
            bottomBar = {
                VeltraFooter(
                    activeRoute = "convert",
                    onDashboardClick = { }, 
                    onPocketsClick = { },
                    onProfileClick = { }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp)
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                Text("Add amount and select currency", color = Muted, fontSize = 13.sp, fontFamily = Urbanist)
                Spacer(modifier = Modifier.height(20.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Card, RoundedCornerShape(24.dp))
                        .border(1.dp, Border, RoundedCornerShape(24.dp))
                        .padding(20.dp)
                ) {
                    Text("From", color = Muted, fontSize = 12.sp, fontFamily = Urbanist)
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f).horizontalScroll(rememberScrollState())) {
                            Text(state.fromCurrency.symbol, color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, fontFamily = Urbanist)
                            Spacer(modifier = Modifier.width(4.dp))
                            BasicTextField(
                                value = state.fromAmount,
                                onValueChange = { viewModel.updateAmount(it) },
                                textStyle = LocalTextStyle.current.copy(
                                    color = Color.White, 
                                    fontSize = 26.sp, 
                                    fontWeight = FontWeight.ExtraBold, 
                                    fontFamily = Urbanist
                                ),
                                singleLine = true,
                                cursorBrush = SolidColor(Teal),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        CurrencyChipInteractive(flag = state.fromCurrency.flag, code = state.fromCurrency.code, onClick = { onSelectCurrency(true) })
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .background(
                                    Brush.radialGradient(
                                        colors = listOf(Royal.copy(alpha = 0.4f), Color.Transparent)
                                    )
                                )
                        )
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .shadow(8.dp, CircleShape, spotColor = Royal)
                                .clip(CircleShape)
                                .background(Royal)
                                .border(2.dp, Card, CircleShape)
                                .clickable { viewModel.swapCurrencies() },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.SyncAlt, contentDescription = "Swap", tint = Color.White, modifier = Modifier.size(20.dp))
                        }
                    }

                    Text("Amount you will receive", color = Muted, fontSize = 12.sp, fontFamily = Urbanist)
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "${state.toCurrency.symbol}${String.format(Locale.US, "%,.2f", state.toAmount.toDouble())}", 
                            color = Muted, 
                            fontSize = 26.sp, 
                            fontWeight = FontWeight.ExtraBold, 
                            fontFamily = Urbanist,
                            modifier = Modifier.weight(1f).horizontalScroll(rememberScrollState())
                        )
                        CurrencyChipInteractive(flag = state.toCurrency.flag, code = state.toCurrency.code, onClick = { onSelectCurrency(false) })
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))
                
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Current Balance (${state.fromCurrency.code})", color = Color.White, fontSize = 14.sp, fontFamily = Urbanist)
                    Text("${state.fromCurrency.symbol}100,000.00", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, fontFamily = Urbanist)
                }

                Spacer(modifier = Modifier.height(12.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Card2, RoundedCornerShape(20.dp))
                        .border(1.dp, Border, RoundedCornerShape(20.dp))
                ) {
                    DetailRowInteractive(label = "Conversion fee", value = "${state.toCurrency.symbol}${String.format(Locale.US, "%,.2f", state.fee.toDouble())}")
                    HorizontalDivider(color = Border, modifier = Modifier.padding(horizontal = 16.dp))
                    DetailRowInteractive(label = "Amount converted", value = "${state.toCurrency.symbol}${String.format(Locale.US, "%,.2f", state.totalToAmount.toDouble())}")
                    HorizontalDivider(color = Border, modifier = Modifier.padding(horizontal = 16.dp))
                    DetailRowInteractive(label = "Today's rate", value = "${state.toCurrency.symbol}1 = ${state.fromCurrency.symbol}${String.format(Locale.US, "%.6f", state.conversionRate.toDouble())}")
                }

                Spacer(modifier = Modifier.height(48.dp))

                Button(
                    onClick = onConvertSuccess,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .shadow(
                            elevation = 24.dp,
                            shape = RoundedCornerShape(50.dp),
                            ambientColor = Ultra.copy(alpha = 0.5f),
                            spotColor = Ultra.copy(alpha = 0.5f)
                        ),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues(),
                    shape = RoundedCornerShape(50.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(Ultra.copy(alpha = 0.7f), Teal.copy(alpha = 0.7f))
                                )
                            )
                            .border(1.dp, Color.White.copy(alpha = 0.25f), RoundedCornerShape(50.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Convert", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = Urbanist)
                    }
                }
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConvertPreview() {
    VeltraTheme {
        ConvertScreen({})
    }
}
